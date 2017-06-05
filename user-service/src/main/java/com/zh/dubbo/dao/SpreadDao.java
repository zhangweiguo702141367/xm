package com.zh.dubbo.dao;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * Created by Administrator on 2017/6/5.
 */
@Mapper
@Component
public interface SpreadDao {
    /**
     * 插入推广人以及被推广人关系
     * @param spreadInfo
     * @return
     */
    @Insert("INSERT INTO zh_member.mb_spread_log(member_id,spread_member_id,nick_name,spread_nick_name,add_time) VALUES(" +
            "#{member_id},#{spread_member_id},#{nick_name},#{spread_nick_name},#{add_time})")
    public int insertSpread(Map<String,Object> spreadInfo);

    /**
     * 根据spreadId获取推广人id和昵称
     * @param spreadId
     * @return
     */
    @Select("SELECT id,nick_name FROM zh_member.mb_member WHERE spread_id = #{spreadId}")
    public Map<String,Object> getMemberInfoBySpreadId(@Param("spreadId") String spreadId);
}
