package com.zh.dubbo.manage.auth.impl;

import com.zh.dubbo.dao.MemberDao;
import com.zh.dubbo.manage.auth.AuthService;
import com.zh.dubbo.untils.DateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2017/6/5.
 */
@Service
public class AuthServiceImpl implements AuthService {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    MemberDao memberDao;
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void phoneAuth(Map<String, Object> params) throws Exception {
        if(params == null || params.size() == 0){
            throw new Exception("参数列表不能为空！");
        }
        if(params.get("member_id") == null || "".equals(params.get("member_id").toString())){
            throw new Exception("用户id不能为空！");
        }
        if(params.get("member_phone") == null || "".equals(params.get("member_phone").toString())){
            throw new Exception("认证手机号不能为空！");
        }
        String mobile_phone = params.get("member_phone").toString();

        String member_id = params.get("member_id").toString();
        Map<String,Object> memberInfo = memberDao.getMemberInfoById(member_id);
        if(memberInfo == null || memberInfo.size() == 0){
            throw new Exception("获取用户信息异常！");
        }
        //如果当前用户已绑定手机号则判断和之前绑定的一样不，如果一样则不更新，否则更新，并更新手机变更表
        if(memberInfo.get("mobile_phone") != null && !"".equals(memberInfo.get("mobile_phone").toString())){
            String mobile_phone_current = memberInfo.get("mobile_phone").toString();
            //相同则抛异常返回
            if(mobile_phone_current.equals(mobile_phone)){
                throw new Exception("请不要使用相同手机号认证");
            }
            //不同则做更新
            //1、更新用户表，如果当前手机号被认证则至为空
            memberDao.updateMemeberInfoFreeByPhone(mobile_phone);
            //2、更新用户表手机号
            memberDao.updateMemeberInfoByPhone(mobile_phone,member_id);
            //3、更新手机变更表
            Map<String,Object> phoneRecord = new HashMap<>();
            phoneRecord.put("member_id",member_id);
            phoneRecord.put("last_mobile",mobile_phone_current);
            phoneRecord.put("update_mobile",mobile_phone);
            phoneRecord.put("add_time", DateUtil.getCurrentTime());
            memberDao.insertPhoneRecording(phoneRecord);
        }
        //否则获取最近一次变更的手机号作为上一次手机号，同时当前手机号作为这次手机号更新用户表更新手机记录表
        //1、更新用户表，如果当前手机号被认证则至为空
        memberDao.updateMemeberInfoFreeByPhone(mobile_phone);
        //2、更新用户表手机号
        memberDao.updateMemeberInfoByPhone(mobile_phone,member_id);
        //3、获取上一次更新的手机号
        Map<String,Object> memberRecordPhone = memberDao.getLastChangePhoneById(member_id);
        if(memberRecordPhone == null || memberRecordPhone.size() == 0){
            throw new Exception("获取最新一次手机号变更异常！");
        }
        if(memberRecordPhone.get("update_mobile") == null || "".equals(memberRecordPhone.get("update_mobile").toString())){
            throw new Exception("");
        }
        //4、插入手机变更表
        String update_mobile = memberRecordPhone.get("update_mobile").toString();
        Map<String,Object> phoneRecord = new HashMap<>();
        phoneRecord.put("memberId",member_id);
        phoneRecord.put("lastMobile",update_mobile);
        phoneRecord.put("updateMobile",mobile_phone);
        phoneRecord.put("addTime", DateUtil.getCurrentTime());
        memberDao.insertPhoneRecording(phoneRecord);
    }

    @Override
    public boolean isPhoneAuth(Map<String, Object> params) throws Exception {
        if(params == null || params.size() == 0){
            throw new Exception("参数列表不能为空！");
        }
        if(params.get("mobile_phone") == null || "".equals(params.get("mobile_phone").toString())){
            throw new Exception("手机号不能为空");
        }
        //如果当前手机号是登陆名则返回false
        Map<String,Object> member_info = memberDao.getMemberInfoByLoginPhone(params.get("mobile_phone").toString());
        if(member_info != null || member_info.size() != 0){
            return false;
        }
        //如果当前手机号是认证手机号则返回false
        Map<String,Object> memberInfo = memberDao.getMemberInfoByPhone(params.get("mobile_phone").toString());
        if(memberInfo != null || memberInfo.size() != 0){
            return false;
        }
        return true;
    }

    @Override
    public void emailAuth(Map<String, Object> params) throws Exception {

    }

    @Override
    public void realNameAuth(Map<String, Object> params) throws Exception {

    }
}
