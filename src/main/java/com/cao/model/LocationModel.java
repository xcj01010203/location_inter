package com.cao.model;

/**
 * 位置信息
 */
public class LocationModel {
    public static final String TABLE_NAME = "t_location";

    private int id;
    private String tagId;   //标签ID
    private double positionX;   //横坐标
    private double positionY;   //纵坐标
    private double positionZ;   //
    private double smoothedPositionX;   //滤波后的定位横坐标
    private double smoothedPositionY;   //滤波后的定位纵坐标
    private double smoothedPositionZ;   //
    private long responseTimestampEpoch;    //标签位置的请求时间
    private long positionTimestampEpoch;    //标签位置的读取时间

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTagId() {
        return tagId;
    }

    public void setTagId(String tagId) {
        this.tagId = tagId;
    }

    public double getPositionX() {
        return positionX;
    }

    public void setPositionX(double positionX) {
        this.positionX = positionX;
    }

    public double getPositionY() {
        return positionY;
    }

    public void setPositionY(double positionY) {
        this.positionY = positionY;
    }

    public double getPositionZ() {
        return positionZ;
    }

    public void setPositionZ(double positionZ) {
        this.positionZ = positionZ;
    }

    public double getSmoothedPositionX() {
        return smoothedPositionX;
    }

    public void setSmoothedPositionX(double smoothedPositionX) {
        this.smoothedPositionX = smoothedPositionX;
    }

    public double getSmoothedPositionY() {
        return smoothedPositionY;
    }

    public void setSmoothedPositionY(double smoothedPositionY) {
        this.smoothedPositionY = smoothedPositionY;
    }

    public double getSmoothedPositionZ() {
        return smoothedPositionZ;
    }

    public void setSmoothedPositionZ(double smoothedPositionZ) {
        this.smoothedPositionZ = smoothedPositionZ;
    }

    public long getResponseTimestampEpoch() {
        return responseTimestampEpoch;
    }

    public void setResponseTimestampEpoch(long responseTimestampEpoch) {
        this.responseTimestampEpoch = responseTimestampEpoch;
    }

    public long getPositionTimestampEpoch() {
        return positionTimestampEpoch;
    }

    public void setPositionTimestampEpoch(long positionTimestampEpoch) {
        this.positionTimestampEpoch = positionTimestampEpoch;
    }
}
