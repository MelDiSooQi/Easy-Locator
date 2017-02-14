/**
 * Copyright 2014 Google Inc. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.pureix.easylocator.service.activityRecognitionService;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.google.android.gms.location.ActivityRecognitionResult;
import com.google.android.gms.location.DetectedActivity;
import com.pureix.easylocator.service.activityRecognitionService.broadcastReceiver.ActivityRecognitionBroadcast;
import com.pureix.easylocator.service.activityRecognitionService.broadcastReceiver.ActivityRecognitionSender;

import java.util.ArrayList;

/**
 *  IntentService for handling incoming intents that are generated as a result of requesting
 *  activity updates using
 *  {@link com.google.android.gms.location.ActivityRecognitionApi#requestActivityUpdates}.
 */
public class ActivitiesRecognitionIntentService extends IntentService {

    protected static final String TAG = "DetectedActivitiesIS";

    private Context context;
    private ActivityRecognitionSender sender;

    /**
     * This constructor is required, and calls the super IntentService(String)
     * constructor with the name for a worker thread.
     */
    public ActivitiesRecognitionIntentService() {
        // Use the TAG to name the worker thread.
        super(TAG);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        this.context = getApplicationContext();

        sender = new ActivityRecognitionSender(context,
                ActivityRecognitionBroadcast.class);
    }

    /**
     * Handles incoming intents.
     * @param intent The Intent is provided (inside a PendingIntent) when requestActivityUpdates()
     *               is called.
     */
    @Override
    protected void onHandleIntent(Intent intent) {
        ActivityRecognitionResult result = ActivityRecognitionResult.extractResult(intent);

        // Get the list of the probable activities associated with the current state of the
        // device. Each activity is associated with a confidence level, which is an int between
        // 0 and 100.
        ArrayList<DetectedActivity> detectedActivities = (ArrayList) result.getProbableActivities();

        // Log each activity.
        Log.i(TAG, "activities detected");
        for (DetectedActivity da: detectedActivities) {
            Log.i(TAG, Constants.getActivityString(
                            getApplicationContext(),
                            da.getType()) + " " + da.getConfidence() + "%"
            );
        }

        // Broadcast the list of detected activities.
        sender.sendDetectedActivitiesToApp(detectedActivities);
    }
}
