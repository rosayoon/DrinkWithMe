package com.example.drinkwithme;
import com.bumptech.glide.Glide;

import android.content.Context;
import android.net.Uri;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import static com.example.drinkwithme.ImageManager.loadImageFromStorage;

public class Adapter_pictures extends RecyclerView.Adapter<Adapter_pictures.Viewholder_pictures>{
    ArrayList<DrinkingPic> mDrinkingGallery ;
    Context mContext;

    public Adapter_pictures(ArrayList<DrinkingPic> DrinkingGallery , Context context){
        mDrinkingGallery = DrinkingGallery;
        mContext = context;
    }


    @NonNull
    @Override
    public Viewholder_pictures onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.item_picture_view,parent,false);
        Adapter_pictures.Viewholder_pictures vh = new  Adapter_pictures.Viewholder_pictures(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull Viewholder_pictures holder, int position) {
       DrinkingPic pic = mDrinkingGallery.get(position);

        holder.img_item_picture.setImageBitmap(loadImageFromStorage(pic.path, pic.img_name));
        holder.img_item_picture.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                return false;
            }
        });
    }
    @Override
    public int getItemCount() {
        return mDrinkingGallery.size();
    }
    public void addData(DrinkingPic pic){
        mDrinkingGallery.add(pic);
        notifyDataSetChanged();
        notifyItemInserted(mDrinkingGallery.size());
    }

    public class Viewholder_pictures extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener{
        ImageView img_item_picture;
        public Viewholder_pictures(@NonNull View itemView) {
            super(itemView);

            img_item_picture = itemView.findViewById(R.id.img_item_picture);


        }
        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
            //3. 컨텍스트 메뉴를 생성하고 항목 선택시 호출되는 리스너를 등록해줍니다.
            // ID 1001, 1002로 어떤 메뉴를 선택했는지 리스너에서 구분하게 됩니다.

            MenuItem Delete = menu.add(Menu.NONE, 1002, 2, "삭제");
            Delete.setOnMenuItemClickListener(onEditMenu);

        }

        private final MenuItem.OnMenuItemClickListener onEditMenu =
                new MenuItem.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {

                        switch (item.getItemId()) {


                            case 1002:
                                mDrinkingGallery.remove(getAdapterPosition());
                                notifyItemRangeRemoved(getAdapterPosition(), mDrinkingGallery.size());
                                notifyItemChanged(getAdapterPosition(), mDrinkingGallery.size());

                                break;

                        }
                        return false;
                    }
                };

    }
}
