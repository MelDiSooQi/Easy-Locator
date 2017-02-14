package com.pureix.easylocator.controller.service;

import android.content.Context;
import android.content.Intent;

import com.pureix.easylocator.service.activityRecognitionService.ActivitiesRecognitionService;
import com.pureix.easylocator.service.activityRecognitionService.broadcastReceiver.InitializeActivityRecognitionBroadcast;
import com.pureix.easylocator.service.activityRecognitionService.listener.ActivityRecognitionListener;

/**
 * Created by MelDiSooQi on 2/11/2017.
 */

public class ActivityRecognitionAPI
{
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
}
