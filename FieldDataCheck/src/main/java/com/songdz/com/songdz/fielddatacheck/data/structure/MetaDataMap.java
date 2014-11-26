package com.songdz.com.songdz.fielddatacheck.data.structure;

import com.songdz.com.songdz.fielddatacheck.data.MetaData;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by SongDz on 2014/11/17.
 */
public class MetaDataMap {

    private static MetaDataMap metaDataMap = new MetaDataMap();

    public Map<Integer, MetaData> getMetaDatas() {
        return metaDatas;
    }

    private Map<Integer, MetaData> metaDatas = null;

    private long updateTime;

    private int count;

    public static MetaDataMap getInstance() {
        return metaDataMap;
    }

    public long getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(long updateTime) {
        this.updateTime = updateTime;
    }

    private MetaDataMap() {
        metaDatas = new HashMap<Integer, MetaData>();
    }

    public void addMetaData(int id, MetaData metaData) {
        metaDatas.put(id, metaData);
    }

    public int getMetaDataCount() {
        return count;
    }

    public void setMetaDataCount(int count) {
        this.count = count;
    }

    public MetaData getMetaData(int id ) {
        return (MetaData)metaDatas.get(id);
    }
}
