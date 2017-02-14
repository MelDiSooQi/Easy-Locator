package com.pureix.easylocator.service.internetService.broadcastReceiver;

/**
 * Created by Lincoln on 18/03/16.
 */

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import static com.pureix.easylocator.controller.service.InternetAPI.connectivityReceiverListener;

public class ConnectivityReceiver
        extends BroadcastReceiver {

    public static int TYPE_WIFI = 1;
    public static int TYPE_MOBILE = 2;
    public static int TYPE_NOT_CONNECTED = 0;
//    public static final int NETWORK_STATUS_NOT_CONNECTED = 0,
//            NETWORK_STATUS_WIFI = 1, NETWORK_STATUS_MOBILE = 2;

    public ConnectivityReceiver() {
        super();
    }

    @Override
    public void onReceive(Context context, Intent arg1) {
        ConnectivityManager cm = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();

        boolean isConnected = activeNetwork != null
                && activeNetwork.isConnectedOrConnecting();

        if (null != activeNetwork) {
            if (activeNetwork.getType() == ConnectivityManager.TYPE_WIFI) {
                if (connectivityReceiverListener != null) {
                    connectivityReceiverListener
                            .onNetworkConnectionChanged(isConnected, TYPE_WIFI);
                    return;
                }
            }

            if (activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE) {
                if (connectivityReceiverListener != null) {
                    connectivityReceiverListener
                            .onNetworkConnectionChanged(isConnected, TYPE_MOBILE);
                    return;
                }
            }
        }

        if (connectivityReceiverListener != null) {
            connectivityReceiverListener
                    .onNetworkConnectionChanged(isConnected, TYPE_NOT_CONNECTED);
            return;
        }
    }

    public static boolean isConnected(Context context) {
        ConnectivityManager
                cm = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null
                && activeNetwork.isConnectedOrConnecting();
    }

    public static int getNetworkProvider(Context context) {
        ConnectivityManager
                cm = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();

        if (null != activeNetwork) {
            if (activeNetwork.getType() == ConnectivityManager.TYPE_WIFI) {
                return TYPE_WIFI;
            }

            if (activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE) {
                return TYPE_MOBILE;
            }
        }
        return TYPE_NOT_CONNECTED;
    }
}