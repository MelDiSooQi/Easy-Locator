package com.pureix.easylocator.service.batteryService.broadcastReceiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;
import android.widget.Toast;

import com.pureix.easylocator.service.batteryService.listener.BatteryReceiverListener;

import static com.pureix.easylocator.controller.service.BatteryAPI.batteryReceiverListener;

public class BatteryStateReceiver extends BroadcastReceiver
{
    //private static Context context;


    private static Boolean  batteryReceiverIsRegistered = false;

    public BatteryStateReceiver() {
    }

/*    public void onResume(Context context)
    {
        if (!batteryReceiverIsRegistered)
        {
            IntentFilter filter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
            context.registerReceiver(this, filter);

            batteryReceiverIsRegistered = true;
        }
    }

    public void onPause(Context context)
    {
        try {
            if (batteryReceiverIsRegistered) {
                context.unregisterReceiver(this);
                batteryReceiverIsRegistered = false;
            }
        }catch (Exception e)
        {}
    }*/


    @Override
    public void onReceive(Context context, Intent intent)
    {
        int level       = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
        int scale       = intent.getIntExtra(BatteryManager.EXTRA_SCALE, -1);
        int temperature = intent.getIntExtra(BatteryManager.EXTRA_TEMPERATURE, -1);
        int voltage     = intent.getIntExtra(BatteryManager.EXTRA_VOLTAGE, -1);

        float temperatureInC = temperature / 10;
        float batteryPct = level / (float)scale;

        // Are we charging / charged?
        int status = intent.getIntExtra(BatteryManager.EXTRA_STATUS, -1);
        boolean isCharging = status == BatteryManager.BATTERY_STATUS_CHARGING ||
                             status == BatteryManager.BATTERY_STATUS_FULL;

        // How are we charging?
        int chargePlug = intent.getIntExtra(BatteryManager.EXTRA_PLUGGED, -1);
        boolean usbCharge = chargePlug == BatteryManager.BATTERY_PLUGGED_USB;
        boolean acCharge = chargePlug == BatteryManager.BATTERY_PLUGGED_AC;

        if(!(level == -1 && scale == -1 && temperature == -1 && voltage == -1
            && status == -1 && chargePlug == -1)) {
            if(batteryReceiverListener != null) {
                batteryReceiverListener.onBatteryInformationChanged(level, scale,
                        temperature, voltage, batteryPct, status, isCharging,
                        chargePlug, usbCharge, acCharge);
            }
            Toast.makeText(context,
                    "level is " + level + "/" + scale +
                            ", temp is " + temperature +
                            ", voltage is " + voltage
                            + " status :" + status +
                            " chargePlug :" + chargePlug +
                            " Battery Pct : " + batteryPct * 100,
                    Toast.LENGTH_SHORT).show();
        }
    }
}
