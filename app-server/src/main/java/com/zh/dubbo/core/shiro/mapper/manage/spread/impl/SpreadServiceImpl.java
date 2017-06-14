package com.zh.dubbo.core.shiro.mapper.manage.spread.impl;

import com.zh.dubbo.core.shiro.mapper.dao.SpreadDao;
import com.zh.dubbo.core.shiro.mapper.manage.spread.SpreadService;
import com.zh.dubbo.exception.ProcException;
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
public class SpreadServiceImpl implements SpreadService {
    @Autowired
    SpreadDao spreadDao;
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void insertSpread(Map<String, Object> params) throws Exception {
        if(params == null || params.size() == 0){
            throw new Exception("参数列表不能为空！");
        }
        if(params.get("member_id") == null || "".equals(params.get("member_id").toString())){
            throw new ProcException("被推广人id不能为空！");
        }
        if(params.get("nick_name") == null || "".equals(params.get("nick_name").toString())){
            throw new ProcException("被推广人昵称不能为空！");
        }
        if(params.get("spread_id") == null || "".equals(params.get("spread_id").toString())){
            throw new ProcException("推广序列号不能为空！");
        }
        String spreadId = params.get("spread_id").toString();
        Map<String,Object> spread_member_info = spreadDao.getMemberInfoBySpreadId(spreadId);
        if(spread_member_info == null || spread_member_info.size() == 0){
            throw new ProcException("获取推广人信息失败！");
        }
        if(spread_member_info.get("id") == null || "".equals(spread_member_info.get("id").toString())){
            throw new ProcException("获取推广人ID失败！");
        }
        if(spread_member_info.get("nick_name") == null || "".equals(spread_member_info.get("nick_name").toString())){
            throw new ProcException("获取推广人昵称失败！");
        }
        Map<String,Object> spreadMap = new HashMap<String,Object>();
        //推广人用户id
        spreadMap.put("member_id",spread_member_info.get("id"));
        //当前用户id
        spreadMap.put("spread_member_id",params.get("member_id"));
        spreadMap.put("nick_name",spread_member_info.get("nick_name"));
        spreadMap.put("spread_nick_name",params.get("nick_name"));
        spreadMap.put("add_time", DateUtil.getCurrentTime());
        spreadDao.insertSpread(spreadMap);
    }
}
