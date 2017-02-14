package com.pureix.easylocator.service.locatonService.bean;

/**
 * Created by MelDiSooQi on 7/16/2016.
 */
public class Location
{
    private double  latitude;
    private double  longitude;
    private String locationProvider;
    private Float accuracy;
    private long    time;
    private double  altitude;
    private float   bearing;
    private float   speed;

    public Location() {
    }

    public Location(double latitude, double longitude, String locationProvider, Float accuracy, long time, double altitude, float bearing, float speed)
    {
        this.latitude           = latitude;
        this.longitude          = longitude;
        this.locationProvider   = locationProvider;
        this.accuracy           = accuracy;
        this.time               = time;
        this.altitude           = altitude;
        this.bearing            = bearing;
        this.speed              = speed;
    }

    public void setLocation(double latitude, double longitude, String locationProvider, Float accuracy, long time, double altitude, float bearing, float speed)
    {
        this.latitude           = latitude;
        this.longitude          = longitude;
        this.locationProvider   = locationProvider;
        this.accuracy           = accuracy;
        this.time               = time;
        this.altitude           = altitude;
        this.bearing            = bearing;
        this.speed              = speed;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public String getLocationProvider() {
        return locationProvider;
    }

    public Float getAccuracy() {
        return accuracy;
    }

    public long getTime() {
        return time;
    }

    public double getAltitude() {
        return altitude;
    }

    public float getBearing() {
        return bearing;
    }

    public float getSpeed() {
        return speed;
    }
}
