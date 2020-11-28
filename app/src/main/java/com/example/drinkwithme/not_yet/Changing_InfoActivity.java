package com.example.drinkwithme.not_yet;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.example.drinkwithme.R;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public class Changing_InfoActivity extends AppCompatActivity {
    private final int GET_GALLERY_IMAGE =200;
    ImageView img_profile_changing_info;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_changing_info);
        ImageButton btn_back_changing_info = findViewById(R.id.btn_back_changing_info);
        img_profile_changing_info = findViewById(R.id.img_profile_changing_info);

        btn_back_changing_info.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              finish();
          }
        });

        img_profile_changing_info.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              Intent intent = new Intent();
              intent.setType("image/*");
              intent.setAction(Intent.ACTION_GET_CONTENT);
              startActivityForResult(intent, GET_GALLERY_IMAGE);
          }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == GET_GALLERY_IMAGE){
            if(requestCode == RESULT_OK){

                try{
                    InputStream in = getContentResolver().openInputStream(data.getData());
                    Bitmap img = BitmapFactory.decodeStream(in);
                    in.close();

                    img_profile_changing_info.setImageBitmap(img);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}