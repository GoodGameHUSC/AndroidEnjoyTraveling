package com.example.myapplication.shared;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.myapplication.MyApplication;

import java.util.HashMap;
import java.util.Map;

public class SharedLocalData {
    static Context context = MyApplication.getContext();

    public static void saveAccessToken(String accessToken) {
        final SharedPreferences loginState = context.getSharedPreferences("AUTH_STATE", context.MODE_PRIVATE);

        SharedPreferences.Editor editor = loginState.edit();

        editor.putString("access_token", accessToken);

        editor.apply();
    }

    public static String getAccessToken() {
        final SharedPreferences loginState = context.getSharedPreferences("AUTH_STATE", context.MODE_PRIVATE);
        String accessToken = loginState.getString("access_token", "");
        return accessToken;
    }

    public static void saveLocation(String latitude, String longitude) {
        final SharedPreferences loginState = context.getSharedPreferences("AUTH_STATE", context.MODE_PRIVATE);

        SharedPreferences.Editor editor = loginState.edit();

        editor.putString("current_lat", latitude);
        editor.putString("current_lng", longitude);

        editor.apply();
    }


    public static Map<String, String> getCurrentLocation() {
        Map<String, String> result = new HashMap<>();
        final SharedPreferences loginState = context.getSharedPreferences("AUTH_STATE", context.MODE_PRIVATE);

        String lat = loginState.getString("current_lat", "");
        String lng = loginState.getString("current_lng", "");
        if (lat.isEmpty() || lng.isEmpty()) return null;
        result.put("lat", lat);
        result.put("lng", lng);
        return result;
    }
}
