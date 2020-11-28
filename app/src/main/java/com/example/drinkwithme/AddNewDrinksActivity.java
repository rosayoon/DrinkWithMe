package com.example.drinkwithme;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;

import static com.example.drinkwithme.ImageManager.saveAlcoholToInternalStorage;
import static com.example.drinkwithme.PreferenceManager.saveAlcoholList;
import static com.example.drinkwithme.application.addAllPics;
import static com.example.drinkwithme.application.alcohol_fridge;
import static com.example.drinkwithme.application.basic_alcohol_pics;

public class AddNewDrinksActivity extends AppCompatActivity {

    ImageView img_drinkimg_add_new_drinks;
    private final int GET_GALLERY_IMAGE =200;
    private final int GET_BASIC_IMAGE =100;
    Uri alcohol_img = Uri.parse("android.resource://com.example.drinkwithme/"  + R.drawable.icon_glass);
    int alcohol_content= 0;
    Bitmap alcohol_bitmap;  //이 액티비티에서 비트맵 형식의 술 사진
    String imgFile_name;
    String key;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_drinks);
        ImageButton btn_back_goto_drinks = findViewById(R.id.btn_back_goto_drinks);
       img_drinkimg_add_new_drinks = findViewById(R.id.img_drinkimg_add_new_drinks);
       Button btn_save_add_new_drink  =findViewById(R.id.btn_save_add_new_drink);
        EditText et_name_add_new_drinks = findViewById(R.id.et_name_add_new_drinks);
        EditText et_details_add_new_drinks =findViewById(R.id.et_details_add_new_drinks);
        EditText edittext_content_add_drinking_schedule =findViewById(R.id.edittext_content_add_drinking_schedule);
        addAllPics(getApplicationContext());

        //SharedPreferences  안의 데이터 불러오기기

        registerForContextMenu(img_drinkimg_add_new_drinks);

        btn_back_goto_drinks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btn_save_add_new_drink.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              String name = et_name_add_new_drinks.getText().toString();
              String details = et_details_add_new_drinks.getText().toString();
              String content_string =edittext_content_add_drinking_schedule.getText().toString();



              //해당 술 이미지를 내부저장소에 저장한 후에 저장된 이미지 파일이 있는 path를 return 해줌
              String path = saveAlcoholToInternalStorage(getApplicationContext(),alcohol_bitmap,  imgFile_name);

              //imgFile_name을 현재 시간으로 설정했으므로 중복되지 않는 KEY 값으로 설정할 수 있다.
              key = imgFile_name;

              if(content_string.isEmpty() || name.isEmpty() ){
                  btn_save_add_new_drink.setPressed(false);
              }
              else {
                  int content =Integer.parseInt(content_string);
                  Alcohol new_alcohol =new Alcohol(name,key, path,imgFile_name, content, details);
                  alcohol_fridge.add(new_alcohol);
                  Toast.makeText(getApplicationContext(), key,Toast.LENGTH_LONG).show();
                  saveAlcoholList(getApplicationContext());

                  finish();
              }

              }
        });


    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
       MenuInflater inflater = getMenuInflater();
       inflater.inflate(R.menu.edit_delete, menu);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {

            case R.id.item1:
                Intent intent_1 = new Intent(getApplicationContext(), SelectAlocholImageActivity.class);
                intent_1.addCategory(Intent.CATEGORY_OPENABLE);
                startActivityForResult(intent_1, GET_BASIC_IMAGE);
                return true;

            case R.id.item2:
                checkSelfPermission();
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent, GET_GALLERY_IMAGE);
                return true;

        }
        return super.onContextItemSelected(item);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK) {

            if (requestCode == GET_GALLERY_IMAGE) {
                Uri selectedImage = data.getData();
                Bitmap bitmapImage;
                try {
                    bitmapImage = MediaStore.Images.Media.getBitmap(getContentResolver(), selectedImage);
                    alcohol_bitmap = bitmapImage;
                    img_drinkimg_add_new_drinks.setImageBitmap(bitmapImage);

                } catch (Exception e) {
                    e.printStackTrace();
                    Log.v("알림", "앨범에서 가져오기 에러");
                }
                long time =System.currentTimeMillis();
                imgFile_name = Long.toString(time); // 이미지 이름이 중복되지 않기 위해 이름을 현재 시간으로 설정
            }
            else if(requestCode ==GET_BASIC_IMAGE){
                Toast.makeText(getApplicationContext(), "기본이미지 가져옴",Toast.LENGTH_LONG).show();
                int position = data.getIntExtra("position", -1);
                Uri selectedImage = basic_alcohol_pics.get(position).uri;
                Bitmap bitmapImage;
                try {
                    bitmapImage = MediaStore.Images.Media.getBitmap(getContentResolver(), selectedImage);
                    alcohol_bitmap = bitmapImage;
                    img_drinkimg_add_new_drinks.setImageBitmap(bitmapImage);

                } catch (Exception e) {
                    e.printStackTrace();
                    Log.v("알림", "앨범에서 가져오기 에러");
                }
                imgFile_name = basic_alcohol_pics.get(position).name; // 동일한 이미지를 선택했을 때, 두 이미지 모두 내부저장소에 저장되지 않게하기 위해서
            }
        }

    }

    //권한에 대한 응답이 있을때 작동하는 함수
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        //권한을 허용 했을 경우
        if(requestCode == 1){
            int length = permissions.length;
            for (int i = 0; i < length; i++) {
                if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                    // 동의
                    Log.d("MainActivity","권한 허용 : " + permissions[i]);
                }
            }
        }


    }
    public void checkSelfPermission() {

        String temp = "";

        //파일 읽기 권한 확인
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            temp += Manifest.permission.READ_EXTERNAL_STORAGE + " ";
        }

        //파일 쓰기 권한 확인
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            temp += Manifest.permission.WRITE_EXTERNAL_STORAGE + " ";
        }


        if (TextUtils.isEmpty(temp) == false) {
            // 권한 요청
            ActivityCompat.requestPermissions(this, temp.trim().split(" "),1);
        }else {
            // 모두 허용 상태
            Toast.makeText(this, "권한을 모두 허용", Toast.LENGTH_SHORT).show();
        }
    }


}