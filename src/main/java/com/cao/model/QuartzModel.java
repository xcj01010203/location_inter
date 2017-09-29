package com.cao.model;

/**
 * 定时器设置
 */
public class QuartzModel {
    public static final String TABLE_NAME = "t_quartz";
    private Integer id;
    private Integer time;
    private Integer maxAge;

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
