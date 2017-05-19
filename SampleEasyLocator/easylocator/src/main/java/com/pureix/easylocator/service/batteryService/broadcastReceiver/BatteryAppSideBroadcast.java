package com.pureix.easylocator.service.batteryService.broadcastReceiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.google.gson.Gson;
import com.pureix.easylocator.model.ObservableHandler;
import com.pureix.easylocator.service.batteryService.bean.BatteryInformation;
import com.pureix.easylocator.service.batteryService.BatteryServicesConstant;

public class BatteryAppSideBroadcast extends BroadcastReceiver
{
    public static ObservableHandler batteryChangedObservable = new ObservableHandler();

    boolean isLocationInitialized = false;

    public BatteryAppSideBroadcast() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle extras = intent.getExtras();

        if(extras.getInt(BatteryServicesConstant.SERVICE_ID) ==
                BatteryServicesConstant.SERVICE_ID_SEND_JSON_BATTERY_INFORMATION) {

            String jsonBatteryInformation = extras.getString(BatteryServicesConstant.JSON_BATTERY_INFORMATION);

            BatteryInformation batteryInformation = new Gson()
                    .fromJson(jsonBatteryInformation, BatteryInformation.class);

                batteryChangedObservable.setChange(batteryInformation);

        }

    }
}
