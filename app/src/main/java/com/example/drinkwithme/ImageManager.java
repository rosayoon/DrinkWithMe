package com.example.drinkwithme;
import android.widget.Toast;

import android.content.Context;
import android.content.ContextWrapper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class ImageManager {
    static final String folderOfAlcohol ="AlcoholPicDirectory"; //술 이미지들을 저장한는 디렉토리 이름
    static final String folderOfDrinkingPics = "DrinkingPicDirectory"; //술 약속에서 등록한 사진들을 저장하는 디렉토리 이름

    public static void BasicGallery(){
        String folder_name = "/" + folderOfAlcohol + "/"; //폴더의 이름
        String ex_storage = Environment.getExternalStorageDirectory().getAbsolutePath(); //폴더의 이름 내부 저장소의 절대 경로
        String string_path = ex_storage + folder_name; //이미지 디렉토리의 경로 path to /data/data/yourapp/app_data/imageDir
        DrinkingPic soju = new DrinkingPic("soju",string_path);

    }
    public static Bitmap loadImageFromStorage(String path,String file_name)  //이미지를 내부저장소로 부터 로드하여 비트맵 이미지를 리턴하기
    {
        String name = file_name+".jpg";
        try {
            File f=new File(path, name);
            Bitmap b = BitmapFactory.decodeStream(new FileInputStream(f));
            return b;
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
            return null;
        }
    }

    public static String saveAlcoholToInternalStorage(Context context,Bitmap bitmapImage, String name) { //술 등록하기에서 술 이미지를 내부저장소에 저장하기
        //ContextWrapper는 모든 context의 function들을 감싸고 있으며, CotextImpl을 클래스를 사용해서 실행시킨다
        ContextWrapper cw = new ContextWrapper(context);
        String folder_name = "/" + folderOfAlcohol + "/"; //폴더의 이름
        String ex_storage = Environment.getExternalStorageDirectory().getAbsolutePath(); //폴더의 이름 내부 저장소의 절대 경로
        String string_path = ex_storage + folder_name; //이미지 디렉토리의 경로 path to /data/data/yourapp/app_data/imageDir


        File directory; //술 이미지들을 저장할 디렉토리
        directory = new File(string_path);
        if (!directory.isDirectory()) {  //해당 디렉토리가 존재하지 않으면
            directory = cw.getDir(folderOfAlcohol, Context.MODE_PRIVATE); //이미지 디렉토리 새로 생성
        }


        String file_name = name + ".jpg"; //저장될 술 이미지 파일의 이름
        File mypath = new File(directory, file_name); // Parameter (parent의 path name, child의 path.name)

        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(mypath);


            //Bitmap object에 compress 메소드를 사용해서 outputstream에 이미지를  한다.
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

    public static String saveDrinkingPicsToInternalStorage(Context context,Bitmap bitmapImage, String name) { // 술약속 등록하기에서 사진들을 내부저장소에 저장하기
        //ContextWrapper는 모든 context의 function들을 감싸고 있으며, CotextImpl을 클래스를 사용해서 실행시킨다
        ContextWrapper cw = new ContextWrapper(context);
        String folder_name = "/" + folderOfDrinkingPics + "/"; //폴더의 이름
        String ex_storage = Environment.getExternalStorageDirectory().getAbsolutePath(); //폴더의 이름 내부 저장소의 절대 경로
        String string_path = ex_storage + folder_name; //이미지 디렉토리의 경로 path to /data/data/yourapp/app_data/imageDir

        File directory; //술 이미지들을 저장할 디렉토리
        directory = new File(string_path);
        if (!directory.isDirectory()) {  //해당 디렉토리가 존재하지 않으면
            directory = cw.getDir(folderOfDrinkingPics, Context.MODE_PRIVATE); //이미지 디렉토리 새로 생성
        }

        String file_name = name + ".jpg"; //저장될 술 이미지 파일의 이름
        File mypath = new File(directory, file_name); // Parameter (parent의 path name, child의 path.name)

        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(mypath);
            //Bitmap object에 compress 메소드를 사용해서 outputstream에 이미지를  한다.
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
    public static void deletePics(String path, String file_name){
        File file = new File(path, file_name);
        boolean deleted = file.delete();
        Log.i("삭제 확인", "");
    }
}
