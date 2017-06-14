package com.zh.dubbo.core.shiro.mapper.dao;

import com.zh.dubbo.entity.UUser;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * Created by Administrator on 2017/6/1.
 */
@Mapper
@Component
public interface MemberDao {
    /**
     * 插入用户登录信息
     * @param login
     * @return
     */
    @Insert("INSERT INTO zh_member.mb_login_log(member_id,login_time,login_date,login_ip,add_time) VALUES(#{memberId},#{loginTime},#{loginDate},#{loginIp},#{addTime})")
    public int insertLoginLog(Map<String, Object> login);

    /**
     * 创建用户
     * @param member
     * @return
     */
    @Insert("INSERT INTO zh_member.mb_member(user_id,login_name,password,salt,nick_name,mobile_phone,head_image,is_mobile,is_email,is_identity,last_login_time,last_login_date,register_time,register_date,spread_id,role_id,add_time,email) VALUES(" +
                                                  "#{userId},#{loginName},#{password},#{salt},#{nickName},#{mobilePhone},#{headImage},#{isMobile},#{isEmail},#{isIdentity},#{lastLoginTime},#{lastLoginDate},#{registerTime},#{registerDate},#{spreadId},#{roleId},#{addTime},#{email})")
    @Options(useGeneratedKeys = true, keyProperty = "memberId", keyColumn = "id")
    public int insertMember(Map<String, Object> member);

    /**
     * 根据用户id去修改用户密码
     * @param password
     * @param salt
     * @param member_id
     */
    @Update("UPDATE zh_member.mb_member SET password=#{password},salt=#{salt} WHERE id=#{member_id}")
    public void updateMemberByPasswordSaltMemberId(@Param("password") String password, @Param("salt") String salt, @Param("member_id") String member_id);

    /**
     * 根据用户登录名和密码获取用户信息
     * @param login_name
     * @param password
     * @return
     */
    @Results({
            @Result(property = "loginName", column = "login_name"),
            @Result(property = "userId", column = "user_id"),
            @Result(property = "nickName", column = "nick_name"),
            @Result(property = "mobilePhone", column = "mobile_phone"),
            @Result(property = "headImage", column = "head_image"),
            @Result(property = "isMobile", column = "is_mobile"),
            @Result(property = "isEmail", column = "is_email"),
            @Result(property = "isIdentity", column = "is_identity"),
            @Result(property = "lastLoginTime", column = "last_login_time"),
            @Result(property = "lastLoginDate", column = "last_login_date"),
            @Result(property = "registerTime", column = "register_time"),
            @Result(property = "registerDate", column = "register_date"),
            @Result(property = "spreadId", column = "spread_id"),
            @Result(property = "roleId", column = "role_id"),
            @Result(property = "addTime", column = "add_time"),
    })
    @Select("SELECT * FROM zh_member.mb_member WHERE login_name=#{login_name} AND password=#{password}")
    public UUser getMemberInfoByUsernameAndPassword(@Param("login_name") String login_name, @Param("password") String password);

    /**
     * 根据登录名获取用户随机盐
     * @param login_name
     * @return
     */
    @Select("SELECT salt FROM zh_member.mb_member WHERE login_name=#{login_name}")
    public Map<String,Object> getSaltByLoginName(@Param("login_name") String login_name);
    /**
     * 插入用户手机变更记录
     * @param phoneRecord
     * @return
     */
    @Insert("INSERT INTO zh_member.mb_phone_recording(member_id,last_mobile,update_mobile,add_time) VALUES(#{memberId},#{lastMobile},#{updateMobile},#{addTime})")
    public int insertPhoneRecording(Map<String, Object> phoneRecord);

    /**
     * 根据用户ID获取用户信息
     * @param member_id
     * @return
     */
    @Results({
            @Result(property = "loginName", column = "login_name"),
            @Result(property = "userId", column = "user_id"),
            @Result(property = "nickName", column = "nick_name"),
            @Result(property = "mobilePhone", column = "mobile_phone"),
            @Result(property = "headImage", column = "head_image"),
            @Result(property = "isMobile", column = "is_mobile"),
            @Result(property = "isEmail", column = "is_email"),
            @Result(property = "isIdentity", column = "is_identity"),
            @Result(property = "lastLoginTime", column = "last_login_time"),
            @Result(property = "lastLoginDate", column = "last_login_date"),
            @Result(property = "registerTime", column = "register_time"),
            @Result(property = "registerDate", column = "register_date"),
            @Result(property = "spreadId", column = "spread_id"),
            @Result(property = "roleId", column = "role_id"),
            @Result(property = "addTime", column = "add_time"),
    })
    @Select("SELECT * FROM zh_member.mb_member WHERE id=#{member_id}")
    public UUser getMemberInfoById(@Param("member_id") String member_id);

