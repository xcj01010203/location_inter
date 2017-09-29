package com.cao.controller;

import com.cao.service.LocationService;
import com.cao.util.ApplicationContextUtil;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.springframework.scheduling.quartz.QuartzJobBean;

public class JobController extends QuartzJobBean {

    Logger logger = LoggerFactory.getLogger(JobController.class);

    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
        try {
            JobDataMap jobDataMap = context.getJobDetail().getJobDataMap();
            int maxAge = jobDataMap.getInt("maxAge");

            LocationService locationService = ApplicationContextUtil.getBeanDetail("locationService");

//            LocationService locationService = (LocationService) new FileSystemXmlApplicationContext("classpath:spring-context.xml").getBean("locationService");
            locationService.syncLocationData(maxAge);
        } catch (Exception e) {
            logger.error("定时获取坐标失败", e);
        }
    }
}
