package com.songdz.util;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by SongDz on 2014/6/12.
 */
public class SharedPreferencesHelper {
    SharedPreferences sp;
    SharedPreferences.Editor editor;

    Context context;

    public SharedPreferencesHelper(Context c,String name){
        context = c;
        sp = context.getSharedPreferences(name, Context.MODE_PRIVATE);
        editor = sp.edit();
    }

    public void putValue(String key, String value){
        editor = sp.edit();
        editor.putString(key, value);
        editor.commit();
    }

    public String getValue(String key){
        return sp.getString(key, null);
    }
}
