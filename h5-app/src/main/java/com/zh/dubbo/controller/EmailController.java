package com.zh.dubbo.controller;

import com.zh.dubbo.constant.RspConstants;
import com.zh.dubbo.entity.RespData;
import com.zh.dubbo.entity.UUser;
import com.zh.dubbo.exception.ProcException;
import com.zh.dubbo.fo.EmailServiceFo;
import com.zh.dubbo.fo.MemberServiceFo;
import com.zh.dubbo.untils.DateUtil;
import com.zh.dubbo.untils.RequestUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2017/6/14.
 * 有关邮箱的操作，用户邮箱认证 用户邮箱修改密码 用户邮箱获取验证码
 */
@RestController
@RequestMapping("/inner/email/")
public class EmailController {
    @Autowired
    EmailServiceFo emailServiceFo;
    @Autowired
    MemberServiceFo memberServiceFo;

    /**
     * 邮箱认证第一步，判断邮箱是否已被认证
     * @param request
     * @return
     */
    @PostMapping("isEmailAuth")
    public RespData isEmailAuth(HttpServletRequest request){
        try {
            Map<String, Object> params = RequestUtil.getRequestMap(request);
            if(params == null || params.size() == 0){
                throw new Exception("参数列表不能为空！");
            }
            if (params.get("email") == null || "".equals(params.get("email").toString())) {
                throw new ProcException("认证邮箱不能为空!");
            }
            String email = params.get("email").toString();
            if(emailServiceFo.isEmailAuth(email)){
                return RespData.create(RspConstants.CONFIRM,"当前邮箱已被认证，认证成功后将会覆盖之前的邮箱",null, DateUtil.getCurrentTime());
            }
            return RespData.create(RspConstants.SUCCESS,"欢迎您进行邮箱认证",null, DateUtil.getCurrentTime());
        }catch (ProcException proc){
            return RespData.create(RspConstants.SERVICEERROR,proc.getMessage(),null,DateUtil.getCurrentTime());
        }catch (Exception e){
            return RespData.create(RspConstants.OTHERERROR,"操作异常，请您重试",null,DateUtil.getCurrentTime());
        }
    }

    /**
     * 邮箱认证第二步，发送认证邮件
     * @param request
     * @return
     */
    @PostMapping("sendAuthEmail")
    public RespData sendAuthEmail(HttpServletRequest request){
        try {
            Map<String, Object> params = RequestUtil.getRequestMap(request);
            if(params == null || params.size() == 0){
                throw new Exception("参数列表不能为空！");
            }
            if (params.get("email") == null || "".equals(params.get("email").toString())) {
                throw new ProcException("认证邮箱不能为空!");
            }
            String email = params.get("email").toString();
//            String tokenAndSign = emailServiceFo.getEmailSign(TokenManager.getUserId().toString(),TokenManager.getNickname(),email);
//            params.put("member_id",TokenManager.getUserId());
            String tokenAndSign = emailServiceFo.getEmailSign("33","13834412697",email);
            params.put("member_id","26");
            params.put("template_nid","register");
            params.put("to_email",email);
		    Map<String,Object> template_param = new HashMap<String,Object>();
		    template_param.put("tokenAndSign",tokenAndSign);
		    params.put("template_param",template_param);
            emailServiceFo.sendAuthEmail(params);
            return RespData.create(RspConstants.SUCCESS,"邮件发送成功",null, DateUtil.getCurrentTime());
        }catch (ProcException proc){
            return RespData.create(RspConstants.SERVICEERROR,proc.getMessage(),null,DateUtil.getCurrentTime());
        }catch (Exception e){
            return RespData.create(RspConstants.OTHERERROR,"操作异常，请您重试",null,DateUtil.getCurrentTime());
        }
    }

    /**
     * 邮件认证第三步，激活认证
     * @param request
     * @return
     */
    @PostMapping("e/authEmail")
    public RespData authEmail(HttpServletRequest request){
        try {
            Map<String, Object> params = RequestUtil.getRequestMap(request);
            if(params == null || params.size() == 0){
                throw new Exception("参数列表不能为空！");
            }
            if (params.get("token") == null || "".equals(params.get("token").toString())) {
                throw new ProcException("token不能为空！");
            }
            if(params.get("sign") == null || "".equals(params.get("sign").toString())){
                throw new ProcException("用户信息异常！");
            }
            String token = params.get("token").toString();
            String sign = params.get("sign").toString();
            Map<String,Object> signMap = emailServiceFo.design(token,sign);
            UUser user = memberServiceFo.getMemberInfoByUsernameAndMemberId(signMap.get("login_name").toString(),signMap.get("member_id").toString());
            if(user == null){
                throw new ProcException("token不合法");
            }
            //认证邮箱
            emailServiceFo.emailAuth(signMap);
            //做登录
//            TokenManager.login(user,Boolean.FALSE);
            return RespData.create(RspConstants.SUCCESS,"邮箱认证成功",null, DateUtil.getCurrentTime());
        }catch (ProcException proc){
            return RespData.create(RspConstants.SERVICEERROR,proc.getMessage(),null,DateUtil.getCurrentTime());
        }catch (Exception e){
            return RespData.create(RspConstants.OTHERERROR,"操作异常，请您重试",null,DateUtil.getCurrentTime());
        }
    }
    /**
     * 获取当前用户有没有绑定邮箱
     * @param request
     * @return
     */
    @PostMapping("e/isMemberAuthEmail")
    public RespData authEisMemberAuthEmailmail(HttpServletRequest request){
        try {
            Map<String, Object> params = RequestUtil.getRequestMap(request);
            if(params == null || params.size() == 0){
                throw new Exception("参数列表不能为空！");
            }
            if (params.get("login_name") == null || "".equals(params.get("login_name").toString())) {
                throw new ProcException("用户登录名不能为空！");
            }
            String login_name = params.get("login_name").toString();
            boolean isAuth = emailServiceFo.isMemberEmailAuth(login_name);
            return RespData.create(RspConstants.SUCCESS,"继续修改密码操作吧",isAuth, DateUtil.getCurrentTime());
        }catch (ProcException proc){
            return RespData.create(RspConstants.SERVICEERROR,proc.getMessage(),null,DateUtil.getCurrentTime());
        }catch (Exception e){
            return RespData.create(RspConstants.OTHERERROR,"操作异常，请您重试",null,DateUtil.getCurrentTime());
        }
    }

}
