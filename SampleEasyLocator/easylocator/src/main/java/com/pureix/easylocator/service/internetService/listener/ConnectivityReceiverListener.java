package com.pureix.easylocator.service.internetService.listener;

/**
 * Created by MelDiSooQi on 1/27/2017.
 */

public interface ConnectivityReceiverListener
{
    void onNetworkConnectionChanged(boolean isConnected, int connectionProvider);
}
