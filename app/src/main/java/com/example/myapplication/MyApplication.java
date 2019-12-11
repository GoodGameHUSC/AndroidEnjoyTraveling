package com.example.myapplication;

import android.Manifest;
import android.app.Application;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.widget.Toast;

import java.security.Permission;

public class MyApplication extends Application implements LocationListener {
    private static Context context;
    protected LocationManager locationManager;
    protected LocationListener locationListener;

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;

//        if (ContextCompat.checkSelfPermission(this,
//                Manifest.permission.ACCESS_FINE_LOCATION)
//                != PackageManager.PERMISSION_GRANTED) {
//
//
//            ActivityCompat.requestPermissions(this,
//                    new String[]{Manifest.permission.READ_CONTACTS},
//                    LocationServic .MY_PERMISSIONS_REQUEST_READ_CONTACTS);
//
//        } else {
//            // Permission has already been granted
//        }
//
//        try {
//            locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
//            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
//        } catch (SecurityException e) {
//            e.printStackTrace();
//            Toast.makeText(this, "Unable to use location", Toast.LENGTH_LONG).show();
//        }

    }

    public static Context getContext() {
        return context;
    }

    @Override
    public void onLocationChanged(Location location) {


    }

    @Override
    public void onProviderDisabled(String provider) {
        Log.d("Latitude", "disable");
    }

    @Override
    public void onProviderEnabled(String provider) {
        Log.d("Latitude", "enable");
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }
}
