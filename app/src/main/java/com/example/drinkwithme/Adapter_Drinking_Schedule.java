package com.example.drinkwithme;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import static com.example.drinkwithme.ImageManager.deletePics;
import static com.example.drinkwithme.PreferenceManager.saveScheduleList;
import static com.example.drinkwithme.application.drinking_diary;

public class Adapter_Drinking_Schedule extends RecyclerView.Adapter<Adapter_Drinking_Schedule.ViewHolder_Drinking_Schedule> {
    ArrayList<Drinking_Schedule> mDrinking_Schedules;
    Context mContext;

    public Adapter_Drinking_Schedule(Context context, ArrayList<Drinking_Schedule> Drinking_Schedules) {
        mDrinking_Schedules = Drinking_Schedules;
        mContext = context;
    }

    void addData(Drinking_Schedule data) {
        mDrinking_Schedules.add(data);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @NonNull
    @Override
    public Adapter_Drinking_Schedule.ViewHolder_Drinking_Schedule onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_drinking_schedule_view, parent, false);
        Adapter_Drinking_Schedule.ViewHolder_Drinking_Schedule vh = new Adapter_Drinking_Schedule.ViewHolder_Drinking_Schedule(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull Adapter_Drinking_Schedule.ViewHolder_Drinking_Schedule holder, int position) {
        holder.tv_title_item_drinking_schedule.setText(mDrinking_Schedules.get(position).title);
        String date_time = mDrinking_Schedules.get(position).getDate() + " " + mDrinking_Schedules.get(position).getTime();
        holder.tv_date_item_drinking_schedule.setText(date_time);
        holder.tv_location_item_drinking_schedule.setText(mDrinking_Schedules.get(position).getLocation());

        holder.Viewreturn().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mintent = new Intent(mContext, EditDrinkingScheduleActivity.class);
                mintent.putExtra("position", position);
                mContext.startActivity(mintent);
            }
        });

    }

    public void upDateItemList(ArrayList<Drinking_Schedule> Drinking_Schedules) {
        mDrinking_Schedules = Drinking_Schedules;
        notifyDataSetChanged(); //data 업데이트된 내용을 적용할 때, 업데이트된 데이터를 어댑터에 적용하기 위해 어댑터를 호출하는 메소드
    }

    @Override
    public int getItemCount() {
        return mDrinking_Schedules.size();
    }

    public void addItems(Drinking_Schedule schedule) {
        mDrinking_Schedules.add(schedule);
        notifyDataSetChanged();

    }

    public void notifyItemRangeRemoved(int position) {
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    public class ViewHolder_Drinking_Schedule extends RecyclerView.ViewHolder {
        View view;
        TextView tv_title_item_drinking_schedule;
        TextView tv_date_item_drinking_schedule;
        TextView tv_location_item_drinking_schedule;
        ImageButton btn_delete_drinking_schedule;

        public ViewHolder_Drinking_Schedule(@NonNull View view) {
            super(view);
            tv_title_item_drinking_schedule = (TextView) view.findViewById(R.id.tv_title_item_drinking_schedule);
            tv_date_item_drinking_schedule = (TextView) view.findViewById(R.id.tv_date_item_drinking_schedule);
            tv_location_item_drinking_schedule = (TextView) view.findViewById(R.id.tv_location_item_drinking_schedule);
            btn_delete_drinking_schedule = view.findViewById(R.id.btn_delete_drinking_schedule);

            //OnCreateContextMenuListener 리스너를 현재 클래스에서 구현한다고 설정해둡니다.
            this.view = view;
            btn_delete_drinking_schedule.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showDeleteDialog(getAdapterPosition(), view);
                }
            });
        }

        View Viewreturn() {
            return view;
        }


        public void removeItem(int position) {
            drinking_diary.remove(position);
            notifyItemRemoved(position);
            //갱신처리 반드시 해야함
            notifyDataSetChanged();
            saveScheduleList(mContext);
        }

        private void showDeleteDialog(int position, View v) {
            final Dialog deelteDialog = new Dialog(v.getContext());
            deelteDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            deelteDialog.setContentView(R.layout.dialog_delete);
            if (deelteDialog.getWindow() != null)
                deelteDialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));        //Android: how to create a transparent dialog-themed activity

            Button btn_no_deletedialog = deelteDialog.findViewById(R.id.btn_no_deletedialog);
            Button btn_yes_deletedialog = deelteDialog.findViewById(R.id.btn_yes_deletedialog);


            btn_no_deletedialog.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (v.getId() == R.id.btn_no_deletedialog) {
                        if (deelteDialog != null && deelteDialog.isShowing()) {
                            deelteDialog.dismiss();
                        }
                    }
                }
            });

            btn_yes_deletedialog.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (v.getId() == R.id.btn_yes_deletedialog) {
                        if (deelteDialog != null && deelteDialog.isShowing()) {
                            deelteDialog.dismiss();
                            removeItem(position);
                        }

                    }
                }
            });

            if (mContext != null && !((Activity) mContext).isFinishing()) {
                deelteDialog.show();
            }
        }
    }
}
