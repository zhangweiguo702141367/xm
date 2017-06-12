package com.zh.dubbo.core.shiro.mapper.dao;

import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * Created by Administrator on 2017/6/5.
 */
@Mapper
@Component
public interface AuthDao {
    /**
     * 根据邮箱获取当前邮箱是否被绑定
     * @param email
     * @return
     */
    @Select("SELECT * FROM zh_member.mb_member WHERE email=#{email}")
    public Map<String,Object> getMemberByEmail(@Param("email") String email);

    /**
     * 更新用户邮箱解绑状态
     * @param email
     * @return
     */
    @Update("UPDATE zh_member.mb_member SET is_email=2,email=null WHERE email=#{email}")
    public int updateMemberInfoByEmail(@Param("email") String email);

    /**
     * 根据用户Id更新用户邮箱信息
     * @param email
     * @param member_id
     * @return
     */
    @Update("UPDATE zh_member.mb_member SET is_email=1,email=#{email} WHERE id=#{member_id}")
    public int updateEmailMemberInfoByMemberId(@Param("email") String email, @Param("member_id") String member_id);

    /**
     * 根据用户id去获取上一次邮箱变更信息(最近的一次)
     * @param member_id
     * @return
     */
    @Select("SELECT update_email FROM zh_member.mb_email_recording WHERE member_id=#{member_id} ORDER BY add_time DESC LIMIT 0,1")
    public Map<String,Object> getEmailRecordByMemberId(@Param("member_id") String member_id);

    /**
     * 插入用户邮箱变更记录
     * @param emailRecord
     * @return
     */
    @Insert("INSERT INTO zh_member.mb_email_recording(member_id,last_email,update_email,add_time) VALUES(#{memberId},#{lastEmail},#{updateEmail},#{addTime})")
    public int insertEmailRecording(Map<String, Object> emailRecord);
}
