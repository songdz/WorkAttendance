package com.songdz.workattendance.attendance;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.songdz.util.ActivitiesContainer;


public class Attendance extends Activity {

    private static final int Menu_ChangeUser = Menu.FIRST;
    private static final int Menu_AlarmClock = Menu.FIRST + 1;
    private static final int Menu_About = Menu.FIRST + 2;
    private static final int Menu_Exit = Menu.FIRST + 3;

    private TextView textView_showAttendanceInfo;
    private ImageView imageView_attendanceShow;
    private Button button_attendance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendance);
        ActivitiesContainer.getInstance().addActivity(this);

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
        menu.add(0, Menu_ChangeUser, 0, R.string.menu_change_user);
        menu.add(0, Menu_AlarmClock, 0, R.string.menu_alarm_clock);
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
            case Menu_ChangeUser: break;
            case Menu_AlarmClock: break;
            case Menu_About: break;
            case Menu_Exit: ActivitiesContainer.getInstance().exitAllActivities();
                              break;
        }
        return super.onOptionsItemSelected(item);
    }

    private class AboutAlertDialog extends AlertDialog {

        protected AboutAlertDialog(Context context) {
            super(context);
        }


    }
}
