package com.example.drinkwithme.not_yet;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.example.drinkwithme.R;
import com.example.drinkwithme.not_yet.LoginActivity;

public class SignOutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signout);
        Button btn_signout_yes = findViewById(R.id.btn_signout_yes);
        Button btn_signout_no = findViewById(R.id.btn_signout_no);
        ImageButton btn_back_signout = findViewById(R.id.btn_back_signout);

        btn_signout_no.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              finish();
          }
        });
        btn_signout_yes.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              Intent intent = new Intent(getApplicationContext(),
                      LoginActivity.class);
              startActivity(intent);
          }
        });

        btn_back_signout.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              finish();
          }
        });
    }
}