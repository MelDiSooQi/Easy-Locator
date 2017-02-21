package com.pureix.easylocator.model.bean;

/**
 * Created by MelDiSooQi on 2/18/2017.
 */
public class CustomSettingsLocation {
    private int priority;
    private long interval;
    private long fastestInterval;
    private float smallestDisplacement;
    private int detectedActivityType;
    private String detectedActivityProvider;

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public void setInterval(long interval) {
        this.interval = interval;
    }

    public void setFastestInterval(long fastestInterval) {
        this.fastestInterval = fastestInterval;
    }

    public void setSmallestDisplacement(float smallestDisplacement) {
        this.smallestDisplacement = smallestDisplacement;
    }

    public void setDetectedActivityType(int detectedActivityType) {
        this.detectedActivityType = detectedActivityType;
    }

    public void setDetectedActivityProvider(String detectedActivityProvider) {
        this.detectedActivityProvider = detectedActivityProvider;
    }

    public int getPriority() {
        return priority;
    }

    public long getInterval() {
        return interval;
    }

    public long getFastestInterval() {
        return fastestInterval;
    }

    public float getSmallestDisplacement() {
        return smallestDisplacement;
    }

    public int getDetectedActivityType() {
        return detectedActivityType;
    }

    public String getDetectedActivityProvider() {
        return detectedActivityProvider;
    }
}
