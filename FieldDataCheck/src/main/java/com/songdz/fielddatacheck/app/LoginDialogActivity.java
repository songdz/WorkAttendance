package com.songdz.fielddatacheck.app;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import com.songdz.util.ActivitiesContainer;
import com.songdz.util.SharedPreferencesHelper;

public class LoginDialogActivity extends Activity {

    private Button btn_login;
    private Button btn_login_offline;
    private Button btn_cancel;
    private EditText et_username;
    private EditText et_password;
    private ProgressDialog progressDialog;
    private CheckBox ck_remember_password;

    private static boolean ChangeUser = false;

    android.os.Handler handler = new android.os.Handler();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_dialog);
        setTitle(R.string.label_activity_login_dialog);
        setFinishOnTouchOutside(false);
        Intent intent = getIntent();
        ChangeUser = intent.getBooleanExtra("ChangeUser", false);
        if(!ChangeUser) {
            ActivitiesContainer.getInstance().exitAllActivities();
            ActivitiesContainer.getInstance().addActivity(this);
        }
        getWidget();
        setButtonOnClickListener();
    }

    @Override
    protected void onStart() {
        super.onStart();
        SharedPreferencesHelper user_info = new SharedPreferencesHelper(LoginDialogActivity.this, "user_info");
        et_username.setText(user_info.getValue("username"));
        et_password.setText(user_info.getValue("password"));
    }

    private void setButtonOnClickListener() {
        /*btn_login_offline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserInfo.onlineState = OnlineState.OFFLINE;
                login();
            }
        });*/
        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ChangeUser) {
                    LoginDialogActivity.this.finish();
                } else {
                    ActivitiesContainer.getInstance().exitAllActivities();
                    System.exit(0);
                }
            }
        });
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserInfo.onlineState = OnlineState.ONLINE;
                login();
            }
        });
    }

    private void getUsernamePassword() {
        UserInfo.username = et_username.getText().toString().trim();
        UserInfo.password = et_password.getText().toString().trim();
    }

    private void login() {
        getUsernamePassword();
        if(inputIsEmpty()) {
            return;
        }
        loginingDialog();
        et_password.setText(null);
        Thread loginThread = new Thread(new Runnable() {
            @Override
            public void run() {
                if(CheckPassword.checkPassword(LoginDialogActivity.this) == UserAuthority.INVALID_USER) {
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            progressDialog.cancel();
                            showAlertDialog(getString(R.string.invalid_username_password));
                        }
                    });
                    return;
                }
                if(UserInfo.onlineState == OnlineState.ONLINE) {
                    if(ck_remember_password.isChecked() && (UserInfo.authority.compareTo(UserAuthority.PRIVILEGED_USER) != 0 )) {
                        SharedPreferencesHelper user_info = new SharedPreferencesHelper(LoginDialogActivity.this, "user_info");
                        user_info.putValue("username", UserInfo.username);
                        user_info.putValue("password", UserInfo.password);
                        user_info.putValue("authority", UserInfo.authority.getAuthorityIntValue() + "");
                    }
                }
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        if(ChangeUser) {
                            ActivitiesContainer.getInstance().exitAllActivities();
                            ActivitiesContainer.getInstance().addActivity(LoginDialogActivity.this);
                        }
                        Intent intent = new Intent();
                        intent.setClass(LoginDialogActivity.this, MainActivity.class);
                        startActivity(intent);
                        progressDialog.cancel();
                        LoginDialogActivity.this.finish();
                    }
                });
            }
        });
        loginThread.start();
    }
//    private void login() {
//        UserInfo.username = "song";
//        UserInfo.password = "123";
//        UserInfo.authority = UserAuthority.valueOf(1);
//        SharedPreferencesHelper user_info = new SharedPreferencesHelper(LoginDialogActivity.this, "user_info");
//        user_info.putValue("username", UserInfo.username);
//        user_info.putValue("password", UserInfo.password);
//        user_info.putValue("authority", UserInfo.authority.ordinal() + "");
//    }

    private void loginingDialog() {
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("登陆");
        progressDialog.setMessage("正在登陆，请稍后...");
        progressDialog.setCanceledOnTouchOutside(false);
//        progressDialog.setCancelable(false);
        progressDialog.show();
    }

    private boolean inputIsEmpty() {
        if(UserInfo.username.isEmpty() || UserInfo.password.isEmpty()) {
            showAlertDialog(getString(R.string.not_empty_username_password));
            return true;
        }
        return false;
    }

    private void showAlertDialog(String text) {
        Dialog dialog = new AlertDialog.Builder(this)
                .setMessage(text)
                .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                }).create();
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
    }

    private void getWidget() {
        btn_login = (Button)findViewById(R.id.btn_login);
        btn_login_offline = (Button)findViewById(R.id.btn_login_offline);
        btn_cancel = (Button)findViewById(R.id.btn_cancel);
        et_username = (EditText)findViewById(R.id.et_username);
        et_password = (EditText)findViewById(R.id.et_password);
        ck_remember_password = (CheckBox)findViewById(R.id.ck_remember_password);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.login_dialog, menu);
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
