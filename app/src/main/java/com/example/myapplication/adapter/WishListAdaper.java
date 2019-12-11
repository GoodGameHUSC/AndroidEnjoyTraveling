package com.example.myapplication.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.R;
import com.example.myapplication.activities.DestinationDetail;
import com.example.myapplication.models.Destination;
import com.example.myapplication.services.WishListService;
import com.example.myapplication.shared.RetrofitHelper;
import com.example.myapplication.shared.SharedLocalData;
import com.example.myapplication.utils.RequireLogin;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class WishListAdaper extends RecyclerView.Adapter<WishListAdaper.MyViewHolder> {

    private List<Destination> mDataSet;
    private LayoutInflater mLayoutInflater;
    private Context mContext;

    public WishListAdaper(Context context, List<Destination> destinationList) {
        this.mDataSet = destinationList;
        this.mContext = context;
        this.mLayoutInflater = LayoutInflater.from(context);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView title, brief, address, rate_avg, total_like;
        ImageView mainImage;
        ImageButton removebtn;

        public MyViewHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.wish_des_title);
            brief = itemView.findViewById(R.id.wish_des_brief);
            address = itemView.findViewById(R.id.wish_des_addess);
            rate_avg = itemView.findViewById(R.id.wish_item_rate);
            total_like = itemView.findViewById(R.id.wish_item_like);
            mainImage = itemView.findViewById(R.id.wish_des_img);
            removebtn = itemView.findViewById(R.id.wish_remove);

            title.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    viewDetail();
                }
            });

            mainImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    viewDetail();
                }
            });

            removebtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    new AlertDialog.Builder(mContext)
                            .setMessage("Are you sure delete this destination ?")
                            .setCancelable(false)
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    onClickRemove();
                                }
                            })
                            .setNegativeButton("No", null)
                            .show();

                }
            });

        }

        void viewDetail() {
            int position = getLayoutPosition();

            Destination destination = mDataSet.get(position);

            int id = destination.id;

            Intent intentViewDetail = new Intent(mContext, DestinationDetail.class);
            intentViewDetail.putExtra("ID", id);
            intentViewDetail.putExtra("DATA", new Gson().toJson(destination));

            mContext.startActivity(intentViewDetail);
        }

        void onClickRemove() {

            String currentAccessToken = SharedLocalData.getAccessToken();
            if (currentAccessToken.isEmpty()) {
                RequireLogin.require(mContext);
                return;
            }

            Retrofit retrofit = RetrofitHelper.create();

            WishListService service = retrofit.create(WishListService.class);

            int position = getLayoutPosition();
            Destination destination = mDataSet.get(position);

            int id = destination.id;
            Call<Object> call = service.remove(id, "Bearer " + currentAccessToken);

            call.enqueue(new Callback<Object>() {
                @Override
                public void onResponse(Call<Object> call, Response<Object> response) {
                    int code = response.code();
                    try {
                        if (code == 200) {

                            mDataSet.remove(position);
                            notifyDataSetChanged();

                            Toast.makeText(mContext, "Remove " + destination.name + " from wishlist", Toast.LENGTH_SHORT).show();

                        } else {

                            JSONObject error = new JSONObject(response.errorBody().string());
                            Toast.makeText(mContext, "Remove failed", Toast.LENGTH_SHORT).show();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        Toast.makeText(mContext, "Remove failed", Toast.LENGTH_SHORT).show();
                    }

                }

                @Override
                public void onFailure(Call<Object> call, Throwable t) {

                    Toast.makeText(mContext, "Remove failed", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View item = mLayoutInflater.inflate(R.layout.fragment_wishlist_item, viewGroup, false);
        return new MyViewHolder(item);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int position) {

        Destination destination = mDataSet.get(position);

        // binding data
        Picasso.get().load(destination.image).into(myViewHolder.mainImage);
        myViewHolder.title.setText(destination.name);
        myViewHolder.brief.setText(destination.title);
        myViewHolder.address.setText(destination.address);
        myViewHolder.rate_avg.setText(String.valueOf(destination.avg_rate));
        myViewHolder.total_like.setText(String.valueOf(destination.likes_count));
    }

    @Override
    public int getItemCount() {
        return mDataSet.size();
    }
}
