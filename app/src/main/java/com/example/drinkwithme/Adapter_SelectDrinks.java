package com.example.drinkwithme;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import static com.example.drinkwithme.ImageManager.loadImageFromStorage;

public class Adapter_SelectDrinks extends RecyclerView.Adapter<Adapter_SelectDrinks.SelectDrinks_ViewHolder> {
    ArrayList<Alcohol> mAlcohol;
    Context mContext;
    public int mSelectedItem = -1;
    //컨텍스트는 새로 생성된 객체가 지금 어떤 일이 일어나고 있는지 알 수 있도록 합니다.
    //컨택스트는 시스템의 핸들과도 같습니다. 리소스, 데이터베이스, preferences등에 대한 접근을 제공합니디ㅏ.
    //Context는 크게 2가지 역할을 수행하는 Abstract 클래스
    // 어플리케이션에 관하여 시스템이 관리하고 있는 정보에 접근하기
    // 안드로이드 시스템 서비스에서 제공하는 API를 호출할 수 있는 기능
    // CONTEXT는 어떤 Activitiy 혹은 어떤 Application인가에 대해서 구별하는 정보가 context라고 이해하면 된다.

    public Adapter_SelectDrinks(ArrayList<Alcohol> Alcohol, Context Context) {
        mAlcohol = Alcohol;
        mContext = Context;
    }

    void addData(Alcohol data) {
        mAlcohol.add(data);
    }

    public Context getmContext() {
        return mContext;
    }

    // 아이템 뷰를 위한 뷰홀더 객체 생성하여 리턴
    @Override
    public Adapter_SelectDrinks.SelectDrinks_ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = (View) LayoutInflater.from(parent.getContext()).inflate(R.layout.item_alcohol_view, parent, false);

        Adapter_SelectDrinks.SelectDrinks_ViewHolder vh = new Adapter_SelectDrinks.SelectDrinks_ViewHolder(v);
        return vh;
        // Context context = parent.getContext();
        // LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        // View v = inflater.inflate(R.layout.item_view,parent, false);

        //LayoutInflater은 XML에 미리 정의해둔 틀을 실제 메모리에 올려주는 역할을 한다. 즉, LayoutInflater는 XML에 정의된 Resource를 View 객체로 반환해주는 역할을 한다.
        //LayoutInflater을 사용하기 위한 조건
        //1. 객체화하고자 하는 xml 파일을 작성한다.
        //2. LayoutInflater inflater = (LayoutInflater) getSystem.Service(Context_LAYOUT_INFLATER_SERVICE)라는 코드를 사용해서 LayoutInflater 객체 사용 준비를 완료한다.
        //3. inflater.inflate(R.layout.sub1,container, true) 라는 코드를 통해서 사전에 미리 선언해뒀던 container라는 레이아웃에 작성했던 xml의 메모리객체가 삽입되게 된다.
        // 매개변수 설명: inflate(1.객체화하고픈 xml 파일, 2. 객체화된 뷰를 넣을 부모 레이아웃/컨테이너 , 3. true(바로 인플레이션 하고자 하는지))

    }

    //onBindViewHolder() - position에 해당하는 데이터를 뷰홀더의 아이템뷰에 표시
    @Override
    public void onBindViewHolder(@NonNull SelectDrinks_ViewHolder holder, int position) {
        Alcohol drankAlcohol =mAlcohol.get(position);

        Bitmap bitmapImage;
        try {
            bitmapImage = loadImageFromStorage(drankAlcohol.path, drankAlcohol.imgFile_name);
            holder.img_item_alcohol.setImageBitmap(bitmapImage);
        } catch (Exception e) {
            e.printStackTrace();
        }
        holder.tv_item_alcohol.setText(drankAlcohol.name);
        holder.radio_item_alcohol.setChecked(position == mSelectedItem);
    }

    @Override
    public int getItemCount() {
        return mAlcohol.size();
    }

    public class SelectDrinks_ViewHolder extends RecyclerView.ViewHolder  { // Recycler View는  뷰홀더가 생성되는 시점에 이벤트 리스너를 추가한다.

        ImageView img_item_alcohol;
        TextView tv_item_alcohol;
        RadioButton radio_item_alcohol;

        public SelectDrinks_ViewHolder(@NonNull View itemView) {
            super(itemView);

            //뷰 객체에 대한 참조

            img_item_alcohol = (ImageView) itemView.findViewById(R.id.img_item_alcohol);
            tv_item_alcohol = (TextView) itemView.findViewById(R.id.tv_item_alcohol);
            radio_item_alcohol = itemView.findViewById(R.id.radio_item_alcohol);

            View.OnClickListener clickListener = new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    mSelectedItem = getAdapterPosition();
                    notifyDataSetChanged();
                }
            };
            itemView.setOnClickListener(clickListener);
            radio_item_alcohol.setOnClickListener(clickListener);
        }


    }


}
