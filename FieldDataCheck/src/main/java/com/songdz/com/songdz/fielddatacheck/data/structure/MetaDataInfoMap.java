package com.songdz.com.songdz.fielddatacheck.data.structure;

import com.songdz.com.songdz.fielddatacheck.data.MetaData;
import com.songdz.com.songdz.fielddatacheck.data.MetaDataInfo;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by SongDz on 2014/11/26.
 */
public class MetaDataInfoMap {

    private static MetaDataInfoMap metaDataInfoMap = new MetaDataInfoMap();

    private Map<Integer, MetaDataInfo> metaDataInfos = null;

    public Map<Integer, MetaDataInfo> getMetaDataInfos() {
        return metaDataInfos;
    }

    private MetaDataInfoMap() {
        metaDataInfos = new HashMap<Integer, MetaDataInfo>();
    }

    public void addMetaDataInfo(int id, MetaDataInfo metaDataInfo) {
        metaDataInfos.put(id, metaDataInfo);
    }

    public static MetaDataInfoMap getInstance() {
        return metaDataInfoMap;
    }

    public MetaDataInfo getMetaDataInfo(int id) {
        return metaDataInfos.get(id);
    }

}
