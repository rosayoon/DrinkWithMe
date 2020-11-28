package com.example.drinkwithme;
import android.annotation.SuppressLint;
import android.os.Message;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import static com.example.drinkwithme.application.alcohol_fridge;
import static com.example.drinkwithme.application.drinking_diary;

public class  MainFragment extends Fragment {
    RecyclerView recyclerView_schedule;
    LinearLayoutManager linearLayoutManager_schedule;
    AdapterMainDrinkingSchedule adapterMainDrinkingSchedule;
    ArrayList<Drinking_Schedule> scheduleInWeek;
    ArrayList<AlcoholRanking> alcohol_ranking;

    RecyclerView recyclerView_ranking;
    LinearLayoutManager linearLayoutManager_ranking;
    Adapter_AlcoholRanking adapter_alcoholRanking;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @SuppressLint("HandlerLeak")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_fragment_main, null);
        // Toast.makeText(getContext(), "frag: oncreateview", Toast.LENGTH_LONG).show();

        Button btn_add_drinking_main = view.findViewById(R.id.btn_add_drinking_main);


        recyclerView_schedule = view.findViewById(R.id.recyclerview_main);
        recyclerView_schedule.setHasFixedSize(true);

        linearLayoutManager_schedule = new LinearLayoutManager(getContext());
        recyclerView_schedule.setLayoutManager(linearLayoutManager_schedule);

        recyclerView_ranking = view.findViewById(R.id.recyclerview_main_ranking);
        recyclerView_ranking.setHasFixedSize(true);

        linearLayoutManager_ranking = new LinearLayoutManager(getContext());
        recyclerView_ranking.setLayoutManager(linearLayoutManager_ranking);


        initiateList();
         alcohol_ranking =initializeRankingList();
        adapterMainDrinkingSchedule = new AdapterMainDrinkingSchedule(scheduleInWeek, getContext());
        recyclerView_schedule.setAdapter(adapterMainDrinkingSchedule);

        adapter_alcoholRanking = new Adapter_AlcoholRanking(getContext(),alcohol_ranking);
        recyclerView_ranking.setAdapter(adapter_alcoholRanking);

        btn_add_drinking_main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mIntent = new Intent(getActivity(), AddDrinkingScheduleActivity.class);
                startActivity(mIntent);
            }
        });

        return view;
    }
    @SuppressLint("HandlerLeak")
    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) { // handler에서 사용하는 메세지 객체를 받아옵니다

        }
    };

    private void initiateList() {
        scheduleInWeek = new ArrayList<>();

        for (int i = 0; i < drinking_diary.size(); i++) { //날짜가 앞으로 일주일 안에 포함되면 리스트에 추가한다.
            Drinking_Schedule schedule = drinking_diary.get(i);
            Boolean inWeek = calDate(schedule.date, schedule.time);
            if (inWeek) {
                scheduleInWeek.add(schedule);
            }
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        initiateList();
        adapterMainDrinkingSchedule.upDateItemList(scheduleInWeek);
        initializeRankingList();
        adapter_alcoholRanking.upDateList(alcohol_ranking);

    }


    public Boolean calDate(String date, String time) { // 술 약속 객체의 날짜가 현재 날짜~현재날짜+7일 안에 포함되는지 안되는지
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

            if (calDateDays < 7 && calDateDays > 0) { //약속 날짜가 현재 날짜~현재 날짜+7 사이에 있을 때

                return true;
            } else if (calDateDays == 0) { //약속 날짜가 오늘일 때
                String getTodayTime = simpleTime.format(today_first); //"hh시 mm분" 형태로 변형

                Date ScheduleTime = simpleTime.parse(time);// 약속 시간을 parse() 통해 Date 형으로 변형
                Date todayTime = simpleTime.parse(getTodayTime);// 현재 시간을 parse() 통해 Date 형으로 변형

                long calTime = ScheduleTime.getTime() - todayTime.getTime();

                if (calTime >= 0) { // 약속시간이 현재 시간보다 늦을 때
                    return true;
                } else {
                    return false;
                }
            } else {
                return false;
            }

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static ArrayList initializeRankingList() {
        ArrayList<AlcoholRanking>alcohol_ranking = new ArrayList<>();

        for (int t = 0; t < alcohol_fridge.size(); t++) {
            int numberOfDrinking = 0;
            Alcohol drankAlcohol = alcohol_fridge.get(t);
            String keyOfAlcohol = drankAlcohol.key;
            for (int i = 0; i < drinking_diary.size(); i++) {
                Drinking_Schedule schedule = drinking_diary.get(i);
                if (schedule.alcohol_map_that_drank.containsKey(keyOfAlcohol)) { //저장되어있는 술 약속에서 이 술을 마셨다고 기록되어 있을 때
                    numberOfDrinking++;  //마신 횟수 +1

                }
            }
            int position = 0;
            for (int i = 0; i < alcohol_ranking.size(); i++) {
                AlcoholRanking ranking = alcohol_ranking.get(i);
                int rank = ranking.numberOfDrinking;
                if (numberOfDrinking > rank) {
                } else {
                    position++;
                }

            }
            alcohol_ranking.add(position, new AlcoholRanking(drankAlcohol.name, drankAlcohol.path, drankAlcohol.imgFile_name, numberOfDrinking))
            ;
        }
        return alcohol_ranking;

    }

    //public boolean calDrinkRate(int)
}