package com.example.drinkwithme.not_yet;
import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.example.drinkwithme.R;
import com.example.drinkwithme.not_yet.LoginActivity;

public class LogoutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_logout); //logout layout을 화면에 띄운다.

        Button btn_logout_yes = findViewById(R.id.btn_logout_yes);
         Button btn_logout_no = findViewById(R.id.btn_logout_no);
        ImageButton btn_loggout_back =findViewById(R.id.btn_logout_back);

       btn_logout_no.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
             finish();
         }
       });
       btn_logout_yes.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
             Intent intent = new Intent(getApplicationContext(),
                     LoginActivity.class);
             startActivity(intent);
         }
       });
       btn_logout_no.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
             finish();
         }
       });
       btn_loggout_back.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
             finish();
         }
       });

    }

}