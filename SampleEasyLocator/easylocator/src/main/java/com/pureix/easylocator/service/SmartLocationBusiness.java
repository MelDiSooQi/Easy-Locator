package com.pureix.easylocator.service;

import android.content.Context;
import android.location.Location;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.location.DetectedActivity;
import com.google.android.gms.location.LocationRequest;
import com.google.gson.Gson;
import com.pureix.easylocator.controller.service.ActivityRecognitionAPI;
import com.pureix.easylocator.model.ObservableHandler;
import com.pureix.easylocator.model.bean.CustomSettingsLocation;
import com.pureix.easylocator.model.storage.LocalStorage;
import com.pureix.easylocator.model.storage.LocalStorageConstant;
import com.pureix.easylocator.service.activityRecognitionService.listener.ActivityRecognitionListener;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by MelDiSooQi on 2/18/2017.
 */

public class SmartLocationBusiness
{
    private String TAG = "SmartBusinessTAG";

    private boolean smart;
    private Context context;
    private CustomSettingsLocation customSettingsLocation;
    private ActivityRecognitionAPI activityRecognitionAPI;

    public static final int LOCATION_PRIORITY_PRIORITY_HIGH_ACCURACY
            = LocationRequest.PRIORITY_HIGH_ACCURACY;
    public static final int LOCATION_PRIORITY_PRIORITY_BALANCED_POWER_ACCURACY
            = LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY;
    public static final int LOCATION_PRIORITY_PRIORITY_LOW_POWER
            = LocationRequest.PRIORITY_LOW_POWER;
    public static final int LOCATION_PRIORITY_PRIORITY_NO_POWER
            = LocationRequest.PRIORITY_NO_POWER;
    private Timer mtimer;

    public void start(Context context) {
        this.context = context;
        SmartBusiness();
    }

    private void SmartBusiness() {
        customSettingsLocation = new CustomSettingsLocation();
        if(smart) {
            activityRecognitionAPI = new ActivityRecognitionAPI(context);

            activityRecognitionAPI.start();

            activityRecognitionAPI.setActivitiesRecognitionListener(new ActivityRecognitionListener() {
                @Override
                public void updateDetectedActivitiesList(ArrayList<DetectedActivity> updatedActivities) {
                    ArrayList<DetectedActivity> activityArrayList =
                            activityRecognitionAPI.getArrayList(updatedActivities);

                    DetectedActivity detectedActivity = null;
                    int biggestNumber = -1;
                    for (int i = 0; i < activityArrayList.size(); i++) {
                        DetectedActivity currentActivity = activityArrayList.get(i);
                        int confidence = activityArrayList.get(i).getConfidence();

                        if (biggestNumber < confidence) {
                            biggestNumber = confidence;
                            detectedActivity = currentActivity;
                        }
                    }
                    Toast.makeText(context,
                            activityRecognitionAPI.getActivityString(context,
                                    detectedActivity.getType()) + " - "
                                    + detectedActivity.getConfidence()
                                    + "%", Toast.LENGTH_SHORT).show();
                    decideAction(detectedActivity);
                }
            });
        }else{
            // if not smart use User Custom Location
        }
    }

    private final static int time = 1000;

