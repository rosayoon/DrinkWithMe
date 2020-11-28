package com.example.drinkwithme;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import static com.example.drinkwithme.ImageManager.loadImageFromStorage;
import static com.example.drinkwithme.application.alcohol_fridge;
import static com.example.drinkwithme.application.drinking_diary;

public class Adapter_AboutDrinks extends RecyclerView.Adapter<Adapter_AboutDrinks.ViewHolder_AboutDrinks>{

    ArrayList<Alcohol> mAlcohol;
    Context mcontext;


    public Adapter_AboutDrinks(ArrayList<Alcohol> Alcohol, Context context){
        mAlcohol = Alcohol;
        mcontext = context;
    }
    @NonNull
    @Override
    public Adapter_AboutDrinks.ViewHolder_AboutDrinks onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mcontext).inflate(R.layout.item_about_drinks_view,parent, false);
        ViewHolder_AboutDrinks vh = new ViewHolder_AboutDrinks(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull Adapter_AboutDrinks.ViewHolder_AboutDrinks holder, int position) {
       Alcohol drankAlcohol = alcohol_fridge.get(position);
        holder.tv_name_item_about_drinks.setText(drankAlcohol.name);
        Bitmap bitmapImage;
        try {
            bitmapImage = loadImageFromStorage(drankAlcohol.path, drankAlcohol.imgFile_name);
            holder.img_item_about_drinks.setImageBitmap(bitmapImage);
        } catch (Exception e) {
            e.printStackTrace();
        }
        String keyOfAlcohol = drankAlcohol.key;
        int numberOfDrinking=0;
        int DrinkingAmount_glass = 0;
        int DrinkingAmount_bottle = 0;

        String MaximumDrinkingAmount ="0잔";

        for(int i =0; i<drinking_diary.size(); i++){
            Drinking_Schedule schedule = drinking_diary.get(i);

            if(schedule.alcohol_map_that_drank.containsKey(keyOfAlcohol)){ //저장되어있는 술 약속에서 이 술을 마셨다고 기록되어 있을 때
              numberOfDrinking++;  //마신 횟수 +1
                String amount = schedule.alcohol_map_that_drank.get(keyOfAlcohol);// ex) "1병" or "2잔"
                int amount_num = Integer.parseInt(amount.substring(0,amount.length()-1));// amount에서 뒤에 문자를 빼고 숫자값만 가져오기
                int maximum_amount_num =  Integer.parseInt(MaximumDrinkingAmount.substring(0,amount.length()-1));
                if(MaximumDrinkingAmount.endsWith("잔")) {
                    if (amount.endsWith("병")) {
                        DrinkingAmount_bottle += amount_num; // 마신 양을 더하기
                        MaximumDrinkingAmount = amount;
                    } else if (amount.endsWith("잔")) {
                        DrinkingAmount_glass += amount_num;
                        if(amount_num>maximum_amount_num){ // 둘 다 잔으로 끝날 때: 값을 비교하기
                            MaximumDrinkingAmount = amount;
                        }
                    }
                }
                else if(MaximumDrinkingAmount.endsWith("병")){
                    if (amount.endsWith("병")) {
                        DrinkingAmount_bottle += amount_num; // 마신 양을 더하기
                        if(amount_num>maximum_amount_num){ // 둘 다 병으로 끝날 때: 값을 비교하기
                            MaximumDrinkingAmount = amount;
                        }
                    } else if (amount.endsWith("잔")) {
                        DrinkingAmount_glass += amount_num;
                    }
                }

            }
        }
        String sumOfAmount ="";
        if(DrinkingAmount_glass!=0){//잔의 숫자값이 0이 아닐때
            if(DrinkingAmount_bottle!=0){ //병의 숫자값이 0이 아닐때
                sumOfAmount = DrinkingAmount_bottle +"병  &  "+DrinkingAmount_glass+"잔";
            }
            else{ //병의 숫자값이 0이고 잔의 숫자값은 0이 아닐 때
                sumOfAmount = DrinkingAmount_glass +"잔"; //glass값만 표시한다.
            }
        }
        else{
            if(DrinkingAmount_bottle!=0){//잔의 숫자값이 0이고 병의 숫자값이 0이 아닐때
                sumOfAmount =DrinkingAmount_bottle+"병";
            }
            else{//잔, 병 숫자값 모두 0일 때
                sumOfAmount = "마신 기록이 없습니다.";
            }
        }
        holder.tv_NumberOfDrinking_item_about_drinks.setText(String.valueOf(numberOfDrinking));
        holder.tv_MaxiumDrinking_item_about_drinks.setText(String.valueOf(MaximumDrinkingAmount));
        holder.tv_sum_item_about_drinks.setText(sumOfAmount);


    }
    @Override
    public int getItemCount() {
        return alcohol_fridge.size();
    }

    public class ViewHolder_AboutDrinks extends RecyclerView.ViewHolder{
       TextView tv_name_item_about_drinks;
       TextView tv_sum_item_about_drinks;
       TextView tv_MaxiumDrinking_item_about_drinks;
       TextView tv_NumberOfDrinking_item_about_drinks;

       ImageView img_item_about_drinks;


        public ViewHolder_AboutDrinks(@NonNull View itemView) {
            super(itemView);
            tv_MaxiumDrinking_item_about_drinks = itemView. findViewById(R.id.tv_MaxiumDrinking_item_about_drinks);
            tv_NumberOfDrinking_item_about_drinks=itemView. findViewById(R.id.tv_NumberOfDrinking_item_about_drinks);
            img_item_about_drinks =               itemView. findViewById(R.id.img_item_about_drinks);
            tv_name_item_about_drinks =           itemView.  findViewById(R.id.tv_name_item_about_drinks);
            tv_sum_item_about_drinks = itemView.findViewById(R.id.tv_sum_item_about_drinks);
        }
    }
}
