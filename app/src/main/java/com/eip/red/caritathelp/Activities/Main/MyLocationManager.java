package com.eip.red.caritathelp.Activities.Main;

import android.Manifest;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import java.util.List;

/**
 * Created by pierr on 18/01/2017.
 */

public class MyLocationManager implements LocationListener {

    private MainActivity activity;
    private LocationManager manager;

    public MyLocationManager(MainActivity activity) {
        this.activity = activity;
        manager = (LocationManager) activity.getSystemService(Context.LOCATION_SERVICE);

        Dexter.checkPermissions(new MultiplePermissionsListener() {
            @Override
            public void onPermissionsChecked(MultiplePermissionsReport report) {
                if (report.getGrantedPermissionResponses().size() == 2)
                    obtainLocation();
            }

            @Override
            public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                token.continuePermissionRequest();
            }
        }, Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION);

    }

    @Override
    public void onLocationChanged(Location location) {
        activity.getModelManager().setUserLocation(location);

        manager.removeUpdates(this);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                obtainLocation();
            }
        }, 5000);
    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }

    private void obtainLocation() {
        if (manager.isProviderEnabled(LocationManager.GPS_PROVIDER))
            manager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 1000, this);
        else if (manager.isProviderEnabled(LocationManager.NETWORK_PROVIDER))
            manager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 1000, this);
    }

}
