package com.pureix.easylocator.service.locatonService.Listener;

import android.location.Location;

/**
 * Created by MelDiSooQi on 1/27/2017.
 */

public interface LocationReceiverListener
{
    void getLastKnownLocation(Location location);

    void onLocationChanged(Location location);
}
