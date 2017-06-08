package com.zh.dubbo.entity;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/6/8.
 * 用户实体
 */
public class UUser implements Serializable{
    private Long id;
    private String loginName;//登录名
    private String salt;//随机加密盐
    private String password;//登录密码
    private String userId;//用户id标识类似QQ号
    private String nickName;//昵称
    private String mobilePhone;//手机号
    private String headImage;//头像路径
    private int isMobile;//是否手机认证(-1未认证，1认证，2解绑)
    private int isEmail;//是否邮箱认证(-1未认证，1认证，2解绑，3认证中)
    private int isIdentity;//是否实名认证(-1未认证，1认证)
    private String lastLoginTime;//上一次登录时间(hh:mm:ss)
    private String lastLoginDate;//上一次登录时间(yyyy-MM-dd)
    private String registerTime;//注册时间(hh:mm:ss)
    private String registerDate;//注册时间(yyyy-MM-dd)
    private String spreadId;//推广人id
    private String roleId;//用户角色(默认为1、普通用户分号隔开字符串,0为超级管理员)
    private String email;//邮箱
    private int status;//用户状态(1正常、2锁定)
    private Long addTime;//开户时间

    public UUser(){
        super();
    }

    public UUser(Long id, String loginName, String salt, String password, String userId, String nickName, String mobilePhone, String headImage, int isMobile, int isEmail, int isIdentity, String lastLoginTime, String lastLoginDate, String registerTime, String registerDate, String spreadId, String roleId, String email, int status, Long addTime) {
        this.id = id;
        this.loginName = loginName;
        this.salt = salt;
        this.password = password;
        this.userId = userId;
        this.nickName = nickName;
        this.mobilePhone = mobilePhone;
        this.headImage = headImage;
        this.isMobile = isMobile;
        this.isEmail = isEmail;
        this.isIdentity = isIdentity;
        this.lastLoginTime = lastLoginTime;
        this.lastLoginDate = lastLoginDate;
        this.registerTime = registerTime;
        this.registerDate = registerDate;
        this.spreadId = spreadId;
        this.roleId = roleId;
        this.email = email;
        this.status = status;
        this.addTime = addTime;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getMobilePhone() {
        return mobilePhone;
    }

    public void setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
    }

    public String getHeadImage() {
        return headImage;
    }

    public void setHeadImage(String headImage) {
        this.headImage = headImage;
    }

    public int getIsMobile() {
        return isMobile;
    }

    public void setIsMobile(int isMobile) {
        this.isMobile = isMobile;
    }

    public int getIsEmail() {
        return isEmail;
    }

    public void setIsEmail(int isEmail) {
        this.isEmail = isEmail;
    }

    public int getIsIdentity() {
        return isIdentity;
    }

    public void setIsIdentity(int isIdentity) {
        this.isIdentity = isIdentity;
    }

    public String getLastLoginTime() {
        return lastLoginTime;
    }

    public void setLastLoginTime(String lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }

    public String getLastLoginDate() {
        return lastLoginDate;
    }

    public void setLastLoginDate(String lastLoginDate) {
        this.lastLoginDate = lastLoginDate;
    }

    public String getRegisterTime() {
        return registerTime;
    }

    public void setRegisterTime(String registerTime) {
        this.registerTime = registerTime;
    }

    public String getRegisterDate() {
        return registerDate;
    }

    public void setRegisterDate(String registerDate) {
        this.registerDate = registerDate;
    }

    public String getSpreadId() {
        return spreadId;
    }

    public void setSpreadId(String spreadId) {
        this.spreadId = spreadId;
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Long getAddTime() {
        return addTime;
    }

    public void setAddTime(Long addTime) {
        this.addTime = addTime;
    }

    @Override
    public String toString() {
        return "UUser{" +
                "id=" + id +
                ", loginName='" + loginName + '\'' +
                ", salt='" + salt + '\'' +
                ", password='" + password + '\'' +
                ", userId='" + userId + '\'' +
                ", nickName='" + nickName + '\'' +
                ", mobilePhone='" + mobilePhone + '\'' +
                ", headImage='" + headImage + '\'' +
                ", isMobile=" + isMobile +
                ", isEmail=" + isEmail +
                ", isIdentity=" + isIdentity +
                ", lastLoginTime='" + lastLoginTime + '\'' +
                ", lastLoginDate='" + lastLoginDate + '\'' +
                ", registerTime='" + registerTime + '\'' +
                ", registerDate='" + registerDate + '\'' +
                ", spreadId='" + spreadId + '\'' +
                ", roleId='" + roleId + '\'' +
                ", email='" + email + '\'' +
                ", status=" + status +
                ", addTime=" + addTime +
                '}';
    }
}
