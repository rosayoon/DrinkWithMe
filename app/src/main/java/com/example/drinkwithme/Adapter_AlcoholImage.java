package com.example.drinkwithme;
import com.bumptech.glide.Glide;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioButton;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import static android.app.Activity.RESULT_OK;

public class Adapter_AlcoholImage extends RecyclerView.Adapter<Adapter_AlcoholImage.ViewHolder_AlcoholImage>{
   Context mContext;
   ArrayList<BasicAlcoholPic> mBasicGallery;
    public int mSelectedItem = -1;

   public Adapter_AlcoholImage(Context Context, ArrayList<BasicAlcoholPic> BasicGallery){
       mContext = Context;
       mBasicGallery = BasicGallery;
   }
    @NonNull
    @Override
    public ViewHolder_AlcoholImage onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.item_alcohol_basic_image,parent, false);
        ViewHolder_AlcoholImage vh = new ViewHolder_AlcoholImage(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder_AlcoholImage holder, int position) {

       /*  의존성(dependencies) 추가
           implementation 'com.github.bumptech.glide:glide:4.11.0'
             annotationProcessor 'com.github.bumptech.glide:compiler:4.11.0'
       */
       Glide.with(mContext) //해당 환경의 Context나 객체 입력
           .load(mBasicGallery.get(position).uri) //URL, URI 등등 이미지를 받아올 경로
           .into(holder.img_item_basicalcohol); //받아온 이미지를 받을 공간(ex. ImageView);
        holder.radio_item_basicalcohol.setChecked(position == mSelectedItem);


    }

    @Override
    public int getItemCount() {
        return mBasicGallery.size();
    }

    public class ViewHolder_AlcoholImage extends RecyclerView.ViewHolder {
        ImageView img_item_basicalcohol ;
        RadioButton radio_item_basicalcohol;
        public ViewHolder_AlcoholImage(@NonNull View itemView) {
            super(itemView);

            img_item_basicalcohol  = itemView.findViewById(R.id.img_item_basicalcohol);
            radio_item_basicalcohol= itemView.findViewById(R.id.radio_item_basicalcohol);
            View.OnClickListener clickListener = new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    mSelectedItem = getAdapterPosition();
                    notifyDataSetChanged();
                }
            };
            itemView.setOnClickListener(clickListener);
            radio_item_basicalcohol.setOnClickListener(clickListener);
        }        }

    }

