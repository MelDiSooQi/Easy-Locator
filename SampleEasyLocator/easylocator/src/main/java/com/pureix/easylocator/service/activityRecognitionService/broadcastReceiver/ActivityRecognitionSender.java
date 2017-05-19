package com.pureix.easylocator.service.activityRecognitionService.broadcastReceiver;

import android.content.Context;
import android.content.Intent;

import com.google.android.gms.location.DetectedActivity;
import com.pureix.easylocator.service.activityRecognitionService.Constants;

import java.util.ArrayList;

/**
 * Created by MelDiSooQi on 7/16/2016.
 */
public class ActivityRecognitionSender
{
    private final Context context;
    private final Class<?> aClass;
    private Intent intent;

    public ActivityRecognitionSender(Context context, Class<?> aClass)
    {
        this.context = context;
        this.aClass = aClass;
        this.intent = new Intent(context, aClass);
    }

    public Intent getIntent() {
        return intent;
    }

//    public void sendLocationToApp(String jsonLocation)
//    {
//        intent.putExtra(LocationServicesConstant.SERVICE_ID     , LocationServicesConstant
//                .SERVICE_ID_SEND_JSON_LOCATION);
//        intent.putExtra(LocationServicesConstant.JSON_LOCATION  , jsonLocation);
//
//        context.sendBroadcast(intent);
//    }

    public void sendDetectedActivitiesToApp(ArrayList<DetectedActivity> detectedActivities)
    {
        intent.putExtra(Constants.ACTIVITY_EXTRA, detectedActivities);

        context.sendBroadcast(intent);
    }
}
