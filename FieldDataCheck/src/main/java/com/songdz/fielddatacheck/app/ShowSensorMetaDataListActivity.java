package com.songdz.fielddatacheck.app;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.songdz.util.ActivitiesContainer;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class ShowSensorMetaDataListActivity extends ListActivity {

    private TextView sensorID;
    private TextView updateTime;

    private boolean  makeFileSystem() {

        File metaDataDir = new File(getFilesDir(), "MetaData");
        if(!metaDataDir.exists()) {
            if(metaDataDir.mkdir()) {
                Log.i("file", "Create Success!");
            } else {
                Log.i("file", "Create Failed!");
                return false;
            }
        }
        File[] files = metaDataDir.listFiles(new FilenameFilter() {
            @Override
            public boolean accept(File dir, String filename) {
                if(filename.equals("MetaData.xml")) return true;
                return false;
            }
        });
        if(files.length != 1) {
            Thread requestMetaDataThread = new Thread(new Runnable() {
                @Override
                public void run() {
                    requestMetaData();
                }
            });
            requestMetaDataThread.start();
        }
        return false;
    }

    private  void requestMetaData() {
        String requestUrl = "http://159.226.15.192:8081/FieldDataCheck/servlet/TestServlet?sourceFile=MetaData.xml";
        HttpGet httpGet = new HttpGet(requestUrl);
        DefaultHttpClient httpClient = new DefaultHttpClient();
        HttpResponse response;
        BufferedReader br = null;
        BufferedWriter bw = null;
        try {
            response = httpClient.execute(httpGet);
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                System.out.println("Get Response!!");
                HttpEntity resEntity = response.getEntity();
                System.out.println(resEntity.getContentLength());
                if(resEntity.getContentLength() == 0) return;
                InputStream in = resEntity.getContent();
                File outFile = new File(getFilesDir(), "MetaData/MetaData.xml");
                br = new BufferedReader(new InputStreamReader(in,"UTF-8"));
                bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(outFile), "UTF-8"));
                String line = null;
                while((line = br.readLine()) != null) {
                    bw.write(line + "\r\n");
                    System.out.println(line);
                }
                System.out.println("Read File");
            } else {
                System.out.println("It's Wrong!");
            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("FILE IO !!");
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if(br != null)
                    br.close();
                if(bw != null)
                    bw.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            httpGet.abort();
            httpClient.getConnectionManager().shutdown();
        }
    }

    public void updateList(View view) {
        Log.i("updateList", "updateList");
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_result);
        getWidget();
        setWidgetText();
        ActivitiesContainer.getInstance().addActivity(this);
        makeFileSystem();
        List<Map<String, String>> list = getListData();
        setAdapter(list);
    }

    private void getWidget() {
        sensorID = (TextView)findViewById(R.id.sensor_id);
        updateTime = (TextView)findViewById(R.id.update_time);
    }

    private void setWidgetText() {
        sensorID.setText(getText(R.string.title_sensor_id));
        updateTime.setText(getText(R.string.title_update_time));
    }

    private void setAdapter(List<Map<String, String>> list) {
        if(list == null || list.isEmpty()) {
            return;
        }
        SimpleAdapter simpleAdapter = new SimpleAdapter(this, list, R.layout.activity_show_result_item, new String[]{getString(R.string.title_sensor_id), getString(R.string.title_update_time)}, new int[]{R.id.node_id, R.id.last_time});
        setListAdapter(simpleAdapter);
    }

    private List<Map<String, String>> getListData() {
        List<Map<String, String>> list = null;
        Intent intent = getIntent();
        String result = intent.getStringExtra(Constants.result);
        if((result == null) || (result.equals("Wrong Request"))) {
            return list;
        }
        list = new ArrayList<Map<String, String>>();
        Map<String, String> map;
        try {
            /*if (result.length() > 1 && result.charAt(0) == 65279) {
                result = result.substring(1);
            }*/
            JSONArray jsonArray = new JSONArray(result);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                map = new HashMap<String, String>();
                map.put(getString(R.string.title_node), jsonObject.getString(getString(R.string.title_node)));
                map.put(getString(R.string.title_last_time), jsonObject.getString(getString(R.string.title_last_time)));
                list.add(map);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return list;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_show_sensor_meta_data_list, menu);
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
