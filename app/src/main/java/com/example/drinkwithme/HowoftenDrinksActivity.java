package com.example.drinkwithme;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class HowoftenDrinksActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_howoften_drinks);
        ImageButton btn_back_howmuch_drinks = findViewById(R.id.btn_back_howofter_drinks);
        btn_back_howmuch_drinks.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              finish();
          }
        });
    }
}