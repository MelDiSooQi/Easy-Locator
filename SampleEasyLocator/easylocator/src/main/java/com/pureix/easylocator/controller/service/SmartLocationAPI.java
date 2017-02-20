package com.pureix.easylocator.controller.service;

import android.content.Context;

import com.pureix.easylocator.model.bean.CustomLocation;
import com.pureix.easylocator.service.SmartLocationBusiness;
import com.pureix.easylocator.service.locatonService.Listener.LocationReceiverListener;

/**
 * Created by MelDiSooQi on 2/18/2017.
 */

public class SmartLocationAPI
{
    private boolean isSmart = true;
    private LocationReceiverListener locationReceiverListener;
    private SmartLocationBusiness smartLocationBusiness;
    private Context context;

    public SmartLocationAPI(Context context) {
        this.context = context;

        smartLocationBusiness = new SmartLocationBusiness();
        smartLocationBusiness.setSmart(isSmart);
    }

    public void smart(boolean isSmart) {
        this.isSmart = isSmart;
        smartLocationBusiness.setSmart(isSmart);
    }

    public boolean isSmart() {
        return isSmart;
    }

    public void customLocation(CustomLocation customLocation) {
        smartLocationBusiness.setCustomLocation(customLocation);
    }

    public void setLocationReceiverListener(LocationReceiverListener locationReceiverListener)
    {
        smartLocationBusiness.start(context);
        this.locationReceiverListener = locationReceiverListener;
    }

    public void pause() {
        smartLocationBusiness.pause(context);
    }
}
