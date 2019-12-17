package com.example.myapplication.contracts;

import org.json.JSONObject;

public class DestinationDetailContract {

    public interface View {

        void onGetDataSuccess(JSONObject data);

        void onGetDataFail(String errors);
    }

    public interface Presenter {

        void setView(DestinationDetailContract.View view);

        void getData(int id);
    }
}
