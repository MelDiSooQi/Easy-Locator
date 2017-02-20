package com.pureix.easylocator.service;

import android.content.Context;
import android.widget.Toast;

import com.google.android.gms.location.DetectedActivity;
import com.pureix.easylocator.controller.service.ActivityRecognitionAPI;
import com.pureix.easylocator.model.bean.CustomLocation;
import com.pureix.easylocator.service.activityRecognitionService.listener.ActivityRecognitionListener;

import java.util.ArrayList;

/**
 * Created by MelDiSooQi on 2/18/2017.
 */

public class SmartLocationBusiness {

    private CustomLocation customLocation;
    private boolean smart;

    public void start(final Context context) {
    if(smart) {
        ActivityRecognitionAPI.start(context);

        ActivityRecognitionAPI.setActivitiesRecognitionListener(new ActivityRecognitionListener() {
            @Override
            public void updateDetectedActivitiesList(ArrayList<DetectedActivity> updatedActivities) {
                ArrayList<DetectedActivity> activityArrayList =
                        ActivityRecognitionAPI.getArrayList(updatedActivities);

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
                        ActivityRecognitionAPI.getActivityString(context,
                                detectedActivity.getType()) + " - "
                                + detectedActivity.getConfidence()
                                + "%" + "\n", Toast.LENGTH_SHORT).show();
                decideAction(detectedActivity);
            }
        });
    }else{
        // if not smart use User Custom Location
    }
    }

    private void decideAction(DetectedActivity detectedActivity) {
        int type = detectedActivity.getType();

        if(DetectedActivity.STILL == type){

        }

        if(DetectedActivity.ON_FOOT == type){

        }

        if(DetectedActivity.WALKING == type){

        }

        if(DetectedActivity.RUNNING == type){

        }

        if(DetectedActivity.ON_BICYCLE == type){

        }

        if(DetectedActivity.IN_VEHICLE == type){

        }

        if(DetectedActivity.TILTING == type){

        }

        if(DetectedActivity.UNKNOWN == type){

        }
    }

    public void setCustomLocation(CustomLocation customLocation) {
        this.customLocation = customLocation;
    }

    public void setSmart(boolean smart) {
        this.smart = smart;
    }

    public void pause(Context context) {
        ActivityRecognitionAPI.start(context);
    }
}
