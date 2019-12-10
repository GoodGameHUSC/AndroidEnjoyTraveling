package com.example.myapplication.presenter;

import com.example.myapplication.contracts.SignUpContract;
import com.example.myapplication.services.AuthService;
import com.example.myapplication.shared.RetrofitHelper;
import com.example.myapplication.shared.SharedLocalData;
import com.google.gson.Gson;

import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class SignUpPresenter implements SignUpContract.Presenter {

    private SignUpContract.View mView;

    public void setmView(SignUpContract.View mView) {
        this.mView = mView;
    }

    @Override
    public void handleSignup(String username, String email, String password) {

        Retrofit retrofit = RetrofitHelper.create();

        AuthService authService = retrofit.create(AuthService.class);

        Call<Object> call = authService.doSignUp(email, username, password);

        call.enqueue(new Callback<Object>() {
            @Override
            public void onResponse(Call<Object> call, Response<Object> response) {
                int code = response.code();
                try {
                    if (code == 200) {

                        JSONObject data = new JSONObject(new Gson().toJson(response.body()));
                        String access_token = data.getJSONObject("data").get("access_token").toString();

                        SharedLocalData.saveAccessToken(access_token);

                        mView.signupSuccess();
                    } else {

                        JSONObject error = new JSONObject(response.errorBody().string());
                        mView.signupFail(error.getString("message"));
                    }
                } catch (Exception jsonE) {
                    jsonE.printStackTrace();
                    mView.signupFail("Login failed");
                }

            }

            @Override
            public void onFailure(Call<Object> call, Throwable t) {
                mView.signupFail("Login failed");
            }
        });
    }
}
