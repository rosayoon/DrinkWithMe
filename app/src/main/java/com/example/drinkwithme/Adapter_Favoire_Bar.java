package com.example.drinkwithme;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import static com.example.drinkwithme.PreferenceManager.saveBarList;
import static com.example.drinkwithme.PreferenceManager.saveScheduleList;
import static com.example.drinkwithme.application.FavoriteBarCollection;

public class Adapter_Favoire_Bar extends RecyclerView.Adapter<Adapter_Favoire_Bar.ViewHolder_Favoire_Bar> {
    ArrayList<FavoriteBar> mFavoriteBars;
    Context mContext;

    public Adapter_Favoire_Bar(Context context, ArrayList<FavoriteBar> FavoriteBars) {
        mFavoriteBars = FavoriteBars;
        mContext = context;
    }

    void addData(FavoriteBar data) {
        mFavoriteBars.add(data);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @NonNull
    @Override
    public Adapter_Favoire_Bar.ViewHolder_Favoire_Bar onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_favorite_bar_view, parent, false);
        Adapter_Favoire_Bar.ViewHolder_Favoire_Bar vh = new Adapter_Favoire_Bar.ViewHolder_Favoire_Bar(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder_Favoire_Bar holder, int position) {
        FavoriteBar bar = mFavoriteBars.get(position);
        holder.tv_title_item_favorite_bar.setText(bar.name);
        holder.tv_address_item_favorite_bar.setText(bar.address);
        holder.tv_reason_item_favorite_bar.setText(bar.reason);
        holder. btn_delete_favorite_bar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mFavoriteBars.remove(position);
               // showDeleteDialog(position, holder.itemView);
            }
        });
    }


    public void upDateItemList(ArrayList<FavoriteBar> FavoriteBars) {
        mFavoriteBars = FavoriteBars;
        notifyDataSetChanged(); //data 업데이트된 내용을 적용할 때, 업데이트된 데이터를 어댑터에 적용하기 위해 어댑터를 호출하는 메소드
    }

    @Override
    public int getItemCount() {
        return mFavoriteBars.size();
    }

    public void addItems(FavoriteBar bar) {
        mFavoriteBars.add(bar);
        notifyDataSetChanged();

    }

    public void notifyItemRangeRemoved(int position) {
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    public class ViewHolder_Favoire_Bar extends RecyclerView.ViewHolder {
        View view;
        TextView tv_title_item_favorite_bar;
        TextView tv_address_item_favorite_bar;
        TextView tv_reason_item_favorite_bar;
        Button btn_delete_favorite_bar;

       public ViewHolder_Favoire_Bar(@NonNull View view) {
            super(view);
            tv_title_item_favorite_bar =view. findViewById(R.id.tv_title_item_favorite_bar);
            tv_address_item_favorite_bar=view. findViewById(R.id.tv_address_item_favorite_bar);
            tv_reason_item_favorite_bar =view. findViewById(R.id.tv_reason_item_favorite_bar);
            btn_delete_favorite_bar =view. findViewById(R.id.btn_delete_favorite_bar);

            //OnCreateContextMenuListener 리스너를 현재 클래스에서 구현한다고 설정해둡니다.
            this.view = view;

        }

        View Viewreturn() {
            return view;
        }





    }
    public void removeItem(int position) {
        FavoriteBarCollection.remove(position);
        notifyItemRemoved(position);
        //갱신처리 반드시 해야함
        notifyDataSetChanged();
        saveBarList(mContext);
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
