package com.pureix.sampleeasylocator;

import android.location.Location;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.android.gms.location.DetectedActivity;
import com.pureix.easylocator.controller.service.ActivityRecognitionAPI;
import com.pureix.easylocator.controller.service.BatteryAPI;
import com.pureix.easylocator.controller.service.InternetAPI;
import com.pureix.easylocator.controller.service.LocationAPI;
import com.pureix.easylocator.controller.service.SmartLocationAPI;
import com.pureix.easylocator.service.batteryService.bean.BatteryInformation;
import com.pureix.easylocator.model.bean.CustomSettingsLocation;
import com.pureix.easylocator.service.batteryService.listener.BatteryReceiverListener;
import com.pureix.easylocator.service.activityRecognitionService.listener.ActivityRecognitionListener;
import com.pureix.easylocator.service.internetService.listener.ConnectivityReceiverListener;
import com.pureix.easylocator.service.locatonService.Listener.LocationReceiverListener;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import static com.pureix.easylocator.service.batteryService.broadcastReceiver.BatteryAppSideBroadcast.batteryChangedObservable;

public class MainActivity extends AppCompatActivity {

    private TextView txt;
    private LocationAPI locationAPI;
    private ActivityRecognitionAPI activityRecognitionAPI;
    private BatteryAPI batteryAPI;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        txt = (TextView) findViewById(R.id.txt);

        activityRecognitionAPI = new ActivityRecognitionAPI(MainActivity.this);
        activityRecognitionAPI.setActivitiesRecognitionListener(new ActivityRecognitionListener()
        {
            @Override
            public void updateDetectedActivitiesList(ArrayList<DetectedActivity> updatedActivities) {
                //Toast.makeText(MainActivity.this, "okay", Toast.LENGTH_SHORT).show();
                ArrayList<DetectedActivity> tempList = activityRecognitionAPI.getArrayList(updatedActivities);

                for (int i = 0; i < tempList.size(); i++) {
                    txt.append(activityRecognitionAPI.getActivityString(MainActivity.this,
                            tempList.get(i).getType()) +" - "
                    + tempList.get(i).getConfidence()+ "%"+"\n");
                }
                txt.append("\n");
            }
        });

        InternetAPI.networkListener(new ConnectivityReceiverListener() {
            @Override
            public void onNetworkConnectionChanged(boolean isConnected, int connectionProvider) {
                txt.append("isConnected"+isConnected+" - "+ connectionProvider +"\n\n");
            }
        });

        batteryAPI = new BatteryAPI(MainActivity.this);

        batteryAPI.batteryListener(new BatteryReceiverListener() {
            @Override
            public void onBatteryInformationChanged(BatteryInformation batteryInformation) {
                txt.append("level is " + batteryInformation.getLevel()
                        + "/" + batteryInformation.getScale() +
                        ", temp is " + batteryInformation.getTemperature() +
                        ", voltage is " + batteryInformation.getTemperature()
                        + " status :" + batteryInformation.getStatus() +
                        " chargePlug :" + batteryInformation.getChargePlug() +
                        " Battery Pct : " + batteryInformation.getBatteryPct() * 100 +"\n\n");
            }
        });

        locationAPI = new LocationAPI(MainActivity.this);
        locationAPI.setLocationReceiverListener(new LocationReceiverListener() {
            @Override
            public void getLastKnownLocation(Location location) {
                txt.append("getLastKnownLocation "+location+"\n\n");
//                Toast.makeText(MainActivity.this, "Broadlocationss getLastKnownLocation "+location.toString()+"", Toast.LENGTH_SHORT).show();
//                Log.d("Taag", "Broadlocationss getLastKnownLocation "+location.toString()+"");
            }

            @Override
            public void onLocationChanged(Location location) {
                txt.append("onLocationChanged "+location+"\n\n");
//                Toast.makeText(MainActivity.this, "Broadlocationss onLocationChanged "+location.toString()+"", Toast.LENGTH_SHORT).show();
//                Log.d("Taag", "Broadlocationss onLocationChanged "+location.toString()+"");
            }
        });

        SmartLocationAPI smartLocationAPI = new SmartLocationAPI(MainActivity.this);
        smartLocationAPI.smart(true);
        if(!smartLocationAPI.isSmart()) {
            smartLocationAPI.customLocation(new CustomSettingsLocation());
        }
        smartLocationAPI.setLocationReceiverListener(new LocationReceiverListener() {
            @Override
            public void getLastKnownLocation(Location location) {
                txt.append("Smart getLastKnownLocation "+location+"\n\n");
            }

            @Override
            public void onLocationChanged(Location location) {
                txt.append("Smart onLocationChanged "+location+"\n\n");
            }
        });



        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setVisibility(View.GONE);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();
        activityRecognitionAPI.start();
        locationAPI.start();
        batteryAPI.start();
    }

    @Override
    protected void onPause() {
        super.onPause();
        activityRecognitionAPI.pause();
        locationAPI.pause();
        batteryAPI.pause();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[],
                                           int[] grantResults) {
        locationAPI.requestPermission(MainActivity.this);
        locationAPI.onRequestPermissionsResult(requestCode, permissions,
                grantResults);
    }
}
