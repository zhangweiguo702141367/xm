package com.zh.common.job;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.util.Date;

/**
 * 该方法仅仅用来测试每分钟执行
 * @author lance
 */
public class MinuteJob implements Job{
//	Logger logger = LogManager.getLogger(getClass());

	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
//		logger.info("JobName: {}", context.getJobDetail().getKey().getName());
		System.out.println("JobName1: "+context.getJobDetail().getKey().getName()+"current=="+new Date());
	}
}