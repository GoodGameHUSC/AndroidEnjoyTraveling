package com.example.myapplication.services;

import com.example.myapplication.shared.SharedLocalData;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface WishListService {


    @FormUrlEncoded
    @POST("/api/wishlist/add")
    Call<Object> add(
            @Field("destination_id") int destination_id,
            @Header("Authorization") String authHeader
    );


    @FormUrlEncoded
    @POST("/api/wishlist/remove")
    Call<Object> remove(
            @Field("destination_id") int destination_id,
            @Header("Authorization") String authHeader
    );

    @GET("/api/wishlist")
    Call<Object> getAll(@Header("Authorization") String authHeader);

}
