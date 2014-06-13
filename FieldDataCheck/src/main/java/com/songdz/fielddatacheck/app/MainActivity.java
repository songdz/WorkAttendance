package com.songdz.fielddatacheck.app;

import android.app.Activity;
import android.content.Entity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.songdz.util.SimpleHttpRequest;

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

    private Button btn_send;
    private TextView tv_show_result;

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
                /*Intent intent = new Intent();
                intent.setClass(MainActivity.this, ShowResultList.class);
                startActivity(intent);*/
            }
        });
    }
    android.os.Handler handler = new android.os.Handler();
    class NetworkRequest implements Runnable {
        String result = ResponseCode.WRONG_REQUEST;
        @Override
        public void run() {
            List<NameValuePair> paramList = new ArrayList<NameValuePair>();
            paramList.add(new BasicNameValuePair(Constants.request, RequestCode.CHECK_DATA));
            paramList.add(new BasicNameValuePair(Constants.username, "songdz"));
            paramList.add(new BasicNameValuePair(Constants.password, "songdz"));
            paramList.add(new BasicNameValuePair(Constants.querySql, RequestCode.querySql_checkData));
            HttpResponse response = SimpleHttpRequest.httpPostRequest(RequestCode.httpUrl_checkData, paramList);
            if ((response != null) && (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK)) {
                try {
                    result = EntityUtils.toString(response.getEntity());
                } catch (IOException e) {
                    e.printStackTrace();
                    result = ResponseCode.WRONG_REQUEST;
                }
            } else {
                result = ResponseCode.WRONG_REQUEST;
            }
            handler.post(new Runnable() {
                @Override
                public void run() {
                    tv_show_result.setText(result);
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Intent intent = new Intent();
                            intent.setClass(MainActivity.this, ShowResultList.class);
                            intent.putExtra(Constants.result, result);
                            startActivity(intent);
                        }
                    }, 3000);
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
