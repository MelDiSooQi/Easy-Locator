package com.pureix.easylocator.service.batteryService.broadcastReceiver;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

import com.pureix.easylocator.service.activityRecognitionService.broadcastReceiver.ActivityRecognitionBroadcast;

/**
 * Created by MelDiSooQi on 1/28/2017.
 */

public class InitializeBatteryBroadcast
{
    private static BatteryStateReceiver  broadcastReceiver                     = null;
    private static Boolean isRegistered = false;

    public InitializeBatteryBroadcast() {
        initialize();
    }

    private void initialize()
    {
        broadcastReceiver = new BatteryStateReceiver();
    }

    public void onPause(Context context)
    {
        try {
            if (isRegistered) {
                context.unregisterReceiver(broadcastReceiver);
                isRegistered = false;
            }
        }catch (Exception e)
        {}
    }

    public void onResume(Context context)
    {
    if (!isRegistered)
    {
        IntentFilter filter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
        context.registerReceiver(broadcastReceiver, filter);

        isRegistered = true;
    }
    }

    public static boolean isMyServiceRunning(Activity activity, Class<?> serviceClass)
    {
        ActivityManager manager = (ActivityManager) activity
                .getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager
                .getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }

}
