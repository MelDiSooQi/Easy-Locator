package com.pureix.easylocator.service.locatonService.broadcastReceiver;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.IntentFilter;

/**
 * Created by MelDiSooQi on 1/28/2017.
 */

public class InitializeLocationBroadcast
{
    private static LocationBroadcast  locationBroadcast                     = null;
    private static Boolean 	          locationServiceReceiverIsRegistered   = false;

    public InitializeLocationBroadcast() {
        initialize();
    }

    private void initialize()
    {
        locationBroadcast = new LocationBroadcast();
    }

    public void onPause(Context context)
    {
        try {
            if (locationServiceReceiverIsRegistered) {
                context.unregisterReceiver(locationBroadcast);
                locationServiceReceiverIsRegistered = false;
            }
        }catch (Exception e)
        {}
    }

    public void onResume(Context context)
    {
        if (!locationServiceReceiverIsRegistered)
        {
        context.registerReceiver(locationBroadcast,
        new IntentFilter("com.pureix.easylocator.service.locatonService.broadcastReceiver.LocationBroadcast"));

        locationServiceReceiverIsRegistered = true;
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
