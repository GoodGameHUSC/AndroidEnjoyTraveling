package com.example.myapplication.activities;

import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.R;
import com.example.myapplication.contracts.AppActivity;
import com.example.myapplication.contracts.DestinationDetailContract;
import com.example.myapplication.models.Destination;
import com.example.myapplication.presenter.DestinationDetailPresenter;
import com.example.myapplication.services.DestinationService;
import com.example.myapplication.shared.RetrofitHelper;
import com.example.myapplication.shared.SharedLocalData;
import com.example.myapplication.utils.ImageDialog;
import com.example.myapplication.utils.RequireLogin;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class DestinationDetail extends AppCompatActivity implements AppActivity, DestinationDetailContract.View {

    int id;
    DestinationDetail instance;

    Destination destination;
    DestinationDetailPresenter presenter;

    TextView name, province, address, description, likeCount;
    TextView open_time_txt;
    ImageButton goMap, viewPhoto, like_bnt, rate_btn, share_btn;
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
        DecimalFormat dd = new DecimalFormat("##.00");
        Intent intent = getIntent();
        id = intent.getIntExtra("ID", 0);
        destination = new Gson().fromJson(intent.getStringExtra("DATA"), new TypeToken<Destination>() {
        }.getType());

        Picasso.get().load(destination.image).into(avatarImage);
        name.setText(destination.name);
//        province.setText(destination.province.name);
        address.setText(destination.address);
        description.setText(destination.description);
        likeCount.setText(destination.likes_count + " Like");
        if (destination.open_time != 0)
            open_time_txt.setText(dd.format(destination.open_time) + "h - " + dd.format(destination.close_time) + "h");
        else open_time_txt.setText("All days");
    }

    void updateData() {

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
        like_bnt = findViewById(R.id.des_detail_like_button);
        rate_btn = findViewById(R.id.des_detail_rate_btn);

        open_time_txt = findViewById(R.id.open_time);
        share_btn = findViewById(R.id.des_detail_btn_share);
    }

    @Override
    public void initPresenter() {
        presenter = new DestinationDetailPresenter();
        presenter.setView(this);
        presenter.getData(destination.id);
    }

    @Override
    public void registerListener() {

        goMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Map<String, String> currentLocation = SharedLocalData.getCurrentLocation();

                String currentLocationLat = currentLocation.get("lat");
                String currentLocationLng = currentLocation.get("lng");
                String uri = String.format(Locale.ENGLISH, "geo:%f,%f", Double.parseDouble(destination.lat), Double.parseDouble(destination.lng));

                if (!currentLocationLat.isEmpty() && !currentLocationLng.isEmpty())
                    uri = String.format(Locale.ENGLISH, "http://maps.google.com/maps?saddr=%f,%f&daddr=%f,%f",
                            Double.parseDouble(currentLocationLat), Double.parseDouble(currentLocationLng), Double.parseDouble(destination.lat), Double.parseDouble(destination.lng));
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
                instance.startActivity(intent);
            }
        });

        viewPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ImageDialog dialog = new ImageDialog(instance, destination.gallery);
                dialog.show();
                Window window = dialog.getWindow();
                window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
            }
        });

        like_bnt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                like();
            }
        });

        share_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Intent shareIntent = new Intent(Intent.ACTION_SEND);
                    shareIntent.setType("text/plain");
                    shareIntent.putExtra(Intent.EXTRA_SUBJECT, "New Post");
                    String shareMessage = "Enjoy traveling destination: " + destination.name + "" +
                            ", " + destination.address + ", " + destination.image;

                    shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage);
                    shareIntent.putExtra(Intent.EXTRA_STREAM, destination.image);
                    startActivity(Intent.createChooser(shareIntent, "Choose app"));
                } catch (Exception e) {
                    //e.toString();
                }
            }
        });
    }

    void getLocation() {

    }

    void like() {
        RequireLogin.require(this);

        String accessToken = SharedLocalData.getAccessToken();
        if (accessToken.isEmpty()) return;
        Retrofit retrofit = RetrofitHelper.create();

        DestinationService service = retrofit.create(DestinationService.class);

        Call<Object> call = service.like(destination.id, "Bearer " + accessToken);

        call.enqueue(new Callback<Object>() {
            @Override
            public void onResponse(Call<Object> call, Response<Object> response) {
                int code = response.code();
                try {
                    if (code == 200) {
                        destination.likes_count++;
                        likeCount.setText(destination.likes_count + " Like");
                        Toast.makeText(instance, "Liked", Toast.LENGTH_SHORT).show();
                    } else {
                        JSONObject error = new JSONObject(response.errorBody().string());
                        Toast.makeText(instance, "Opps! Try late", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    Toast.makeText(instance, "Opps! Try late", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(Call<Object> call, Throwable t) {
                Toast.makeText(instance, "Opps! Try late", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onGetDataSuccess(JSONObject data) {
        try {

            Gson gson = new Gson();

            Destination destinations_data = gson.fromJson(data.toString(), new TypeToken<Destination>() {
            }.getType());

            destination = destinations_data;

//            Toast.makeText(this, destination.name, Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Toast.makeText(this, "Opps!", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onGetDataFail(String errors) {

    }
}
