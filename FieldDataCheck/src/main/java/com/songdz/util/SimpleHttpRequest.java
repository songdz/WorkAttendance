package com.songdz.util;

import com.songdz.fielddatacheck.app.Constants;
import com.songdz.fielddatacheck.app.RequestCode;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.CoreConnectionPNames;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by SongDz on 2014/6/13.
 */
public class SimpleHttpRequest {
    private SimpleHttpRequest() {}
    public static HttpResponse httpPostRequest(String httpUrl, List<NameValuePair> paramList) {
        HttpResponse response = null;
        HttpPost request = new HttpPost(httpUrl);
        try {
            UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity(paramList, "UTF-8");
            request.setEntity(formEntity);
            HttpClient httpClient = new DefaultHttpClient();
            httpClient.getParams().setIntParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 1000);
            httpClient.getParams().setIntParameter(CoreConnectionPNames.SO_TIMEOUT, 5000);
            response = httpClient.execute(request);
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return response;
    }
}
