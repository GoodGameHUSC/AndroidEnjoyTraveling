package com.example.myapplication.activities;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
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

import java.util.Locale;

public class DestinationDetail extends AppCompatActivity implements AppActivity, DestinationDetailContract.View {

    int id;
    Destination destination;
    DestinationDetailPresenter presenter;

    TextView name, province, address, description, goMap, likeCount, viewPhoto;
    ImageView avatarImage;

    DestinationDetail instance;

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
//        province.setText(destination.province.name);
        address.setText(destination.address);
        description.setText(destination.description);
        likeCount.setText("Liked by " + destination.likes_count + " people");
    }

    @Override
    public void initView() {

        instance = this;
        name = findViewById(R.id.des_detail_name);
//        province = findViewById(R.id.des_detail_province);
        address = findViewById(R.id.des_detail_address);
        description = findViewById(R.id.des_detail_description);
        avatarImage = findViewById(R.id.des_detail_image);
        likeCount = findViewById(R.id.des_detail_like_count2);
        viewPhoto = findViewById(R.id.des_view_photo);
        goMap = findViewById(R.id.gotoGoogleMap);
    }

    @Override
    public void initPresenter() {
        presenter = new DestinationDetailPresenter();
        presenter.setView(this);
    }

    @Override
    public void registerListener() {

        goMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String uri = String.format(Locale.ENGLISH, "geo:%f,%f", Double.parseDouble(destination.lat), Double.parseDouble(destination.lng));
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
                instance.startActivity(intent);
            }
        });
        viewPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    @Override
    public void onGetDataSuccess(JSONObject data) {

    }

    @Override
    public void onGetDataFail(String errors) {

    }
}
