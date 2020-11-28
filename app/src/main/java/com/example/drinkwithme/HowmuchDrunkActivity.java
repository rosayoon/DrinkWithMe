package com.example.drinkwithme;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

import static com.example.drinkwithme.PreferenceManager.loadAlcoholData;
import static com.example.drinkwithme.application.alcohol_fridge;
import static com.example.drinkwithme.application.fridge_num;

public class HowmuchDrunkActivity extends AppCompatActivity {
    RecyclerView recyclerview_hwmh_drunk;
    LinearLayoutManager linearLayoutManager;
    Adapter_AboutDrinks adapter_aboutDrinks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_howmuch_drunk);

        recyclerview_hwmh_drunk = findViewById(R.id.recyclerview_about_drinks);
        recyclerview_hwmh_drunk.setHasFixedSize(true);

        linearLayoutManager = new LinearLayoutManager(this);
        recyclerview_hwmh_drunk.setLayoutManager(linearLayoutManager);

        adapter_aboutDrinks= new Adapter_AboutDrinks(alcohol_fridge, this);
        recyclerview_hwmh_drunk.setAdapter(adapter_aboutDrinks);

        ImageButton btn_back_howmuch_drunk_each = findViewById(R.id.btn_back_howmuch_drunk_each);
        btn_back_howmuch_drunk_each.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();

            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        //adapter_aboutDrinks.notifyDataSetChanged();

    }
    protected void onResume() {
        super.onResume();
        //ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallback);
        //itemTouchHelper.attachToRecyclerView(recyclerview_hwmh_drunk);

    }

}