package com.songdz.util;

import android.app.Activity;
import android.app.Application;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by SongDz on 2014/5/7.
 */
public class ActivitiesContainer extends Application {

    private static ActivitiesContainer activitiesContainer = new ActivitiesContainer();

    private List<Activity> activitiesList = new ArrayList<Activity>();

    public static ActivitiesContainer getInstance() {
        return activitiesContainer;
    }

    public void addActivity(Activity activity) {
        activitiesList.add(activity);
    }

    public void exitAllActivities() {
        for(Activity activity : activitiesList) {
            if(activity != null) {
                activity.finish();
            }
        }
        System.exit(0);
    }

}
