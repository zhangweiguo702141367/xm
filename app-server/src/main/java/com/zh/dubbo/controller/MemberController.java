package com.zh.dubbo.controller;

import com.zh.dubbo.constant.RspConstants;
import com.zh.dubbo.core.shiro.tooken.manager.TokenManager;
import com.zh.dubbo.entity.RespData;
import com.zh.dubbo.entity.UUser;
import com.zh.dubbo.fo.MemberServiceFo;
import com.zh.dubbo.untils.DateUtil;
import com.zh.dubbo.untils.RequestUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2017/6/12.
 */
@RestController
public class MemberController {
    @Autowired
    MemberServiceFo memberServiceFo;
    /**
     * 忘记密码
     * @param request
     * @return
     */
    @GetMapping("forgetPassword")
    public RespData forgetPassword(HttpServletRequest request){
        RespData respData = new RespData();
        try {
            Map<String, String> paramMap = RequestUtil.getQueryMap(request, "utf-8");
            Map<String,Object> params = new HashMap<>();
            params.putAll(paramMap);
            if(paramMap == null || paramMap.size() == 0){
                throw new Exception("参数列表不能为空！");
            }
            if(paramMap.get("login_name") == null || "".equals(paramMap.get("login_name").toString())){
                throw new Exception("用户名不能为空!");
            }
            UUser user = memberServiceFo.getMmeberInfoByLoginName(paramMap.get("login_name").toString());
            if(user == null){
                throw new Exception("当前用户不存在！");
            }
            params.put("member_id",user.getId());
            memberServiceFo.updateMemberPassword(params);
            respData.setRspTime(DateUtil.getCurrentTime());
            respData.setStatus(RspConstants.SUCCESS);
            respData.setMessage("修改密码成功！");
            return respData;
        }catch (Exception e){
            respData.setMessage(e.getMessage());
            respData.setStatus(RspConstants.OTHERERROR);
            respData.setRspTime(DateUtil.getCurrentTime());
            return respData;
        }
    }
}
