package com.pureix.easylocator.controller.service;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.Location;

import com.pureix.easylocator.service.activityRecognitionService.ActivitiesRecognitionService;
import com.pureix.easylocator.service.activityRecognitionService.broadcastReceiver.InitializeActivityRecognitionBroadcast;
import com.pureix.easylocator.service.locatonService.Listener.LocationReceiverListener;
import com.pureix.easylocator.service.locatonService.LocationService;
import com.pureix.easylocator.service.locatonService.broadcastReceiver.InitializeLocationBroadcast;
import com.pureix.easylocator.service.locatonService.broadcastReceiver.LocationBroadcast;
import com.pureix.easylocator.service.locatonService.permission.LocationPermission;

import java.util.Observable;
import java.util.Observer;

import static com.pureix.easylocator.service.locatonService.broadcastReceiver.LocationBroadcast.locationChangedObservable;
import static com.pureix.easylocator.service.locatonService.broadcastReceiver.LocationBroadcast.locationLastKnownLocationObservable;

/**
 * Created by MelDiSooQi on 2/11/2017.
 */

public class LocationAPI
{
    private static int instanceCounter;

    private Context context;
    private InitializeLocationBroadcast broadcast;
    private LocationPermission locationPermission;
    private LocationReceiverListener locationReceiverListener;

    public LocationAPI(Context context)
    {
        this.context = context;
        instanceCounter++;
    }

    public static int getInstanceCounter() {
        return instanceCounter;
    }

    public void start()
    {
        broadcast = new InitializeLocationBroadcast();
        broadcast.onResume(context);

        Intent i = new Intent(context, LocationService.class);
        context.stopService(i);
        context.startService(i);
    }

    public void pause()
    {
        broadcast.onPause(context);
    }

    public void setLocationReceiverListener(LocationReceiverListener locationReceiverListener) {
        this.locationReceiverListener = locationReceiverListener;
        createListener();
    }

    private void createListener() {

        locationLastKnownLocationObservable.addObserver(new Observer() {
            @Override
            public void update(Observable o, Object location) {
                if(locationReceiverListener != null) {
                    locationReceiverListener.getLastKnownLocation((Location) location);
                }
            }
        });

        locationChangedObservable.addObserver(new Observer() {
            @Override
            public void update(Observable o, Object location) {
                if(locationReceiverListener != null) {
                    locationReceiverListener.onLocationChanged((Location) location);
                }
            }
        });
    }

    public void requestPermission(Activity activity){
        locationPermission = new LocationPermission(activity);
    }

    public void onRequestPermissionsResult(int requestCode, String permissions[],
                                           int[] grantResults) {
        locationPermission.onRequestPermissionsResult(requestCode, permissions,
                grantResults);
    }
}
