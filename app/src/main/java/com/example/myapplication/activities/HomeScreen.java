package com.example.myapplication.activities;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.R;
import com.example.myapplication.contracts.AppActivity;
import com.example.myapplication.fragments.HomeIndexFragment;
import com.example.myapplication.fragments.ProfileFragment;
import com.example.myapplication.fragments.WishListFragment;
import com.example.myapplication.shared.SharedLocalData;

public class HomeScreen extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener, AppActivity {

    TextView screenTitle = null;
    protected LocationManager locationManager;
    protected LocationListener locationListener;
    Location myLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Bottom nav init
        setContentView(R.layout.activity_home_screen);
        // init
        initView();
        initPresenter();
        registerListener();


        BottomNavigationView navView = findViewById(R.id.nav_view);
        navView.getMenu().getItem(1).setChecked(true);
        navView.setOnNavigationItemSelectedListener(this);

        // Default fragment
        loadFragment(new HomeIndexFragment());

        initLocationListener();

    }


    @Override
    public void initView() {
        screenTitle = findViewById(R.id.page_title);
    }

    @Override
    public void initPresenter() {

    }

    @Override
    public void registerListener() {

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Fragment fragment = null;
        switch (item.getItemId()) {
            case R.id.navigation_home:
                screenTitle.setText("Home");
                fragment = new HomeIndexFragment();
                break;
            case R.id.navigation_wishlist:
                screenTitle.setText("Wishlist");
                fragment = new WishListFragment();
                break;
            case R.id.navigation_profile:
                screenTitle.setText("Profile");
                fragment = new ProfileFragment();
                break;

        }
        return loadFragment(fragment);
    }

    private boolean loadFragment(Fragment fragment) {
        //switching fragment
        if (fragment != null) {

            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.home_frame_container, fragment)
                    .commit();
            return true;
        }
        return false;
    }


    LocationListener mLocationListener = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
            SharedLocalData.saveLocation(String.valueOf(location.getLatitude()), String.valueOf(location.getLongitude()));
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }

        @Override
        public void onProviderEnabled(String provider) {

        }

        @Override
        public void onProviderDisabled(String provider) {

        }
    };

    void initLocationListener() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 200);

        } else {
            try {
                locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, mLocationListener);
                locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, mLocationListener);
                myLocation = locationManager.getLastKnownLocation(LocationManager.PASSIVE_PROVIDER);
                SharedLocalData.saveLocation(String.valueOf(myLocation.getLatitude()), String.valueOf(myLocation.getLongitude()));

            } catch (SecurityException e) {
                e.printStackTrace();
                Toast.makeText(this, "Unable to use location", Toast.LENGTH_LONG).show();
            }
        }
    }


}
