package com.songdz.fielddatacheck.app;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Entity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.songdz.util.ActivitiesContainer;
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

    private static final int Menu_ChangeUser = Menu.FIRST;
    private static final int Menu_About = Menu.FIRST + 1;
    private static final int Menu_Logout = Menu.FIRST + 2;
    private static final int Menu_Exit = Menu.FIRST + 3;

    private Button btn_get_last_time;
    private TextView tv_show_user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ActivitiesContainer.getInstance().addActivity(this);
        getWidget();
        tv_show_user.setText("  Hi, " + UserInfo.username + "  ");
        setButtonOnClickListener();
    }

    private void setButtonOnClickListener() {
        btn_get_last_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Thread networkRequest = new Thread(new NetworkRequest());
                networkRequest.start();
            }
        });
    }
    private void getWidget() {
        btn_get_last_time = (Button)findViewById(R.id.button_get_last_time);
        tv_show_user = (TextView)findViewById(R.id.textView_show_user);
    }
    android.os.Handler handler = new android.os.Handler();
    class NetworkRequest implements Runnable {
        String result = ResponseCode.WRONG_REQUEST;
        @Override
        public void run() {
            List<NameValuePair> paramList = new ArrayList<NameValuePair>();
            paramList.add(new BasicNameValuePair(Constants.request, RequestCode.CHECK_DATA));
            paramList.add(new BasicNameValuePair(Constants.username, UserInfo.username));
            paramList.add(new BasicNameValuePair(Constants.password, UserInfo.password));
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
        menu.add(0, Menu_ChangeUser, 0, R.string.menu_change_user);
        menu.add(0, Menu_Logout, 0, R.string.menu_logout);
        menu.add(0, Menu_About, 0, R.string.menu_about);
        menu.add(0, Menu_Exit, 0, R.string.menu_exit);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        switch (id) {
            case Menu_ChangeUser:
                changeUser();
                break;
            case Menu_Logout:
                logout();
                break;
            case Menu_About:
                showAboutDialog();
                break;
            case Menu_Exit:
                ActivitiesContainer.getInstance().exitAllActivities();
                System.exit(0);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void changeUser() {
        Intent intent = new Intent();
        intent.setClass(MainActivity.this, LoginDialogActivity.class);
        intent.putExtra("ChangeUser", true);
        startActivity(intent);
    }

    private void logout() {
        Intent intent = new Intent();
        intent.setClass(MainActivity.this, LoginDialogActivity.class);
        startActivity(intent);
    }

    private void showAboutDialog() {
        AlertDialog dialog = new  AlertDialog.Builder(this)
                .setTitle(R.string.about_author)
                .setMessage(R.string.author_info)
                .setIcon(R.drawable.author)
                .setPositiveButton(R.string.ok, null).create();
        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(false);
        dialog.show();
    }
}
