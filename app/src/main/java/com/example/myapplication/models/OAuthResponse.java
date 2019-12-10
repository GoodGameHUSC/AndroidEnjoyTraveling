package com.example.myapplication.models;

import com.google.gson.annotations.SerializedName;

public class OAuthResponse {

    @SerializedName("success")
    public boolean success;

    @SerializedName("message")
    public String message;

    @SerializedName("data")
    public Token token;


    public class Token {
        @SerializedName("access_token")
        public String access_token;

        @SerializedName("token_type")
        public String token_type;

        @SerializedName("expires_in")
        public Long expires_in;
    }

}
