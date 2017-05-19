package com.pureix.easylocator.service.locatonService.broadcastReceiver;

import android.content.Context;
import android.content.Intent;

import com.pureix.easylocator.service.locatonService.LocationServicesConstant;

/**
 * Created by MelDiSooQi on 7/16/2016.
 */
public class LocationSender
{
    private final Context context;
    private final Class<?> aClass;
    private Intent intent;

    public LocationSender(Context context, Class<?> aClass)
    {
        this.context = context;
        this.aClass = aClass;
        this.intent = new Intent(context, aClass);
    }

    public Intent getIntent() {
        return intent;
    }

/*
    public void sendLocationToApp(int userID, double latitude, double longitude, String provider, float accuracy, long time)
    {
        intent.putExtra(LocationServicesConstant.SERVICE_ID         , LocationServicesConstant.SERVICE_ID_SEND_LOCATION);
        intent.putExtra(LocationServicesConstant.USER_ID            , userID);
        intent.putExtra(LocationServicesConstant.LATITUDE           , latitude);
        intent.putExtra(LocationServicesConstant.LONGITUDE          , longitude);
        intent.putExtra(LocationServicesConstant.LOCATION_PROVIDER  , provider);
        intent.putExtra(LocationServicesConstant.ACCURACY           , accuracy);
        intent.putExtra(LocationServicesConstant.TIME               , time);

        context.sendBroadcast(intent);
    }
*/

    public void sendLocationToApp(int userID, double latitude, double longitude, String provider, float accuracy, long time, double altitude, float bearing, float speed)
    {
        intent.putExtra(LocationServicesConstant.SERVICE_ID         , LocationServicesConstant.SERVICE_ID_SEND_LOCATION);
        intent.putExtra(LocationServicesConstant.USER_ID            , userID);
        intent.putExtra(LocationServicesConstant.LATITUDE           , latitude);
        intent.putExtra(LocationServicesConstant.LONGITUDE          , longitude);
        intent.putExtra(LocationServicesConstant.LOCATION_PROVIDER  , provider);
        intent.putExtra(LocationServicesConstant.ACCURACY           , accuracy);
        intent.putExtra(LocationServicesConstant.TIME               , time);
        intent.putExtra(LocationServicesConstant.ALTITUDE           , altitude);
        intent.putExtra(LocationServicesConstant.BEARING            , bearing);
        intent.putExtra(LocationServicesConstant.SPEED              , speed);

        context.sendBroadcast(intent);
    }

    public void sendLocationToApp(String userID, double latitude, double longitude, String provider, float accuracy, long time, double altitude, float bearing, float speed)
    {
        intent.putExtra(LocationServicesConstant.SERVICE_ID         , LocationServicesConstant.SERVICE_ID_SEND_LOCATION);
        intent.putExtra(LocationServicesConstant.USER_ID            , userID);
        intent.putExtra(LocationServicesConstant.LATITUDE           , latitude);
        intent.putExtra(LocationServicesConstant.LONGITUDE          , longitude);
        intent.putExtra(LocationServicesConstant.LOCATION_PROVIDER  , provider);
        intent.putExtra(LocationServicesConstant.ACCURACY           , accuracy);
        intent.putExtra(LocationServicesConstant.TIME               , time);
        intent.putExtra(LocationServicesConstant.ALTITUDE           , altitude);
        intent.putExtra(LocationServicesConstant.BEARING            , bearing);
        intent.putExtra(LocationServicesConstant.SPEED              , speed);

        context.sendBroadcast(intent);
    }

    public void sendLocationToApp(double latitude, double longitude, String provider, float accuracy, long time, double altitude, float bearing, float speed)
    {
        intent.putExtra(LocationServicesConstant.SERVICE_ID         , LocationServicesConstant.SERVICE_ID_SEND_LOCATION);
        intent.putExtra(LocationServicesConstant.LATITUDE           , latitude);
        intent.putExtra(LocationServicesConstant.LONGITUDE          , longitude);
        intent.putExtra(LocationServicesConstant.LOCATION_PROVIDER  , provider);
        intent.putExtra(LocationServicesConstant.ACCURACY           , accuracy);
        intent.putExtra(LocationServicesConstant.TIME               , time);
        intent.putExtra(LocationServicesConstant.ALTITUDE           , altitude);
        intent.putExtra(LocationServicesConstant.BEARING            , bearing);
        intent.putExtra(LocationServicesConstant.SPEED              , speed);

        context.sendBroadcast(intent);
    }

    public void sendLocationToApp(String jsonLocation)
    {
        intent.putExtra(LocationServicesConstant.SERVICE_ID     , LocationServicesConstant
                .SERVICE_ID_SEND_JSON_LOCATION);
        intent.putExtra(LocationServicesConstant.JSON_LOCATION  , jsonLocation);

        context.sendBroadcast(intent);
    }
}
