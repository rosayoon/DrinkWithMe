package com.example.drinkwithme;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import static com.example.drinkwithme.application.addAllPics;
import static com.example.drinkwithme.application.basic_alcohol_pics;

public class SelectAlocholImageActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    GridLayoutManager gridLayoutManager;
    Adapter_AlcoholImage adapter_alcoholImage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selecting_basic_alcohol_image);

        ImageButton btn_back_SelectAlcoholImage =findViewById(R.id.btn_back_SelectAlcoholImage);
        Button btn_select_SelectAlcoholImage  =findViewById(R.id.btn_select_SelectAlcoholImage);

        addAllPics(getApplicationContext()); //수정 필요

        recyclerView = findViewById(R.id.recyclerview_SelectAlcoholImage);
        recyclerView.setHasFixedSize(true);

        gridLayoutManager = new GridLayoutManager(this, 4);
        recyclerView.setLayoutManager(gridLayoutManager);

        adapter_alcoholImage = new Adapter_AlcoholImage(getApplicationContext(),basic_alcohol_pics);
        recyclerView.setAdapter(adapter_alcoholImage);

        btn_back_SelectAlcoholImage.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              finish();
          }
        });

        btn_select_SelectAlcoholImage.setOnClickListener(new View.OnClickListener() { //선택 버튼을 눌렀을 때
          @Override
          public void onClick(View v) {
              if(adapter_alcoholImage.mSelectedItem ==-1){
                  Toast.makeText(getApplicationContext(), "술 이미지를 선택해주세요",Toast.LENGTH_LONG).show();
              }

              else {
                  int position = adapter_alcoholImage.mSelectedItem;
                  Toast.makeText(getApplicationContext(), "술 position" + position, Toast.LENGTH_SHORT).show();
                  Intent intent = new Intent();
                  intent.putExtra("position", position);
                  setResult(RESULT_OK, intent);
                  finish();
              }
          }
        });
    }
}