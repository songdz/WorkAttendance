package com.songdz.fielddatacheck.app;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.SimpleAdapter;

import com.songdz.util.ActivitiesContainer;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class ShowResultList extends ListActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_result);
        ActivitiesContainer.getInstance().addActivity(this);
        List<Map<String, String>> list = getListData();
        setAdapter(list);
    }

    private void setAdapter(List<Map<String, String>> list) {
        if(list == null || list.isEmpty()) {
            return;
        }
        SimpleAdapter simpleAdapter = new SimpleAdapter(this, list, R.layout.activity_show_result_item, new String[]{getString(R.string.title_node), getString(R.string.title_last_time)}, new int[]{R.id.node_id, R.id.last_time});
        setListAdapter(simpleAdapter);
    }

    private List<Map<String, String>> getListData() {
        List<Map<String, String>> list = null;
        Intent intent = getIntent();
        String result = intent.getStringExtra("result");
        if(result.equals("Wrong Request")) {
            return list;
        }
        list = new ArrayList<Map<String, String>>();
        Map<String, String> map;
        try {
            /*if (result.length() > 1 && result.charAt(0) == 65279) {
                result = result.substring(1);
            }*/
            JSONArray jsonArray = new JSONArray(result);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                map = new HashMap<String, String>();
                map.put(getString(R.string.title_node), jsonObject.getString(getString(R.string.title_node)));
                map.put(getString(R.string.title_last_time), jsonObject.getString(getString(R.string.title_last_time)));
                list.add(map);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return list;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.show_result_list, menu);
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
