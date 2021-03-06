package com.songdz.fielddatacheck.app;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Entity;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

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
    private Button btn_get_last_data;
    private TextView tv_show_user;

    private ProgressDialog progressDialog;

    android.os.Handler handler = new android.os.Handler();

    private long exitTime = 0;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN){
            if((System.currentTimeMillis()-exitTime) > 2000){
                Toast.makeText(getApplicationContext(), "再按一次退出程序", Toast.LENGTH_SHORT).show();
                exitTime = System.currentTimeMillis();
            } else {
                ActivitiesContainer.getInstance().exitAllActivities();
                System.exit(0);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
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
                processingDialog();
                Thread networkRequest = new Thread(new NetworkRequest(RequestCode.CHECK_DATA_LAST_TIME, RequestCode.querySql_getLastTime, ShowResultList.class));
                networkRequest.start();
            }
        });
        btn_get_last_data.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                processingDialog();
                inputQueryIdDialog();
            }
        });
    }
    private void getWidget() {
        btn_get_last_time = (Button)findViewById(R.id.button_get_last_time);
        btn_get_last_data = (Button)findViewById(R.id.button_get_last_data);
        tv_show_user = (TextView)findViewById(R.id.textView_show_user);
    }

    class NetworkRequest implements Runnable {
        String requestCode;
        String result = ResponseCode.WRONG_REQUEST;
        String querySql;
        Class jumpClass;
        public NetworkRequest(String requestCode, String querySql, Class jumpClass) {
            this.requestCode = requestCode;
            this.querySql = querySql;
            this.jumpClass = jumpClass;
        }
        @Override
        public void run() {
            List<NameValuePair> paramList = new ArrayList<NameValuePair>();
            paramList.add(new BasicNameValuePair(Constants.request, requestCode));
            paramList.add(new BasicNameValuePair(Constants.username, UserInfo.username));
            paramList.add(new BasicNameValuePair(Constants.password, UserInfo.password));
            paramList.add(new BasicNameValuePair(Constants.querySql, querySql));
            HttpResponse response = SimpleHttpRequest.httpPostRequest(RequestCode.httpUrl_checkData, paramList);
            if ((response != null) && (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK)) {
                try {
                    result = EntityUtils.toString(response.getEntity());
         System.out.println("MainActivity:"+result);
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
/*                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {*/
                    if(!ResponseCode.WRONG_REQUEST.equals(result)) {
                        Intent intent = new Intent();
                        intent.setClass(MainActivity.this, jumpClass);
                        intent.putExtra(Constants.result, result);
                        startActivity(intent);
                    } else {
                        Toast.makeText(MainActivity.this, ResponseCode.WRONG_REQUEST, Toast.LENGTH_SHORT).show();
                    }
                    progressDialog.cancel();
                }
//                    }, 3000);
//                }
            });
        }
    }

    private void inputQueryIdDialog() {
        final EditText et_input = new EditText(this);
        et_input.setInputType(InputType.TYPE_CLASS_NUMBER);
        et_input.setHint("节点编号");
        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        et_input.setLayoutParams(layoutParams);
        et_input.setGravity(Gravity.CENTER);
        AlertDialog alertDialog = new AlertDialog.Builder(this)
                .setTitle("输入查询节点的项目内编号")
                .setView(et_input)
                .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String input = et_input.getText().toString();
                        if (input.isEmpty()) {
                            Toast.makeText(MainActivity.this, "输入不能为空！", Toast.LENGTH_SHORT).show();
                        } else {
                            String querySql = RequestCode.querySql_getLastData.replace("?", input);
                            Thread networkRequest = new Thread(new NetworkRequest(RequestCode.CHECK_DATA_LAST_DATA, querySql, ShowLastDataActivity.class));
                            networkRequest.start();
                            System.out.println(input);
                        }
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .create();
        alertDialog.setCanceledOnTouchOutside(false);
        alertDialog.show();
    }

    private void processingDialog() {
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("处理");
        progressDialog.setMessage("正在处理，请稍后...");
        progressDialog.setCanceledOnTouchOutside(false);
//        progressDialog.setCancelable(false);
        progressDialog.show();
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
