package com.pureix.easylocator.service.batteryService.listener;

/**
 * Created by MelDiSooQi on 1/27/2017.
 */

public interface BatteryReceiverListener
{
    void onBatteryInformationChanged(int level, int scale, int temperature,
                                     int voltage, float batteryPct, int status,
                                     boolean isCharging, int chargePlug,
                                     boolean usbCharge, boolean acCharge);
}
