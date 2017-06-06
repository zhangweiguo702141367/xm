package com.bhjf.controller;

import com.bhjf.request.OpenRequestApi;
import com.bhjf.response.OpenApiUtil;
import com.bhjf.response.OpenApiUtil.Context;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2017/6/6.
 */
@RestController
public class ApiTestController {
    @Value("${kh.publicrsa}")
    private String publicRsa;
    @Value("${kh.username}")
    private String username;
    @Value("${kh.password}")
    private String password;
    @Value("${kh.client_nid}")
    private String nid;

    @Autowired
    private OpenApiUtil openApiUtil;
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * 查询调用
     * @return
     */
    @GetMapping("/sendApi")
    public String sendApi(){
        try {
            //nid, username, password, publicRsa这些可放置数据库中做查询
            OpenRequestApi openapi = new OpenRequestApi(true, nid, username, password, publicRsa);
            //业务参数
            Map<String, Object> resultRedeemMap = openapi.redeem("1","12","23useNo","23serialNo","4553","100");
            System.out.println("redeem.response =" + resultRedeemMap.toString());
            Integer redeemCode  = Integer.valueOf(resultRedeemMap.get("code").toString());
            if(redeemCode == openapi.CODE_SUCCESS){
                return "redeem() = true && "+ resultRedeemMap.get("data").toString();
            }else{
                return "redeem() = false";
            }

        } catch (Exception e) {
            e.printStackTrace();
            return "erroe";
        }
    }
    /**
     * 数据业务处理完成后返回结果
     * @return
     */
    @PostMapping("/redeem")
    public Object redeem(HttpServletRequest request, String nid, String key, String data){
        Context ctx = openApiUtil.newContext();
        try {
            String clientIp = request.getRemoteAddr();
            Map<String, Object> requestMap = openApiUtil.getRequestData(ctx, "redeem", clientIp,nid,2, key, data);
            if (openApiUtil.hasError(requestMap)) {
                return openApiUtil.setResult(ctx, (int) requestMap.get("code"));
            }
            System.out.println("requestMap==="+requestMap.toString());
            Map<String, Object> dataMap = new HashMap<String, Object>();
            //业务处理完成后封装处理结果
            dataMap.put("redeem", true);

            return openApiUtil.setResult(ctx, openApiUtil.CODE_SUCCESS, dataMap);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return openApiUtil.setResult(ctx, openApiUtil.CODE_INTERNAL_ERROR);
        } finally {
            openApiUtil.saveLog(ctx);
        }
    }
}
