package com.pureix.easylocator.service.batteryService.broadcastReceiver;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.IntentFilter;

/**
 * Created by MelDiSooQi on 1/28/2017.
 */

public class InitializeBatteryFromAppBroadcast
{
    private static BatteryAppSideBroadcast broadcastReceiverFromApp    = null;
    private static Boolean isRegistered = false;

    public InitializeBatteryFromAppBroadcast() {
        initialize();
    }

    private void initialize()
    {
        broadcastReceiverFromApp = new BatteryAppSideBroadcast();
    }

    public void onPause(Context context)
    {
        try {
            if (isRegistered) {
                context.unregisterReceiver(broadcastReceiverFromApp);
                isRegistered = false;
            }
        }catch (Exception e)
        {}
    }

    public void onResume(Context context)
    {
    if (!isRegistered)
    {
        IntentFilter filter = new IntentFilter("com.pureix.easylocator.service.batteryService.broadcastReceiver.BatteryAppSideBroadcast");
        context.registerReceiver(broadcastReceiverFromApp, filter);

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
