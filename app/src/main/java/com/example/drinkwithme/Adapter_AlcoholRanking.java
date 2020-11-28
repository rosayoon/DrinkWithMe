package com.example.drinkwithme;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import static com.example.drinkwithme.ImageManager.loadImageFromStorage;

public class Adapter_AlcoholRanking extends RecyclerView.Adapter<Adapter_AlcoholRanking.ViewHolder_AlcoholRanking>{
    Context mContext;
    ArrayList<AlcoholRanking> mRankings;

    public Adapter_AlcoholRanking(Context Context, ArrayList<AlcoholRanking> Rankings) {
        mContext = Context;
        mRankings = Rankings;
    }
    @NonNull
    @Override
    public Adapter_AlcoholRanking.ViewHolder_AlcoholRanking onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.item_drinks_rankingview,parent, false);
        ViewHolder_AlcoholRanking vh = new ViewHolder_AlcoholRanking(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull Adapter_AlcoholRanking.ViewHolder_AlcoholRanking holder, int position) {
        AlcoholRanking ranking = mRankings.get(position);
        holder.tv_name_item_alcohol_ranking.setText(ranking.name);
        String drinking_num = ranking.numberOfDrinking+"회";
        holder.tv_NumberOfDrinking_item_alcohol_ranking.setText(drinking_num);
        String drinking_rank = (position+1)+"위";
        holder.tv_rank_item_alcohol_ranking.setText(drinking_rank);
        holder.img_item_alcohol_ranking.setImageBitmap(loadImageFromStorage(ranking.path, ranking.imgFile_name));

    }

    @Override
    public int getItemCount() {
        return mRankings.size();
    }
    public void upDateList(ArrayList<AlcoholRanking> Rankings){
        mRankings = Rankings;
        notifyDataSetChanged();

    }

    public class ViewHolder_AlcoholRanking extends RecyclerView.ViewHolder {
        TextView tv_name_item_alcohol_ranking;
        TextView tv_NumberOfDrinking_item_alcohol_ranking;
        TextView tv_rank_item_alcohol_ranking;
        ImageView img_item_alcohol_ranking;

        public ViewHolder_AlcoholRanking(@NonNull View itemView) {
            super(itemView);
            tv_name_item_alcohol_ranking=itemView.findViewById(R.id.tv_name_item_alcohol_ranking);
            tv_NumberOfDrinking_item_alcohol_ranking=itemView.findViewById(R.id.tv_NumberOfDrinking_item_alcohol_ranking);
            img_item_alcohol_ranking =itemView.findViewById(R.id.img_item_alcohol_ranking);
            tv_rank_item_alcohol_ranking =itemView.findViewById(R.id.tv_rank_item_alcohol_ranking);

        }
    }

}
