package com.example.drinkwithme;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import static com.example.drinkwithme.application.FavoriteBarCollection;

public class FavoriteBarActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    LinearLayoutManager layoutManager;
    Adapter_Favoire_Bar adapter_favoire_bar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_favorite_bar);
        FloatingActionButton fab_add_Favorite_Bar = findViewById(R.id.fab_add_Favorite_Bar);

        recyclerView = findViewById(R.id.recyclerview_Favorite_Bar);
         recyclerView.setHasFixedSize(true);

         layoutManager = new LinearLayoutManager(getApplicationContext());
         adapter_favoire_bar =new Adapter_Favoire_Bar(getApplicationContext(),FavoriteBarCollection);

         recyclerView.setAdapter(adapter_favoire_bar);
         recyclerView.setLayoutManager(layoutManager);


         findViewById(R.id.imgbtn_back_favorite_bars).setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               finish();
           }
         });
        fab_add_Favorite_Bar.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              Intent intent = new Intent(getApplicationContext(), AddFavoriteBarActivity.class);
              startActivity(intent);
          }
        });
    }
    @Override
    protected void onStart() {
        super.onStart();
        adapter_favoire_bar.upDateItemList(FavoriteBarCollection);
    }
}