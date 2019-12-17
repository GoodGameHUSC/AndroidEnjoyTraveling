package com.example.myapplication.services;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface UserService {

    @GET("/api/user/me")
    Call<Object> me(@Header("Authorization") String authHeader);

//    @Multipart
//    @POST("/api/user/update-profile")
//    Call<Object> upload(
//            @Part("description") Object description,
//            @Part MultipartBody.Part file
//    );
}
