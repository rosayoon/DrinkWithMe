package com.example.drinkwithme;
import android.os.Handler;
import android.os.Message;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_welcome);

        Handler handler = new Handler() {
            @Override
            public void handleMessage(Message msg) { // handler에서 사용하는 메세지 객체를 받아옵니다
                finish();
            }
        };
        handler.sendEmptyMessageDelayed(0,1500);
    }
}