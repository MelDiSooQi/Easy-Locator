package com.pureix.easylocator.controller.service;

import android.content.Context;
import android.content.Intent;
import android.widget.ArrayAdapter;

import com.google.android.gms.location.DetectedActivity;
import com.pureix.easylocator.service.activityRecognitionService.ActivitiesRecognitionService;
import com.pureix.easylocator.service.activityRecognitionService.Constants;
import com.pureix.easylocator.service.activityRecognitionService.broadcastReceiver.InitializeActivityRecognitionBroadcast;
import com.pureix.easylocator.service.activityRecognitionService.listener.ActivityRecognitionListener;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by MelDiSooQi on 2/11/2017.
 */

public class ActivityRecognitionAPI
{
    public static final int[] MONITORED_ACTIVITIES = Constants.MONITORED_ACTIVITIES;
    private static ArrayAdapter<DetectedActivity> arrayAdapter;

    private static InitializeActivityRecognitionBroadcast broadcast;
    public static ActivityRecognitionListener activitiesRecognitionListener;

    public static void start(Context context)
    {
        Intent i = new Intent(context, ActivitiesRecognitionService.class);
        context.stopService(i);
        context.startService(i);

        broadcast = new InitializeActivityRecognitionBroadcast();
        broadcast.onResume(context);
    }

    public static void pause(Context context)
    {
        broadcast.onPause(context);
    }

    public static void setActivitiesRecognitionListener(ActivityRecognitionListener activitiesRecognitionListener) {
        ActivityRecognitionAPI.activitiesRecognitionListener = activitiesRecognitionListener;
    }

    /**
     * Process list of recently detected activities and updates the list of {@code DetectedActivity}
     * objects backing this adapter.
     *
     * @param detectedActivities the freshly detected activities
     */
    public static ArrayList<DetectedActivity> getArrayList(ArrayList<DetectedActivity> detectedActivities)
    {
        HashMap<Integer, Integer> detectedActivitiesMap = new HashMap<>();
        for (DetectedActivity activity : detectedActivities) {
            detectedActivitiesMap.put(activity.getType(), activity.getConfidence());
        }
        // Every time we detect new activities, we want to reset the confidence level of ALL
        // activities that we monitor. Since we cannot directly change the confidence
        // of a DetectedActivity, we use a temporary list of DetectedActivity objects. If an
        // activity was freshly detected, we use its confidence level. Otherwise, we set the
        // confidence level to zero.
        ArrayList<DetectedActivity> tempList = new ArrayList<DetectedActivity>();
        for (int i = 0; i < ActivityRecognitionAPI.MONITORED_ACTIVITIES.length; i++) {
            int confidence = detectedActivitiesMap.containsKey(ActivityRecognitionAPI.MONITORED_ACTIVITIES[i]) ?
                    detectedActivitiesMap.get(ActivityRecognitionAPI.MONITORED_ACTIVITIES[i]) : 0;

            tempList.add(new DetectedActivity(ActivityRecognitionAPI.MONITORED_ACTIVITIES[i],
                    confidence));
        }

        return tempList;
    }

    public static ArrayAdapter<DetectedActivity> getArrayAdapter(ArrayList<DetectedActivity> tempList)
    {
        // Remove all items.
        arrayAdapter.clear();

        // Adding the new list items notifies attached observers that the underlying data has
        // changed and views reflecting the data should refresh.
        for (DetectedActivity detectedActivity: tempList) {
            arrayAdapter.add(detectedActivity);
        }

        return arrayAdapter;
    }
}
