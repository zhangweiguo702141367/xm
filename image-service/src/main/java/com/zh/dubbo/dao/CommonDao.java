package com.zh.dubbo.dao;

import com.zh.dubbo.entity.Config;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by 70214 on 2017/5/20.
 */
@Component
@Mapper
public interface CommonDao {
    @Results({
            @Result(property = "createTime", column = "create_time")
    })
    @Select("SELECT * FROM zh_system.sys_qiniu_conf")
    List<Config> getConfList();
}
