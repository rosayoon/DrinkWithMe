package com.example.drinkwithme;

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class Adapter_Community extends RecyclerView.Adapter<Adapter_Community.ViewHolder_Community>{
    @NonNull
    @Override
    public ViewHolder_Community onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder_Community holder, int position) {

    }



    @Override
    public int getItemCount() {
        return 0;
    }
    public class ViewHolder_Community extends RecyclerView.ViewHolder {

        public ViewHolder_Community(@NonNull View itemView) {
            super(itemView);
        }
    }
}
