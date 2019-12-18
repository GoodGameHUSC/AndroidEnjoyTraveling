package com.example.myapplication.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.myapplication.R;

public class AppLauncherScreen extends AppCompatActivity {

    final AppLauncherScreen instance = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_launcher);

    }

    @Override
    protected void onResume() {
        super.onResume();

        this.initApp();
//        this.initialize();
    }

    private void initApp() {
        Intent startHome = new Intent(instance, HomeScreen.class);
        startActivity(startHome);

    }

    private void initialize() {

        final SharedPreferences loginState = getSharedPreferences("AUTH_STATE", MODE_PRIVATE);


        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        initApp();
                    }
                },
                800);

    }


}
