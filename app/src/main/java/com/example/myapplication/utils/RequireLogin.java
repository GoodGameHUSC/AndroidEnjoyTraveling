package com.example.myapplication.utils;

import android.content.Context;
import android.content.Intent;

import com.example.myapplication.activities.LoginScreen;
import com.example.myapplication.shared.SharedLocalData;

public class RequireLogin {

    public static void require(Context mContext){

        String currentAccessToken = SharedLocalData.getAccessToken();
        if(currentAccessToken.isEmpty()){
            Intent intent =  new Intent(mContext, LoginScreen.class);
            mContext.startActivity(intent);

        }
    }
}
