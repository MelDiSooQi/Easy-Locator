package com.pureix.easylocator.service.locatonService.broadcastReceiver;

import android.content.Context;
import android.content.Intent;

import com.pureix.easylocator.service.locatonService.ServicesConstant;

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
        intent.putExtra(ServicesConstant.SERVICE_ID         , ServicesConstant.SERVICE_ID_SEND_LOCATION);
        intent.putExtra(ServicesConstant.USER_ID            , userID);
        intent.putExtra(ServicesConstant.LATITUDE           , latitude);
        intent.putExtra(ServicesConstant.LONGITUDE          , longitude);
        intent.putExtra(ServicesConstant.LOCATION_PROVIDER  , provider);
        intent.putExtra(ServicesConstant.ACCURACY           , accuracy);
        intent.putExtra(ServicesConstant.TIME               , time);

        context.sendBroadcast(intent);
    }
*/

    public void sendLocationToApp(int userID, double latitude, double longitude, String provider, float accuracy, long time, double altitude, float bearing, float speed)
    {
        intent.putExtra(ServicesConstant.SERVICE_ID         , ServicesConstant.SERVICE_ID_SEND_LOCATION);
        intent.putExtra(ServicesConstant.USER_ID            , userID);
        intent.putExtra(ServicesConstant.LATITUDE           , latitude);
        intent.putExtra(ServicesConstant.LONGITUDE          , longitude);
        intent.putExtra(ServicesConstant.LOCATION_PROVIDER  , provider);
        intent.putExtra(ServicesConstant.ACCURACY           , accuracy);
        intent.putExtra(ServicesConstant.TIME               , time);
        intent.putExtra(ServicesConstant.ALTITUDE           , altitude);
        intent.putExtra(ServicesConstant.BEARING            , bearing);
        intent.putExtra(ServicesConstant.SPEED              , speed);

        context.sendBroadcast(intent);
    }

    public void sendLocationToApp(String userID, double latitude, double longitude, String provider, float accuracy, long time, double altitude, float bearing, float speed)
    {
        intent.putExtra(ServicesConstant.SERVICE_ID         , ServicesConstant.SERVICE_ID_SEND_LOCATION);
        intent.putExtra(ServicesConstant.USER_ID            , userID);
        intent.putExtra(ServicesConstant.LATITUDE           , latitude);
        intent.putExtra(ServicesConstant.LONGITUDE          , longitude);
        intent.putExtra(ServicesConstant.LOCATION_PROVIDER  , provider);
        intent.putExtra(ServicesConstant.ACCURACY           , accuracy);
        intent.putExtra(ServicesConstant.TIME               , time);
        intent.putExtra(ServicesConstant.ALTITUDE           , altitude);
        intent.putExtra(ServicesConstant.BEARING            , bearing);
        intent.putExtra(ServicesConstant.SPEED              , speed);

        context.sendBroadcast(intent);
    }

    public void sendLocationToApp(double latitude, double longitude, String provider, float accuracy, long time, double altitude, float bearing, float speed)
    {
        intent.putExtra(ServicesConstant.SERVICE_ID         , ServicesConstant.SERVICE_ID_SEND_LOCATION);
        intent.putExtra(ServicesConstant.LATITUDE           , latitude);
        intent.putExtra(ServicesConstant.LONGITUDE          , longitude);
        intent.putExtra(ServicesConstant.LOCATION_PROVIDER  , provider);
        intent.putExtra(ServicesConstant.ACCURACY           , accuracy);
        intent.putExtra(ServicesConstant.TIME               , time);
        intent.putExtra(ServicesConstant.ALTITUDE           , altitude);
        intent.putExtra(ServicesConstant.BEARING            , bearing);
        intent.putExtra(ServicesConstant.SPEED              , speed);

        context.sendBroadcast(intent);
    }

    public void sendLocationToApp(String jsonLocation)
    {
        intent.putExtra(ServicesConstant.SERVICE_ID     , ServicesConstant
                .SERVICE_ID_SEND_JSON_LOCATION);
        intent.putExtra(ServicesConstant.JSON_LOCATION  , jsonLocation);

        context.sendBroadcast(intent);
    }
}
