package com.example.myapplication.activities;

import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.annotation.NonNull;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.myapplication.R;
import com.example.myapplication.contracts.AppActivity;
import com.example.myapplication.fragments.HomeIndexFragment;
import com.example.myapplication.fragments.ProfileFragment;
import com.example.myapplication.fragments.WishListFragment;

public class HomeScreen extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener, AppActivity {

    TextView screenTitle = null;

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

//            this.switchUI(fragment);
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.home_frame_container, fragment)
                    .commit();
            return true;
        }
        return false;
    }

    private void switchUI(Fragment fragment) {

//        switch (fragment instanceof )
    }

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

    }


}
