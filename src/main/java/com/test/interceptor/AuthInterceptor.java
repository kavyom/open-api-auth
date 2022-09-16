package com.test.interceptor;

import com.google.gson.Gson;
import com.test.api.mapper.OpenApiConfigMapper;
import com.test.api.model.OpenApiConfig;
import com.test.common.BaseResult;
import com.test.util.MD5Util;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by pzh on 2022/7/28.
 */
@Slf4j
public class AuthInterceptor implements HandlerInterceptor {

    @Autowired
    private OpenApiConfigMapper openApiConfigMapper;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object o) throws Exception {
        String appId = request.getParameter("appId");
        if (StringUtils.isEmpty(appId)) {
            responseError(response, 20001, "appId not found");
            return false;
        }
        String timestamp = request.getHeader("timestamp");
        if (StringUtils.isEmpty(timestamp)) {
            responseError(response, 20002, "timestamp not found");
            return false;
        }
        // sign过期校验
        if ((Math.abs(System.currentTimeMillis() - Long.valueOf(timestamp)) / 1000 / 60) > 120) {
            responseError(response, 20003, "sign expired");
            return false;
        }

        // 将参数按顺序加入列表
        List<String> paramList = new ArrayList<>();
        paramList.add(appId);
        paramList.add(timestamp);

        // 根据appId获取配置的appSecret
        String appSecret = null;
        OpenApiConfig openApiConfig = openApiConfigMapper.getByAppId(appId);
        if (openApiConfig != null) {
            appSecret = openApiConfig.getAppSecret();
        }
        paramList.add(appSecret);

        // 将参数字符串拼接成一个字符串进行MD5
        StringBuilder builder = new StringBuilder();
        for (String str : paramList) {
            builder.append(str);
        }
        String md5 = MD5Util.getMD5(builder.toString());
        // sign判断是否为空
        String sign = request.getHeader("sign");
        if (StringUtils.isEmpty(sign)) {
            responseError(response, 20004, "sign not found");
            return false;
        }

        // 与带入的sign比较是否相等
        if (!StringUtils.equals(sign, md5)) {
            responseError(response, 20005, "invalid sign");
            return false;
        }

        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }

    private void responseError(ServletResponse response, int code, String message) {
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        String json = new Gson().toJson(BaseResult.error(code, message));
        try {
            httpResponse.getWriter().print(json);
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
    }
}
