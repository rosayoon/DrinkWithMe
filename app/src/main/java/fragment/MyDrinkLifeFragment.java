package fragment;
import android.annotation.SuppressLint;
import android.os.Handler;
import android.os.Message;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.drinkwithme.Adapter_Drinking_Schedule;
import com.example.drinkwithme.AddDrinkingScheduleActivity;
import com.example.drinkwithme.DrinkingScheduleActivity;
import com.example.drinkwithme.FavoriteBarActivity;
import com.example.drinkwithme.HowmuchDrunkActivity;
import com.example.drinkwithme.HowoftenDrinksActivity;
import com.example.drinkwithme.ProgressDialog;
import com.example.drinkwithme.R;

import java.text.SimpleDateFormat;
import java.util.Date;

import static com.example.drinkwithme.application.drinking_diary;

public class MyDrinkLifeFragment extends Fragment {
    RecyclerView recyclerview_drinking_schedule;
    RecyclerView.LayoutManager layoutManager;
    Adapter_Drinking_Schedule adapterDrinkingSchedule;
    private ProgressDialog progressDialog;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_drinking_schedule,null);


        ImageButton btn_add_schedule_drinking_schedule = view.findViewById(R.id.btn_add_schedule_drinking_schedule);
        Button btn_future_drinking_schedule = view.findViewById(R.id.btn_future_drinking_schedule);
        Button btn_past_drinking_schedule = view.findViewById(R.id.btn_past_drinking_schedule);

        recyclerview_drinking_schedule = view.findViewById(R.id.recyclerview_drinking_schedule);
        recyclerview_drinking_schedule.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(getContext());
        recyclerview_drinking_schedule.setLayoutManager(layoutManager);

        adapterDrinkingSchedule = new Adapter_Drinking_Schedule(getContext(), drinking_diary);

        recyclerview_drinking_schedule.setAdapter(adapterDrinkingSchedule);


        btn_past_drinking_schedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });



        btn_add_schedule_drinking_schedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), AddDrinkingScheduleActivity.class);
                startActivity(intent);

            }
        });

        view.findViewById(R.id.btn_favorite_bar_fragment).setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              Intent intent = new Intent(getContext(), FavoriteBarActivity.class);
              startActivity(intent);
          }
        });
        return view;


    }
    protected void initialized(){


    }


    public void onResume() {
        super.onResume();
        //initialized();

        adapterDrinkingSchedule.upDateItemList(drinking_diary);

    }
    public String calDate(String date, String time) { // 술 약속 객체의 날짜가 현재 날짜~현재날짜+7일 안에 포함되는지 안되는지
        long now = System.currentTimeMillis(); //현재 시간
        SimpleDateFormat simpleDate = new SimpleDateFormat("yyyy년 MM월 dd일");
        SimpleDateFormat simpleTime = new SimpleDateFormat("hh시 mm분");
        Date today_first = new Date(now);
        String getTodayDate = simpleDate.format(today_first); //"yyyy년 MM월 dd일" 형태로 변형


        try { //String Type을 Date Type으로 캐스팅하면서 생기는 예외처리(안해주면 컴파일 실패)
            Date ScheduleDate = simpleDate.parse(date); //String date를 parse()를 통해 Date형으로 변환
            Date todayDate = simpleDate.parse(getTodayDate); //오늘 날짜 String date르 parse()를 통해 Date형으로 변환

            //Date로 변환된 두 날짜를 계산한 뒤 그 리턴 값으로 long type 변수를 초기화하고 있다.
            //연산결과 -950400000. long type으로 return된다.
            long calDate = ScheduleDate.getTime() - todayDate.getTime();

            //Date.getTime() 은 해당날짜를 기준으로 1970년 00:00:00부터 몇 초가 흘렀는지를 반환해준다.
            //이제 24*60*60*1000(각 시간값에 따른 차이점)을 나눠주면 일 수가 나온다.
            long calDateDays = calDate / (24 * 60 * 60 * 1000);

            if (calDateDays > +0) { //약속 날짜가 현재 날짜~현재 날짜+7 사이에 있을 때

                return "future";
            } else {
                return "past";
            }

        } catch (Exception e) {
            e.printStackTrace();
            return "false";
        }
    }



}