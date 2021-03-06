package com.songdz.fielddatacheck.app;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.songdz.fielddatacheck.app.R;

import org.json.JSONException;
import org.json.JSONObject;

public class ShowLastDataActivity extends Activity {

    private TextView tv_realTime;
    private TextView tv_batteryVoltage;
    private TextView tv_solarVoltage;
    private TextView tv_infraredTemperature;
    private TextView tv_precipitation;
    private TextView tv_airTemperature;
    private TextView tv_airHumidity;
    private TextView tv_anemometerSpeed;
    private TextView tv_anemometerDirection;
    private TextView tv_salinity;
    private TextView tv_degreeCentigrade;
    private TextView tv_conductivity;
    private TextView tv_waterVolume;
    private Spinner spi_chooseSoilSensor;

    NodeDataInfo nodeDataInfo = null;


    private boolean HAS_DATA = false;

    private void getWidget() {
        tv_realTime = (TextView) findViewById(R.id.realTime);
        tv_batteryVoltage = (TextView) findViewById(R.id.batteryVoltage);
        tv_solarVoltage = (TextView) findViewById(R.id.solarVoltage);
        tv_infraredTemperature = (TextView) findViewById(R.id.infraredTemperature);
        tv_precipitation = (TextView) findViewById(R.id.precipitation);
        tv_airTemperature = (TextView) findViewById(R.id.airTemperature);
        tv_airHumidity = (TextView) findViewById(R.id.airHumidity);
        tv_anemometerSpeed = (TextView) findViewById(R.id.anemometerSpeed);
        tv_anemometerDirection = (TextView) findViewById(R.id.anemometerDirection);
        tv_salinity = (TextView) findViewById(R.id.salinity);
        tv_degreeCentigrade = (TextView) findViewById(R.id.degreeCentigrade);
        tv_conductivity = (TextView) findViewById(R.id.conductivity);
        tv_waterVolume = (TextView) findViewById(R.id.waterVolume);
        spi_chooseSoilSensor = (Spinner) findViewById(R.id.chooseSoilSensor);
    }

    private void setWidgetText() {
        if(!HAS_DATA)
            return ;
        tv_realTime.setText(nodeDataInfo.getRealTime());
        tv_batteryVoltage.setText(nodeDataInfo.getBatteryVoltage());
        tv_solarVoltage.setText(nodeDataInfo.getSolarVoltage());
        tv_infraredTemperature.setText(nodeDataInfo.getInfraredTemperature());
        tv_precipitation.setText(nodeDataInfo.getPrecipitation());
        tv_airTemperature.setText(nodeDataInfo.getAirTemperature());
        tv_airHumidity.setText(nodeDataInfo.getAirHumidity());
        tv_anemometerSpeed.setText(nodeDataInfo.getAnemometerSpeed());
        tv_anemometerDirection.setText(nodeDataInfo.getAnemometerDirection());
        //setSoilSensorWidgetText(nodeDataInfo.getSoilSensor1());
    }

    private void setSpinnerChangedListen() {
        spi_chooseSoilSensor.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(!HAS_DATA) {
                    Toast.makeText(ShowLastDataActivity.this, R.string.nodata, Toast.LENGTH_SHORT).show();
                    return;
                }
                switch (position) {
                    case 0:
                        setSoilSensorWidgetText(nodeDataInfo.getSoilSensor1());
                        break;
                    case 1:
                        setSoilSensorWidgetText(nodeDataInfo.getSoilSensor2());
                        break;
                    case 2:
                        setSoilSensorWidgetText(nodeDataInfo.getSoilSensor3());
                        break;
                    default:break;
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                if(!HAS_DATA) {
                    Toast.makeText(ShowLastDataActivity.this, R.string.nodata, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void setSoilSensorWidgetText(SoilSensor soilSensor) {
        tv_salinity.setText(soilSensor.getSalinity());
        tv_degreeCentigrade.setText(soilSensor.getDegreeCentigrade());
        tv_conductivity.setText(soilSensor.getConductivity());
        tv_waterVolume.setText(soilSensor.getWaterVolume());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_last_data);
        getWidget();
        getNodeDataInfo();
        if(HAS_DATA)
            setWidgetText();
        setSpinnerChangedListen();
    }

    private void getNodeDataInfo() {
        Intent intent = getIntent();
        String result = intent.getStringExtra(Constants.result);
        if (result == null) return;
        nodeDataInfo = new NodeDataInfo();
        try {
            JSONObject sensorJSON = new JSONObject(result);
            nodeDataInfo.setNodeNumber(sensorJSON.getString("nodeNumber"));
            nodeDataInfo.setRealTime(sensorJSON.getString("realTime"));
            nodeDataInfo.setBatteryVoltage(sensorJSON.getString("batteryVoltage"));
            nodeDataInfo.setSolarVoltage(sensorJSON.getString("solarVoltage"));
            nodeDataInfo.setInfraredTemperature(sensorJSON.getString("infraredTemperature"));
            nodeDataInfo.setAnemometerDirection(sensorJSON.getString("anemometerDirection"));
            nodeDataInfo.setAnemometerSpeed(sensorJSON.getString("anemometerSpeed"));
            nodeDataInfo.setAirTemperature(sensorJSON.getString("airTemperature"));
            nodeDataInfo.setAirHumidity(sensorJSON.getString("airHumidity"));
            nodeDataInfo.setPrecipitation(sensorJSON.getString("precipitation"));
            JSONObject soilSensorJSON1 = sensorJSON.getJSONObject("soilSensor1");
            SoilSensor soilSensor1 = new SoilSensor();
            soilSensor1.setDegreeCentigrade(soilSensorJSON1.getString("degreeCentigrade"));
            soilSensor1.setWaterVolume(soilSensorJSON1.getString("waterVolume"));
            soilSensor1.setSalinity(soilSensorJSON1.getString("salinity"));
            soilSensor1.setConductivity(soilSensorJSON1.getString("conductivity"));
            JSONObject soilSensorJSON2 = sensorJSON.getJSONObject("soilSensor2");
            SoilSensor soilSensor2 = new SoilSensor();
            soilSensor2.setDegreeCentigrade(soilSensorJSON2.getString("degreeCentigrade"));
            soilSensor2.setWaterVolume(soilSensorJSON2.getString("waterVolume"));
            soilSensor2.setSalinity(soilSensorJSON2.getString("salinity"));
            soilSensor2.setConductivity(soilSensorJSON2.getString("conductivity"));
            JSONObject soilSensorJSON3 = sensorJSON.getJSONObject("soilSensor1");
            SoilSensor soilSensor3 = new SoilSensor();
            soilSensor3.setDegreeCentigrade(soilSensorJSON3.getString("degreeCentigrade"));
            soilSensor3.setWaterVolume(soilSensorJSON3.getString("waterVolume"));
            soilSensor3.setSalinity(soilSensorJSON3.getString("salinity"));
            soilSensor3.setConductivity(soilSensorJSON3.getString("conductivity"));
            nodeDataInfo.setSoilSensor1(soilSensor1);
            nodeDataInfo.setSoilSensor2(soilSensor2);
            nodeDataInfo.setSoilSensor3(soilSensor3);
        } catch (JSONException e) {
            e.printStackTrace();
            nodeDataInfo = null;
            HAS_DATA = Boolean.FALSE;
            return;
        }
        HAS_DATA = Boolean.TRUE;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.show_last_data, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
