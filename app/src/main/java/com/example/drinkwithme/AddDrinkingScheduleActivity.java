package com.example.drinkwithme;

import android.annotation.SuppressLint;
import android.content.ClipData;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

import static com.example.drinkwithme.ImageManager.saveDrinkingPicsToInternalStorage;
import static com.example.drinkwithme.PreferenceManager.loadScheduleData;
import static com.example.drinkwithme.PreferenceManager.saveScheduleList;
import static com.example.drinkwithme.application.alcohol_fridge;
import static com.example.drinkwithme.application.drinking_diary;

public class AddDrinkingScheduleActivity extends AppCompatActivity {
    int y = 0, m = 0, d = 0, h = 0, mi = 0;
    final Calendar c = Calendar.getInstance(); //캘린더 클래스 객체 생성
    int year = c.get(Calendar.YEAR);
    int month = c.get(Calendar.MONTH);
    int day = c.get(Calendar.DAY_OF_MONTH);
    int hour = c.get(Calendar.HOUR);
    int minute = c.get(Calendar.MINUTE);
    TextView txt_date_add_drinking_schedule;
    TextView txt_time_add_drinking_schedule;
    Button btn_date_add_drinking_schedule;
    Button btn_time_add_drinking_schedule;
    private DatePickerDialog.OnDateSetListener callbackDateMethod;
    private TimePickerDialog.OnTimeSetListener callbackTimeMethod;
    private static final String TAG = "AddDrinkingSchedule";
    private final int GET_GALLERY_IMAGE = 200;
    int requestCode_alcohol = 100;
    ArrayList<DrinkingPic> DrinkingGallery;
    HashMap<String, String> alcohol_map;

    RecyclerView recyclerView_pics;
    LinearLayoutManager linearLayoutManager_pics;
    Adapter_pictures adapter_pictures;

    RecyclerView recyclerView_selected_drinks;
    LinearLayoutManager linearLayoutManager_selected_drinks;
    Adapter_selectedDrinks adapter_selectedDrinks;

    ArrayList<String> key_of_alcohol_list;
    ArrayList<SelectedDrinks> SelectedDrinksList;

