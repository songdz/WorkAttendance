package com.songdz.fielddatacheck.app;

import android.app.Activity;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.songdz.com.songdz.fielddatacheck.data.MetaDataInfo;
import com.songdz.com.songdz.fielddatacheck.data.SoilSensorInfo;
import com.songdz.com.songdz.fielddatacheck.parsers.MetaDataInfoXMLParser;
import com.songdz.util.ActivitiesContainer;

import org.xml.sax.SAXException;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;

import javax.xml.parsers.ParserConfigurationException;


public class ShowSensorMetaDataActivity extends Activity {

    private TitleBorderLayout titleBorderLayout;
    private TitleBorderLayout soilTitleBorderLayout;
    private EditText updateTime;
    private EditText installLocation;
    private EditText eastLongitude_degree;
    private EditText eastLongitude_minute;
    private EditText eastLongitude_second;
    private EditText northLatitude_degree;
    private EditText northLatitude_minute;
    private EditText northLatitude_second;
    private EditText altitude;
    private EditText ipv6_address;
    private EditText soilSensor_serialNum;
    private EditText soilSensor_buriedDepth;
    private EditText rainGauge_serialNum;
    private EditText rainGauge_installHeight;
    private EditText infraredSensor_serialNum;
    private EditText infraredSensor_installHeight;
    private EditText m_c2;
    private EditText m_c1;
    private EditText m_c0;
    private EditText b_c2;
    private EditText b_c1;
    private EditText b_c0;
    private EditText mSB_c2;
    private EditText mSB_c1;
    private EditText mSB_c0;
    private EditText bSB_c2;
    private EditText bSB_c1;
    private EditText bSB_c0;
    private EditText airSensor_serialNum;
    private EditText airSensor_installHeight;
    private EditText anemometer_serialNum;
    private EditText anemometer_installHeight;
    private Spinner spi_chooseSoilSensor;

    private MetaDataInfo metaDataInfo = null;

    private String metaData = "NO-1.xml";

