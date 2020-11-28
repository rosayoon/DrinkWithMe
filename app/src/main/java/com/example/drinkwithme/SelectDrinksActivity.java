package com.example.drinkwithme;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.google.android.material.textfield.TextInputLayout;

import static com.example.drinkwithme.PreferenceManager.loadAlcoholData;
import static com.example.drinkwithme.application.alcohol_fridge;

public class SelectDrinksActivity extends AppCompatActivity {
    RecyclerView recyclerview_Goto_Drinks;
    GridLayoutManager gridLayoutManager;
    Adapter_SelectDrinks gotoDrinks_adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selecting_drink_that_drank);
        loadAlcoholData(getApplicationContext());

        recyclerview_Goto_Drinks = (RecyclerView)findViewById(R.id.recyclerview_Goto_Drinks);
        recyclerview_Goto_Drinks.setHasFixedSize(true); //리사이클러 뷰에서 아이템 뷰가 자주 추가되고 삭제되어도 recyclerview의 크기를 고정한다.

        gridLayoutManager = new GridLayoutManager(this,4);
        recyclerview_Goto_Drinks.setLayoutManager(gridLayoutManager);


        gotoDrinks_adapter = new Adapter_SelectDrinks(alcohol_fridge, this);
        recyclerview_Goto_Drinks.setAdapter(gotoDrinks_adapter);

        ImageButton imgbtn_add_goto_drink =findViewById(R.id.imgbtn_add_goto_drink);
        ImageButton imgbtn_back_goto_drink = findViewById(R.id.imgbtn_back_goto_drink);
        Button btn_select_goto_drinks  = findViewById(R.id.btn_select_goto_drinks);
        EditText edittext_amount_goto_drinks  =findViewById(R.id.edittext_amount_goto_drinks);
        TextInputLayout edit_layout_amount_goto_drinks  =findViewById(R.id.edit_layout_amount_goto_drinks);
        RadioGroup glassOrbottle_goto_drinks = findViewById(R.id.glassOrbottle_goto_drinks);

        //if(gotoDrinks_adapter.mSelectedItem!=-1){ // 마신 술을 선택했을 때 얼마나 마셨는지 기록할 수 있는 UI를 보일 수 있게 하기
        //    edittext_amount_goto_drinks.setVisibility(View.VISIBLE);
        //    glassOrbottle_goto_drinks.setVisibility(View.VISIBLE);
        //    edit_layout_amount_goto_drinks.setVisibility(View.VISIBLE);
        //}


        imgbtn_add_goto_drink.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
        Intent intent = new Intent( getApplicationContext(), AddNewDrinksActivity.class);
        startActivity(intent);

          }
        });


        btn_select_goto_drinks.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              String amount = edittext_amount_goto_drinks.getText().toString();
              if(amount.isEmpty()){
                  amount="0";
              }
              int drink_amount = Integer.valueOf(amount);
             int checkedRadioButtonId =glassOrbottle_goto_drinks.getCheckedRadioButtonId();
             String bottle0rglass ="";
             switch (checkedRadioButtonId) {

                 case R.id.bottle:
                     bottle0rglass ="병";
                     break;

                 case R.id.glass:
                     bottle0rglass ="잔";
                     break;

             }
              if(gotoDrinks_adapter.mSelectedItem ==-1){
                  Toast.makeText(getApplicationContext(), "술을 선택해주세요",Toast.LENGTH_LONG).show();
              }
              else if(amount.isEmpty()|| bottle0rglass.isEmpty()){
                  Toast.makeText(getApplicationContext(), "마신 양을 입력해주세요",Toast.LENGTH_LONG).show();
              }
              else if(drink_amount==0){

                  Toast.makeText(getApplicationContext(), "0 이상의 마신값을 입력해주세요",Toast.LENGTH_LONG).show();
              }
              else {
                  int position = gotoDrinks_adapter.mSelectedItem;
                  Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
                  intent.putExtra("alcohol_position", position);
                  amount = amount+bottle0rglass;
                  intent.putExtra("alcohol_amount",amount);

                  setResult(RESULT_OK, intent);
                  finish();
              }
          }
        });

        imgbtn_back_goto_drink.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              finish();
          }
        });

    }
      @Override
      protected void onResume() {
          super.onResume();

          gotoDrinks_adapter.notifyDataSetChanged();
      }





}