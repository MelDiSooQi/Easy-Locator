package com.pureix.easylocator.controller.service;

import com.pureix.easylocator.model.bean.CustomLocation;
import com.pureix.easylocator.service.locatonService.Listener.LocationReceiverListener;

/**
 * Created by MelDiSooQi on 2/18/2017.
 */

public class SmartLocationAPI
{
    private boolean isSmart = true;
    private LocationReceiverListener locationReceiverListener;

    public void smart(boolean isSmart) {
        this.isSmart = isSmart;
    }

    public boolean isSmart() {
        return isSmart;
    }

    public void customLocation(CustomLocation customLocation) {

    }

    public void setLocationReceiverListener(LocationReceiverListener locationReceiverListener) {
        this.locationReceiverListener = locationReceiverListener;
    }
}
