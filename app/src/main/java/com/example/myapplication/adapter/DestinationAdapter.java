package com.example.myapplication.adapter;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.R;
import com.example.myapplication.activities.DestinationDetail;
import com.example.myapplication.activities.LoginScreen;
import com.example.myapplication.models.Destination;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.io.InputStream;
import java.net.URL;
import java.util.List;

public class DestinationAdapter extends RecyclerView.Adapter<DestinationAdapter.MyViewHolder> {

    private List<Destination> mDataSet;
    private LayoutInflater mLayoutInflater;
    private Context mContext;

    public DestinationAdapter(Context context, List<Destination> destinationList) {
        this.mDataSet = destinationList;
        this.mContext = context;
        this.mLayoutInflater = LayoutInflater.from(context);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView title, brief, address, rate_avg, total_like;
        ImageView mainImage;

        public MyViewHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.item_des_title);
            brief = itemView.findViewById(R.id.item_des_brief);
            address = itemView.findViewById(R.id.item_des_addess);
            rate_avg = itemView.findViewById(R.id.des_item_rate);
            total_like = itemView.findViewById(R.id.des_item_like);
            mainImage = itemView.findViewById(R.id.item_des_img);

            title.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    viewDetail();
                }
            });

            mainImage.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    viewDetail();
                }
            });

        }

        void viewDetail() {
            int position = getLayoutPosition();

            Destination destination = mDataSet.get(position);

            int id = destination.id;

            Intent intentViewDetail = new Intent(mContext, DestinationDetail.class);
            intentViewDetail.putExtra("ID",id);
            intentViewDetail.putExtra("DATA", new Gson().toJson(destination));

            mContext.startActivity(intentViewDetail);
        }
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View item = mLayoutInflater.inflate(R.layout.fragment_destination_item, viewGroup, false);
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
