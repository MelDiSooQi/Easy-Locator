package com.pureix.easylocator.controller.service;

import android.content.Context;
import android.content.Intent;

import com.pureix.easylocator.service.activityRecognitionService.ActivitiesRecognitionService;
import com.pureix.easylocator.service.activityRecognitionService.broadcastReceiver.InitializeActivityRecognitionBroadcast;
import com.pureix.easylocator.service.batteryService.BatteryService;
import com.pureix.easylocator.service.batteryService.broadcastReceiver.BatteryStateReceiver;
import com.pureix.easylocator.service.batteryService.broadcastReceiver.InitializeBatteryBroadcast;
import com.pureix.easylocator.service.batteryService.listener.BatteryReceiverListener;

/**
 * Created by MelDiSooQi on 2/11/2017.
 */

public class BatteryAPI {

    private static InitializeBatteryBroadcast broadcast;
    public static BatteryReceiverListener batteryReceiverListener;

    public static void start(Context context)
    {
        Intent i = new Intent(context, BatteryService.class);
        context.stopService(i);
        context.startService(i);

//        broadcast = new InitializeBatteryBroadcast();
//        broadcast.onResume(context);
    }

    public static void pause(Context context)
    {
//        broadcast.onPause(context);
    }

    public static void batteryListener(BatteryReceiverListener batteryReceiverListener) {
        BatteryAPI
                .batteryReceiverListener = batteryReceiverListener;
    }
}
