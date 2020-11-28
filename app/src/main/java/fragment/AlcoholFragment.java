package fragment;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.bumptech.glide.load.engine.Resource;
import com.example.drinkwithme.Adapter_AlcoholFridge;
import com.example.drinkwithme.AddNewDrinksActivity;

import com.example.drinkwithme.AlcoholChartActivity;
import com.example.drinkwithme.HowmuchDrunkActivity;
import com.example.drinkwithme.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import static com.example.drinkwithme.PreferenceManager.loadAlcoholData;
import static com.example.drinkwithme.application.alcohol_fridge;

public class AlcoholFragment extends Fragment {
    RecyclerView recyclerview_Goto_Drinks;
    GridLayoutManager gridLayoutManager;
    Adapter_AlcoholFridge adapter_alcoholFridge;
    Handler mHandler;
    boolean isShowing=false;
    TextView tv_hint_about_press_alcohol_fragment;
    ImageView img_beer_alcohol_alcohol_fragment;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_fragment_alcohol, null);
        // Inflate the layout for this fragment
        loadAlcoholData(getContext());

        mHandler = new Handler();
        recyclerview_Goto_Drinks = (RecyclerView) view.findViewById(R.id.recyclerview_Alcohol_Fridge);
        recyclerview_Goto_Drinks.setHasFixedSize(true); //리사이클러 뷰에서 아이템 뷰가 자주 추가되고 삭제되어도 recyclerview의 크기를 고정한다.

        gridLayoutManager = new GridLayoutManager(getContext(), 3);
        recyclerview_Goto_Drinks.setLayoutManager(gridLayoutManager);


        adapter_alcoholFridge = new Adapter_AlcoholFridge(alcohol_fridge, getContext());
        recyclerview_Goto_Drinks.setAdapter(adapter_alcoholFridge);

        recyclerview_Goto_Drinks.setVisibility(View.INVISIBLE);

        ImageButton btn_add_alcohol_fragment_alcohol = view.findViewById(R.id.btn_add_alcohol_fragment_alcohol);
        img_beer_alcohol_alcohol_fragment = view.findViewById(R.id.img_beer_alcohol_alcohol_fragment);
        Button btn_chart_fragment_alcohol = view.findViewById(R.id.btn_chart_fragment_alcohol);
        tv_hint_about_press_alcohol_fragment = view.findViewById(R.id.tv_hint_about_press_alcohol_fragment);

        adapter_alcoholFridge.notifyDataSetChanged();

        btn_chart_fragment_alcohol.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              Intent intent = new Intent(getContext(), AlcoholChartActivity.class);
              startActivity(intent);
          }
        });
        btn_add_alcohol_fragment_alcohol.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), AddNewDrinksActivity.class);
                startActivity(intent);
            }
        });
        view.findViewById(R.id.btn_history_fragment_alcohol).setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              Intent intent = new Intent(getContext(), HowmuchDrunkActivity.class);
              startActivity(intent);
          }
        });
        img_beer_alcohol_alcohol_fragment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        if(!isShowing) {
                            int[] beer_pic = {R.drawable.icon_medium_beer, R.drawable.icon_glass};
                            for (int i = 0; i < beer_pic.length; i++) {
                                final int resid = beer_pic[i];

                                mHandler.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        img_beer_alcohol_alcohol_fragment.setImageResource(resid);
                                    }
                                });

                                try {
                                    Thread.sleep(500);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                            }
                            mHandler.post(new Runnable() {
                                @Override
                                public void run() {
                                    recyclerview_Goto_Drinks.setVisibility(View.VISIBLE);
                                    tv_hint_about_press_alcohol_fragment.setVisibility(View.INVISIBLE);
                                    isShowing =true;
                                }
                            });
                        }
                        else{
                            int[] beer_pic = {R.drawable.icon_medium_beer, R.drawable.icon_low_beer};
                            for (int i = 0; i < beer_pic.length; i++) {
                                final int resid = beer_pic[i];

                                mHandler.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        img_beer_alcohol_alcohol_fragment.setImageResource(resid);
                                    }
                                });

                                try {
                                    Thread.sleep(500);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                            }
                            mHandler.post(new Runnable() {
                                @Override
                                public void run() {
                                    recyclerview_Goto_Drinks.setVisibility(View.INVISIBLE);
                                    tv_hint_about_press_alcohol_fragment.setVisibility(View.VISIBLE);
                                    isShowing =false;
                                }
                            });
                        }
                    }
                }).start();
            }
        });
        return view;

    }


    @Override
    public void onStart() {
        super.onStart();
        adapter_alcoholFridge.upDateItemList(alcohol_fridge);
        adapter_alcoholFridge.notifyDataSetChanged();
    }
    @Override
    public void onResume() {
        super.onResume();

        adapter_alcoholFridge.notifyDataSetChanged();

    }


}