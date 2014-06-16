package com.songdz.fielddatacheck.app;

import android.app.Application;

/**
 * Created by SongDz on 2014/6/16.
 */
public class UserInfo extends Application {
    public static String username = null;
    public static String password = null;
    public static UserAuthority authority = UserAuthority.INVALID_USER;
    public static OnlineState onlineState = OnlineState.OFFLINE;
}
