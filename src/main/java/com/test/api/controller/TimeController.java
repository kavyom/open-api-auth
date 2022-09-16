package com.test.api.controller;

import com.test.api.service.TimeService;
import com.test.common.BaseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by pzh on 2022/9/7.
 */
@RestController
@RequestMapping("/time")
public class TimeController {

    @Autowired
    private TimeService timeService;

    @GetMapping("/current")
    public BaseResult current() {
        String current = timeService.currentTime();
        return BaseResult.success(current);
    }
}
