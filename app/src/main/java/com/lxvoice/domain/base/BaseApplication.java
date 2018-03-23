package com.lxvoice.domain.base;

import android.app.Activity;
import android.app.Application;
import android.content.Context;

import com.lxvoice.domain.greendao.main.GreenDaoManager;

import java.util.ArrayList;

/**
 * Created by lz on 2018/3/13.
 */

public class BaseApplication extends Application {

    private static Context context;

    private ArrayList<Activity> activityList = new ArrayList<>();


    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
        GreenDaoManager.getSingleTon();//数据库初始化
    }
    // 获取Application
    public static Context getContext() {
        return context;
    }
    /**
     * 添加到ArrayList<Activity>
     *
     * @param activity：Activity
     */
    public void addActivity(Activity activity) {
        activityList.add(activity);
    }

    /**
     * 退出所有的Activity
     */
    public void finishAllActivity() {
        for (Activity activity : activityList) {
            if (!activity.isFinishing()) {
                activity.finish();
            }
        }
    }
}
