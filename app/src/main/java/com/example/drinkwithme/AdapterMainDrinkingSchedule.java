package com.example.drinkwithme;

import android.content.Context;
import android.content.Intent;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import static com.example.drinkwithme.application.alcohol_fridge;
import static com.example.drinkwithme.application.drinking_diary;

public class AdapterMainDrinkingSchedule extends RecyclerView.Adapter<AdapterMainDrinkingSchedule.ViewHolderMainDrinkingSchedule> {
    Context mContext;
    ArrayList<Drinking_Schedule> mSchedule_this_week;

   public AdapterMainDrinkingSchedule(ArrayList<Drinking_Schedule> Schedule_this_week, Context Context){
       mContext=Context;
       mSchedule_this_week=Schedule_this_week;
   }
    @NonNull
    @Override
    public ViewHolderMainDrinkingSchedule onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.item_recent_drinking_schedule_view,parent,false);
        ViewHolderMainDrinkingSchedule vh = new ViewHolderMainDrinkingSchedule(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderMainDrinkingSchedule holder, int position) {
       holder.tv_title_item_recent_drinking_schedule.setText(mSchedule_this_week.get(position).title);
       String info = mSchedule_this_week.get(position).date+mSchedule_this_week.get(position).time+" "+mSchedule_this_week.get(position).location;
       holder.tv_info_item_recent_drinking_schedule.setText(info);

       holder.itemView.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
             int position1 =drinking_diary.indexOf(mSchedule_this_week.get(position));
             Intent intent = new Intent(mContext, EditDrinkingScheduleActivity.class);
             intent.putExtra("position",position1);
             mContext.startActivity(intent);
         }
       });
    }


    @Override
    public int getItemCount() {
        return mSchedule_this_week.size();
    }
    public void upDateItemList(ArrayList<Drinking_Schedule> Schedule_this_week){
        mSchedule_this_week =Schedule_this_week;
        notifyDataSetChanged();
    }
    public class ViewHolderMainDrinkingSchedule extends RecyclerView.ViewHolder{
        TextView tv_title_item_recent_drinking_schedule;
        TextView tv_info_item_recent_drinking_schedule ;

        public ViewHolderMainDrinkingSchedule(@NonNull View itemView) {
            super(itemView);

            tv_title_item_recent_drinking_schedule = itemView.findViewById(R.id.tv_title_item_recent_drinking_schedule);
            tv_info_item_recent_drinking_schedule=  itemView.findViewById(R.id.tv_info_item_recent_drinking_schedule);

        }
    }
}
