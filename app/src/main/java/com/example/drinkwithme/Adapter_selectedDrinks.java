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

public class Adapter_selectedDrinks extends RecyclerView.Adapter<Adapter_selectedDrinks.Viewholder_pictures> {
    ArrayList<SelectedDrinks> mSelectedDrinks;
    Context mContext;


    public Adapter_selectedDrinks(ArrayList<SelectedDrinks> SelectedDrinks, Context context) {
        mSelectedDrinks = SelectedDrinks;
        mContext = context;
    }


    @NonNull
    @Override
    public Viewholder_pictures onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.item_selectedalcohol_view, parent, false);

        Adapter_selectedDrinks.Viewholder_pictures vh = new Adapter_selectedDrinks.Viewholder_pictures(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull Viewholder_pictures holder, int position) {
        SelectedDrinks drinks = mSelectedDrinks.get(position);

        Bitmap bitmapImage;
        bitmapImage = loadImageFromStorage(drinks.path, drinks.imgFile_name);
        holder.img_alcohol_selected_drinks.setImageBitmap(bitmapImage);

        holder.tv_name_selected_drinks.setText(drinks.name);
        holder.tv_amount_selected_drinks.setText(drinks.amount);
    }

    @Override
    public int getItemCount() {
        return mSelectedDrinks.size();
    }


    public class Viewholder_pictures extends RecyclerView.ViewHolder {
        ImageView img_alcohol_selected_drinks;
        TextView tv_amount_selected_drinks;
        TextView tv_name_selected_drinks;


        public Viewholder_pictures(@NonNull View itemView) {
            super(itemView);

            img_alcohol_selected_drinks = itemView.findViewById(R.id.img_alcohol_selected_drinks);
            tv_amount_selected_drinks = itemView.findViewById(R.id.tv_amount_selected_drinks);
            tv_name_selected_drinks = itemView.findViewById(R.id.tv_name_selected_drinks);

        }

    }
}
