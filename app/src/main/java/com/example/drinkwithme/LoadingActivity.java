package com.example.drinkwithme;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;

public class LoadingActivity extends AppCompatActivity {
    private ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);
        progressDialog = new ProgressDialog(getApplicationContext());
        progressDialog.show();
        Thread thread = new Thread(new Runnable(){

            @Override
            public void run() {
                try{
                    Thread.sleep(4000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                new Handler().post(new Runnable() {
                    @Override
                    public void run() {
                        progressDialog.dismiss();
                        finish();
                    }
                });
            }
        });

        thread.start();
    }
}