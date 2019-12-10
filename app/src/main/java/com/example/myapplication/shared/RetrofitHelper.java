package com.example.myapplication.shared;

import com.example.myapplication.config.APP;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitHelper {

    public static Retrofit create() {
        return new Retrofit.Builder()
                .baseUrl(APP.API_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }
}
