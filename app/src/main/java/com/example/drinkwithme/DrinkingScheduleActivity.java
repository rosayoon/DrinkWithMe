package com.example.drinkwithme;
import android.annotation.SuppressLint;
import android.os.Message;
import android.os.Handler;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import java.text.SimpleDateFormat;
import java.util.Date;

import static com.example.drinkwithme.PreferenceManager.loadScheduleData;
import static com.example.drinkwithme.application.drinking_diary;

public class DrinkingScheduleActivity extends AppCompatActivity {




   // @Override
  //  protected void onCreate(Bundle savedInstanceState) {
     //   super.onCreate(savedInstanceState);
 //    setContentView(R.layout.activity_drinking_schedule);
 //
 //    ImageButton btn_add_schedule_drinking_schedule = findViewById(R.id.btn_add_schedule_drinking_schedule);
 //    Button btn_future_drinking_schedule = findViewById(R.id.btn_future_drinking_schedule);
 //    Button btn_past_drinking_schedule = findViewById(R.id.btn_past_drinking_schedule);

 //    recyclerview_drinking_schedule = findViewById(R.id.recyclerview_drinking_schedule);
 //    recyclerview_drinking_schedule.setHasFixedSize(true);

 //    layoutManager = new LinearLayoutManager(this);
 //    recyclerview_drinking_schedule.setLayoutManager(layoutManager);

 //    adapterDrinkingSchedule = new Adapter_Drinking_Schedule(this, drinking_diary);

 //    recyclerview_drinking_schedule.setAdapter(adapterDrinkingSchedule);


 //    btn_past_drinking_schedule.setOnClickListener(new View.OnClickListener() {
 //    @Override
 //    public void onClick(View v) {

 //    }
 //  });
 //
 //}
 //protected void initialized(){
 //

 //}


 //protected void onResume() {
 //    super.onResume();
 //    //initialized();

 //    adapterDrinkingSchedule.upDateItemList(drinking_diary);

 //}
 //public String calDate(String date, String time) { // 술 약속 객체의 날짜가 현재 날짜~현재날짜+7일 안에 포함되는지 안되는지
 //    long now = System.currentTimeMillis(); //현재 시간
 //    SimpleDateFormat simpleDate = new SimpleDateFormat("yyyy년 MM월 dd일");
 //    SimpleDateFormat simpleTime = new SimpleDateFormat("hh시 mm분");
 //    Date today_first = new Date(now);
 //    String getTodayDate = simpleDate.format(today_first); //"yyyy년 MM월 dd일" 형태로 변형


 //    try { //String Type을 Date Type으로 캐스팅하면서 생기는 예외처리(안해주면 컴파일 실패)
 //        Date ScheduleDate = simpleDate.parse(date); //String date를 parse()를 통해 Date형으로 변환
 //        Date todayDate = simpleDate.parse(getTodayDate); //오늘 날짜 String date르 parse()를 통해 Date형으로 변환

 //        //Date로 변환된 두 날짜를 계산한 뒤 그 리턴 값으로 long type 변수를 초기화하고 있다.
 //        //연산결과 -950400000. long type으로 return된다.
 //        long calDate = ScheduleDate.getTime() - todayDate.getTime();

 //        //Date.getTime() 은 해당날짜를 기준으로 1970년 00:00:00부터 몇 초가 흘렀는지를 반환해준다.
 //        //이제 24*60*60*1000(각 시간값에 따른 차이점)을 나눠주면 일 수가 나온다.
 //        long calDateDays = calDate / (24 * 60 * 60 * 1000);

 //        if (calDateDays >+ 0) { //약속 날짜가 현재 날짜~현재 날짜+7 사이에 있을 때

 //            return "future";
 //        }
 //         else {
 //            return "past";
 //        }

 //    } catch (Exception e) {
 //        e.printStackTrace();
 //        return "false";
 //    }
 //}


}