import com.aliyun.vod.upload.impl.UploadVideoImpl;
import com.aliyun.vod.upload.req.UploadVideoRequest;
import com.aliyun.vod.upload.resp.UploadVideoResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.vod.model.v20170321.GetPlayInfoRequest;
import com.aliyuncs.vod.model.v20170321.GetPlayInfoResponse;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthRequest;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthResponse;

import java.util.List;

/**
 * 以下Java示例代码演示了如何在服务端上传媒资文件至视频点播，媒资类型支持音频、视频和图片。
 * <p>
 * 一、音视频上传目前支持4种方式上传：
 * <p>
 * 1.上传本地文件，使用分片上传，并支持断点续传，参见testUploadVideo函数。
 * 1.1 当断点续传关闭时，最大支持上传任务执行时间为3000秒，具体可上传文件大小与您的网络带宽及磁盘读写能力有关。
 * 1.2 当断点续传开启时，最大支持48.8TB的单个文件，注意，断点续传开启后，上传任务执行过程中，同时会将当前上传位置写入本地磁盘文件，影响您上传文件的速度，请您根据文件大小选择是否开启
 * <p>
 * 2.上传网络流，可指定文件URL进行上传，支持断点续传，最大支持48.8TB的单个文件。
 * 该上传方式需要先将网络文件下载到本地磁盘，再进行上传，所以要保证本地磁盘有充足的空间。参见testUploadURLStream函数。
 * <p>
 * 3.上传文件流，可指定本地文件进行上传，不支持断点续传，最大支持5GB的单个文件。参见testUploadFileStream函数。
 * <p>
 * 4.流式上传，可指定输入流进行上传，支持文件流和网络流等，不支持断点续传，最大支持5GB的单个文件。参见testUploadStream函数。
 * <p>
 * <p>
 * 二、图片上传目前支持2种方式上传：
 * 1.上传本地文件，不支持断点续传，最大支持5GB的单个文件，参见testUploadImageLocalFile函数
 * 2.上传文件流和网络流，InputStream参数必选，不支持断点续传，最大支持5GB的单个文件。参见testUploadImageStream函数。
 * 注：图片上传完成后，会返回图片ID和图片地址，也可通过GetImageInfo查询图片信息，参见接口文档 https://help.aliyun.com/document_detail/89742.html
 * <p>
 * <p>
 * 三、m3u8文件上传目前支持2种方式：
 * 1.上传本地m3u8音视频文件（包括所有分片文件）到点播，需指定本地m3u8索引文件地址和所有分片地址。
 * 2.上传网络m3u8音视频文件（包括所有分片文件）到点播，需指定m3u8索引文件和分片文件的URL地址。
 * <p>
 * 注：
 * 1) 上传网络m3u8音视频文件时需要保证地址可访问，如果有权限限制，请设置带签名信息的地址，且保证足够长的有效期，防止地址无法访问导致上传失败
 * 2) m3u8文件上传暂不支持进度回调
 * <p>
 * <p>
 * 四、上传进度回调通知：
 * 1.默认上传进度回调函数：视频点播上传SDK内部默认开启上传进度回调函数，输出不同事件通知的日志，您可以设置关闭该上传进度通知及日志输出；
 * 2.自定义上传进度回调函数：您可根据自己的业务场景重新定义不同事件处理的方式，只需要修改上传回调示例函数即可。
 * <p>
 * <p>
 * 五、辅助媒资上传目前支持2种方式：
 * 1.上传本地文件，不支持断点续传，最大支持5GB的单个文件，参见testUploadAttachedMediaLocalFile函数
 * 2.上传文件流和网络流，InputStream参数必选，不支持断点续传，最大支持5GB的单个文件。参见testUploadAttachedMediaStream函数。
 * <p>
 * <p>
 * 六、支持STS方式上传：
 * 1.您需要实现VoDRefreshSTSTokenListener接口的onRefreshSTSToken方法，用于生成STS信息，
 * 当文件上传时间超过STS过期时间时，SDK内部会定期调用此方法刷新您的STS信息进行后续文件的上传。
 * <p>
 * <p>
 * 七、可指定上传脚本部署的ECS区域(设置Request的EcsRegionId参数，取值参考存储区域标识：https://help.aliyun.com/document_detail/98194.html)，
 * 如果与点播存储（OSS）区域相同，则自动使用内网上传文件至存储，上传更快且更省公网流量
 * 由于点播API只提供外网域名访问，因此部署上传脚本的ECS服务器必须具有访问外网的权限。
 * <p>
 * 注意：
 * 请替换示例中的必选参数，示例中的可选参数如果您不需要设置，请将其删除，以免设置无效参数值与您的预期不符。
 */

