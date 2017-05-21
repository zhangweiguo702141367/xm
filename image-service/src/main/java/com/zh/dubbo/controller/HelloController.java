package com.zh.dubbo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
/**
 * Created by 70214 on 2017/5/20.
 */
@RestController
public class HelloController {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @GetMapping(value = "hello")
    public String hello(){
        logger.info("打印日志info");
        logger.debug("打印日志debug");
        logger.error("打印日志error");
        return "logger";

    }
}
