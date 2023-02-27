package com.puge.msmservice.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.puge.msmservice.service.MsmService;
import com.puge.msmservice.utils.TencentCloudResource;
import com.tencentcloudapi.common.Credential;
import com.tencentcloudapi.common.exception.TencentCloudSDKException;
import com.tencentcloudapi.common.profile.ClientProfile;
import com.tencentcloudapi.common.profile.HttpProfile;
import com.tencentcloudapi.sms.v20210111.SmsClient;
import com.tencentcloudapi.sms.v20210111.models.SendSmsRequest;
import com.tencentcloudapi.sms.v20210111.models.SendSmsResponse;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Map;

/**
 * @author pyz
 */
@Service
public class MsmServiceImpl implements MsmService {

    /**
     * 发送短信的方法
     * @param param
     * @param phone
     * @return
     *
     * 没有阿里云支持，未实现
     */
    @Override
    public boolean sendALY(Map<String, Object> param, String phone) {
        if (StringUtils.isEmpty(phone)) {
            return false;
        }

        DefaultProfile profile =
                DefaultProfile.getProfile("default", "LTAI4FvvVEWiTJ3GNJJqJnk7", "9st82dv7EvFk9mTjYO1XXbM632fRbG");
        IAcsClient client = new DefaultAcsClient(profile);

        //设置相关固定的参数
        CommonRequest request = new CommonRequest();
        //request.setProtocol(ProtocolType.HTTPS);
        request.setMethod(MethodType.POST);
        request.setDomain("dysmsapi.aliyuncs.com");
        request.setVersion("2017-05-25");
        request.setAction("SendSms");

        //设置发送相关的参数
        //手机号
        request.putQueryParameter("PhoneNumbers",phone);
        //申请阿里云 签名名称
        request.putQueryParameter("SignName","我的谷粒在线教育网站");
        //申请阿里云 模板code
        request.putQueryParameter("TemplateCode","SMS_180051135");
        //验证码数据，转换json数据传递
        request.putQueryParameter("TemplateParam", JSONObject.toJSONString(param));

        try {
            //最终发送
            CommonResponse response = client.getCommonResponse(request);
            boolean success = response.getHttpResponse().isSuccess();
            return success;
        }catch(Exception e) {
            e.printStackTrace();
            return false;
        }

    }

    @Override
    public boolean sendTXY(String phone, String code,String time) {
        String keyId = TencentCloudResource.KEY_ID;
        String keySecret = TencentCloudResource.KEY_SECRET;
        try {
            /* 必要步骤：
             * 实例化一个认证对象，入参需要传入腾讯云账户密钥对secretId，secretKey。
             * 这里采用的是从环境变量读取的方式，需要在环境变量中先设置这两个值。
             * 你也可以直接在代码中写死密钥对，但是小心不要将代码复制、上传或者分享给他人，
             * 以免泄露密钥对危及你的财产安全。
             * CAM密匙查询获取: https://console.cloud.tencent.com/cam/capi*/
            Credential cred = new Credential(keyId, keySecret);

            // 实例化一个http选项，可选的，没有特殊需求可以跳过
            HttpProfile httpProfile = new HttpProfile();

            // httpProfile.setReqMethod("POST"); // 默认使用POST

            /* SDK会自动指定域名。通常是不需要特地指定域名的，但是如果你访问的是金融区的服务
             * 则必须手动指定域名，例如sms的上海金融区域名： sms.ap-shanghai-fsi.tencentcloudapi.com */
            httpProfile.setEndpoint("sms.tencentcloudapi.com");

            // 实例化一个client选项
            ClientProfile clientProfile = new ClientProfile();
            clientProfile.setHttpProfile(httpProfile);
            // 实例化要请求产品的client对象,clientProfile是可选的
            SmsClient client = new SmsClient(cred, "ap-nanjing", clientProfile);

            // 实例化一个请求对象,每个接口都会对应一个request对象
            SendSmsRequest req = new SendSmsRequest();
            //电话号码
            String[] phoneNumberSet1 = {"+86" + phone};
            req.setPhoneNumberSet(phoneNumberSet1);
            // 短信应用ID: 短信SdkAppId在 [短信控制台] 添加应用后生成的实际SdkAppId
            req.setSmsSdkAppId("1400754317");
            // 签名
            req.setSignName("Code学习个人公众号");
            // 模板id：必须填写已审核通过的模板 ID。模板ID可登录 [短信控制台] 查看
            req.setTemplateId("1603652");

            /* 模板参数（自定义占位变量）: 若无模板参数，则设置为空 */
            String[] templateParamSet1 = {code,time};
            req.setTemplateParamSet(templateParamSet1);

            // 返回的resp是一个SendSmsResponse的实例，与请求对象对应
            SendSmsResponse resp = client.SendSms(req);

            return true;
            // 输出json格式的字符串回包
//            System.out.println(SendSmsResponse.toJsonString(resp));
        } catch (TencentCloudSDKException e) {
            System.out.println(e.toString());
            return false;
        }
    }

}
