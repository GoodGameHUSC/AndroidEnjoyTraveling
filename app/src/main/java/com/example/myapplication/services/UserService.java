package com.example.myapplication.services;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface UserService {

    @GET("/api/user/me")
    Call<Object> me(@Header("Authorization") String authHeader);

}
