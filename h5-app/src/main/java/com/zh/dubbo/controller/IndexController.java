package com.zh.dubbo.controller;

import com.zh.dubbo.utils.JedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.Jedis;

/**
 * Created by Administrator on 2017/6/21.
 */
@RestController
public class IndexController {
    @GetMapping("/test")
    public Long test(){
        try {
            return JedisUtil.genUniqIdByRedis("test01");
        }catch (Exception e){
            return 1l;
        }
    }
}
