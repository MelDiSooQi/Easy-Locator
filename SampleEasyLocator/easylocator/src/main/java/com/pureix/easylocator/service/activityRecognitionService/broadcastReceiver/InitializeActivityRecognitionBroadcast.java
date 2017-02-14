package com.pureix.easylocator.service.activityRecognitionService.broadcastReceiver;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.IntentFilter;

/**
 * Created by MelDiSooQi on 1/28/2017.
 */

public class InitializeActivityRecognitionBroadcast
{
    private static ActivityRecognitionBroadcast  broadcastReceiver                     = null;
    private static Boolean isRegistered = false;

    public InitializeActivityRecognitionBroadcast() {
        initialize();
    }

    private void initialize()
    {
        broadcastReceiver = new ActivityRecognitionBroadcast();
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
        String PACKAGE_NAME = "com.pureix.easylocator.service.activityRecognitionService.broadcastReceiver.ActivityRecognitionBroadcast";

        context.registerReceiver(broadcastReceiver, new IntentFilter(PACKAGE_NAME));

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
