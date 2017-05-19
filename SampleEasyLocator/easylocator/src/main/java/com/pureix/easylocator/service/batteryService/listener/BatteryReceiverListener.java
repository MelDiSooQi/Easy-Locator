package com.pureix.easylocator.service.batteryService.listener;

import com.pureix.easylocator.service.batteryService.bean.BatteryInformation;

/**
 * Created by MelDiSooQi on 1/27/2017.
 */

public interface BatteryReceiverListener
{
    /*void onBatteryInformationChanged(int level, int scale, int temperature,
                                     int voltage, float batteryPct, int status,
                                     boolean isCharging, int chargePlug,
                                     boolean usbCharge, boolean acCharge);*/

    void onBatteryInformationChanged(BatteryInformation batteryInformation);
}
