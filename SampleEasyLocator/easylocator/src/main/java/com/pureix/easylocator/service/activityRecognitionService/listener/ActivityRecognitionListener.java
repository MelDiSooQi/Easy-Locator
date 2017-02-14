package com.pureix.easylocator.service.activityRecognitionService.listener;

import com.google.android.gms.location.DetectedActivity;

import java.util.ArrayList;

/**
 * Created by MelDiSooQi on 2/11/2017.
 */

public interface ActivityRecognitionListener {
    void updateDetectedActivitiesList(ArrayList<DetectedActivity> updatedActivities);
}
