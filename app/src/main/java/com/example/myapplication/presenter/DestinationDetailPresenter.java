package com.example.myapplication.presenter;

import com.example.myapplication.contracts.DestinationDetailContract;

public class DestinationDetailPresenter implements DestinationDetailContract.Presenter {

    DestinationDetailContract.View mView;

    @Override
    public void setView(DestinationDetailContract.View view) {
        mView = view;
    }

    @Override
    public void getData() {

    }
}
