package com.example.myapplication.services;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.Tag;

public interface DestinationService {

//    @FormUrlEncoded
//    @POST("/api/auth/signup")
//    Call<Object> doSignUp(
//            @Field("email") String email,
//            @Field("username") String username,
//            @Field("password") String password
//    );

    @GET("/api/destination")
    Call<Object> getAll();


    @GET("/api/destination/search?")
    Call<Object> search(
            @Query("keyword") String keyword
    );
}
