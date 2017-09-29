package com.cao.controller;

import com.cao.common.Page;
import com.cao.model.QuartzModel;
import com.cao.service.LocationService;
import com.cao.service.QuartzService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * 位置信息
 */
@RestController
@RequestMapping("/location")
public class LocationController {

    private Logger logger = LoggerFactory.getLogger(LocationController.class);

    @Autowired
    private LocationService locationService;

    @Autowired
    private QuartzService quartzService;

    /**
     * 同步位置信息
     *
     * @param maxAge
     */
    @RequestMapping("/syncLocationData")
    public Map<String, Object> syncLocationData(Integer maxAge) {
        Map<String, Object> resultMap = new HashMap<String, Object>();

        boolean success = true;
        String message = "";
        try {
            if (maxAge == null) {
                maxAge = 100;
            }
            this.locationService.syncLocationData(maxAge);
        } catch (Exception e) {
            success = false;
            message = "出现错误";
            logger.error("出现错误", e);
        }

        resultMap.put("success", success);
        resultMap.put("message", message);
        return resultMap;
    }

    /**
     * 获取位置信息列表
     *
     * @return
     */
    @RequestMapping("/queryLocationData")
    public Map<String, Object> queryLocationData(Page page) {
        Map<String, Object> resultMap = new HashMap<String, Object>();

        boolean success = true;
        String message = "";
        try {

            resultMap.put("locationList", this.locationService.queryLocationList(page));
        } catch (Exception e) {
            success = false;
            message = "出现错误";
            logger.error("出现错误", e);
        }

        resultMap.put("success", success);
        resultMap.put("message", message);
        return resultMap;
    }

    /**
     * 调整定时器时间
     * @param time
     * @return
     */
    @RequestMapping("/adjustQuartzTime")
    public Map<String, Object> adjustQuartzTime(Integer time, Integer maxAge) {
        Map<String, Object> resultMap = new HashMap<String, Object>();

        boolean success = true;
        String message = "";
        try {
            if (time == null) {
                throw new IllegalArgumentException("请选择执行时间");
            }
            if (maxAge == null) {
                throw new IllegalArgumentException("请输入maxAge");
            }
            this.locationService.adjustQuartzTime(time, maxAge);

        } catch (Exception e) {
            success = false;
            message = "出现错误";
            logger.error("出现错误", e);
        }

        resultMap.put("success", success);
        resultMap.put("message", message);
        return resultMap;
    }

    /**
     * 暂停任务
     * @param time
     * @param maxAge
     * @return
     */
    @RequestMapping("/pauseQuartz")
    public Map<String, Object> pauseQuartz(Integer time, Integer maxAge) {
        Map<String, Object> resultMap = new HashMap<String, Object>();

        boolean success = true;
        String message = "";
        try {
            this.locationService.pauseQuartz();

        } catch (Exception e) {
            success = false;
            message = "出现错误";
            logger.error("出现错误", e);
        }

        resultMap.put("success", success);
        resultMap.put("message", message);
        return resultMap;
    }

    /**
     * 重新启动任务
     * @param time
     * @param maxAge
     * @return
     */
    @RequestMapping("/resumeQuartz")
    public Map<String, Object> resumeQuartz(Integer time, Integer maxAge) {
        Map<String, Object> resultMap = new HashMap<String, Object>();

        boolean success = true;
        String message = "";
        try {
            this.locationService.resumeQuartz();

        } catch (Exception e) {
            success = false;
            message = "出现错误";
            logger.error("出现错误", e);
        }

        resultMap.put("success", success);
        resultMap.put("message", message);
        return resultMap;
    }

    /**
     * 查询定时器信息
     * @return
     */
    @RequestMapping("/queryQuartz")
    public Map<String, Object> queryQuartz() {
        Map<String, Object> resultMap = new HashMap<String, Object>();

        boolean success = true;
        String message = "";
        try {
            QuartzModel quartzModel = this.quartzService.queryRecord();
            resultMap.put("time", quartzModel.getTime());
            resultMap.put("maxAge", quartzModel.getMaxAge());
            resultMap.put("status", quartzModel.getStatus());
        } catch (Exception e) {
            success = false;
            message = "出现错误";
            logger.error("出现错误", e);
        }

        resultMap.put("success", success);
        resultMap.put("message", message);
        return resultMap;
    }
}
