package com.songdz.com.songdz.fielddatacheck.data;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by SongDz on 2014/11/17.
 */
public class MetaDataList {

    private static MetaDataList metaDataList = new MetaDataList();

    private static String metaDataFile = "MetaData.xml";

    private List<MetaData> metaDatas = null;

    private long updateTime;

    private MetaDataList() {
        metaDatas = new LinkedList<MetaData>();
        updateMetaDataList();
    }

    public static MetaDataList getInstance() {
        return metaDataList;
    }

    public void updateMetaDataList() {

    }

    public MetaData getMetaData(int id) {
        for(MetaData metaData : metaDatas) {
            if(id == metaData.getId()) {
                return (MetaData)metaData.clone();
            }
        }
        return null;
    }

    public int getMetaDataCount() {
        return metaDatas.size();
    }

}
