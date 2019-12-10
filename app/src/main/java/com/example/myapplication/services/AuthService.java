package com.example.myapplication.services;

import com.example.myapplication.models.OAuthResponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface AuthService {

    @FormUrlEncoded
    @POST("/api/auth/login")
    Call<Object> doLogin(
            @Field("email") String email,
            @Field("password") String password
    );

    @FormUrlEncoded
    @POST("/api/auth/signup")
    Call<Object> doSignUp(
            @Field("email") String email,
            @Field("username") String username,
            @Field("password") String password
    );

}
