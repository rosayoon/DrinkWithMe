package com.example.drinkwithme;
import android.net.Uri;
import android.widget.Toast;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;

import java.util.ArrayList;

public class application {
    public static ArrayList<Alcohol> alcohol_fridge = new ArrayList<Alcohol>();
    public static ArrayList<Drinking_Schedule > drinking_diary = new ArrayList<Drinking_Schedule>();
    public static ArrayList<BasicAlcoholPic> basic_alcohol_pics;
    public static ArrayList<FavoriteBar> FavoriteBarCollection;

    public static int fridge_num =0;

    public static void addAllPics(Context context){


        int [] basic = { R.drawable.icon_bongu, R.drawable.icon_glass,
                R.drawable.icon_wineglass, R.drawable.icon_whiskey, R.drawable.icon_cocktail};


        if(basic_alcohol_pics !=null) {

        }
        else{

            basic_alcohol_pics = new ArrayList<BasicAlcoholPic>();
            for(int i =0 ; i<basic.length; i++) {
                int a = basic[i];
                Uri uri = Uri.parse("android.resource://com.example.drinkwithme/" + a);
                String name = String.valueOf(a);
                basic_alcohol_pics.add(new BasicAlcoholPic(name, uri) );
            }
            Toast.makeText(context, "기본이미지"+basic_alcohol_pics.size(),Toast.LENGTH_LONG).show();

        }
    }
}
