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
import com.pureix.easylocator.model.bean.CustomLocation;
import com.pureix.easylocator.service.batteryService.listener.BatteryReceiverListener;
import com.pureix.easylocator.service.activityRecognitionService.listener.ActivityRecognitionListener;
import com.pureix.easylocator.service.internetService.listener.ConnectivityReceiverListener;
import com.pureix.easylocator.service.locatonService.Listener.LocationReceiverListener;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private TextView txt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        txt = (TextView) findViewById(R.id.txt);

        ActivityRecognitionAPI.setActivitiesRecognitionListener(new ActivityRecognitionListener()
        {
            @Override
            public void updateDetectedActivitiesList(ArrayList<DetectedActivity> updatedActivities) {
                //Toast.makeText(MainActivity.this, "okay", Toast.LENGTH_SHORT).show();
                ArrayList<DetectedActivity> tempList = ActivityRecognitionAPI.getArrayList(updatedActivities);

                for (int i = 0; i < tempList.size(); i++) {
                    txt.append(ActivityRecognitionAPI.getActivityString(MainActivity.this,
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

        BatteryAPI.start(MainActivity.this);
        BatteryAPI.batteryListener(new BatteryReceiverListener() {
            @Override
            public void onBatteryInformationChanged(int level, int scale, int temperature, int voltage, float batteryPct, int status, boolean isCharging, int chargePlug, boolean usbCharge, boolean acCharge) {
                txt.append("level is " + level + "/" + scale +
                        ", temp is " + temperature +
                        ", voltage is " + voltage
                        + " status :" + status +
                        " chargePlug :" + chargePlug +
                        " Battery Pct : " + batteryPct * 100 +"\n\n");
            }
        });

        LocationAPI.setLocationReceiverListener(new LocationReceiverListener() {
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
            smartLocationAPI.customLocation(new CustomLocation());
        }
        smartLocationAPI.setLocationReceiverListener(new LocationReceiverListener() {
            @Override
            public void getLastKnownLocation(Location location) {

            }

            @Override
            public void onLocationChanged(Location location) {

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
        ActivityRecognitionAPI.start(MainActivity.this);
        LocationAPI.start(MainActivity.this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        ActivityRecognitionAPI.pause(MainActivity.this);
        LocationAPI.pause(MainActivity.this);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[],
                                           int[] grantResults) {
        LocationAPI.onRequestPermissionsResult(requestCode, permissions,
                grantResults);
    }
}
