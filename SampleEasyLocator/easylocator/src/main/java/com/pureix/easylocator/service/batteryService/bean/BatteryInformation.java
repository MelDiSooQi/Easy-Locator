package com.pureix.easylocator.service.batteryService.bean;

/**
 * Created by MelDiSooQi on 5/18/2017.
 */

public class BatteryInformation
{
    int level;
    int scale;
    int temperature;
    int voltage;
    float batteryPct;
    int status;
    boolean isCharging;
    int chargePlug;
    boolean usbCharge;
    boolean acCharge;

    public BatteryInformation() {
    }

    public BatteryInformation(int level, int scale, int temperature, int voltage,
                              float batteryPct, int status, boolean isCharging,
                              int chargePlug, boolean usbCharge, boolean acCharge) {
        this.level = level;
        this.scale = scale;
        this.temperature = temperature;
        this.voltage = voltage;
        this.batteryPct = batteryPct;
        this.status = status;
        this.isCharging = isCharging;
        this.chargePlug = chargePlug;
        this.usbCharge = usbCharge;
        this.acCharge = acCharge;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getScale() {
        return scale;
    }

    public void setScale(int scale) {
        this.scale = scale;
    }

    public int getTemperature() {
        return temperature;
    }

    public void setTemperature(int temperature) {
        this.temperature = temperature;
    }

    public int getVoltage() {
        return voltage;
    }

    public void setVoltage(int voltage) {
        this.voltage = voltage;
    }

    public float getBatteryPct() {
        return batteryPct;
    }

    public void setBatteryPct(float batteryPct) {
        this.batteryPct = batteryPct;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public boolean isCharging() {
        return isCharging;
    }

    public void setCharging(boolean charging) {
        isCharging = charging;
    }

    public int getChargePlug() {
        return chargePlug;
    }

    public void setChargePlug(int chargePlug) {
        this.chargePlug = chargePlug;
    }

    public boolean isUsbCharge() {
        return usbCharge;
    }

    public void setUsbCharge(boolean usbCharge) {
        this.usbCharge = usbCharge;
    }

    public boolean isAcCharge() {
        return acCharge;
    }

    public void setAcCharge(boolean acCharge) {
        this.acCharge = acCharge;
    }
}
