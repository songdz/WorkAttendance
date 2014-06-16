package com.songdz.fielddatacheck.app;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_dialog);
        setFinishOnTouchOutside(false);
        ActivitiesContainer.getInstance().addActivity(this);
        getWidget();
        setButtonOnClickListener();
    }

    private void setButtonOnClickListener() {
        btn_login_offline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getUsernamePassword();
                login_offline();
                et_password.setText(null);
            }
        });
        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivitiesContainer.getInstance().exitAllActivities();
            }
        });
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getUsernamePassword();
                login();
                et_password.setText(null);
            }
        });
    }

    private void getUsernamePassword() {
        UserInfo.username = et_username.getText().toString().trim();
        UserInfo.password = et_password.getText().toString().trim();
    }

    private void login_offline() {
        if(inputIsEmpty()) {
            return;
        }
        if(CheckPassword.checkPasswordOffline(LoginDialogActivity.this) == UserAuthority.INVALID_USER) {
            showAlertDialog(getString(R.string.invalid_username_password));
        } else {
            UserInfo.onlineState = OnlineState.OFFLINE;
            showAlertDialog("It's ok");
        }
    }
    android.os.Handler handler = new android.os.Handler();
    private void login() {
        if(inputIsEmpty()) {
            return;
        }
        loginingDialog();
        Thread loginThread = new Thread(new Runnable() {
            @Override
            public void run() {
                if(CheckPassword.checkPassword() == UserAuthority.INVALID_USER) {
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            progressDialog.cancel();
                            showAlertDialog(getString(R.string.invalid_username_password));
                        }
                    });
                    return;
                }
                if(ck_remember_password.isChecked()) {
                    SharedPreferencesHelper user_info = new SharedPreferencesHelper(LoginDialogActivity.this, "user_info");
                    user_info.putValue("username", UserInfo.username);
                    user_info.putValue("password", UserInfo.password);
                    user_info.putValue("authority", UserInfo.authority.ordinal() + "");
                }
                UserInfo.onlineState = OnlineState.OFFLINE;
                //Start Main Activity here
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        progressDialog.cancel();
                    }
                });
            }
        });
        loginThread.start();
    }

    private void loginingDialog() {
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("登陆");
        progressDialog.setMessage("正在登陆服务器，请稍后...");
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
