package com.androiddev.zf.devframe;

import android.app.Activity;
import android.app.Application;

import com.androiddev.zf.devframe.utils.ValidateUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by greedy on 17/3/31.
 */

public class App extends Application {

    public static App sApplication;

    private static final List<Activity> ACTIVITIES = new ArrayList<Activity>();

    public static List<Activity> getActivities() {
        return ACTIVITIES;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        sApplication = this;
    }

    public static Application getInstance() {
        return sApplication;
    }

    public static void addActivity(Activity activity) {
        if (!ACTIVITIES.contains(activity)) {
            ACTIVITIES.add(activity);
        }
    }

    public static void removeActivity(Activity activity) {
        if (ACTIVITIES.contains(activity)) {
            ACTIVITIES.remove(activity);
        }
    }

    public static Activity getTop2Activity() {
        if (ACTIVITIES.size() >= 2) {
            return ACTIVITIES.get(ACTIVITIES.size() - 2);
        }
        return null;
    }

    /**
     * finish掉所有非栈顶的activity
     */
    public static void finishNoTopActivity() {
        if (ValidateUtil.isValidate(ACTIVITIES)) {
            while (ACTIVITIES.size() > 1) {
                Activity activity = ACTIVITIES.get(0);
                ACTIVITIES.remove(activity);
                if (activity != null && !activity.isFinishing()) {
                    activity.finish();
                }
            }
        }

    }

    /**
     * finish掉所有的activity
     */
    public static void finishAllActivity() {
        while (ValidateUtil.isValidate(ACTIVITIES)) {
            Activity activity = ACTIVITIES.get(0);
            ACTIVITIES.remove(activity);
            if (activity != null && !activity.isFinishing()) {
                activity.finish();
            }
        }
    }

    public static Activity getCurrentActivity() {
        if (ValidateUtil.isValidate(ACTIVITIES)) {
            return ACTIVITIES.get(ACTIVITIES.size() - 1);
        }
        return null;
    }

    public static int getStackActivitiesNum() {
        return ACTIVITIES.size();
    }
}
