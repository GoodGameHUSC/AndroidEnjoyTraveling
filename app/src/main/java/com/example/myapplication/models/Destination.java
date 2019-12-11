package com.example.myapplication.models;

import java.util.List;

public class Destination {

    public int id;
    public String name;
    public String image;
    public String title;
    public int open_time;
    public int close_time;
    public String contact;
    public String website;
    public int integer;
    public String address;
    public String google_map_id;
    public String lat;
    public String lng;
    public String description;
    public String avg_rate;
    public int rate_count;
    public int likes_count;

    public Province province;

    public List<Rate> rate;

    public List<Gallery> gallery;


    public class Province {
        public int id;

        public String name;
    }

    public class Gallery {

        public int id;
        public int destination_id;
        public String file_alt;
        public String url;
    }

}
