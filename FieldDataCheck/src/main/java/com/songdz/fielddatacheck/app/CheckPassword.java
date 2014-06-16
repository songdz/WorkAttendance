package com.songdz.fielddatacheck.app;

import android.content.Context;

import com.songdz.fielddatacheck.app.Constants;
import com.songdz.fielddatacheck.app.RequestCode;
import com.songdz.fielddatacheck.app.ResponseCode;
import com.songdz.fielddatacheck.app.UserAuthority;
import com.songdz.util.SharedPreferencesHelper;
import com.songdz.util.SimpleHttpRequest;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by SongDz on 2014/6/13.
 * return User Type: privileged user, super user, common user.
 */
public class CheckPassword implements RequestCode, ResponseCode {

    public static UserAuthority checkPassword() {
        List<NameValuePair> paramList = new ArrayList<NameValuePair>();
        paramList.add(new BasicNameValuePair(Constants.request, CHECK_PASSWORD));
        paramList.add(new BasicNameValuePair(Constants.username, UserInfo.username));
        paramList.add(new BasicNameValuePair(Constants.password, UserInfo.password));
        HttpResponse response = SimpleHttpRequest.httpPostRequest(httpUrl_checkPassword, paramList);
        if ((response != null) && (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK)) {
            try {
                UserInfo.authority = UserAuthority.valueOf(Integer.valueOf(EntityUtils.toString(response.getEntity())));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return UserInfo.authority;
    }

    public static UserAuthority checkPasswordOffline(Context c) {
        SharedPreferencesHelper user_info = new SharedPreferencesHelper(c, "user_info");
        String sp_username = user_info.getValue("username");
        String sp_password = user_info.getValue("password");
        String sp_authority = user_info.getValue("authority");
        if(UserInfo.username.equals(sp_username) && UserInfo.password.equals(sp_password)) {
            UserInfo.authority = UserAuthority.valueOf(Integer.valueOf(sp_authority));
            return UserInfo.authority;
        } else {
            return UserAuthority.INVALID_USER;
        }
    }
}
