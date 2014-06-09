package com.songdz.fielddatacheck.app;

import android.app.Activity;
import android.content.Entity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Handler;


public class MainActivity extends Activity {

    Button btn_send;
    TextView tv_show_result;
    String httpUrl = "http://159.226.15.192:8080/FieldDataCheck/CheckData";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn_send = (Button)findViewById(R.id.request);
        tv_show_result = (TextView)findViewById(R.id.show_result);

        btn_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Thread networkRequest = new Thread(new NetworkRequest());
                networkRequest.start();
            }
        });

        /*btn_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String httpUrl = "http://localhost:8080/FieldDataCheck/CheckData";
                HttpPost request = new HttpPost(httpUrl);
                List<NameValuePair> paramList = new ArrayList<NameValuePair>();
                paramList.add(new BasicNameValuePair("request", "1"));
                paramList.add(new BasicNameValuePair("username", "songdz"));
                paramList.add(new BasicNameValuePair("password", "songdz"));
                paramList.add(new BasicNameValuePair("querySql", "SELECT 项目内节点编号,MAX(紧缩型时间传感器_实时时间) FROM 水文监测仪lzipv6 GROUP BY 项目内节点编号"));
                try {
                    UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity(paramList, "UTF-8");
                    request.setEntity(formEntity);
                    HttpClient httpClient = new DefaultHttpClient();
                    HttpResponse response = httpClient.execute(request);
                    if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                        String result = EntityUtils.toString(response.getEntity());
                        tv_show_result.setText(result);
                    }
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                } catch (ClientProtocolException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        });*/
    }
    android.os.Handler handler = new android.os.Handler();
    class NetworkRequest implements Runnable {
        String result = "Wrong Request";
        @Override
        public void run() {
            HttpPost request = new HttpPost(httpUrl);
            List<NameValuePair> paramList = new ArrayList<NameValuePair>();
            paramList.add(new BasicNameValuePair("request", "1"));
            paramList.add(new BasicNameValuePair("username", "songdz"));
            paramList.add(new BasicNameValuePair("password", "songdz"));
            paramList.add(new BasicNameValuePair("querySql", "SELECT 项目内节点编号,MAX(紧缩型时间传感器_实时时间) FROM 水文监测仪lzipv6 GROUP BY 项目内节点编号"));
            try {
                UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity(paramList, "UTF-8");
                request.setEntity(formEntity);
                HttpClient httpClient = new DefaultHttpClient();
                httpClient.getParams().setIntParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 1000);
                HttpResponse response = httpClient.execute(request);
                if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                    result = EntityUtils.toString(response.getEntity());
                } else {
                    result = "Wrong Request";
                }
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            } catch (ClientProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            handler.post(new Runnable() {
                @Override
                public void run() {
                    tv_show_result.setText(result);
                }
            });
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
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