public class TestVod2 {
    //账号AK信息请填写(必选)
    private static final String accessKeyId = "LTAI5t9CSNB3D9Lw4siUzKkp";
    //账号AK信息请填写(必选)
    private static final String accessKeySecret = "lbNDnEZ2KBieR1qseh8FVT3Py9QzBF";

    public static void main(String[] args) throws Exception {

        String title = "6 - What If I Want to Move Faster";   //上传之后文件名称
        String fileName = "/Users/pyz/Desktop/谷粒学苑资料/工具/1-阿里云上传测试视频//6 - What If I Want to Move Faster.mp4";  //本地文件路径和名称
        //上传视频的方法
        UploadVideoRequest request = new UploadVideoRequest(accessKeyId, accessKeySecret, title, fileName);
        /* 可指定分片上传时每个分片的大小，默认为2M字节 */
        request.setPartSize(2 * 1024 * 1024L);
        /* 可指定分片上传时的并发线程数，默认为1，(注：该配置会占用服务器CPU资源，需根据服务器情况指定）*/
        request.setTaskNum(1);
        request.setApiRegionId("cn-beijing");
        request.setStorageLocation("outin-d443b6cd474b11edaebe00163e0eb78b.oss-cn-beijing.aliyuncs.com");


        UploadVideoImpl uploader = new UploadVideoImpl();
        UploadVideoResponse response = uploader.uploadVideo(request);

        if (response.isSuccess()) {
            System.out.print("VideoId=" + response.getVideoId() + "\n");
        } else {
            /* 如果设置回调URL无效，不影响视频上传，可以返回VideoId同时会返回错误码。其他情况上传失败时，VideoId为空，此时需要根据返回错误码分析具体错误原因 */
            System.out.print("VideoId=" + response.getVideoId() + "\n");
            System.out.print("ErrorCode=" + response.getCode() + "\n");
            System.out.print("ErrorMessage=" + response.getMessage() + "\n");
        }
//        getPlayUrl();
    }

    //1 根据视频iD获取视频播放凭证
    public static void getPlayAuth() throws Exception {

        DefaultAcsClient client = InitObject.initVodClient(accessKeyId, accessKeySecret);

        GetVideoPlayAuthRequest request = new GetVideoPlayAuthRequest();
        GetVideoPlayAuthResponse response = new GetVideoPlayAuthResponse();

        request.setVideoId("474be24d43ad4f76af344b9f4daaabd1");

        response = client.getAcsResponse(request);
        System.out.println("playAuth:" + response.getPlayAuth());
    }

    //1 根据视频iD获取视频播放地址
    public static void getPlayUrl() throws Exception {
        //创建初始化对象
        DefaultAcsClient client = InitObject.initVodClient(accessKeyId, accessKeySecret);

        //创建获取视频地址request和response
        GetPlayInfoRequest request = new GetPlayInfoRequest();
        GetPlayInfoResponse response = new GetPlayInfoResponse();

        //向request对象里面设置视频id
        request.setVideoId("54aed61a083543a69cbabbbb39c52c1d");

        //调用初始化对象里面的方法，传递request，获取数据

        response = client.getAcsResponse(request);

        List<GetPlayInfoResponse.PlayInfo> playInfoList = response.getPlayInfoList();
        //播放地址
        for (GetPlayInfoResponse.PlayInfo playInfo : playInfoList) {
            System.out.print("PlayInfo.PlayURL = " + playInfo.getPlayURL() + "\n");
        }
        //Base信息
        System.out.print("VideoBase.Title = " + response.getVideoBase().getTitle() + "\n");
    }
}
