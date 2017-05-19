package com.pureix.easylocator.service.locatonService.broadcastReceiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.google.gson.Gson;
import com.pureix.easylocator.model.ObservableHandler;
import com.pureix.easylocator.service.locatonService.LocationServicesConstant;
import com.pureix.easylocator.service.locatonService.bean.Location;

public class LocationBroadcast extends BroadcastReceiver
{
    public static ObservableHandler locationLastKnownLocationObservable = new ObservableHandler();
    public static ObservableHandler locationChangedObservable = new ObservableHandler();

    boolean isLocationInitialized = false;

    public LocationBroadcast() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle extras = intent.getExtras();
        if(extras.getInt(LocationServicesConstant.SERVICE_ID) ==
                LocationServicesConstant.SERVICE_ID_SEND_LOCATION)
        {
            Location location;

            int    USER_ID          = extras.getInt   (LocationServicesConstant.USER_ID);
            double latitude         = extras.getDouble(LocationServicesConstant.LATITUDE);
            double longitude        = extras.getDouble(LocationServicesConstant.LONGITUDE);
            String locationProvider = extras.getString(LocationServicesConstant.LOCATION_PROVIDER);
            Float  accuracy         = extras.getFloat(LocationServicesConstant.ACCURACY);
            long   time             = extras.getLong(LocationServicesConstant.TIME);
            double altitude         = extras.getDouble(LocationServicesConstant.ALTITUDE);
            float  bearing          = extras.getFloat(LocationServicesConstant.BEARING);
            float  speed            = extras.getFloat(LocationServicesConstant.SPEED);


            if(!isLocationInitialized) {
                isLocationInitialized = true;
                location = new Location(latitude, longitude, locationProvider, accuracy,
                        time, altitude, bearing, speed);
            }

            float kMeter = speed * 3.6f;

            String s = latitude
                    + " longitude "+ longitude
                    +" Provider "+locationProvider
                    +" altitude "+altitude
                    +" bearing "+bearing
                    +" speed "+ kMeter
                    +" accuracy "+accuracy
                    +" USER_ID "+USER_ID;
            Toast.makeText(context, "From BroadCast : "+s, Toast.LENGTH_SHORT).show();
//            LoggerAndToastHandler.PrintToastMsg(s);
        }else if(extras.getInt(LocationServicesConstant.SERVICE_ID) ==
                LocationServicesConstant.SERVICE_ID_SEND_JSON_LOCATION)
        {
            String jsonLocation = extras.getString(LocationServicesConstant.JSON_LOCATION);

            android.location.Location location = new Gson()
                    .fromJson(jsonLocation, android.location.Location.class);

                if ("LocalStorage".equals(location.getProvider())) {
                    locationLastKnownLocationObservable.setChange(location);
                }
                if (!"LocalStorage".equals(location.getProvider())){
                    locationChangedObservable.setChange(location);
                }

            float kMeter = location.getSpeed() * 3.6f;

            String s = location.getLatitude()
                    + " longitude "+ location.getLongitude()
                    +" Provider "+location.getProvider()
                    +" altitude "+location.getAltitude()
                    +" bearing "+location.getBearing()
                    +" speed "+ kMeter
                    +" accuracy "+location.getAccuracy();

//            Toast.makeText(context, "AFrom BroadCast : "+s, Toast.LENGTH_SHORT).show();
        }
    }
}
