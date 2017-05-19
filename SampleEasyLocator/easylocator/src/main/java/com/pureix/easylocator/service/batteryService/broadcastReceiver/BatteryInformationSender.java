package com.pureix.easylocator.service.batteryService.broadcastReceiver;

import android.content.Context;
import android.content.Intent;

import com.pureix.easylocator.service.batteryService.BatteryServicesConstant;
import com.pureix.easylocator.service.locatonService.LocationServicesConstant;

/**
 * Created by MelDiSooQi on 7/16/2016.
 */
public class BatteryInformationSender
{
    private final Context context;
    private final Class<?> aClass;
    private Intent intent;

    public BatteryInformationSender(Context context, Class<?> aClass)
    {
        this.context = context;
        this.aClass = aClass;
        this.intent = new Intent(context, aClass);
    }

    public Intent getIntent() {
        return intent;
    }


    public void sendBatteryInformationToApp(String jsonLocation)
    {
        intent.putExtra(BatteryServicesConstant.SERVICE_ID     , BatteryServicesConstant
                .SERVICE_ID_SEND_JSON_BATTERY_INFORMATION);
        intent.putExtra(BatteryServicesConstant.JSON_BATTERY_INFORMATION  , jsonLocation);

        context.sendBroadcast(intent);
    }
}
