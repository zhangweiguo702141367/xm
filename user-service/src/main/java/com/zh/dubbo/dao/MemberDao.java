package com.zh.dubbo.dao;

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
    public int insertMember(Map<String,Object> member);

    /**
     * 根据用户id去修改用户密码
     * @param password
     * @param salt
     * @param member_id
     */
    @Update("UPDATE zh_member.mb_member SET password=#{password},salt=#{salt} WHERE id=#{member_id}")
    public void updateMemberByPasswordSaltMemberId(@Param("password") String password,@Param("salt") String salt,@Param("member_id") String member_id);

    /**
     * 根据用户登录名和密码获取用户信息
     * @param login_name
     * @param password
     * @return
     */
    @Select("SELECT * FROM zh_member.mb_member WHERE login_name=#{login_name} AND password=#{password}")
    public Map<String,Object> getMemberInfoByUsernameAndPassword(@Param("login_name") String login_name,@Param("password") String password);

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
    public int insertPhoneRecording(Map<String,Object> phoneRecord);

    /**
     * 根据用户ID获取用户信息
     * @param member_id
     * @return
     */
    @Select("SELECT * FROM zh_member.mb_member WHERE id=#{member_id}")
    public Map<String,Object> getMemberInfoById(@Param("member_id") String member_id);

    /**
     * 根据用户认证手机号获取用户信息
     * @param phone
     * @return
     */
    @Select("SELECT * FROM zh_member.mb_member WHERE mobile_phone=#{phone}")
    public Map<String,Object> getMemberInfoByPhone(@Param("phone") String phone);
    /**
     * 根据用户登陆手机号获取用户信息
     * @param phone
     * @return
     */
    @Select("SELECT * FROM zh_member.mb_member WHERE login_name=#{phone}")
    public Map<String,Object> getMemberInfoByLoginPhone(@Param("phone") String phone);
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
    public void updateMemeberInfoByPhone(@Param("phone") String phone,@Param("member_id") String member_id);
    /**
     * 根据用户id获取最近一次手机变更表中的用户手机号
     * @param member_id
     * @return
     */
    @Select("SELECT update_mobile FROM zh_member.mb_phone_recording WHERE member_id=#{member_id} ORDER BY add_time DESC LIMIT 0,1")
    public Map<String,Object> getLastChangePhoneById(@Param("member_id") String member_id);

}