    private File metaDataDir = new File(getFilesDir(), "MetaData");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_sensor_meta_data);
        ActivitiesContainer.getInstance().addActivity(this);
        try {
            metaDataInfo = new MetaDataInfoXMLParser().getMetaDataInfo(new File(metaDataDir, metaData));
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }
        setWidgetText();
        setSpinnerChangedListen();
    }

    private void setSpinnerChangedListen() {
        spi_chooseSoilSensor.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(metaDataInfo == null) {
                    Toast.makeText(ShowSensorMetaDataActivity.this, R.string.nodata, Toast.LENGTH_SHORT).show();
                    return;
                }
                switch (position) {
                    case 0:
                        setSoilSensorWidgetText(metaDataInfo.getSoilSensorInfo1());
                        break;
                    case 1:
                        setSoilSensorWidgetText(metaDataInfo.getSoilSensorInfo2());
                        break;
                    case 2:
                        setSoilSensorWidgetText(metaDataInfo.getSoilSensorInfo3());
                        break;
                    default:break;
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                if(metaDataInfo == null) {
                    Toast.makeText(ShowSensorMetaDataActivity.this, R.string.nodata, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void setSoilSensorWidgetText(SoilSensorInfo soilSernsorInfo) {
        soilSensor_serialNum.setText(soilSernsorInfo.getSerialNum());
        soilSensor_buriedDepth.setText("" + soilSernsorInfo.getBuriedDepth());
    }


    private void setWidgetText() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        titleBorderLayout.setTitle("" + titleBorderLayout.getTitle() + metaDataInfo.getSensorID());
        installLocation.setText(metaDataInfo.getInstallLocation());

        altitude.setText("" + metaDataInfo.getAltitude());
        ipv6_address.setText(metaDataInfo.getIpv6_address());


        rainGauge_serialNum.setText(metaDataInfo.getRainGauge().getSerialNum());
        rainGauge_installHeight.setText("" + metaDataInfo.getRainGauge().getInstallHeight());
        infraredSensor_serialNum.setText(metaDataInfo.getInfraredSensor().getSerialNum());
        infraredSensor_installHeight.setText("" + metaDataInfo.getInfraredSensor().getInstallHeight());
        m_c2.setText("" + metaDataInfo.getInfraredSensor().getM_c2());
        m_c1.setText("" + metaDataInfo.getInfraredSensor().getM_c1());
        m_c0.setText("" + metaDataInfo.getInfraredSensor().getM_c0());
        b_c2.setText("" + metaDataInfo.getInfraredSensor().getB_c2());
        b_c1.setText("" + metaDataInfo.getInfraredSensor().getB_c1());
        b_c0.setText("" + metaDataInfo.getInfraredSensor().getB_c0());
        mSB_c2.setText("" + metaDataInfo.getInfraredSensor().getmSB_c2());
        mSB_c1.setText("" + metaDataInfo.getInfraredSensor().getmSB_c1());
        mSB_c0.setText("" + metaDataInfo.getInfraredSensor().getmSB_c0());
        bSB_c2.setText("" + metaDataInfo.getInfraredSensor().getbSB_c2());
        bSB_c1.setText("" + metaDataInfo.getInfraredSensor().getbSB_c1());
        bSB_c0.setText("" + metaDataInfo.getInfraredSensor().getbSB_c0());
        airSensor_serialNum.setText(metaDataInfo.getAirSensor().getSerialNum());
        airSensor_installHeight.setText("" + metaDataInfo.getAirSensor().getInstallHeight());
        anemometer_serialNum.setText(metaDataInfo.getAnemometer().getSerialNum());
        anemometer_installHeight.setText("" + metaDataInfo.getAnemometer().getInstallHeight());
    }

    private void getWidget() {
        titleBorderLayout = (TitleBorderLayout)findViewById(R.id.titleBorderLayout);
        soilTitleBorderLayout = (TitleBorderLayout)findViewById(R.id.soilTitleBorderLayout);
        updateTime = (EditText)findViewById(R.id.updateTime);
        installLocation = (EditText)findViewById(R.id.installLocation);
        eastLongitude_degree = (EditText)findViewById(R.id.eastLongitude_degree);
        eastLongitude_minute = (EditText)findViewById(R.id.eastLongitude_minute);
        eastLongitude_second = (EditText)findViewById(R.id.eastLongitude_second);
        northLatitude_degree = (EditText)findViewById(R.id.northLatitude_degree);
        northLatitude_minute = (EditText)findViewById(R.id.northLatitude_minute);
        northLatitude_second = (EditText)findViewById(R.id.northLatitude_second);
        altitude = (EditText)findViewById(R.id.altitude);
        ipv6_address = (EditText)findViewById(R.id.ipv6_address);
        soilSensor_serialNum = (EditText)findViewById(R.id.soilSensor_serialNum);
        soilSensor_buriedDepth = (EditText)findViewById(R.id.soilSensor_buriedDepth);
        rainGauge_serialNum = (EditText)findViewById(R.id.rainGauge_serialNum);
        rainGauge_installHeight = (EditText)findViewById(R.id.rainGauge_installHeight);
        infraredSensor_serialNum = (EditText)findViewById(R.id.infraredSensor_serialNum);
        infraredSensor_installHeight = (EditText)findViewById(R.id.infraredSensor_installHeight);
        m_c2 = (EditText)findViewById(R.id.m_c2);
        m_c1 = (EditText)findViewById(R.id.m_c1);
        m_c0 = (EditText)findViewById(R.id.m_c0);
        b_c2 = (EditText)findViewById(R.id.b_c2);
        b_c1 = (EditText)findViewById(R.id.b_c1);
        b_c0 = (EditText)findViewById(R.id.b_c0);
        mSB_c2 = (EditText)findViewById(R.id.mSB_c2);
        mSB_c1 = (EditText)findViewById(R.id.mSB_c1);
        mSB_c0 = (EditText)findViewById(R.id.mSB_c0);
        bSB_c2 = (EditText)findViewById(R.id.bSB_c2);
        bSB_c1 = (EditText)findViewById(R.id.bSB_c1);
        bSB_c0 = (EditText)findViewById(R.id.bSB_c0);
        airSensor_serialNum = (EditText)findViewById(R.id.airSensor_serialNum);
        airSensor_installHeight = (EditText)findViewById(R.id.airSensor_installHeight);
        anemometer_serialNum = (EditText)findViewById(R.id.anemometer_serialNum);
        anemometer_installHeight = (EditText)findViewById(R.id.anemometer_installHeight);
        spi_chooseSoilSensor = (Spinner)findViewById(R.id.chooseSoilSensor);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_show_sensor_meta_data, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
