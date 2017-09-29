package com.cao.service;

import com.cao.common.Page;
import com.cao.dao.LocationDao;
import com.cao.model.LocationModel;
import com.cao.model.QuartzModel;
import com.cao.util.GsonUtils;
import com.cao.util.HttpUtils;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 位置信息
 */
@Service
public class LocationService {

    @Autowired
    private LocationDao locationDao;

    @Autowired
    private SchedulerFactoryBean myScheduler;

    @Autowired
    private QuartzService quartzService;

    /**
     * 同步位置信息
     *
     * @param maxAge
     */
    public void syncLocationData(int maxAge) throws Exception {
        //调用http接口，获取数据
        String url = "http://192.168.123.124:8080/qpe/getHAIPLocation?maxAge=" + maxAge;

//        String url = "http://localhost:8080/static/testData.txt";
        String responseStr = HttpUtils.httpGet(url);

        List<Map<String, Object>> responseMap = GsonUtils.fromJson(responseStr, List.class);

        //封装数据
        DecimalFormat df = new DecimalFormat("0.000");

        List<LocationModel> toAddLocationList = new ArrayList<LocationModel>();
        for (Map<String, Object> map : responseMap) {
            String tagId = (String) map.get("id");
            double positionX = (Double) map.get("positionX");
            double positionY = (Double) map.get("positionY");
            double positionZ = (Double) map.get("positionZ");
            double smoothedPositionX = (Double) map.get("smoothedPositionX");
            double smoothedPositionY = (Double) map.get("smoothedPositionY");
            double smoothedPositionZ = (Double) map.get("smoothedPositionZ");
            long responseTimestampEpoch = ((Double) map.get("responseTimestampEpoch")).longValue();
            long positionTimestampEpoch = ((Double) map.get("positionTimestampEpoch")).longValue();

            new Date().getTime();
            LocationModel locationModel = new LocationModel();
            locationModel.setTagId(tagId);
            locationModel.setPositionX(Double.parseDouble(df.format(positionX)));
            locationModel.setPositionY(Double.parseDouble(df.format(positionY)));
            locationModel.setPositionZ(Double.parseDouble(df.format(positionZ)));
            locationModel.setSmoothedPositionX(Double.parseDouble(df.format(smoothedPositionX)));
            locationModel.setSmoothedPositionY(Double.parseDouble(df.format(smoothedPositionY)));
            locationModel.setSmoothedPositionZ(Double.parseDouble(df.format(smoothedPositionZ)));
            locationModel.setResponseTimestampEpoch(responseTimestampEpoch);
            locationModel.setPositionTimestampEpoch(positionTimestampEpoch);

            toAddLocationList.add(locationModel);
        }

        //把数据入库
        this.locationDao.addBatch(toAddLocationList, LocationModel.class);
    }

    /**
     * 获取所有记录
     *
     * @return
     */
    public Page queryLocationList(Page page) {
        return this.locationDao.querylocationlist(page);
    }

    /**
     * 调整定时器设置
     * @param time
     * @param maxAge
     * @throws SchedulerException
     */
    public void adjustQuartzTime(int time, int maxAge) throws Exception {
        Scheduler scheduler = this.myScheduler.getScheduler();

        TriggerKey key = TriggerKey.triggerKey("myTrigger");
        SimpleTrigger trigger = (SimpleTrigger) scheduler.getTrigger(key);

        JobKey jobKey = trigger.getJobKey();
        JobDetail jobDetail = scheduler.getJobDetail(jobKey);
        jobDetail.getJobDataMap().put("maxAge", maxAge);

        TriggerBuilder<SimpleTrigger> triggerBuilder = trigger.getTriggerBuilder();

        SimpleScheduleBuilder simpleScheduleBuilder = SimpleScheduleBuilder.simpleSchedule();
        simpleScheduleBuilder.withIntervalInMilliseconds(time);
        simpleScheduleBuilder.repeatForever();

        Trigger newTrigger = triggerBuilder.withSchedule(simpleScheduleBuilder).build();

        scheduler.deleteJob(jobKey);
        scheduler.scheduleJob(jobDetail, newTrigger);

        //把定时器参数入库
        QuartzModel quartzModel = this.quartzService.queryRecord();
        quartzModel.setMaxAge(maxAge);
        quartzModel.setTime(time);
        this.quartzService.updateOne(quartzModel);
    }

    /**
     * 暂停任务
     */
    public void pauseQuartz() throws Exception {
        this.myScheduler.getScheduler().pauseAll();

        //把定时器参数入库
        QuartzModel quartzModel = this.quartzService.queryRecord();
        quartzModel.setStatus(QuartzModel.E_STATUS_PAUSE);
        this.quartzService.updateOne(quartzModel);
    }

    /**
     * 暂停任务
     */
    public void resumeQuartz() throws Exception {
        this.myScheduler.getScheduler().resumeAll();

        //把定时器参数入库
        QuartzModel quartzModel = this.quartzService.queryRecord();
        quartzModel.setStatus(QuartzModel.E_STATUS_RUNNING);
        this.quartzService.updateOne(quartzModel);
    }
}
