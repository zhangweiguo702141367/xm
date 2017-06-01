package com.zh.dubbo.dao;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * Created by Administrator on 2017/6/1.
 */
@Mapper
@Component
public interface MemberDao {
    @Insert("INSERT INTO zh_member.mb_login_log(member_id,login_time,login_date,login_ip,add_time) VALUES(#{memberId},#{loginTime},#{loginDate},#{loginIp},#{addTime})")
    public int insertLoginLog(Map<String, Object> login);

    @Insert("INSERT INTO zh_member.mb_member(user_id,login_name,nick_name,mobile_phone,head_image,is_mobile,is_eamil,is_identity,last_login_time,last_login_date,register_time,register_date,spread_id,role_id,add_time) VALUES(" +
                                                  "#{userId},#{loginName},#{nickName},#{mobilePhone},#{headImage},#{isMobile},#{isEamil},#{isIdentity},#{lastLoginTime},#{lastLoginDate},#{registerTime},#{registerDate},#{spreadId},#{roleId},#{addTime})")
    @Options(useGeneratedKeys = true, keyProperty = "memberId", keyColumn = "id")
    public int insertMember(Map<String,Object> member);

    @Insert("INSERT INTO zh_member.mb_phone(member_id,mobile_phone,add_time) VALUES(#{memberId},#{mobilePhone},#{addTime})")
    public int insertPhone(Map<String,Object> phone);

    @Insert("INSERT INTO zh_member.mb_phone_recording(member_id,last_mobile,update_mobile,add_time) VALUES(#{memberId},#{lastMobile},#{updateMobile},#{addTime})")
    public int insertPhoneRecording(Map<String,Object> phoneRecord);
}
