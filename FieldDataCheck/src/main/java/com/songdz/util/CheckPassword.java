package com.songdz.util;

import com.songdz.fielddatacheck.app.Constants;
import com.songdz.fielddatacheck.app.RequestCode;
import com.songdz.fielddatacheck.app.ResponseCode;
import com.songdz.fielddatacheck.app.UserAuthority;

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
    public UserAuthority checkPassword(String httpUrl, String username, String password) {
        UserAuthority userAuthority = UserAuthority.INVALID_USER;
        List<NameValuePair> paramList = new ArrayList<NameValuePair>();
        paramList.add(new BasicNameValuePair(Constants.request, RequestCode.CHECK_PASSWORD));
        paramList.add(new BasicNameValuePair(Constants.username, username));
        paramList.add(new BasicNameValuePair(Constants.password, password));
        HttpResponse response = SimpleHttpRequest.httpPostRequest(httpUrl_checkPassword, paramList);
        if ((response != null) && (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK)) {
            try {
                userAuthority = UserAuthority.valueOf(Integer.valueOf(EntityUtils.toString(response.getEntity())));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return userAuthority;
    }
}