    /**
     * 根据用户认证手机号获取用户信息
     * @param phone
     * @return
     */
    @Results({
            @Result(property = "loginName", column = "login_name"),
            @Result(property = "userId", column = "user_id"),
            @Result(property = "nickName", column = "nick_name"),
            @Result(property = "mobilePhone", column = "mobile_phone"),
            @Result(property = "headImage", column = "head_image"),
            @Result(property = "isMobile", column = "is_mobile"),
            @Result(property = "isEmail", column = "is_email"),
            @Result(property = "isIdentity", column = "is_identity"),
            @Result(property = "lastLoginTime", column = "last_login_time"),
            @Result(property = "lastLoginDate", column = "last_login_date"),
            @Result(property = "registerTime", column = "register_time"),
            @Result(property = "registerDate", column = "register_date"),
            @Result(property = "spreadId", column = "spread_id"),
            @Result(property = "roleId", column = "role_id"),
            @Result(property = "addTime", column = "add_time"),
    })
    @Select("SELECT * FROM zh_member.mb_member WHERE mobile_phone=#{phone}")
    public UUser getMemberInfoByPhone(@Param("phone") String phone);
    /**
     * 根据用户登陆手机号获取用户信息
     * @param phone
     * @return
     */
    @Results({
            @Result(property = "loginName", column = "login_name"),
            @Result(property = "userId", column = "user_id"),
            @Result(property = "nickName", column = "nick_name"),
            @Result(property = "mobilePhone", column = "mobile_phone"),
            @Result(property = "headImage", column = "head_image"),
            @Result(property = "isMobile", column = "is_mobile"),
            @Result(property = "isEmail", column = "is_email"),
            @Result(property = "isIdentity", column = "is_identity"),
            @Result(property = "lastLoginTime", column = "last_login_time"),
            @Result(property = "lastLoginDate", column = "last_login_date"),
            @Result(property = "registerTime", column = "register_time"),
            @Result(property = "registerDate", column = "register_date"),
            @Result(property = "spreadId", column = "spread_id"),
            @Result(property = "roleId", column = "role_id"),
            @Result(property = "addTime", column = "add_time"),
    })
    @Select("SELECT * FROM zh_member.mb_member WHERE login_name=#{phone}")
    public UUser getMemberInfoByLoginPhone(@Param("phone") String phone);
    /**
     * 根据用户认证手机号更新用户信息(解除绑定)
     * @param phone
     * @return
     */
    @Update("UPDATE zh_member.mb_member SET mobile_phone=null,is_mobile=2 WHERE mobile_phone=#{phone}")
    public void updateMemeberInfoFreeByPhone(@Param("phone") String phone);
    /**
     * 根据用户认证手机号和用户id更新用户信息
     * @param phone
     * @return
     */
    @Update("UPDATE zh_member.mb_member SET mobile_phone=#{phone},is_mobile=1 WHERE id=#{member_id}")
    public void updateMemeberInfoByPhone(@Param("phone") String phone, @Param("member_id") String member_id);
    /**
     * 根据用户id获取最近一次手机变更表中的用户手机号
     * @param member_id
     * @return
     */
    @Select("SELECT update_mobile FROM zh_member.mb_phone_recording WHERE member_id=#{member_id} ORDER BY add_time DESC LIMIT 0,1")
    public Map<String,Object> getLastChangePhoneById(@Param("member_id") String member_id);
    /**
     * 根据用户登录名和id去获取用户信息
     * @param login_name
     * @param password
     * @return
     */
    @Results({
            @Result(property = "loginName", column = "login_name"),
            @Result(property = "userId", column = "user_id"),
            @Result(property = "nickName", column = "nick_name"),
            @Result(property = "mobilePhone", column = "mobile_phone"),
            @Result(property = "headImage", column = "head_image"),
            @Result(property = "isMobile", column = "is_mobile"),
            @Result(property = "isEmail", column = "is_email"),
            @Result(property = "isIdentity", column = "is_identity"),
            @Result(property = "lastLoginTime", column = "last_login_time"),
            @Result(property = "lastLoginDate", column = "last_login_date"),
            @Result(property = "registerTime", column = "register_time"),
            @Result(property = "registerDate", column = "register_date"),
            @Result(property = "spreadId", column = "spread_id"),
            @Result(property = "roleId", column = "role_id"),
            @Result(property = "addTime", column = "add_time"),
    })
    @Select("SELECT * FROM zh_member.mb_member WHERE login_name=#{login_name} AND id=#{memebr_id}")
    public UUser getMemberInfoByUsernameAndMemberId(@Param("login_name") String login_name, @Param("memebr_id") String memebr_id);

}
