package com.example.myapplication.presenter;

import com.example.myapplication.contracts.DestinationDetailContract;
import com.example.myapplication.services.DestinationService;
import com.example.myapplication.shared.RetrofitHelper;
import com.google.gson.Gson;

import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class DestinationDetailPresenter implements DestinationDetailContract.Presenter {

    DestinationDetailContract.View mView;

    @Override
    public void setView(DestinationDetailContract.View view) {
        mView = view;
    }

    @Override
    public void getData(int id) {
        Retrofit retrofit = RetrofitHelper.create();

        DestinationService service = retrofit.create(DestinationService.class);

        Call<Object> call = service.detail(id);

        call.enqueue(new Callback<Object>() {
            @Override
            public void onResponse(Call<Object> call, Response<Object> response) {
                int code = response.code();
                try {
                    if (code == 200) {

                        Gson gson = new Gson();
                        JSONObject data = new JSONObject(gson.toJson(response.body()));
                        data = data.getJSONObject("data");
                        mView.onGetDataSuccess(data);
                    } else {

                        JSONObject error = new JSONObject(response.errorBody().string());
                        mView.onGetDataFail(error.getString("message"));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    mView.onGetDataFail(e.getMessage());
                }

            }

            @Override
            public void onFailure(Call<Object> call, Throwable t) {
                mView.onGetDataFail(t.getMessage());
            }
        });
    }
}
