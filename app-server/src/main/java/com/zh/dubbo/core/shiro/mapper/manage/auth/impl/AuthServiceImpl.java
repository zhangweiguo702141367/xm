package com.zh.dubbo.core.shiro.mapper.manage.auth.impl;

import com.zh.dubbo.core.shiro.mapper.dao.AuthDao;
import com.zh.dubbo.core.shiro.mapper.dao.MemberDao;
import com.zh.dubbo.core.shiro.mapper.manage.auth.AuthService;
import com.zh.dubbo.entity.UUser;
import com.zh.dubbo.exception.ProcException;
import com.zh.dubbo.untils.DateUtil;
import com.zh.dubbo.untils.MatchUtil;
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
    @Autowired
    AuthDao authDao;
    @Override
    @Transactional(rollbackFor = Exception.class)
    public UUser phoneAuth(Map<String, Object> params) throws Exception {
        if(params == null || params.size() == 0){
            throw new Exception("参数列表不能为空！");
        }
        if(params.get("member_id") == null || "".equals(params.get("member_id").toString())){
            throw new Exception("用户id不能为空！");
        }
        if(params.get("member_phone") == null || "".equals(params.get("member_phone").toString())){
            throw new ProcException("认证手机号不能为空！");
        }
        String mobile_phone = params.get("member_phone").toString();

        String member_id = params.get("member_id").toString();
        UUser memberInfo = memberDao.getMemberInfoById(member_id);
        if(memberInfo == null){
            throw new Exception("获取用户信息异常！");
        }
        //如果当前用户已绑定手机号则判断和之前绑定的一样不，如果一样则不更新，否则更新，并更新手机变更表
        if(memberInfo.getMobilePhone() != null && !"".equals(memberInfo.getMobilePhone())){
            String mobile_phone_current = memberInfo.getMobilePhone();
            //相同则抛异常返回
            if(mobile_phone_current.equals(mobile_phone)){
                throw new ProcException("请不要使用相同手机号认证");
            }
            //不同则做更新
            //1、更新用户表，如果当前手机号被认证则至为空
            memberDao.updateMemeberInfoFreeByPhone(mobile_phone);
            //2、更新用户表手机号
            memberDao.updateMemeberInfoByPhone(mobile_phone,member_id);
            //3、更新手机变更表
            Map<String,Object> phoneRecord = new HashMap<>();
            phoneRecord.put("memberId",member_id);
            phoneRecord.put("lastMobile",mobile_phone_current);
            phoneRecord.put("updateMobile",mobile_phone);
            phoneRecord.put("addTime", DateUtil.getCurrentTime());
            memberDao.insertPhoneRecording(phoneRecord);
            //完成操作后结束否则继续执行，当前认证手机用户中手机号不存在的情况
            //根据用户id获取最新用户信息
            UUser member = memberDao.getMemberInfoById(member_id);
            member.setSalt(null);
            return member;
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
            throw new Exception("获取最新一次手机号变更异常！");
        }
        //4、插入手机变更表
        String update_mobile = memberRecordPhone.get("update_mobile").toString();
        Map<String,Object> phoneRecord = new HashMap<>();
        phoneRecord.put("memberId",member_id);
        phoneRecord.put("lastMobile",update_mobile);
        phoneRecord.put("updateMobile",mobile_phone);
        phoneRecord.put("addTime", DateUtil.getCurrentTime());
        memberDao.insertPhoneRecording(phoneRecord);
        //根据用户id获取最新用户信息
        UUser member = memberDao.getMemberInfoById(member_id);
        member.setPassword(null);
        member.setSalt(null);
        return member;
    }

    @Override
    public boolean isPhoneRegister(Map<String, Object> params) throws Exception {
        if(params == null || params.size() == 0){
            throw new Exception("参数列表不能为空！");
        }
        if(params.get("mobile_phone") == null || "".equals(params.get("mobile_phone").toString())){
            throw new ProcException("手机号不能为空");
        }
        //如果当前手机号是登陆名则返回false
        UUser member_info = memberDao.getMemberInfoByLoginPhone(params.get("mobile_phone").toString());
        if(member_info != null){
            return true;
        }
        return false;
    }

    @Override
    public boolean isPhoneBind(Map<String, Object> params) throws Exception {
        if(params == null || params.size() == 0){
            throw new Exception("参数列表不能为空！");
        }
        if(params.get("mobile_phone") == null || "".equals(params.get("mobile_phone").toString())){
            throw new ProcException("手机号不能为空");
        }
        //如果当前手机号是认证手机号则返回false
        UUser memberInfo = memberDao.getMemberInfoByPhone(params.get("mobile_phone").toString());
        if(memberInfo != null){
            return true;
        }
        return false;
    }

    @Override
    public boolean isPhoneAuth(Map<String, Object> params) throws Exception {
        if(params == null || params.size() == 0){
            throw new Exception("参数列表不能为空！");
        }
        if(params.get("mobile_phone") == null || "".equals(params.get("mobile_phone").toString())){
            throw new ProcException("手机号不能为空");
        }
        //如果当前手机号是登陆名则返回false
        UUser member_info = memberDao.getMemberInfoByLoginPhone(params.get("mobile_phone").toString());
        if(member_info != null){
            return false;
        }
        //如果当前手机号是认证手机号则返回false
        UUser memberInfo = memberDao.getMemberInfoByPhone(params.get("mobile_phone").toString());
        if(memberInfo != null){
            return false;
        }
        return true;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public UUser emailAuth(Map<String, Object> params) throws Exception {
        if(params == null || params.size() == 0){
            throw new Exception("参数列表不能为空！");
        }
        if(params.get("email") == null || "".equals(params.get("email").toString())){
            throw new ProcException("邮件地址不能为空");
        }
        String email = params.get("email").toString();
        if(!MatchUtil.checkEmail(email)){
            throw new ProcException("请输入正确的邮箱地址");
        }
        if(params.get("member_id") == null || "".equals(params.get("member_id").toString())){
            throw new Exception("用户id不能为空");
        }
        String member_id = params.get("member_id").toString();
        //根据用户id获取用户信息
        UUser memberInfo = memberDao.getMemberInfoById(member_id);
        if(memberInfo == null){
            throw new Exception("获取用户信息异常！");
        }
        //获取用户邮箱绑定状态
        int is_email = memberInfo.getIsEmail();
        //用户变更邮箱时(当前用户是绑定状态)
        if(1 == is_email){
            String email_current = memberInfo.getEmail();
            //相同则抛异常返回
            if(email_current.equals(email)){
                throw new ProcException("请不要使用相同邮箱认证");
            }
            //不同则做更新
            //1、解绑已绑定这个邮箱的用户
            authDao.updateMemberInfoByEmail(email);
            //2、更新当前用户的邮箱信息
            authDao.updateEmailMemberInfoByMemberId(email,member_id);
            //3、插入邮箱变更表
            Map<String,Object> emailRecord = new HashMap<>();
            emailRecord.put("memberId",member_id);
            emailRecord.put("lastEmail",email_current);
            emailRecord.put("updateEmail",email);
            emailRecord.put("addTime", DateUtil.getCurrentTime());
            authDao.insertEmailRecording(emailRecord);
            //完成操作后结束否则继续执行，当前认证手机用户中手机号不存在的情况
        }else if(-1 == is_email){//如果是1则表示用户第一次绑定
            //1、解绑已绑定这个邮箱的用户
            authDao.updateMemberInfoByEmail(email);
            //2、更新当前用户的邮箱信息
            authDao.updateEmailMemberInfoByMemberId(email,member_id);
            //3、插入邮箱变更表
            Map<String,Object> emailRecord = new HashMap<>();
            emailRecord.put("memberId",member_id);
            emailRecord.put("lastEmail",email);
            emailRecord.put("updateEmail",email);
            emailRecord.put("addTime", DateUtil.getCurrentTime());
            authDao.insertEmailRecording(emailRecord);
        }else if(2 == is_email){//解绑状态
            //1、解绑已绑定这个邮箱的用户
            authDao.updateMemberInfoByEmail(email);
            //2、更新当前用户的邮箱信息
            authDao.updateEmailMemberInfoByMemberId(email,member_id);
            //3、获取上一次绑定邮箱
            Map<String,Object> email_recording = authDao.getEmailRecordByMemberId(member_id);
            if(email_recording == null || email_recording.size() == 0){
                throw new Exception("获取上一次邮箱变更记录失败！");
            }
            String last_email = email_recording.get("update_email").toString();
            //3、插入邮箱变更表
            Map<String,Object> emailRecord = new HashMap<>();
            emailRecord.put("memberId",member_id);
            emailRecord.put("lastEmail",last_email);
            emailRecord.put("updateEmail",email);
            emailRecord.put("addTime", DateUtil.getCurrentTime());
            authDao.insertEmailRecording(emailRecord);
        }
        //根据member_id获取用户信息
        UUser member = memberDao.getMemberInfoById(member_id);
        member.setSalt(null);
        member.setPassword(null);
        return member;
    }

    @Override
    public boolean isEmailAuth(String email) throws Exception {
        if("".equals(email)){
            throw new ProcException("邮箱不能为空！");
        }
        Map<String,Object> member_info = authDao.getMemberByEmail(email);
        if(member_info != null && member_info.size() != 0){
            //如果用户信息不为空，则说明已被认证
            return true;
        }
        return false;
    }

    @Override
    public void realNameAuth(Map<String, Object> params) throws Exception {

    }
}
