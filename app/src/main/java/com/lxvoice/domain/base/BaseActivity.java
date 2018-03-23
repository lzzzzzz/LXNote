package com.lxvoice.domain.base;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.lxvoice.domain.R;

/**
 * Created by lz on 2018/3/13.
 */
public abstract class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView();
        initActivity();
        initView();
        initData();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.anim_activity_slide_enter, R.anim.anim_activity_slide_out);
    }

    /**
     * 查找组件
     *
     * @param viewId：View的ID
     * @return View
     */
    @SuppressWarnings("unchecked")
    protected <view extends View> view getViewById(int viewId) {
        return (view) findViewById(viewId);
    }

    /**
     * 启动Activity
     *
     * @param context：Context
     * @param cls：Class<?>
     * @param isFinish：是否关闭Activity
     */
    protected void startActivityByIntent(Context context, Class<?> cls, Boolean isFinish) {
        startActivity(new Intent(context, cls));
        if (isFinish) {
            finish();
        }
        overridePendingTransition(R.anim.anim_activity_slide_enter , R.anim.anim_activity_slide_out);
    }

    /**
     * 显示短Toast
     *
     * @param context：Context
     * @param text：String
     */
    protected void showShortToastByString(Context context, String text) {
        Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
    }

    /**
     * 显示长Toast
     *
     * @param context：Context
     * @param text：String
     */
    protected void showLongToastByString(Context context, String text) {
        Toast.makeText(context, text, Toast.LENGTH_LONG).show();
    }

    /**
     * 设置SharedPreferences
     *
     * @param context：Context
     * @param key：键
     * @param value：值
     */
    protected void setSP(Context context, String key, int value) {
        SharedPreferences.Editor et = context.getSharedPreferences(Keys.SP_NAME, Context.MODE_PRIVATE).edit();
        et.putInt(key, value);
        et.apply();
    }

    /**
     * 设置SharedPreferences
     *
     * @param context：Context
     * @param key：键
     * @param value：值
     */
    protected void setSP(Context context, String key, String value) {
        SharedPreferences.Editor et = context.getSharedPreferences(Keys.SP_NAME, Context.MODE_PRIVATE).edit();
        et.putString(key, value);
        et.apply();
    }

    /**
     * 设置SharedPreferences
     *
     * @param context：Context
     * @param key：键
     * @param value：值
     */
    protected void setSP(Context context, String key, Boolean value) {
        SharedPreferences.Editor et = context.getSharedPreferences(Keys.SP_NAME, Context.MODE_PRIVATE).edit();
        et.putBoolean(key, value);
        et.apply();
    }

    /**
     * 获取SharedPreferences
     *
     * @param context：Context
     * @param key：键
     * @param defaultValue：值
     * @return 值
     */
    protected int getSP(Context context, String key, int defaultValue) {
        SharedPreferences sp = context.getSharedPreferences(Keys.SP_NAME, Context.MODE_PRIVATE);
        return sp.getInt(key, defaultValue);
    }


    /**
     * 获取SharedPreferences
     *
     * @param context：Context
     * @param key：键
     * @param defaultValue：值
     * @return 值
     */
    protected String getSP(Context context, String key, String defaultValue) {
        SharedPreferences sp = context.getSharedPreferences(Keys.SP_NAME, Context.MODE_PRIVATE);
        return sp.getString(key, defaultValue);
    }

    /**
     * 获取SharedPreferences
     *
     * @param context：Context
     * @param key：键
     * @param defaultValue：值
     * @return 值
     */
    protected boolean getSP(Context context, String key, Boolean defaultValue) {
        SharedPreferences sp = context.getSharedPreferences(Keys.SP_NAME, Context.MODE_PRIVATE);
        return sp.getBoolean(key, defaultValue);
    }

    /**
     * 设置布局
     */
    public abstract void setContentView();

    /**
     * 初始化Activity
     */
    public abstract void initActivity();

    /**
     * 初始化View
     */
    public abstract void initView();

    /**
     * 初始化数据
     */
    public abstract void initData();

}