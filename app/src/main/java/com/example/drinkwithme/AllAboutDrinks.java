package com.example.drinkwithme;
import android.graphics.Bitmap;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import static com.example.drinkwithme.ImageManager.loadImageFromStorage;
import static com.example.drinkwithme.application.alcohol_fridge;

public class AllAboutDrinks extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_about_drinks);
         TextView tv_name_AllAboutDrinks =findViewById(R.id.tv_name_AllAboutDrinks);
         TextView tv_content_AllAboutDrinks =findViewById(R.id.tv_content_AllAboutDrinks);

         TextView tv_details_AllAboutDrinks =findViewById(R.id.tv_details_AllAboutDrinks);

         Button btn_edit_AllAboutDrinks = findViewById(R.id.btn_edit_AllAboutDrinks);
         ImageButton btn_back_AllAboutDrinks = findViewById(R.id.btn_back_AllAboutDrinks);
        ImageView img_AllAboutDrinks = findViewById(R.id.img_AllAboutDrinks);


        btn_back_AllAboutDrinks.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               finish();
           }
         });

         String alcohol_content =null;

        Intent intent = getIntent();
        int alcohol_position = intent.getIntExtra("aboutdrinks_position",-1); //리사이클러 뷰에서 받은 position 값


        Alcohol cAlcohol = alcohol_fridge.get(alcohol_position);
        tv_name_AllAboutDrinks.setText(cAlcohol.name);


        tv_content_AllAboutDrinks.setText(String.valueOf(cAlcohol.alcohol_content));
        tv_details_AllAboutDrinks.setText(cAlcohol.detail);
        Bitmap bitmapImage;
        try {
            bitmapImage = loadImageFromStorage(cAlcohol.path,cAlcohol.imgFile_name);
            img_AllAboutDrinks.setImageBitmap(bitmapImage);
        } catch (Exception e) {
            e.printStackTrace();
        }
        btn_edit_AllAboutDrinks.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              Intent mintent = new Intent(getApplicationContext(), EditAllAboutDrinks.class);
              mintent.putExtra("alcohol_position",alcohol_position);
              Toast.makeText(getApplicationContext(), "position:"+alcohol_position,Toast.LENGTH_LONG).show();
              startActivity(mintent);
          }
        });
    }
    @Override
    protected void onStart() {
        super.onStart();
        Toast.makeText(this, "onStart", Toast.LENGTH_LONG).show();

    }
}