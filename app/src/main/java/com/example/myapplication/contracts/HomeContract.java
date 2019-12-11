package com.example.myapplication.contracts;

import org.json.JSONObject;

public class HomeContract {

    public interface View {

        void onGetDataSuccess(JSONObject data);

        void onGetDataFail(String errors);
    }

    public interface Presenter {

        void setView(HomeContract.View view);
        void getData();
    }
}
