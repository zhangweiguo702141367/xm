package com.zh.common.job;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.util.Date;

public class Minute2Job implements Job {
//	Logger logger = LogManager.getLogger(getClass());

	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
//		logger.info("JobName2: {}", context.getJobDetail().getKey().getName());
		System.out.println("JobName2: "+context.getJobDetail().getKey().getName()+"current=="+new Date());
	}

}
