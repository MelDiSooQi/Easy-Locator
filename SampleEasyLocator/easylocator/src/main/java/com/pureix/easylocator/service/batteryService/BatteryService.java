package com.pureix.easylocator.service.batteryService;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import com.pureix.easylocator.service.batteryService.broadcastReceiver.BatteryStateReceiver;
import com.pureix.easylocator.service.batteryService.broadcastReceiver.InitializeBatteryBroadcast;

public class BatteryService extends Service {
    private String TAG = "BatteryServiceTAG";

    private Context context;
//    private BatteryStateReceiver batteryStateReceiver;
    private static InitializeBatteryBroadcast broadcast;

    public BatteryService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        //throw new UnsupportedOperationException("Not yet implemented");
        return null;
    }

    @Override
    public void onCreate() {
        Log.e(TAG, "onCreate");
        super.onCreate();
        context = getApplicationContext();
//        batteryStateReceiver
//                = new BatteryStateReceiver();
//        batteryStateReceiver.onResume(context);

        broadcast = new InitializeBatteryBroadcast();
        broadcast.onResume(context);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.e(TAG, "onStartCommand");
        super.onStartCommand(intent, flags, startId);


        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        Log.e(TAG, "onDestroy");
//        batteryStateReceiver.onPause(context);
        broadcast.onPause(context);
        super.onDestroy();
    }
}
