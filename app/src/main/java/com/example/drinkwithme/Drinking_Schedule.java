package com.example.drinkwithme;

import android.graphics.drawable.Drawable;
import android.net.Uri;


import java.util.ArrayList;
import java.util.HashMap;

public class Drinking_Schedule  {
     String title, date, time, location, who;
    ArrayList<DrinkingPic> DrinkingGallery;
   HashMap<String, String> alcohol_map_that_drank;


    public Drinking_Schedule(String title, String date, String time, String location, String who, HashMap<String, String> alcohol_map_that_drank,  ArrayList<DrinkingPic> DrinkingGallery) {
        this.title = title;
        this.date = date;
        this.time = time;
        this.location = location;
        this.who = who;
        this.alcohol_map_that_drank =alcohol_map_that_drank;

        this.DrinkingGallery = DrinkingGallery;
    }

    public String getTitle(){
       return title;
    }
    public String getDate(){
        return date;
    }
    public String getTime(){
        return time;
    }
    public String getLocation(){
        return location;
    }
    public String getWho(){
        return who;
    }
    public ArrayList getPics(){
        return DrinkingGallery;
    }


    public void setTitle(String title){
        this.title=title;
    }
    public void setDate(String date){
        this.date = date;
    }
    public void setTime(String time){
        this.time = time;
    }
    public void setLocation(String location){
        this.location = location;
    }
    public void setWho(String who){
        this.who = who;
    }
    public void addPics(DrinkingPic pic ){
        DrinkingGallery.add(pic);
    }

}
