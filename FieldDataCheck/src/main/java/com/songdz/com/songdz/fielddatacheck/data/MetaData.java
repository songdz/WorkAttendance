package com.songdz.com.songdz.fielddatacheck.data;

import java.util.Date;

/**
 * Created by SongDz on 2014/11/17.
 */
public class MetaData implements Cloneable {

    private int id;

    private long updateTime;

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

    public long getDate() {
        return updateTime;
    }

    public void setDate(long updateTime) {
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
}
