package com.songdz.com.songdz.fielddatacheck.data;

import java.util.Date;

/**
 * Created by SongDz on 2014/11/17.
 */
public class MetaData implements Cloneable {

    private int id;

    private long updateTime;

    public long getDeviceCheckTime() {
        return deviceCheckTime;
    }

    public void setDeviceCheckTime(long deviceCheckTime) {
        this.deviceCheckTime = deviceCheckTime;
    }

    private long deviceCheckTime;

    private String fileName;

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public long getupdateTime() {
        return updateTime;
    }

    public void setupdateTime(long updateTime) {
        this.updateTime = updateTime;
    }

    public Object clone() {
        MetaData metaData = null;
        try {
            metaData = (MetaData)super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return metaData;
    }

    @Override
    public String toString() {
        return "ID = " + id + "FileName = " + fileName;
    }
}
