package com.zh.dubbo.controller;

import com.zh.dubbo.utils.MessageUtil;
import com.zh.dubbo.utils.SignUtil;
import com.zh.dubbo.utils.dispatcher.EventDispatcher;
import com.zh.dubbo.utils.dispatcher.MsgDispatcher;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.Map;

/**
 * Created by 70214 on 2017/5/18.
 */
@RestController
public class WechatController {
    @GetMapping(value = "hello")
    public String hello(){
        return "hello";
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public void index(@RequestParam Map<String,Object> paramMap, HttpServletRequest request, HttpServletResponse response){

        System.out.println("收到请求，请求数据为："+paramMap.toString());

        String signature = paramMap.get("signature").toString();
        String timestamp = paramMap.get("timestamp").toString();
        String nonce = paramMap.get("nonce").toString();
        String echostr = paramMap.get("echostr").toString();

        try {
            if (SignUtil.checkSignature(signature, timestamp, nonce)) {
                PrintWriter out = response.getWriter();
                out.print(echostr);
                out.close();
            } else {
                System.out.println("这里存在非法请求！");
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }


    @RequestMapping(value = "/", method = RequestMethod.POST)
    public String DoPost(HttpServletRequest request,HttpServletResponse response) {
        String result = "";
        try{
            Map<String, String> map= MessageUtil.parseXml(request);
//            logger.info("收到微信消息，请求内容："+map.toString());
            String msgtype=map.get("MsgType");
            if(MessageUtil.REQ_MESSAGE_TYPE_EVENT.equals(msgtype)){
                result = EventDispatcher.processEvent(map); //进入事件处理
            }else{
                result = MsgDispatcher.processMessage(map); //进入消息处理
            }
        }catch(Exception e){
//            logger.error("出现错误");
            e.printStackTrace();
        }

        return result;
    }
}
