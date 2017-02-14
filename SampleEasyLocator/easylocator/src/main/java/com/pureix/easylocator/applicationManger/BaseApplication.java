package com.pureix.easylocator.applicationManger;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;

import com.pureix.easylocator.applicationManger.configuration.GlobalData;

/**
 * Created by MelDiSooQi on 5/10/2016.
 */
public class BaseApplication extends Application {

    public static final String TAG = BaseApplication.class.getSimpleName();

    private static BaseApplication mInstance;

    public BaseApplication()
    {
    }

    @Override
    public void onCreate()
    {
        super.onCreate();
        mInstance = this;
        GlobalData.initialize(mInstance.getApplicationContext());
    }

    public static synchronized BaseApplication getInstance()
    {
        return mInstance;
    }

    protected void attachBaseContext(Context base)
    {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

}
