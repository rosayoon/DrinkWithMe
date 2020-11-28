package com.example.drinkwithme;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class Adapter_Naver extends RecyclerView.Adapter<Adapter_Naver.ViewHolder_Naver> {
   Context mContext;
   ArrayList< SearchBar> mBarRecommendation;
      @NonNull
      @Override
      public ViewHolder_Naver onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
          View v = LayoutInflater.from(mContext).inflate(R.layout.item_naver_view, parent, false);
          ViewHolder_Naver vh = new ViewHolder_Naver(v);
          return vh;
      }

      @Override
      public void onBindViewHolder(@NonNull ViewHolder_Naver holder, int position) {
          SearchBar bar = mBarRecommendation.get(position);

          holder.tv_title_naver_item.setText(bar.title);
          holder.tv_category_naver_item.setText(bar.category);
          holder.tv_address_naver_item.setText(bar.address);

      }



      @Override
      public int getItemCount() {
          return mBarRecommendation.size();
      }


      public Adapter_Naver(Context Context,ArrayList<SearchBar> BarRecommendation){
          mContext = Context;
          mBarRecommendation =BarRecommendation;

      }
      public class ViewHolder_Naver extends RecyclerView.ViewHolder{

          TextView tv_title_naver_item;
          TextView tv_category_naver_item;
          TextView tv_address_naver_item;
          public ViewHolder_Naver(@NonNull View itemView) {
              super(itemView);

              tv_title_naver_item =itemView.findViewById(R.id.tv_title_naver_item);
              tv_category_naver_item =itemView.findViewById(R.id.tv_category_naver_item);
              tv_address_naver_item =itemView.findViewById(R.id.tv_address_naver_item);

          }
      }
}
