package com.songdz.fielddatacheck.app;

import java.io.Serializable;

/**
 * Created by SongDz on 2014/6/19.
 */
public class SoilSensor implements Serializable {

    //土壤传感器_摄氏温度
    private String degreeCentigrade;
    //土壤传感器_含水量体积比
    private String waterVolume;
    //土壤传感器_盐渍度
    private String salinity;
    //土壤传感器_电导率
    private String conductivity;

    public String getConductivity() {
        return conductivity;
    }
    public void setConductivity(String conductivity) {
        this.conductivity = conductivity;
    }
    public String getDegreeCentigrade() {
        return degreeCentigrade;
    }
    public void setDegreeCentigrade(String degreeCentigrade) {
        this.degreeCentigrade = degreeCentigrade;
    }
    public String getWaterVolume() {
        return waterVolume;
    }
    public void setWaterVolume(String waterVolume) {
        this.waterVolume = waterVolume;
    }
    public String getSalinity() {
        return salinity;
    }
    public void setSalinity(String salinity) {
        this.salinity = salinity;
    }

}
