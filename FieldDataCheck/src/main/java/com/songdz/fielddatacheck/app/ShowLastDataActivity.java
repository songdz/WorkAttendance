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


    private static boolean HAS_DATA = false;

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
        Intent intent = getIntent();
        nodeDataInfo = (NodeDataInfo)intent.getSerializableExtra("nodeDataInfo");
        if(nodeDataInfo != null)
            HAS_DATA = true;
        getWidget();
        setSpinnerChangedListen();
        setWidgetText();
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
