package com.example.drinkwithme;
import android.util.Log;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;

public class Adapter_YouTube extends RecyclerView.Adapter<Adapter_YouTube.ViewHolder_Youtube>{
    private ArrayList<SearchData> mItems;


    Context mContext;

    public Adapter_YouTube(Context Context, ArrayList<SearchData> Items){
        mContext =Context;
        mItems =Items;
    }
    @NonNull
    @Override
    public Adapter_YouTube.ViewHolder_Youtube onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.item_youtube_view,parent, false);
       ViewHolder_Youtube vh = new ViewHolder_Youtube(v);
        return vh;
    }


    @Override
    public void onBindViewHolder(@NonNull Adapter_YouTube.ViewHolder_Youtube holder, int position) {
        SearchData finfo = mItems.get(position);

        String url = finfo.getUrl();

        String sUrl ="";
        String eUrl ="";
        sUrl =url.substring(0,url.lastIndexOf("/")+1);
        eUrl = url.substring(url.lastIndexOf("/")+1,url.length());
        try {
            eUrl = URLEncoder.encode(eUrl, "EUC-KR").replace("+", "%20");
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        String new_url = sUrl + eUrl;

        Glide.with(mContext).load(new_url).into(holder.img_youtube_item);
        String date = finfo.publishedAt;
        holder.tv_date_youtube_item.setText(date);
        String title = finfo.title;
        holder.tv_title_youtube_item.setText(title);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, StartActivity.class);
                intent.putExtra("id", mItems.get(position).getVideoId());
                mContext.startActivity(intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
                //서비스는 태스크가 없기 때문에 액티비티를 시작하려면 new task 플래그를 줘야 한다고 한다.

            }
        });
    }


    @Override
    public int getItemCount() {
        return mItems.size();
    }


    public class ViewHolder_Youtube extends RecyclerView.ViewHolder{
        View view;
        ImageView img_youtube_item;
        TextView tv_title_youtube_item;
        TextView tv_date_youtube_item;
        public ViewHolder_Youtube(@NonNull View itemView) {
            super(itemView);
            view = itemView;
            img_youtube_item=  itemView.findViewById(R.id.img_youtube_item);
            tv_title_youtube_item =itemView.findViewById(R.id.tv_title_youtube_item);
            tv_date_youtube_item =itemView.findViewById(R.id.tv_date_youtube_item);
        }


    }
}
