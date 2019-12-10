package com.example.myapplication.shared;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.myapplication.MyApplication;

public class SharedLocalData {
    static Context context = MyApplication.getContext();

    public static void saveAccessToken(String accessToken) {
        final SharedPreferences loginState = context.getSharedPreferences("AUTH_STATE", context.MODE_PRIVATE);

        SharedPreferences.Editor editor = loginState.edit();

        editor.putString("access_token", accessToken);

        editor.apply();
    }
}
