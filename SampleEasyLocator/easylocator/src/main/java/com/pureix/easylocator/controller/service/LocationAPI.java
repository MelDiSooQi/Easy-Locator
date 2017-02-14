package com.pureix.easylocator.controller.service;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import com.pureix.easylocator.service.activityRecognitionService.ActivitiesRecognitionService;
import com.pureix.easylocator.service.activityRecognitionService.broadcastReceiver.InitializeActivityRecognitionBroadcast;
import com.pureix.easylocator.service.locatonService.Listener.LocationReceiverListener;
import com.pureix.easylocator.service.locatonService.LocationService;
import com.pureix.easylocator.service.locatonService.broadcastReceiver.InitializeLocationBroadcast;
import com.pureix.easylocator.service.locatonService.permission.LocationPermission;

/**
 * Created by MelDiSooQi on 2/11/2017.
 */

public class LocationAPI
{
    private static InitializeLocationBroadcast broadcast;
    private static LocationPermission locationPermission;
    public static LocationReceiverListener locationReceiverListener;


    public static void start(Context context)
    {
        broadcast = new InitializeLocationBroadcast();
        broadcast.onResume(context);

        Intent i = new Intent(context, LocationService.class);
        context.stopService(i);
        context.startService(i);
    }

    public static void pause(Context context)
    {
        broadcast.onPause(context);
    }

    public static void setLocationReceiverListener(LocationReceiverListener locationReceiverListener) {
        LocationAPI.locationReceiverListener = locationReceiverListener;
    }

    public static void requestPermission(Activity activity){
        locationPermission = new LocationPermission(activity);
    }

    public static void onRequestPermissionsResult(int requestCode, String permissions[],
                                           int[] grantResults) {
        locationPermission.onRequestPermissionsResult(requestCode, permissions,
                grantResults);
    }
}
