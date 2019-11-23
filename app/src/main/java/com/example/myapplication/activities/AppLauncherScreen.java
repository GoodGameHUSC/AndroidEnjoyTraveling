package com.example.myapplication.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.myapplication.R;

public class AppLauncherScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_launcher);

    }

    @Override
    protected void onResume() {
        super.onResume();

        this.initialize();
    }

    // Test

    private void initialize() {

        final SharedPreferences loginState = getSharedPreferences("AUTH_STATE", MODE_PRIVATE);

//        SharedPreferences.Editor editor = loginState.edit();
//
//        editor.putString("username", "pham hung");
//        editor.putBoolean("isLogin", true);
//
//        editor.commit();

        final AppLauncherScreen instance = this;
        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        Log.v("login", String.valueOf(loginState.getBoolean("isLogin", false)));

                        if (!loginState.getBoolean("isLogin", false)) {
                            Intent startLogin = new Intent(instance, LoginScreen.class);
                            startActivity(startLogin);
                        } else {
                            Intent startHome = new Intent(instance, HomeScreen.class);
                            startActivity(startHome);
                        }
                    }
                },
                1000);


    }

}
