package com.example.drinkwithme;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class EachDrinkingSchedule extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_each_drinking_schedule);

        TextView txt_title_each_drinking_schedule =findViewById(R.id.txt_title_each_drinking_schedule);
        TextView txt_time_each_drinking_schedule =findViewById(R.id.txt_time_each_drinking_schedule);
        TextView txt_date_each_drinking_schedule = findViewById(R.id.txt_date_each_drinking_schedule);
        TextView txt_location_each_drinking_schedule = findViewById(R.id.txt_location_each_drinking_schedule);
        TextView txt_who_each_drinking_schedule = findViewById(R.id.txt_who_each_drinking_schedule);

        Intent intent = getIntent();
        txt_title_each_drinking_schedule.setText(intent.getStringExtra("title"));
        txt_date_each_drinking_schedule.setText(intent.getStringExtra("date"));
        txt_time_each_drinking_schedule.setText(intent.getStringExtra("time"));
        txt_location_each_drinking_schedule.setText(intent.getStringExtra("location"));
        txt_who_each_drinking_schedule.setText(intent.getStringExtra("who"));
    }
}