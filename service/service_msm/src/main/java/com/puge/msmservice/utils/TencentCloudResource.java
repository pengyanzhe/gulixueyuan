package com.puge.msmservice.utils;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * 外建基础参数实体类
 * @author pyz
 */
@Component
public class TencentCloudResource implements InitializingBean {
    @Value("${tencent.cloud.secretId}")
    private String secretId;

    @Value("${tencent.cloud.secretKey}")
    private String secretKey;

    public static String KEY_ID;
    public static String KEY_SECRET;
    @Override
    public void afterPropertiesSet() throws Exception {
        KEY_ID = secretId;
        KEY_SECRET = secretKey;
    }
}
