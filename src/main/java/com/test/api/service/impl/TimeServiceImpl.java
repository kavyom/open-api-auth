package com.test.api.service.impl;

import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import com.test.api.service.TimeService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * Created by pzh on 2022/9/7.
 */
@Service
public class TimeServiceImpl implements TimeService {

    @Override
    public String currentTime() {
        return DateUtil.format(new Date(), DatePattern.NORM_DATETIME_PATTERN);
    }
}
