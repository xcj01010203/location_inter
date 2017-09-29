package com.cao.model;

/**
 * 定时器设置
 */
public class QuartzModel {
    public static final String TABLE_NAME = "t_quartz";

    public static final Integer E_STATUS_PAUSE = 0;   //暂停状态
    public static final Integer E_STATUS_RUNNING = 1; //运行中状态

    private Integer id;
    private Integer time;
    private Integer maxAge;
    private Integer status; //任务状态，0-暂停，1-运行中

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getTime() {
        return time;
    }

    public void setTime(Integer time) {
        this.time = time;
    }

    public Integer getMaxAge() {
        return maxAge;
    }

    public void setMaxAge(Integer maxAge) {
        this.maxAge = maxAge;
    }
}
