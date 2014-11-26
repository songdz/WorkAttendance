package com.songdz.com.songdz.fielddatacheck.data;

/**
 * Created by SongDz on 2014/11/17.
 */
public class MetaDataInfo {
    private int sensorID;
    private String installLocation;
    private String eastLongitude;
    private String northLatitude;
    private double altitude;
    private String ipv6_address;
    private SoilSensorInfo soilSensorInfo1;
    private SoilSensorInfo soilSensorInfo2;
    private SoilSensorInfo soilSensorInfo3;
    private RainGauge rainGauge;
    private InfraredSensor infraredSensor;
    private AirSensor airSensor;
    private Anemometer anemometer;

    public int getSensorID() {
        return sensorID;
    }

    public void setSensorID(int sensorID) {
        this.sensorID = sensorID;
    }

    public String getInstallLocation() {
        return installLocation;
    }

    public void setInstallLocation(String installLocation) {
        this.installLocation = installLocation;
    }

    public String getEastLongitude() {
        return eastLongitude;
    }

    public void setEastLongitude(String eastLongitude) {
        this.eastLongitude = eastLongitude;
    }

    public String getNorthLatitude() {
        return northLatitude;
    }

    public void setNorthLatitude(String northLatitude) {
        this.northLatitude = northLatitude;
    }

    public double getAltitude() {
        return altitude;
    }

    public void setAltitude(double altitude) {
        this.altitude = altitude;
    }

    public String getIpv6_address() {
        return ipv6_address;
    }

    public void setIpv6_address(String ipv6_address) {
        this.ipv6_address = ipv6_address;
    }

    public SoilSensorInfo getSoilSensorInfo1() {
        return soilSensorInfo1;
    }

    public void setSoilSensorInfo1(SoilSensorInfo soilSensorInfo1) {
        this.soilSensorInfo1 = soilSensorInfo1;
    }

    public SoilSensorInfo getSoilSensorInfo2() {
        return soilSensorInfo2;
    }

    public void setSoilSensorInfo2(SoilSensorInfo soilSensorInfo2) {
        this.soilSensorInfo2 = soilSensorInfo2;
    }

    public SoilSensorInfo getSoilSensorInfo3() {
        return soilSensorInfo3;
    }

    public void setSoilSensorInfo3(SoilSensorInfo soilSensorInfo3) {
        this.soilSensorInfo3 = soilSensorInfo3;
    }

    public RainGauge getRainGauge() {
        return rainGauge;
    }

    public void setRainGauge(RainGauge rainGauge) {
        this.rainGauge = rainGauge;
    }

    public InfraredSensor getInfraredSensor() {
        return infraredSensor;
    }

    public void setInfraredSensor(InfraredSensor infraredSensor) {
        this.infraredSensor = infraredSensor;
    }

    public AirSensor getAirSensor() {
        return airSensor;
    }

    public void setAirSensor(AirSensor airSensor) {
        this.airSensor = airSensor;
    }

    public Anemometer getAnemometer() {
        return anemometer;
    }

    public void setAnemometer(Anemometer anemometer) {
        this.anemometer = anemometer;
    }
}
