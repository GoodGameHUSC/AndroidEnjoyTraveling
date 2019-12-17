package com.example.myapplication.utils;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.view.MotionEventCompat;
import android.support.v7.widget.CardView;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.myapplication.R;
import com.example.myapplication.models.Destination;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ImageDialog extends Dialog {

    Activity activity;
    List<Destination.Gallery> list_image;
    ImageView display_image;
    CardView dialog_image_holder;
    ImageButton dialog_gallery_next, dialog_gallery_prev;
    TextView dialog_gallery_title;
    int currentIndex = 0;

    public ImageDialog(Activity act, List<Destination.Gallery> gal) {
        super(act);
        activity = act;
        list_image = gal;

    }

    void init() {
        display_image = findViewById(R.id.dialog_gallery_image);
//        dialog_image_holder = findViewById(R.id.dialog_gallery_image_holder);
        dialog_gallery_next = findViewById(R.id.dialog_gallery_next);
        dialog_gallery_prev = findViewById(R.id.dialog_gallery_prev);

        dialog_gallery_title = findViewById(R.id.dialog_gallery_title);

        dialog_gallery_title.setVisibility(View.GONE);
        dialog_gallery_prev.setVisibility(View.GONE);
        dialog_gallery_next.setVisibility(View.GONE);


        if (list_image.size() > 0) {
            dialog_gallery_title.setVisibility(View.VISIBLE);
            dialog_gallery_prev.setVisibility(View.VISIBLE);
            dialog_gallery_next.setVisibility(View.VISIBLE);

            Destination.Gallery currentImage = list_image.get(currentIndex);
            dialog_gallery_title.setText(currentImage.file_alt);
            Picasso.get().load(currentImage.url).into(display_image);

            display_image.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String url = currentImage.url;

                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                    activity.startActivity(intent);
                }
            });
        }

        dialog_gallery_prev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                previousImage();
            }
        });


        dialog_gallery_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nextImage();
            }
        });
//        dialog_image_holder.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                int action = MotionEventCompat.getActionMasked(event);
//
//                switch (action) {
//                    case (MotionEvent.ACTION):
//                        nextImage();
//                        return true;
//
////                    case (MotionEvent.ACTION_UP):
////                        previousImage();
////                        return true;
//                    default:
//                        return true;
//                }
//            }
//        });

    }

    void nextImage() {
        int size = list_image.size();
        if (size > 0) {
            currentIndex = currentIndex + 1;
            currentIndex = currentIndex % size;

            Destination.Gallery currentImage = list_image.get(currentIndex);

            dialog_gallery_title.setText(currentImage.file_alt);
            Picasso.get().load(currentImage.url).into(display_image);
        }
    }

    void previousImage() {
        int size = list_image.size();
        if (size > 0) {
            currentIndex = currentIndex - 1;
            currentIndex = Math.abs(currentIndex % size);
            Destination.Gallery currentImage = list_image.get(currentIndex);

            dialog_gallery_title.setText(currentImage.file_alt);
            Picasso.get().load(currentImage.url).into(display_image);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.image_dialog);
        init();
    }


}
