package com.example.drinkwithme.not_yet;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.example.drinkwithme.R;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public class Posting_New_PostActivity extends AppCompatActivity {
    private final int GET_GALLERY_IMAGE =200;
    ImageView img_posting_new_post;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_posting_newpost);

        ImageButton imgbtn_back_posting_new_post = findViewById(R.id.imgbtn_back_posting_new_post);
        ImageButton imgbtn_camera_posting_new_posts = findViewById(R.id.imgbtn_camera_posting_new_posts);
        img_posting_new_post = findViewById(R.id.img_posting_new_post);

        imgbtn_back_posting_new_post.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              finish();
          }
        });

        imgbtn_camera_posting_new_posts.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,GET_GALLERY_IMAGE);
          }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode ==GET_GALLERY_IMAGE){
            if(resultCode ==RESULT_OK){
                try{
                    //선택한 이미지에서 비트맵 생성

                    InputStream in = getContentResolver().openInputStream(data.getData());
                    Bitmap img = BitmapFactory.decodeStream(in);
                    in.close();
                    //이미지 표시
                    img_posting_new_post.setImageBitmap(img);
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }


}