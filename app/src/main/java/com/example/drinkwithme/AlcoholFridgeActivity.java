package com.example.drinkwithme;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

import static com.example.drinkwithme.PreferenceManager.loadAlcoholData;
import static com.example.drinkwithme.application.alcohol_fridge;
import static com.example.drinkwithme.application.drinking_diary;

public class AlcoholFridgeActivity extends AppCompatActivity {
    RecyclerView recyclerview_Goto_Drinks;
    GridLayoutManager gridLayoutManager;
    Adapter_AlcoholFridge adapter_alcoholFridge;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alcohol_fridge);
        loadAlcoholData(getApplicationContext());

        recyclerview_Goto_Drinks = (RecyclerView) findViewById(R.id.recyclerview_Alcohol_Fridge);
        recyclerview_Goto_Drinks.setHasFixedSize(true); //리사이클러 뷰에서 아이템 뷰가 자주 추가되고 삭제되어도 recyclerview의 크기를 고정한다.

        gridLayoutManager = new GridLayoutManager(this, 3);
        recyclerview_Goto_Drinks.setLayoutManager(gridLayoutManager);


        adapter_alcoholFridge = new Adapter_AlcoholFridge(alcohol_fridge, this);
        recyclerview_Goto_Drinks.setAdapter(adapter_alcoholFridge);


        ImageButton imgbtn_back_alcohol_fridge = findViewById(R.id.imgbtn_back_alcohol_fridge);
        FloatingActionButton fab_add_alcohol_fridge = findViewById(R.id.fab_add_alcohol_fridge);
        adapter_alcoholFridge.notifyDataSetChanged();
        //if(gotoDrinks_adapter.mSelectedItem!=-1){ // 마신 술을 선택했을 때 얼마나 마셨는지 기록할 수 있는 UI를 보일 수 있게 하기
        //    edittext_amount_goto_drinks.setVisibility(View.VISIBLE);
        //    glassOrbottle_goto_drinks.setVisibility(View.VISIBLE);
        //    edit_layout_amount_goto_drinks.setVisibility(View.VISIBLE);
        //}


        imgbtn_back_alcohol_fridge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        fab_add_alcohol_fridge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent( getApplicationContext(), AddNewDrinksActivity.class);
                startActivity(intent);
            }
        });

    }
    @Override
      protected void onStart() {
          super.onStart();
          adapter_alcoholFridge.notifyDataSetChanged();
    }
      @Override
      protected void onResume() {
          super.onResume();
          adapter_alcoholFridge.notifyDataSetChanged();

      }





}