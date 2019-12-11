package com.example.myapplication.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.myapplication.R;
import com.example.myapplication.contracts.AppActivity;
import com.example.myapplication.contracts.DestinationDetailContract;
import com.example.myapplication.models.Destination;
import com.example.myapplication.presenter.DestinationDetailPresenter;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

public class DestinationDetail extends AppCompatActivity implements AppActivity, DestinationDetailContract.View {

    int id;
    Destination destination;
    DestinationDetailPresenter presenter;

    TextView name, province, address, description;
    ImageView avatarImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_destination_detail);

        initView();
        initData();

        initPresenter();
        registerListener();
    }

    void initData() {
        Intent intent = getIntent();
        id = intent.getIntExtra("ID", 0);
        destination = new Gson().fromJson(intent.getStringExtra("DATA"), new TypeToken<Destination>() {
        }.getType());

        Picasso.get().load(destination.image).into(avatarImage);
        name.setText(destination.name);
        province.setText(destination.province.name);
        address.setText(destination.address);
        description.setText(destination.description);
    }

    @Override
    public void initView() {

        name = findViewById(R.id.des_detail_name);
        province = findViewById(R.id.des_detail_province);
        address = findViewById(R.id.des_detail_address);
        description = findViewById(R.id.des_detail_description);
        avatarImage = findViewById(R.id.des_detail_image);
    }

    @Override
    public void initPresenter() {
        presenter = new DestinationDetailPresenter();
        presenter.setView(this);
    }

    @Override
    public void registerListener() {

    }

    @Override
    public void onGetDataSuccess(JSONObject data) {

    }

    @Override
    public void onGetDataFail(String errors) {

    }
}
