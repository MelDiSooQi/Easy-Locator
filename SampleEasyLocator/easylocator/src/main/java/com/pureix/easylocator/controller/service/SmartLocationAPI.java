package com.pureix.easylocator.controller.service;

import android.content.Context;

import com.pureix.easylocator.model.bean.CustomSettingsLocation;
import com.pureix.easylocator.service.SmartLocationBusiness;
import com.pureix.easylocator.service.locatonService.Listener.LocationReceiverListener;

/**
 * Created by MelDiSooQi on 2/18/2017.
 */

public class SmartLocationAPI
{
    private static int instanceCounter;

    private boolean isSmart = true;
    private LocationReceiverListener locationReceiverListener;
    private SmartLocationBusiness smartLocationBusiness;
    private Context context;

    public SmartLocationAPI(Context context) {
        this.context = context;

        smartLocationBusiness = new SmartLocationBusiness();
        smartLocationBusiness.setSmart(isSmart);
        instanceCounter++;
    }

    public static int getInstanceCounter() {
        return instanceCounter;
    }

    public void smart(boolean isSmart) {
        this.isSmart = isSmart;
        smartLocationBusiness.setSmart(isSmart);
    }

    public boolean isSmart() {
        return isSmart;
    }

    public void customLocation(CustomSettingsLocation customSettingsLocation) {
        smartLocationBusiness.setCustomSettingsLocation(customSettingsLocation);
    }

    public void setLocationReceiverListener(LocationReceiverListener locationReceiverListener)
    {
        smartLocationBusiness.start(context);
        this.locationReceiverListener = locationReceiverListener;
    }

    public void pause() {
        smartLocationBusiness.pause();
    }
}
