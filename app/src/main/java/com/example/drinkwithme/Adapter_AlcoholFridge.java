package com.example.drinkwithme;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.ColorDrawable;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import static com.example.drinkwithme.ImageManager.deletePics;
import static com.example.drinkwithme.ImageManager.loadImageFromStorage;
import static com.example.drinkwithme.PreferenceManager.saveAlcoholList;
import static com.example.drinkwithme.PreferenceManager.saveScheduleList;
import static com.example.drinkwithme.application.alcohol_fridge;
import static com.example.drinkwithme.application.drinking_diary;

public class Adapter_AlcoholFridge extends RecyclerView.Adapter<Adapter_AlcoholFridge.AlcoholFridge_ViewHolder> {
    ArrayList<Alcohol> mAlcohol;
    Context mContext;


    public Adapter_AlcoholFridge(ArrayList<Alcohol> Alcohol, Context Context) {
        mAlcohol = Alcohol;
        mContext = Context;
    }

    void addData(Alcohol data) {
        mAlcohol.add(data);
    }

    public Context getmContext() {
        return mContext;
    }

    // 아이템 뷰를 위한 뷰홀더 객체 생성하여 리턴
    @Override
    public Adapter_AlcoholFridge.AlcoholFridge_ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = (View) LayoutInflater.from(parent.getContext()).inflate(R.layout.item_alcohol_fridge_view, parent, false);
        Adapter_AlcoholFridge.AlcoholFridge_ViewHolder vh = new Adapter_AlcoholFridge.AlcoholFridge_ViewHolder(v);
        return vh;
    }

    //onBindViewHolder() - position에 해당하는 데이터를 뷰홀더의 아이템뷰에 표시
    @Override
    public void onBindViewHolder(@NonNull AlcoholFridge_ViewHolder holder, int position) {
        Alcohol drankAlcohol = mAlcohol.get(position);

        Bitmap bitmapImage;
        try {
            bitmapImage = loadImageFromStorage(drankAlcohol.path, drankAlcohol.imgFile_name);
            holder.img_item_alcoholfridge.setImageBitmap(bitmapImage);
        } catch (Exception e) {
            e.printStackTrace();
        }
        holder.tv_item_alcoholfridege.setText(drankAlcohol.name);


        holder.Viewreturn().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, AllAboutDrinks.class);
                intent.putExtra("aboutdrinks_position", position);
                mContext.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return mAlcohol.size();
    }
    public void notifyItemRangeRemoved(int position) {
    }
    public void removeItem(int position) {
        alcohol_fridge.remove(position);
        notifyItemRemoved(position);
        //갱신처리 반드시 해야함
        notifyDataSetChanged();
        saveAlcoholList(mContext);
    }
    public void upDateItemList(ArrayList<Alcohol> Alcohol) {
        mAlcohol = Alcohol;
        notifyDataSetChanged(); //data 업데이트된 내용을 적용할 때, 업데이트된 데이터를 어댑터에 적용하기 위해 어댑터를 호출하는 메소드
    }

    public class AlcoholFridge_ViewHolder extends RecyclerView.ViewHolder  { // Recycler View는  뷰홀더가 생성되는 시점에 이벤트 리스너를 추가한다.

        ImageView img_item_alcoholfridge;
        TextView tv_item_alcoholfridege;
        Button btn_delete_alcohol_fridge;

        public AlcoholFridge_ViewHolder(@NonNull View itemView) {
            super(itemView);

            //뷰 객체에 대한 참조
            btn_delete_alcohol_fridge= itemView.findViewById(R.id.btn_delete_alcohol_fridge);
            img_item_alcoholfridge = (ImageView) itemView.findViewById(R.id.img_item_alcoholfridge);
            tv_item_alcoholfridege = (TextView) itemView.findViewById(R.id.tv_item_alcoholfridege);

            btn_delete_alcohol_fridge.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View v) {
                  showDeleteDialog(getAdapterPosition(),itemView);
              }
            });

        }
        View Viewreturn(){
            return itemView;
        }




    }
    private void showDeleteDialog(int position, View v) {
        final Dialog deleteDialog = new Dialog(v.getContext());
        deleteDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        deleteDialog.setContentView(R.layout.dialog_delete_alcohol);
        if (deleteDialog.getWindow() != null)
            deleteDialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));        //Android: how to create a transparent dialog-themed activity

        Button btn_no_deletedialog = deleteDialog.findViewById(R.id.btn_no_deletedialog);
        Button btn_yes_deletedialog = deleteDialog.findViewById(R.id.btn_yes_deletedialog);
        TextView tv_about_schedule_dialog_delete_alcohol =deleteDialog.findViewById(R.id.tv_about_schedule_dialog_delete_alcohol);
        TextView tv_schedules_dialog_delete_alcohol =deleteDialog.findViewById(R.id.tv_schedules_dialog_delete_alcohol);

        String list="";
        int list_num=0;
        String key = alcohol_fridge.get(position).key;
        for(int i=0; i<drinking_diary.size(); i++){
            Drinking_Schedule schedule = drinking_diary.get(i);
            if(schedule.alcohol_map_that_drank.containsKey(key)){
                list_num++;
                list+=schedule.title+"("+schedule.date+")"+"\n";
            }
        }
        list = list.substring(0,list.length()-2);

        if(list_num>0){
            tv_about_schedule_dialog_delete_alcohol.setVisibility(View.VISIBLE);
            tv_schedules_dialog_delete_alcohol.setText(list);
        }
        btn_no_deletedialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v.getId() == R.id.btn_no_deletedialog) {
                    if (deleteDialog != null && deleteDialog.isShowing()) {
                        deleteDialog.dismiss();
                    }
                }
            }
        });

        btn_yes_deletedialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v.getId() == R.id.btn_yes_deletedialog) {
                    if (deleteDialog != null && deleteDialog.isShowing()) {
                        deleteDialog.dismiss();
                        removeItem(position);
                    }

                }
            }
        });

        if (mContext != null && !((Activity) mContext).isFinishing()) {
            deleteDialog.show();
        }
    }

    public void remove(ViewModel item) {
        int position = alcohol_fridge.indexOf(item);
        alcohol_fridge.remove(position);
        saveAlcoholList(mContext);
        notifyItemRemoved(position);

    }
}
