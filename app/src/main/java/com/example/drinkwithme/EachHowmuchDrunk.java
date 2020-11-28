package com.example.drinkwithme;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class EachHowmuchDrunk extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_each_howmuch_drunk);

        TextView txt_title_each_howmuch_drunk =findViewById(R.id.txt_title_each_howmuch_drunk);
        TextView txt_date_each_howmuch_drunk = findViewById(R.id.txt_date_each_howmuch_drunk);
        TextView txt_location_each_howmuch_drunk = findViewById(R.id.txt_location_each_howmuch_drunk);
        TextView txt_who_each_howmuch_drunk = findViewById(R.id.txt_who_each_howmuch_drunk);

        Intent intent = getIntent();
        txt_title_each_howmuch_drunk.setText(intent.getStringExtra("title"));
        txt_date_each_howmuch_drunk.setText(intent.getStringExtra("date"));
        txt_location_each_howmuch_drunk.setText(intent.getStringExtra("location"));
        txt_who_each_howmuch_drunk.setText(intent.getStringExtra("who"));
    }
}