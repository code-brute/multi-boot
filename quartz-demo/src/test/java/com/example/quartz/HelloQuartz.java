package com.example.quartz;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Description:
 * @author: mitnick
 * @date: 2018-01-22 下午2:12
 */
public class HelloQuartz implements Job {

    Logger logger = LoggerFactory.getLogger(HelloQuartz.class);
    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        logger.debug("HelloQuartz execute", context);
    }
}
