package com.puge.msmservice.service;

import java.util.Map;

/**
 * @author pyz
 */
public interface MsmService {
    /**
     * 阿里云发送短信的方法
     * @param param
     * @param phone
     * @return
     */
    boolean sendALY(Map<String, Object> param, String phone);

    /**
     * 腾讯云发送短信的方法
     * @param phone
     * @param code
     * @return
     */
    boolean sendTXY(String phone,String code,String time);
}
