package com.example.drinkwithme.not_yet;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.example.drinkwithme.R;

public class Alarm_SettingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_alarm);
        ImageButton btn_alarm_back = findViewById(R.id.btn_alarm_back);

        btn_alarm_back.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              finish();
          }
        });

    }
}