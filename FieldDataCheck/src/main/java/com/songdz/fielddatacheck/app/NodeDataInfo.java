package com.songdz.fielddatacheck.app;

import java.io.Serializable;

/**
 * Created by SongDz on 2014/6/19.
 */
public class NodeDataInfo implements Serializable {
    //项目内节点编号
    private String nodeNumber;
    //紧缩型时间传感器_实时时间
    private String realTime;
    //电池电压传感器_电压
    private String batteryVoltage;
    //太阳能电压传感器_电压
    private String solarVoltage;
    //红外温度传感器_表面温度
    private String infraredTemperature;
    //风速风向传感器_风向
    private String anemometerDirection;
    //风速风向传感器_风速
    private String anemometerSpeed;
    //空气温湿度传感器_空气温度
    private String airTemperature;
    //空气温湿度传感器_空气湿度
    private String airHumidity;
    //雨量传感器_降雨量
    private String precipitation;
    //土壤传感器1
    private SoilSensor soilSensor1;
    //土壤传感器2
    private SoilSensor soilSensor2;
    //土壤传感器3
    private SoilSensor soilSensor3;

    public NodeDataInfo() {

    }

    public String getNodeNumber() {
        return nodeNumber;
    }

    public void setNodeNumber(String nodeNumber) {
        this.nodeNumber = nodeNumber;
    }

    public String getRealTime() {
        return realTime;
    }

    public void setRealTime(String realTime) {
        this.realTime = realTime;
    }

    public String getBatteryVoltage() {
        return batteryVoltage;
    }

    public void setBatteryVoltage(String batteryVoltage) {
        this.batteryVoltage = batteryVoltage;
    }

    public String getSolarVoltage() {
        return solarVoltage;
    }

    public void setSolarVoltage(String solarVoltage) {
        this.solarVoltage = solarVoltage;
    }

    public String getInfraredTemperature() {
        return infraredTemperature;
    }

    public void setInfraredTemperature(String infraredTemperature) {
        this.infraredTemperature = infraredTemperature;
    }

    public String getAnemometerDirection() {
        return anemometerDirection;
    }

    public void setAnemometerDirection(String anemometerDirection) {
        this.anemometerDirection = anemometerDirection;
    }

    public String getAnemometerSpeed() {
        return anemometerSpeed;
    }

    public void setAnemometerSpeed(String anemometerSpeed) {
        this.anemometerSpeed = anemometerSpeed;
    }

    public String getAirTemperature() {
        return airTemperature;
    }

    public void setAirTemperature(String airTemperature) {
        this.airTemperature = airTemperature;
    }

    public String getAirHumidity() {
        return airHumidity;
    }

    public void setAirHumidity(String airHumidity) {
        this.airHumidity = airHumidity;
    }

    public String getPrecipitation() {
        return precipitation;
    }

    public void setPrecipitation(String precipitation) {
        this.precipitation = precipitation;
    }

    public SoilSensor getSoilSensor1() {
        return soilSensor1;
    }

    public void setSoilSensor1(SoilSensor soilSensor1) {
        this.soilSensor1 = soilSensor1;
    }

    public SoilSensor getSoilSensor2() {
        return soilSensor2;
    }

    public void setSoilSensor2(SoilSensor soilSensor2) {
        this.soilSensor2 = soilSensor2;
    }

    public SoilSensor getSoilSensor3() {
        return soilSensor3;
    }

    public void setSoilSensor3(SoilSensor soilSensor3) {
        this.soilSensor3 = soilSensor3;
    }
}
