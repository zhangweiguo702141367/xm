package com.zh.dubbo.controller;

import com.zh.dubbo.constant.RspConstants;
import com.zh.dubbo.entity.RespData;
import com.zh.dubbo.exception.ProcException;
import com.zh.dubbo.fo.WeatherServiceFo;
import com.zh.dubbo.untils.DateUtil;
import com.zh.dubbo.untils.IPUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * Created by Administrator on 2017/6/15.
 */
@RestController
@RequestMapping("/inner/member/")
public class WeatherController {
    @Autowired
    WeatherServiceFo weatherServiceFo;
    @PostMapping("getWeather")
    public RespData getWeather(HttpServletRequest request){
        try{
            String member_id = request.getAttribute("userId").toString();
            String ip = IPUtil.getIpAddr(request);
            Map<String,Object> weatherRsp = weatherServiceFo.getWeatherInfoByIp(member_id,ip);
            return RespData.create(RspConstants.SUCCESS,"获取天气信息成功",weatherRsp, DateUtil.getCurrentTime());
        }catch (ProcException proc){
            return RespData.create(RspConstants.SERVICEERROR,proc.getMessage(),null,DateUtil.getCurrentTime());
        }catch (Exception e){
            return RespData.create(RspConstants.OTHERERROR,"操作异常，请您重试",null,DateUtil.getCurrentTime());
        }
    }
}
