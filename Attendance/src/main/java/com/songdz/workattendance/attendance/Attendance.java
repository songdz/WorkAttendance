package com.songdz.workattendance.attendance;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;



public class Attendance extends Activity {

    private TextView textView_showAttendanceInfo;
    private ImageView imageView_attendanceShow;
    private Button button_attendance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendance);

        textView_showAttendanceInfo = (TextView)this.findViewById(R.id.textView_showAttendanceInfo);
        imageView_attendanceShow = (ImageView)this.findViewById(R.id.imageView_attendanceShow);
        button_attendance = (Button)this.findViewById(R.id.button_attendance);
        ViewTreeObserver vto = imageView_attendanceShow.getViewTreeObserver();
        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                int height = imageView_attendanceShow.getMeasuredHeight();
                int width = imageView_attendanceShow.getMeasuredWidth();
                System.out.println("height:" + height + "  " + "width:" + width);
                textView_showAttendanceInfo.setWidth(width/2);
                textView_showAttendanceInfo.setHeight(height);
                imageView_attendanceShow.getViewTreeObserver().removeGlobalOnLayoutListener(this);
            }
        });
        button_attendance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textView_showAttendanceInfo.setText("SongDz\n上班打卡\n时间");
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        textView_showAttendanceInfo.setText(R.string.hello_world);
                    }
                }, 2000);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.attendance, menu);
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
