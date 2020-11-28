package com.example.drinkwithme;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import static com.example.drinkwithme.ImageManager.saveAlcoholToInternalStorage;
import static com.example.drinkwithme.PreferenceManager.saveAlcoholList;
import static com.example.drinkwithme.PreferenceManager.saveBarList;
import static com.example.drinkwithme.application.addAllPics;
import static com.example.drinkwithme.application.basic_alcohol_pics;
import static com.example.drinkwithme.application.FavoriteBarCollection;

public class AddFavoriteBarActivity extends AppCompatActivity {

    ImageView img_drinkimg_add_new_drinks;
    private final int GET_ADDRESS =300;
    TextView txt_bar_address_add_favorite_bar;






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_favorite_bar);


       Button btn_save_add_favorite_bar  =findViewById(R.id.btn_save_add_favorite_bar);
       Button btn_address_add_favorite_bar = findViewById(R.id.btn_address_add_favorite_bar);
        EditText et_name_add_favorite_bar = findViewById(R.id.et_name_add_favorite_bar);
        EditText et_details_add_favorite_bar =findViewById(R.id.et_details_add_favorite_bar);

         txt_bar_address_add_favorite_bar = findViewById(R.id.txt_bar_address_add_favorite_bar);
        addAllPics(getApplicationContext());

        //SharedPreferences  안의 데이터 불러오기기


        findViewById(R.id.btn_back_add_favorite_bar).setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              finish();
          }
        });
        btn_address_add_favorite_bar.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              Intent intent = new Intent(getApplicationContext(), PubLocationMapActivity.class);
              startActivityForResult(intent, GET_ADDRESS);
          }
        });

        btn_save_add_favorite_bar.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              String name = et_name_add_favorite_bar.getText().toString();
              String details = et_details_add_favorite_bar.getText().toString();
              String address =txt_bar_address_add_favorite_bar.getText().toString();




              if( name.isEmpty() ){
                  btn_save_add_favorite_bar.setPressed(false);
              }
              else {

                  FavoriteBar bar = new FavoriteBar(name,address, details);
                  FavoriteBarCollection.add(bar);
                  saveBarList(getApplicationContext());

                  finish();
              }

              }
        });


    }



    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == GET_ADDRESS) {
                String address =data.getStringExtra("location");

                txt_bar_address_add_favorite_bar.setText(address);
            }
        }


    }




}