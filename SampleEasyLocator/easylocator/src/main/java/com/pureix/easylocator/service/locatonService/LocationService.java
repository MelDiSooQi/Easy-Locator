package com.pureix.easylocator.service.locatonService;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.location.Location;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.PowerManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.gson.Gson;
import com.pureix.easylocator.model.storage.LocalStorage;
import com.pureix.easylocator.model.storage.LocalStorageConstant;
import com.pureix.easylocator.service.locatonService.Listener.LocationReceiverListener;
import com.pureix.easylocator.service.locatonService.broadcastReceiver.LocationBroadcast;
import com.pureix.easylocator.service.locatonService.broadcastReceiver.LocationSender;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import static com.pureix.easylocator.controller.service.LocationAPI.locationReceiverListener;

/**
 * Created by MelDiSooQi on 1/28/2017.
 */

public class LocationService extends Service implements
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener {
    private String TAG = "LocationServiceTAG";

    private Context context;
    private Timer   mtimer;

    private LocationSender senderHandler;

    // Binder given to clients
    private IBinder mBinder = new LocalBinder();
    private PowerManager.WakeLock mWakeLock;

    // Flag that indicates if a request is underway.
    private boolean mInProgress;
    private LocationRequest mLocationRequest;
    private Boolean servicesAvailable = false;

    private final static int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;

    private GoogleApiClient mGoogleApiClient;

    /**
     * Class used for the client Binder.  Because we know this service always
     * runs in the same process as its clients, we don't need to deal with IPC.
     */
    public class LocalBinder extends Binder {
        public LocationService getService() {
            // Return this instance of LocalService so clients can call public methods
            return LocationService.this;
        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent, flags, startId);
        Log.e(TAG, "onStartCommand");

        PowerManager mgr = (PowerManager) getSystemService(Context.POWER_SERVICE);

        /*
        WakeLock is reference counted so we don't want to create multiple WakeLocks. So do a check before initializing and acquiring.

        This will fix the "java.lang.Exception: WakeLock finalized while still held: MyWakeLock" error that you may find.
        */
        if (this.mWakeLock == null) { //**Added this
            this.mWakeLock = mgr.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "MyWakeLock");
        }

        if (!this.mWakeLock.isHeld()) { //**Added this
            this.mWakeLock.acquire();
        }

        if (!servicesAvailable || mGoogleApiClient.isConnected() || mInProgress)
            return START_STICKY;

        setUpLocationClientIfNeeded();
        if (!mGoogleApiClient.isConnected() || !mGoogleApiClient.isConnecting() && !mInProgress) {
            //appendLog(DateFormat.getDateTimeInstance().format(new Date()) + ": Started", Constants.LOG_FILE);
            mInProgress = true;
            mGoogleApiClient.connect();
        }

        return START_STICKY;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.e(TAG, "onCreate");
        context = getApplicationContext();

        senderHandler 	= new LocationSender(context,
                LocationBroadcast.class);

        mInProgress = false;
        // Create the LocationRequest object
        mLocationRequest = LocationRequest.create();
        // Use high accuracy
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        // Set the update interval to 5 seconds
        mLocationRequest.setInterval(Constants.UPDATE_INTERVAL);
//        mLocationRequest.setInterval(1000);
        // Set the fastest update interval to 1 second
//        mLocationRequest.setFastestInterval(1000);
        mLocationRequest.setFastestInterval(Constants.FASTEST_INTERVAL);
        //update the location every some distance
        //mLocationRequest.setSmallestDisplacement(10);

        servicesAvailable = servicesConnected();

        /*
         * Create a new location client, using the enclosing class to
         * handle callbacks.
         */
        setUpLocationClientIfNeeded();
    }

    private void setUpLocationClientIfNeeded() {
        if (mGoogleApiClient == null)
            buildGoogleApiClient();
    }

    /*
     * Create a new location client, using the enclosing class to
     * handle callbacks.
     */
    protected synchronized void buildGoogleApiClient() {
        this.mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
    }

    private boolean servicesConnected() {

        // Check that Google Play services is available
        int resultCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);

        // If Google Play services is available
        if (ConnectionResult.SUCCESS == resultCode) {

            return true;
        } else {

            return false;
        }
    }

    private boolean checkPlayServices() {
        GoogleApiAvailability googleAPI = GoogleApiAvailability.getInstance();
        int result = googleAPI.isGooglePlayServicesAvailable(this);
        if (result != ConnectionResult.SUCCESS) {
            if (googleAPI.isUserResolvableError(result)) {
                //googleAPI.getErrorDialog(this, result,
                //        PLAY_SERVICES_RESOLUTION_REQUEST).show();
            }

            return false;
        }

        return true;
    }

    /*
     * Called by Location Services when the request to connect the
     * client finishes successfully. At this point, you can
     * request the current location or start periodic updates
     */
    @Override
    public void onConnected(@Nullable Bundle bundle) {
        Log.e(TAG, "onConnected");
        try {
            Location location = getLastLocation();
            sendLocationToBroadcast(location);
            if(locationReceiverListener !=null) {
                locationReceiverListener.getLastKnownLocation(location);
//                locationReceiverListener.onLocationChanged(location);
            }
        } catch (Exception e) {
        }
        // Request location updates using static settings
        //Intent intent = new Intent(this, LocationReceiver.class);
//        LocationServices.FusedLocationApi.requestLocationUpdates(this.mGoogleApiClient, mLocationRequest, this); // This is the changed line.
        LocationServices
                .FusedLocationApi
                .requestLocationUpdates(this.mGoogleApiClient,
                        mLocationRequest,
                        this); // This is the changed line.

        //appendLog(DateFormat.getDateTimeInstance().format(new Date()) + ": Connected", Constants.LOG_FILE);
    }

    private Location getLastLocation() {
        Location location = null;

        Boolean locationAvailableInByGoogle = false;

        if (getLastLocationByGoogle() != null) {
            location = getLastLocationByGoogle();
            locationAvailableInByGoogle = true;
        }

        //if(isLocationInLocalStorage)
        if(getLocationInLocalStorage() != null)
        {
            // get from local storage
            location = getLocationInLocalStorage();
            Log.e(TAG, "getLastLocationFromLocalStorage");
        }else
        {
            if(locationAvailableInByGoogle)
            {
//                setLocationInLocalStorage(getLastLocationByGoogle());
                saveLocationInLocalStorage(0, getLastLocationByGoogle());
                Log.e(TAG, "getLastLocationFromGoogle");
            }else
            {
                // In this case
                // location not Available at all
                // location == null
            }
        }

        return location;
    }

    private Location getLastLocationByGoogle() {
        return LocationServices
                .FusedLocationApi
                .getLastLocation(this.mGoogleApiClient);
    }

    private Location getLocationInLocalStorage() {
        String jsonLocation = (String) LocalStorage.getPreference(context,
                LocalStorageConstant.LAST_KNOWN_LOCATION, null);
        Location location = new Gson().fromJson(jsonLocation, Location.class);
        location.setProvider("LocalStorage");
        return location;
    }

    private void setLocationInLocalStorage(Location location) {
        Log.e(TAG, "setLocationInLocalStorage");
        String jsonLocation = new Gson().toJson(location);
        LocalStorage.setPreference(context, LocalStorageConstant.LAST_KNOWN_LOCATION,
                jsonLocation);
    }

    /*
     * Called by Location Services if the connection to the
     * location client drops because of an error.
     */
    @Override
    public void onConnectionSuspended(int i) {
        Log.e(TAG, "onConnectionSuspended");
        // Turn off the request flag
        mInProgress = false;
        // Destroy the current location client
        mGoogleApiClient = null;
        // Display the connection status
        // Toast.makeText(this, DateFormat.getDateTimeInstance().format(new Date()) + ": Disconnected. Please re-connect.", Toast.LENGTH_SHORT).show();
        //appendLog(DateFormat.getDateTimeInstance().format(new Date()) + ": Disconnected", Constants.LOG_FILE);
    }

    /*
     * Called by Location Services if the attempt to
     * Location Services fails.
     */
    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult)
    {
        Log.e(TAG, "onConnectionFailed");
        mInProgress = false;

        /*
         * Google Play services can resolve some errors it detects.
         * If the error has a resolution, try sending an Intent to
         * start a Google Play services activity that can resolve
         * error.
         */
        if (connectionResult.hasResolution()) {

            // If no resolution is available, display an error dialog
        } else {

        }
    }

    @Override
    public void onDestroy() {
        Log.e(TAG, "onDestroy");
        // Turn off the request flag
        this.mInProgress = false;

        if (this.servicesAvailable && this.mGoogleApiClient != null) {
            this.mGoogleApiClient.unregisterConnectionCallbacks(this);
            this.mGoogleApiClient.unregisterConnectionFailedListener(this);
            this.mGoogleApiClient.disconnect();
            // Destroy the current location client
            this.mGoogleApiClient = null;
        }
        // Display the connection status
        // Toast.makeText(this, DateFormat.getDateTimeInstance().format(new Date()) + ":
        // Disconnected. Please re-connect.", Toast.LENGTH_SHORT).show();

        if (this.mWakeLock != null) {
            this.mWakeLock.release();
            this.mWakeLock = null;
        }
        super.onDestroy();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        Log.e(TAG, "onConfigurationChanged");
        super.onConfigurationChanged(newConfig);
    }

    @Override
    public void onLowMemory() {
        Log.e(TAG, "onLowMemory");
        super.onLowMemory();
    }

    @Override
    public void onTrimMemory(int level) {
        Log.e(TAG, "onTrimMemory");
        super.onTrimMemory(level);
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Log.e(TAG, "onUnbind");
        return super.onUnbind(intent);
    }

    @Override
    public void onRebind(Intent intent) {
        Log.e(TAG, "onRebind");
        super.onRebind(intent);
    }

    @Override
    public void onTaskRemoved(Intent rootIntent) {
        Log.e(TAG, "onTaskRemoved");
        super.onTaskRemoved(rootIntent);
    }

    // Define the callback method that receives location updates
    @Override
    public void onLocationChanged(Location location) {
        Log.e(TAG, "onLocationChanged");
        // Report to the UI that the location was updated

        if(location != null)
        {
            //setLocationInLocalStorage(location);
            saveLocationInLocalStorage(10000, location);
        }

        sendLocationToBroadcast(location);
        if(locationReceiverListener != null) {
            locationReceiverListener.onLocationChanged(location);
        }
    }

//    private void sendLocationToBroadcast(Location location) {
//        senderHandler.sendLocationToApp(
//                location.getLatitude(),
//                location.getLongitude(),
//                location.getProvider(),
//                location.getAccuracy(),
//                location.getTime(),
//                location.getAltitude(),
//                location.getBearing(),
//                location.getSpeed());
//    }
    private void sendLocationToBroadcast(Location location) {
        senderHandler.sendLocationToApp(new Gson().toJson(location));
    }


    /*// Binding
    public static void setLocationListener(LocationReceiverListener locationReceiverListener) {
        LocationService
                .locationReceiverListener = locationReceiverListener;
    }*/

    /** method for clients */
    /*public int getRandomNumber() {
        return new Random().nextInt(100);
    }*/

    private void saveLocationInLocalStorage(long time, final Location location) {
        if(mtimer != null) {
            mtimer.cancel();
        }

        mtimer = new Timer();
        mtimer.schedule(new TimerTask() {
            @Override
            public void run() {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        setLocationInLocalStorage(location);
                    }
                }).start();
            }
        }, time);
    }

}
