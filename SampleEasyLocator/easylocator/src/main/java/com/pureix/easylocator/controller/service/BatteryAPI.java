package com.pureix.easylocator.controller.service;

import android.content.Context;
import android.content.Intent;

import com.pureix.easylocator.service.batteryService.BatteryService;
import com.pureix.easylocator.service.batteryService.bean.BatteryInformation;
import com.pureix.easylocator.service.batteryService.broadcastReceiver.InitializeBatteryFromAppBroadcast;
import com.pureix.easylocator.service.batteryService.listener.BatteryReceiverListener;

import java.util.Observable;
import java.util.Observer;

import static com.pureix.easylocator.service.batteryService.broadcastReceiver.BatteryAppSideBroadcast.batteryChangedObservable;

/**
 * Created by MelDiSooQi on 2/11/2017.
 */

public class BatteryAPI {

    private static int instanceCounter;
    private Context context;

    //private InitializeBatteryBroadcast broadcast;
    private InitializeBatteryFromAppBroadcast broadcast;
    private static BatteryReceiverListener batteryReceiverListener;

    public BatteryAPI(Context context)
    {
        this.context = context;
        instanceCounter++;
    }

    public static int getInstanceCounter() {
        return instanceCounter;
    }

    public void start()
    {
        broadcast = new InitializeBatteryFromAppBroadcast();
        broadcast.onResume(context);

        Intent i = new Intent(context, BatteryService.class);
        context.stopService(i);
        context.startService(i);

//        broadcast = new InitializeBatteryBroadcast();
//        broadcast.onResume(context);
    }

    public void pause()
    {
        broadcast.onPause(context);
    }

    public void batteryListener(BatteryReceiverListener batteryReceiverListener) {
        this.batteryReceiverListener = batteryReceiverListener;
        createListener();
    }

    private void createListener() {

        batteryChangedObservable.addObserver(new Observer() {
            @Override
            public void update(Observable o, Object batteryInformation) {
                if(batteryReceiverListener != null) {
                    batteryReceiverListener
                    .onBatteryInformationChanged((BatteryInformation) batteryInformation);
                }
            }
        });
    }
}
