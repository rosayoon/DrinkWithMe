package com.example.drinkwithme;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import static com.example.drinkwithme.application.alcohol_fridge;

public class DrinkFridgeActivity extends AppCompatActivity {
    RecyclerView recyclerview_Goto_Drinks;
    GridLayoutManager gridLayoutManager;
    Adapter_SelectDrinks gotoDrinks_adapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selecting_drink_that_drank);

        recyclerview_Goto_Drinks = (RecyclerView)findViewById(R.id.recyclerview_Goto_Drinks);
        recyclerview_Goto_Drinks.setHasFixedSize(true); //리사이클러 뷰에서 아이템 뷰가 자주 추가되고 삭제되어도 recyclerview의 크기를 고정한다.

        gridLayoutManager = new GridLayoutManager(this,4);
        recyclerview_Goto_Drinks.setLayoutManager(gridLayoutManager);

        gotoDrinks_adapter = new Adapter_SelectDrinks(alcohol_fridge, this);
        recyclerview_Goto_Drinks.setAdapter(gotoDrinks_adapter);


        ImageButton imgbtn_add_goto_drink =findViewById(R.id.imgbtn_add_goto_drink);
        ImageButton imgbtn_back_goto_drink = findViewById(R.id.imgbtn_back_goto_drink);


        imgbtn_add_goto_drink.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
        Intent intent = new Intent( getApplicationContext(), AddNewDrinksActivity.class);
        startActivity(intent);
          }
        });

        imgbtn_back_goto_drink.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              finish();
          }
        });

    }
      @Override
      protected void onResume() {
          super.onResume();

          gotoDrinks_adapter.notifyDataSetChanged();
      }




}