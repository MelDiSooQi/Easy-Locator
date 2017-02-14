package com.pureix.easylocator.applicationManger.configuration;

import android.content.Context;
/**
 * Created by MelDiSooQi on 12/19/2015.
 */

public class GlobalData
{
    public static boolean isInitializeRun = false;

    public static Context context ;

    public static void initialize(Context context)
    {
    if(!isInitializeRun)
    {
        isInitializeRun = true;
        //=================initialize=========================
        //GlobalData.activity    = activity;
        GlobalData.context     = context;//activity.getApplicationContext();


    }
    }
}