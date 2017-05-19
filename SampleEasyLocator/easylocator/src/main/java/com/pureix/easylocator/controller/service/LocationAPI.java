package com.pureix.easylocator.controller.service;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.Location;

import com.google.android.gms.location.LocationRequest;
import com.google.gson.Gson;
import com.pureix.easylocator.model.bean.CustomSettingsLocation;
import com.pureix.easylocator.service.locatonService.Constants;
import com.pureix.easylocator.service.locatonService.Listener.LocationReceiverListener;
import com.pureix.easylocator.service.locatonService.LocationService;
import com.pureix.easylocator.service.locatonService.LocationServicesConstant;
import com.pureix.easylocator.service.locatonService.broadcastReceiver.InitializeLocationBroadcast;
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

    private CustomSettingsLocation customSettingsLocation;

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
        startCustomService(createCustomSettingsLocation());
    }

    private CustomSettingsLocation createCustomSettingsLocation() {
        customSettingsLocation = new CustomSettingsLocation();

        customSettingsLocation.setDetectedActivityType(-1);
        customSettingsLocation.setDetectedActivityProvider("Normal");

        customSettingsLocation.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        customSettingsLocation.setInterval(Constants.UPDATE_INTERVAL);
        customSettingsLocation.setFastestInterval(Constants.FASTEST_INTERVAL);
        customSettingsLocation.setSmallestDisplacement(0);

        return customSettingsLocation;
    }

    public void startCustomService(CustomSettingsLocation customSettingsLocation) {
        broadcast = new InitializeLocationBroadcast();
        broadcast.onResume(context);

        Intent i = new Intent(context, LocationService.class);
        context.stopService(i);
        i.putExtra(LocationServicesConstant.CUSTOM_SETTINGS_LOCATION, new Gson().toJson(customSettingsLocation));
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