    EditText edittext_title_add_drinking_schedule;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_drinking_schedule);
        loadScheduleData(getApplicationContext());


        //ImageButton
        ImageButton btn_back_add_drinking_plan = findViewById(R.id.btn_back_add_drinking_plan); //뒤로가기 버튼
        ImageButton imgbtn_camera_add_drinking_schedule = findViewById(R.id.imgbtn_camera_add_drinking_schedule); // 약속 추가에서 카메라로 이동하는 버튼

        //Button
        btn_date_add_drinking_schedule = findViewById(R.id.btn_date_add_drinking_schedule);
        btn_time_add_drinking_schedule = findViewById(R.id.btn_time_add_drinking_schedule);
        Button btn_save_add_drinking_schedule = findViewById(R.id.btn_save_add_drinking_schedule);
        Button btn_select_alcohol_add_drinking_schedule = findViewById(R.id.btn_select_alcohol_add_drinking_schedule);

        //TEXT
        txt_date_add_drinking_schedule = findViewById(R.id.txt_date_add_drinking_schedule);
        txt_time_add_drinking_schedule = findViewById(R.id.txt_time_add_drinking_schedule);

        //ImageView

        key_of_alcohol_list = new ArrayList<>();
        SelectedDrinksList = new ArrayList<>();
        alcohol_map = new HashMap<String, String>();
        DrinkingGallery = new ArrayList<DrinkingPic>();


        //edittext
        EditText edittext_location_add_drinking_schedule = findViewById(R.id.edittext_location_add_drinking_schedule);
        edittext_title_add_drinking_schedule = findViewById(R.id.edittext_title_add_drinking_schedule);
        EditText edittext_who_add_drinking_schedule = findViewById(R.id.edittext_who_add_drinking_schedule);

        this.InitializeDateView(); //날짜 정보를 표시할 텍스트 뷰 객체
        this.InitializeDateListener(); //날짜 정보를 위 텍스트 뷰에 표시하기

        this.InitializeTimeView(); //시간 정보를 표시할 텍스트 뷰 객체
        this.InitializeTimeListener(); // 시간 정보를 위 텍스트 뷰에 표시하기

        recyclerView_pics = findViewById(R.id.recyclerview_pics_add_drinking_schedule);
        recyclerView_pics.setHasFixedSize(true);

        linearLayoutManager_pics = new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false);
        recyclerView_pics.setLayoutManager(linearLayoutManager_pics);

        adapter_pictures = new Adapter_pictures(DrinkingGallery, this);
        recyclerView_pics.setAdapter(adapter_pictures);

        recyclerView_selected_drinks = findViewById(R.id.recyclerview_drinking_schedule);
        recyclerView_selected_drinks.setHasFixedSize(true);

        linearLayoutManager_selected_drinks = new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false);
        recyclerView_selected_drinks.setLayoutManager(linearLayoutManager_selected_drinks);

        adapter_selectedDrinks = new Adapter_selectedDrinks(SelectedDrinksList, this);
        recyclerView_selected_drinks.setAdapter(adapter_selectedDrinks);


        btn_select_alcohol_add_drinking_schedule.setOnClickListener(new View.OnClickListener() { //'술 선택하기'버튼을 누르면 등록된 술들이 있는 창으로 이동
            @Override
            public void onClick(View v) {
                Intent intent_1 = new Intent(Intent.ACTION_OPEN_DOCUMENT);
                intent_1.addCategory(Intent.CATEGORY_OPENABLE);
                startActivityForResult(new Intent(getApplicationContext(), SelectDrinksActivity.class), requestCode_alcohol);
            }
        });

        btn_back_add_drinking_plan.setOnClickListener(new View.OnClickListener() { //'뒤로가기' 버튼
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        btn_save_add_drinking_schedule.setOnClickListener(new View.OnClickListener() { //술 약속 저장하기 버튼
            @Override
            public void onClick(View v) {
                String title = edittext_title_add_drinking_schedule.getText().toString();
                String date = txt_date_add_drinking_schedule.getText().toString();
                String time = txt_time_add_drinking_schedule.getText().toString();
                String location = edittext_location_add_drinking_schedule.getText().toString();
                String who = edittext_who_add_drinking_schedule.getText().toString();

                for (int i = 0; i < SelectedDrinksList.size(); i++) {
                    String key = SelectedDrinksList.get(i).key;
                    String amount = SelectedDrinksList.get(i).amount;
                    alcohol_map.put(key, amount);
                }

                if (title.isEmpty()  || date.isEmpty() || time.isEmpty()) {
                    btn_save_add_drinking_schedule.setPressed(false);
                   if(title.isEmpty()){
                       Toast.makeText(getApplicationContext(), "제목을 입력해주세요",Toast.LENGTH_LONG).show();
                   }
                    else if(date.isEmpty()){
                        Toast.makeText(getApplicationContext(), "날짜를 입력해주세요",Toast.LENGTH_LONG).show();
                    }
                   else if(time.isEmpty()){
                       Toast.makeText(getApplicationContext(), "시간을 입력해주세요",Toast.LENGTH_LONG).show();
                   }

                }
                else {
                    int index = drinking_diary.size();
                    int position = calDate(date, time); //날짜를 계산해서
                    drinking_diary.add(position, new Drinking_Schedule(title, date, time, location, who, alcohol_map, DrinkingGallery)); //빠른순으로 나열하기
                    Intent intent = new Intent(getApplicationContext(), Drinking_Schedule.class);
                    intent.putExtra("index", index);
                    saveScheduleList(getApplicationContext());
                    finish();
                }
            }
        });


        imgbtn_camera_add_drinking_schedule.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("IntentReset")
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK); //버튼을 누르면 해당 Intent 호출
                intent.setType("image/*");
                intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
                intent.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI); //데이터를 uri로 받는다.
                startActivityForResult(intent, GET_GALLERY_IMAGE);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter_pictures.notifyDataSetChanged();
    }


    public void InitializeDateView() // 사용자가 선택한 날짜 정보를 표시할 TextView
    {
        txt_date_add_drinking_schedule = (TextView) findViewById(R.id.txt_date_add_drinking_schedule);
    }

    public void InitializeTimeView() {// 사용자가 선택한 시간 정보를 표시할 TextView
        txt_time_add_drinking_schedule = (TextView) findViewById(R.id.txt_time_add_drinking_schedule);

    }

    public void InitializeDateListener() //setText를 통해서 TextView에 사용자가 지정한 날짜 정보를 표시
    {
        callbackDateMethod = new DatePickerDialog.OnDateSetListener() //
        {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                txt_date_add_drinking_schedule.setText(year + "년 " + (monthOfYear + 1) + "월 " + dayOfMonth + "일");
            }
        };
    }

    public void InitializeTimeListener() {
        callbackTimeMethod = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                txt_time_add_drinking_schedule.setText(hourOfDay + "시 " + minute + "분");
            }
        };
    }

    public void OnClickDateHandler(View view) {
        DatePickerDialog dialog = new DatePickerDialog(this, callbackDateMethod, year, month, day);

        dialog.show();
    }

    public void onClickTimeHandler(View view) {
        TimePickerDialog dialog2 = new TimePickerDialog(this, callbackTimeMethod, hour, minute, true);

        dialog2.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == GET_GALLERY_IMAGE) //이미지 다중 선택하는 것
        {
            if (resultCode == RESULT_OK) {
                if (data.getClipData() == null) {
                } else {
                    ClipData clipData = data.getClipData();
                    if (clipData.getItemCount() > 3) { //사진은 3장을 초과해서는 안된다.
                        Toast.makeText(getApplicationContext(), "사진은 3개 까지 선택가능합니다.", Toast.LENGTH_LONG).show();
                    } else if (clipData.getItemCount() == 1) { //사진을 1개 선택했을 때
                        Uri selectedImage = clipData.getItemAt(0).getUri(); //이미지 URI
                        try {
                            Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), selectedImage);
                            long time = System.currentTimeMillis();
                            String imgFile_name = Long.toString(time); // 이미지 이름이 중복되지 않기 위해 이름을 현재 시간으로 설정
                            String path = saveDrinkingPicsToInternalStorage(getApplicationContext(), bitmap, imgFile_name);
                            DrinkingGallery.add(new DrinkingPic(imgFile_name, path));
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    } else if (clipData.getItemCount() < 3 && clipData.getItemCount() > 1) { //사진을 1개 이상 선택했을 때
                        for (int i = 0; i < clipData.getItemCount(); i++) {
                            Uri selectedImage = clipData.getItemAt(i).getUri();
                            try {
                                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), selectedImage);
                                long time = System.currentTimeMillis();
                                String imgFile_name = Long.toString(time); // 이미지 이름이 중복되지 않기 위해 이름을 현재 시간으로 설정
                                String path = saveDrinkingPicsToInternalStorage(getApplicationContext(), bitmap, imgFile_name);
                                DrinkingGallery.add(new DrinkingPic(imgFile_name, path));
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                        DrinkingGallery = DrinkingGallery;
                    }

                }
            }
        } else if (requestCode == requestCode_alcohol) { //술 선택하기에서 술을 선택해서 술 리스트에서 그 술의 포지션 값을 가져오는 거
            if (resultCode == RESULT_OK) {
                int mposition = data.getIntExtra("alcohol_position", -1);
                Alcohol drankAlcohol = alcohol_fridge.get(mposition);

                String alcohol_amount = data.getStringExtra("alcohol_amount"); //마신 양
                String key = drankAlcohol.key; //키값을 가진 리스트
                String name = drankAlcohol.name; //이름
                String path = drankAlcohol.path; // 이미지 파일 저장된 곳
                String img_file = drankAlcohol.imgFile_name; //이미지 파일 이름


                int has = 0;
                for (int i = 0; i < SelectedDrinksList.size(); i++) { // 같은 술의 객체가 있으면 그 객체의 양 값만 수정한다.
                    if (key.equals(SelectedDrinksList.get(i).key)) {
                        SelectedDrinksList.get(i).amount = alcohol_amount;
                        has++;

                        break;
                    }
                }
                if (has == 0) {
                    SelectedDrinksList.add(new SelectedDrinks(name, key, path, img_file, alcohol_amount)); // 같은 술 객체가 없으면 새로운 객체 추가


                }
                adapter_selectedDrinks.notifyDataSetChanged();
            }
        }
    }

    public int calDate(String date, String time) { // 술 약속 객체의 날짜가 현재 날짜~현재날짜+7일 안에 포함되는지 안되는지
        SimpleDateFormat simpleDate = new SimpleDateFormat("yyyy년 MM월 dd일hh시 mm분");

        String new_schedule_date = date + time;
        int position = 0;
        for (int i = 0; i < drinking_diary.size(); i++) {
            String schedule_date = drinking_diary.get(i).date + drinking_diary.get(i).time;
            try {
                Date getNewScheduleDate = simpleDate.parse(new_schedule_date);//새로 등록할 String date르 parse()를 통해 Date형으로 변환
                Date getScheduleDate = simpleDate.parse(schedule_date); //저장되어있는 술약속 날짜 String date르 parse()를 통해 Date형으로 변환
                long calDate = getNewScheduleDate.getTime() - getScheduleDate.getTime();
                if (calDate >= 0) {
                    position++;
                } else {
                    break;
                }
            } catch (ParseException e) {
                e.printStackTrace();

            }
        }
        return position;
    }
}