package com.example.drinkwithme;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import static com.example.drinkwithme.ImageManager.loadImageFromStorage;
import static com.example.drinkwithme.PreferenceManager.saveAlcoholList;
import static com.example.drinkwithme.application.alcohol_fridge;

public class EditAllAboutDrinks extends AppCompatActivity {

    private final int GET_GALLERY_IMAGE =200;
    ImageView img_edit_AllAboutDrinks;
    Uri alcohol_img;
    TextView  tv_alcohol_add_drinking_schedule;
    String directory_name ="AlcoholPicDirectory";
    Bitmap alcohol_bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_all_about_drinks);
        img_edit_AllAboutDrinks = findViewById(R.id.img_edit_AllAboutDrinks);

        EditText et_name_edit_AllAboutDrinks =findViewById(R.id.et_name_edit_AllAboutDrinks);
        EditText et_details_edit_AllAboutDrinks =findViewById(R.id.et_details_edit_AllAboutDrinks);
        EditText et_content_edit_AllAboutDrinks = findViewById(R.id.et_content_edit_AllAboutDrinks);



         Button btn_save_edit_AllAboutDrinks = findViewById(R.id.btn_save_edit_AllAboutDrinks);
         ImageButton btn_back_edit_AllAboutDrinks =findViewById(R.id.btn_back_edit_AllAboutDrinks);


         //술 객체의 변수를 빈칸에 채워넣기
        Intent intent = getIntent();
        int alcohol_position = intent.getIntExtra("alcohol_position",-1); //리사이클러 뷰에서 받은 position 값
        Toast.makeText(getApplicationContext(), "editposition:"+alcohol_position,Toast.LENGTH_LONG).show();
        Toast.makeText(getApplicationContext(), "editsize:"+alcohol_fridge.size(),Toast.LENGTH_LONG).show();
        Toast.makeText(getApplicationContext(), "이름:"+alcohol_fridge.get(alcohol_position).name,Toast.LENGTH_LONG).show();


        Alcohol cAlcohol = alcohol_fridge.get(alcohol_position);

        et_name_edit_AllAboutDrinks.setText(cAlcohol.name);

        Bitmap bitmapImage;
        bitmapImage = loadImageFromStorage(cAlcohol.path,cAlcohol.imgFile_name);
        img_edit_AllAboutDrinks.setImageBitmap(bitmapImage);

        et_details_edit_AllAboutDrinks.setText(cAlcohol.detail);
        et_content_edit_AllAboutDrinks.setText(String.valueOf(cAlcohol.alcohol_content));

        img_edit_AllAboutDrinks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent, GET_GALLERY_IMAGE);
            }
        });
        btn_save_edit_AllAboutDrinks.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              String name = et_name_edit_AllAboutDrinks.getText().toString();
              String details = et_details_edit_AllAboutDrinks.getText().toString();

              int alcohol_content = Integer.parseInt(et_content_edit_AllAboutDrinks.getText().toString());

              long time =System.currentTimeMillis();
              String imgFile_name = Long.toString(time);
              String path = saveToInternalStorage(alcohol_bitmap, directory_name, imgFile_name);

              cAlcohol.name=name;
              cAlcohol.detail =details;
              cAlcohol.alcohol_content=alcohol_content;
              cAlcohol.path = path;
              cAlcohol.imgFile_name = imgFile_name;
              if(name.isEmpty() || details.isEmpty() || alcohol_content==0){
                  btn_save_edit_AllAboutDrinks.setEnabled(false);
              }
              saveAlcoholList(getApplicationContext());
              finish();
          }
        });
        btn_back_edit_AllAboutDrinks.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              finish();
          }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode ==GET_GALLERY_IMAGE){
            //request가 성공인지 확인
            if(resultCode ==RESULT_OK)
            {
                Uri selectedImage = data.getData();
                Bitmap bitmapImage;
                try {
                    bitmapImage = MediaStore.Images.Media.getBitmap(getContentResolver(), selectedImage);
                    alcohol_bitmap = bitmapImage;
                    img_edit_AllAboutDrinks.setImageBitmap(bitmapImage);
                    alcohol_img = selectedImage;
                } catch (Exception e) {
                    e.printStackTrace();
                    Log.v("알림","앨범에서 가져오기 에러");
                }

            }
        }
    }
    private String saveToInternalStorage(Bitmap bitmapImage,String folder, String name) { //bitmap 이미지를 내부저장소에 저장하기
        //ContextWrapper는 모든 context의 function들을 감싸고 있으며, CotextImpl을 클래스를 사용해서 실행시킨다
        ContextWrapper cw = new ContextWrapper(getApplicationContext());

        String foler_name = "/" + folder + "/"; //폴더의 이름
        String ex_storage = Environment.getExternalStorageDirectory().getAbsolutePath();
        String string_path = ex_storage + foler_name; //이미지 디렉토리의 경로

        File directory;
        directory = new File(string_path);
        if (!directory.isDirectory()) {  //해당 디렉토리가 존재하지 않으면
            // path to /data/data/yourapp/app_data/imageDir
            directory = cw.getDir(directory_name, Context.MODE_PRIVATE); //이미지 디렉토리 생성
        }

        // Create imageDir
        String file_name = name + ".jpg"; //저장될 이미지 파일의 이름
        File mypath = new File(directory, file_name); //(parent의 path name, child의 path.name)

        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(mypath);
            // Use the compress method on the BitMap object to write image to the OutputStream
            //Bitmap 객체에 compress를 사용해서 outputstream에 이미지를 저장하게 한다.
            bitmapImage.compress(Bitmap.CompressFormat.PNG, 100, fos);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return directory.getAbsolutePath();
    }


}