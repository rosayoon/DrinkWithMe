package com.example.drinkwithme.not_yet;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.drinkwithme.MainActivity;
import com.example.drinkwithme.R;

public class LoginActivity extends AppCompatActivity {

    @SuppressLint("UseCompatLoadingForDrawables")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Button btn_login_login = findViewById(R.id.btn_login_login);
        Button btn_go_register = findViewById(R.id.btn_go_register);
        EditText edittext_login_id = findViewById(R.id.edittext_login_id);
        EditText edittext_login_pw = findViewById(R.id.edittext_login_pw);

        String id, pw;
        id = edittext_login_id.getText().toString();
        pw = edittext_login_pw.getText().toString();

        btn_login_login.setBackground(getResources().getDrawable(R.drawable.btn_when_click));
        btn_go_register.setBackground(getResources().getDrawable(R.drawable.btn_when_click));

        btn_login_login.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              Intent intent_login = new Intent(getApplicationContext(), MainActivity.class);
              startActivity(intent_login);
          }
        });

        btn_go_register.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              Intent intent_creating_account = new Intent(getApplicationContext(), Creating_AccountActivity.class);
              startActivity(intent_creating_account);
          }
        });


    }
}