    private void decideAction(DetectedActivity detectedActivity) {
        int type = detectedActivity.getType();

        customSettingsLocation = new CustomSettingsLocation();

        if(DetectedActivity.STILL == type){
            customSettingsLocation.setDetectedActivityType(type);
            customSettingsLocation.setDetectedActivityProvider(activityRecognitionAPI.getActivityString(context, detectedActivity.getType()));

            customSettingsLocation.setPriority(LOCATION_PRIORITY_PRIORITY_LOW_POWER);
            customSettingsLocation.setInterval(60 * 1000);
            customSettingsLocation.setFastestInterval(60 * 1000);
            customSettingsLocation.setSmallestDisplacement(10);
        }

        if(DetectedActivity.ON_FOOT == type){

        }

        if(DetectedActivity.WALKING == type){
            customSettingsLocation.setDetectedActivityType(type);
            customSettingsLocation.setDetectedActivityProvider(activityRecognitionAPI.getActivityString(context, detectedActivity.getType()));

            customSettingsLocation.setPriority(LOCATION_PRIORITY_PRIORITY_BALANCED_POWER_ACCURACY);
            customSettingsLocation.setInterval(10 * 1000);
            customSettingsLocation.setFastestInterval(10 * 1000);
            customSettingsLocation.setSmallestDisplacement(5);
        }

        if(DetectedActivity.RUNNING == type){
            customSettingsLocation.setDetectedActivityType(type);
            customSettingsLocation.setDetectedActivityProvider(activityRecognitionAPI.getActivityString(context, detectedActivity.getType()));

            customSettingsLocation.setPriority(LOCATION_PRIORITY_PRIORITY_HIGH_ACCURACY);
            customSettingsLocation.setInterval(4 * 1000);
            customSettingsLocation.setFastestInterval(4 * 1000);
            customSettingsLocation.setSmallestDisplacement(4);
        }

        if(DetectedActivity.ON_BICYCLE == type){
            customSettingsLocation.setDetectedActivityType(type);
            customSettingsLocation.setDetectedActivityProvider(activityRecognitionAPI.getActivityString(context, detectedActivity.getType()));

            customSettingsLocation.setPriority(LOCATION_PRIORITY_PRIORITY_HIGH_ACCURACY);
            customSettingsLocation.setInterval(4 * 1000);
            customSettingsLocation.setFastestInterval(4 * 1000);
            customSettingsLocation.setSmallestDisplacement(8);
        }

        if(DetectedActivity.IN_VEHICLE == type){
            customSettingsLocation.setDetectedActivityType(type);
            customSettingsLocation.setDetectedActivityProvider(activityRecognitionAPI.getActivityString(context, detectedActivity.getType()));

            customSettingsLocation.setPriority(LOCATION_PRIORITY_PRIORITY_HIGH_ACCURACY);
            customSettingsLocation.setInterval(2 * 1000);
            customSettingsLocation.setFastestInterval(2 * 1000);
            customSettingsLocation.setSmallestDisplacement(10);
        }

        if(DetectedActivity.TILTING == type){
            customSettingsLocation.setDetectedActivityType(type);
            customSettingsLocation.setDetectedActivityProvider(activityRecognitionAPI.getActivityString(context, detectedActivity.getType()));

            customSettingsLocation.setPriority(LOCATION_PRIORITY_PRIORITY_HIGH_ACCURACY);
            customSettingsLocation.setInterval(2 * 1000);
            customSettingsLocation.setFastestInterval(2 * 1000);
            customSettingsLocation.setSmallestDisplacement(10);
        }

        if(DetectedActivity.UNKNOWN == type){
            customSettingsLocation.setDetectedActivityType(type);
            customSettingsLocation.setDetectedActivityProvider(activityRecognitionAPI.getActivityString(context, detectedActivity.getType()));

            customSettingsLocation.setPriority(LOCATION_PRIORITY_PRIORITY_HIGH_ACCURACY);
            customSettingsLocation.setInterval(2 * 1000);
            customSettingsLocation.setFastestInterval(2 * 1000);
            customSettingsLocation.setSmallestDisplacement(10);
        }

        saveCustomSettingsLocationInLocalStorage(time, customSettingsLocation);
    }

    private void saveCustomSettingsLocationInLocalStorage(int time,
                                                          final CustomSettingsLocation customSettingsLocation)
    {
        if(mtimer != null) {
            mtimer.cancel();
        }

        mtimer = new Timer();
        mtimer.schedule(new TimerTask() {
            @Override
            public void run() {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        setCustomSettingsLocationInLocalStorage(customSettingsLocation);
                    }
                }).start();
            }
        }, time);
    }

    private CustomSettingsLocation getCustomSettingsLocationInLocalStorage() {
        String jsonLocation = (String) LocalStorage.getPreference(context,
                LocalStorageConstant.CUSTOM_SETTINGS_LOCATION, null);
        return new Gson().fromJson(jsonLocation, CustomSettingsLocation.class);
    }

    private void setCustomSettingsLocationInLocalStorage(
            CustomSettingsLocation customSettingsLocation)
    {
        Log.e(TAG, "setCustomSettingsLocationInLocalStorage");
        String jsonLocation = new Gson().toJson(customSettingsLocation);
        LocalStorage.setPreference(context,
                LocalStorageConstant.CUSTOM_SETTINGS_LOCATION,
                jsonLocation);
    }

    public void setCustomSettingsLocation(CustomSettingsLocation customSettingsLocation) {
        this.customSettingsLocation = customSettingsLocation;
    }

    public void setSmart(boolean smart) {
        this.smart = smart;
    }

    public void pause() {
        activityRecognitionAPI.pause();
    }
}
