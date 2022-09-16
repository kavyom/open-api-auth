package com.test.util;

import lombok.extern.slf4j.Slf4j;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by pzh on 2022/8/2.
 */
@Slf4j
public class MD5Util {

    public static String getMD5(String sha){
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.reset();
            md.update(sha.getBytes());
            byte[] result = md.digest();
            StringBuffer sb = new StringBuffer();
            int len = result.length;
            for (int i = 0; i < len; i++) {
                int v = result[i] & 0XFF;
                if(v < 16)
                    sb.append("0");
                sb.append(Integer.toString(v, 16).toLowerCase());
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }

    public static void main(String[] args) {
        String appId = "zhangsan";
        String appSecret = "r5M1WaZ5vdN5wWGf6L";
        String timestamp = String.valueOf(System.currentTimeMillis());

        String str = appId + timestamp + appSecret;
        String sign = getMD5(str);
        log.info("加密后的sign：{}，timestamp：{}", sign, timestamp);
    }
}
