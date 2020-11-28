package com.example.drinkwithme;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

import static com.example.drinkwithme.application.FavoriteBarCollection;
import static com.example.drinkwithme.application.alcohol_fridge;
import static com.example.drinkwithme.application.drinking_diary;

public class PreferenceManager {
    public static final String PREFERENCES_NAME ="sharedPrefList";

    private static SharedPreferences getPreferences(Context context){
        return context.getSharedPreferences(PREFERENCES_NAME,context.MODE_PRIVATE);
    }

    public static void saveAlcoholList(Context context) { //술 리스트 저장하는 메소드
        SharedPreferences sharedPreferences = getPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(alcohol_fridge);
        editor.putString("alcohol_list", json);
        editor.apply();
    }
    public static void saveScheduleList(Context context) { //술 약속 리스트 저장하는 메소드
        SharedPreferences sharedPreferences = getPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(drinking_diary);
        editor.putString("drinking_schedule", json);
        editor.apply();
    }
    public static void saveBarList(Context context) { //단골 술집 리스트 저장하는 메소드
        SharedPreferences sharedPreferences = getPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(FavoriteBarCollection);
        editor.putString("FavoriteBarCollection", json);
        editor.apply();
    }

    public static void loadAlcoholData(Context context) { //술 리스트 로드하는 메소드
        SharedPreferences sharedPreferences = getPreferences(context);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("alcohol_list", null);
        Type type = new TypeToken<ArrayList<Alcohol>>() {
        }.getType();
        alcohol_fridge = gson.fromJson(json, type);
        if (alcohol_fridge == null) {
            alcohol_fridge = new ArrayList<>();
        }
    }
    public static void loadScheduleData(Context context) { //술약속 리스트를 로드하는 메소드
        SharedPreferences sharedPreferences = getPreferences(context);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("drinking_schedule", null);
        Type type = new TypeToken<ArrayList<Drinking_Schedule>>() {
        }.getType();
        drinking_diary = gson.fromJson(json, type);
        if (drinking_diary == null) {
            drinking_diary = new ArrayList<Drinking_Schedule>();
        }
    }
    public static void loadFavoriteScheduleData(Context context) { //단골술집 리스트를 로드하는 메소드
        SharedPreferences sharedPreferences = getPreferences(context);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("FavoriteBarCollection", null);
        Type type = new TypeToken<ArrayList<FavoriteBar>>() {
        }.getType();
        FavoriteBarCollection = gson.fromJson(json, type);
        if (FavoriteBarCollection == null) {
            FavoriteBarCollection = new ArrayList<FavoriteBar>();
        }
    }
}
