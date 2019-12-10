package com.example.myapplication.presenter;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import com.example.myapplication.MyApplication;
import com.example.myapplication.config.APP;
import com.example.myapplication.contracts.LoginContract;
import com.example.myapplication.models.ErrorResponse;
import com.example.myapplication.models.OAuthResponse;
import com.example.myapplication.models.User;
import com.example.myapplication.services.AuthService;
import com.example.myapplication.shared.RetrofitHelper;
import com.example.myapplication.shared.SharedLocalData;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginPresenter implements LoginContract.Presenter {

    private LoginContract.View mView;

    public void setmView(LoginContract.View mView) {
        this.mView = mView;
    }

    @Override
    public void handleLogin(String username, String password) {

        Retrofit retrofit = RetrofitHelper.create();

        AuthService authService = retrofit.create(AuthService.class);

        Call<Object> call = authService.doLogin(username, password);

        call.enqueue(new Callback<Object>() {
            @Override
            public void onResponse(Call<Object> call, Response<Object> response) {
                int code = response.code();
                try {
                    if (code == 200) {

                        JSONObject data = new JSONObject(new Gson().toJson(response.body()));
                        String access_token = data.getJSONObject("data").get("access_token").toString();

                        SharedLocalData.saveAccessToken(access_token);

                        mView.loginSuccess();
                    } else {

                        JSONObject error = new JSONObject(response.errorBody().string());
                        mView.loginFail(error.getString("message"));
                    }
                } catch (Exception jsonE) {
                    jsonE.printStackTrace();
                    mView.loginFail("Login failed");
                }

            }

            @Override
            public void onFailure(Call<Object> call, Throwable t) {
                mView.loginFail("Login failed");
            }
        });

    }
}
