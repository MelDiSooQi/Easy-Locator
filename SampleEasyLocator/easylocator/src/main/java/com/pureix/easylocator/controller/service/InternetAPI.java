package com.pureix.easylocator.controller.service;

import android.content.Context;

import com.pureix.easylocator.service.internetService.broadcastReceiver.ConnectivityReceiver;
import com.pureix.easylocator.service.internetService.listener.ConnectivityReceiverListener;

/**
 * Created by MelDiSooQi on 2/11/2017.
 */

public class InternetAPI {

    public static int WIFI = 1;
    public static int MOBILE = 2;
    public static int NOT_CONNECTED = 0;

    public static ConnectivityReceiverListener connectivityReceiverListener;

    public static void networkListener(ConnectivityReceiverListener connectivityReceiverListener) {
        InternetAPI.connectivityReceiverListener = connectivityReceiverListener;
    }

    public static boolean isConnected(Context context) {
        return ConnectivityReceiver.isConnected(context);
    }

    public static int getNetworkProvider(Context context) {
    return ConnectivityReceiver.getNetworkProvider(context);
    }
}
