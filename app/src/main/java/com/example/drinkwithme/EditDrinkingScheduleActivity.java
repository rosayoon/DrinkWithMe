package com.example.drinkwithme;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.ClipData;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

import static com.example.drinkwithme.ImageManager.saveDrinkingPicsToInternalStorage;
import static com.example.drinkwithme.PreferenceManager.saveScheduleList;
import static com.example.drinkwithme.application.alcohol_fridge;
import static com.example.drinkwithme.application.drinking_diary;

public class EditDrinkingScheduleActivity extends AppCompatActivity {
    int y=0, m=0, d=0, h=0, mi=0;
    final Calendar c = Calendar.getInstance(); //캘린더 클래스 객체 생성
    int year = c.get(Calendar.YEAR);
    int month = c.get(Calendar.MONTH);
    int day  = c.get(Calendar.DAY_OF_MONTH);
    int hour = c.get(Calendar.HOUR);
    int minute = c.get(Calendar.MINUTE);
    TextView txt_date_add_drinking_schedule;
    TextView txt_time_add_drinking_schedule;
    Button btn_date_add_drinking_schedule;
    Button btn_time_add_drinking_schedule;
    private DatePickerDialog.OnDateSetListener callbackDateMethod;
    private TimePickerDialog.OnTimeSetListener callbackTimeMethod;
    private static final String TAG = "AddDrinkingSchedule";
    private final int GET_GALLERY_IMAGE =200;
     private final int requestCode_alcohol =100;
    ArrayList<DrinkingPic> DrinkingGallery ;



    RecyclerView recyclerView;
    LinearLayoutManager linearLayoutManager;
    Adapter_pictures adapter_pictures;

    RecyclerView recyclerView_selected_drinks;
    LinearLayoutManager linearLayoutManager_selected_drinks;
    Adapter_selectedDrinks adapter_selectedDrinks;

    ArrayList<String> key_of_alcohol_list;
    ArrayList<SelectedDrinks> SelectedDrinksList;
    HashMap<String,String> alcohol_map;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_drinking_schedule);


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


        SelectedDrinksList = new ArrayList<>();
        alcohol_map = new HashMap<String, String>();



        //edittext
        EditText edittext_location_add_drinking_schedule = findViewById(R.id.edittext_location_add_drinking_schedule);
        EditText edittext_title_add_drinking_schedule = findViewById(R.id.edittext_title_add_drinking_schedule);
        EditText edittext_who_add_drinking_schedule = findViewById(R.id.edittext_who_add_drinking_schedule);

        recyclerView = findViewById(R.id.recyclerview_pics_add_drinking_schedule);
        recyclerView.setHasFixedSize(true);

        linearLayoutManager = new LinearLayoutManager(this,RecyclerView.HORIZONTAL,false);
        recyclerView.setLayoutManager(linearLayoutManager);

        recyclerView_selected_drinks = findViewById(R.id.recyclerview_drinking_schedule);
        recyclerView_selected_drinks.setHasFixedSize(true);

        linearLayoutManager_selected_drinks = new LinearLayoutManager(this,RecyclerView.HORIZONTAL,false);
        recyclerView_selected_drinks.setLayoutManager(linearLayoutManager_selected_drinks);




        this.InitializeDateView(); //날짜 정보를 표시할 텍스트 뷰 객체
        this.InitializeDateListener(); //날짜 정보를 위 텍스트 뷰에 표시하기

        this.InitializeTimeView(); //시간 정보를 표시할 텍스트 뷰 객체
        this.InitializeTimeListener(); // 시간 정보를 위 텍스트 뷰에 표시하기

        Intent intent = getIntent();
        int position = intent.getIntExtra("position",-1);
        Toast.makeText(getApplicationContext(),"포지션" +position,Toast.LENGTH_LONG).show();



        //recyclerview에서 받은 postition값으로 각각 텍스트 뷰에 값 넣기
        Drinking_Schedule schedule =drinking_diary.get(position); //intent로 받은 position 값을 통해 얻은 약속  객체
        edittext_title_add_drinking_schedule.setText(schedule.getTitle());
        txt_date_add_drinking_schedule.setText(schedule.getDate());
        txt_time_add_drinking_schedule.setText(schedule.getTime());
        edittext_location_add_drinking_schedule.setText(schedule.getLocation());
        edittext_who_add_drinking_schedule.setText(schedule.getWho());
        DrinkingGallery =schedule.DrinkingGallery;

        alcohol_map =schedule.alcohol_map_that_drank;
        key_of_alcohol_list = new ArrayList<>(alcohol_map.keySet());
        for(int t =0; t<key_of_alcohol_list.size(); t++) { //리스트에 선택된 술 넣기
            for (int i = 0; i < alcohol_fridge.size(); i++) {
                if (key_of_alcohol_list.get(t).equals(alcohol_fridge.get(i).key)){
                    Alcohol alcohol = alcohol_fridge.get(i);
                    SelectedDrinksList.add(new SelectedDrinks(alcohol.name,alcohol.key,alcohol.path,alcohol.imgFile_name,alcohol_map.get(alcohol.key)));
                }
            }
        }


        adapter_pictures = new Adapter_pictures(DrinkingGallery,this);
        recyclerView.setAdapter(adapter_pictures);

        adapter_selectedDrinks = new Adapter_selectedDrinks(SelectedDrinksList,this);
        recyclerView_selected_drinks.setAdapter(adapter_selectedDrinks);





        btn_select_alcohol_add_drinking_schedule.setOnClickListener(new View.OnClickListener() { //'술 선택하기'버튼을 누르면 등록된 술들이 있는 창으로 이동
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(getApplicationContext(), SelectDrinksActivity.class),requestCode_alcohol);
            }
        });

        btn_back_add_drinking_plan.setOnClickListener(new View.OnClickListener() { //'뒤로가기' 버튼
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        btn_save_add_drinking_schedule.setOnClickListener(new View.OnClickListener() { //술 약속 수정하기, 이미 생성된 객체에서 각각의 변수를 하나씩 다시 설정함
            @Override
            public void onClick(View v) {
                Drinking_Schedule schedule =drinking_diary.get(position); //intent로 받은 position 값을 통해 얻은 약속  객체
                String title = edittext_title_add_drinking_schedule.getText().toString();
                String date = txt_date_add_drinking_schedule.getText().toString();
                String time = txt_time_add_drinking_schedule.getText().toString();
                String location = edittext_location_add_drinking_schedule.getText().toString();
                String who  = edittext_who_add_drinking_schedule.getText().toString();



                for(int i =0; i<SelectedDrinksList.size(); i++){
                    String key = SelectedDrinksList.get(i).key;
                    String amount = SelectedDrinksList.get(i).amount;
                    alcohol_map.put(key, amount);
                }

                if(title.isEmpty()|| location.isEmpty()||who.isEmpty()||date.isEmpty()||time.isEmpty()){
                    btn_save_add_drinking_schedule.setEnabled(false);
                }
                for(int i =0; i<SelectedDrinksList.size(); i++){
                    String key = SelectedDrinksList.get(i).key;
                    String amount = SelectedDrinksList.get(i).amount;
                    alcohol_map.put(key, amount);
                    Toast.makeText(getApplicationContext(), "key"+key+" amount:"+amount,Toast.LENGTH_LONG).show();
                }

                if(title.isEmpty()|| location.isEmpty()||who.isEmpty()||date.isEmpty()||time.isEmpty()){
                    btn_save_add_drinking_schedule.setEnabled(false);
                }
                schedule.title =title;
                schedule.date = date;
                schedule.time = time;
                schedule.location = location;
                schedule.who = who;
                schedule.alcohol_map_that_drank = alcohol_map;
                schedule.DrinkingGallery =DrinkingGallery;
                saveScheduleList(getApplicationContext());
                finish();
            }
        });

        btn_select_alcohol_add_drinking_schedule.setOnClickListener(new View.OnClickListener() { //'술 선택하기'버튼을 누르면 등록된 술들이 있는 창으로 이동
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(getApplicationContext(), SelectDrinksActivity.class),requestCode_alcohol);
            }
        });

        imgbtn_camera_add_drinking_schedule.setOnClickListener(new View.OnClickListener() { //사진 저장하기 버튼
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


    public void InitializeDateView() // 사용자가 선택한 날짜 정보를 표시할 TextView
    {
        txt_date_add_drinking_schedule = (TextView)findViewById(R.id.txt_date_add_drinking_schedule);
    }

    public void InitializeTimeView(){// 사용자가 선택한 시간 정보를 표시할 TextView
        txt_time_add_drinking_schedule = (TextView)findViewById(R.id.txt_time_add_drinking_schedule);

    }

    public void InitializeDateListener() //setText를 통해서 TextView에 사용자가 지정한 날짜 정보를 표시
    {
        callbackDateMethod = new DatePickerDialog.OnDateSetListener() //
        {@Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth)
            { txt_date_add_drinking_schedule.setText(year + "년" + (monthOfYear+1) + "월" + dayOfMonth + "일"); }
        };
    }

    public void InitializeTimeListener(){
        callbackTimeMethod = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                txt_time_add_drinking_schedule.setText(hourOfDay+"시 "+minute +"분 ");
            }
        };
    }

    public void OnClickDateHandler(View view)
    {
        DatePickerDialog dialog = new DatePickerDialog(this, callbackDateMethod, year, month, day);

        dialog.show();
    }
    public void onClickTimeHandler(View view)
    {
        TimePickerDialog dialog2 = new TimePickerDialog(this, callbackTimeMethod, hour, minute, true);

        dialog2.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode ==GET_GALLERY_IMAGE) //이미지 다중 선택하는 것
        {
            if(resultCode ==RESULT_OK) {
                if (data.getClipData() == null) {
                } else {
                    ClipData clipData = data.getClipData();
                    if (clipData.getItemCount() > 3) { //사진은 3장을 초과해서는 안된다.
                        Toast.makeText(getApplicationContext(), "사진은 3개 까지 선택가능합니다.", Toast.LENGTH_LONG).show();
                    }
                    else if (clipData.getItemCount() == 1) { //사진을 1개 선택했을 때
                        Uri selectedImage = clipData.getItemAt(0).getUri(); //이미지 URI
                        try {
                            Bitmap bitmap =  MediaStore.Images.Media.getBitmap(getContentResolver(), selectedImage);
                            long time =System.currentTimeMillis();
                            String imgFile_name = Long.toString(time); // 이미지 이름이 중복되지 않기 위해 이름을 현재 시간으로 설정
                            String path = saveDrinkingPicsToInternalStorage(getApplicationContext(),bitmap, imgFile_name);
                            DrinkingGallery.add(new DrinkingPic(imgFile_name, path));
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    }
                    else if (clipData.getItemCount() < 3 && clipData.getItemCount() > 1) { //사진을 1개 이상 선택했을 때
                        for (int i = 0; i < clipData.getItemCount(); i++) {
                            Uri selectedImage = clipData.getItemAt(i).getUri();
                            try {
                                Bitmap bitmap =  MediaStore.Images.Media.getBitmap(getContentResolver(), selectedImage);
                                long time =System.currentTimeMillis();
                                String imgFile_name = Long.toString(time); // 이미지 이름이 중복되지 않기 위해 이름을 현재 시간으로 설정
                                String path = saveDrinkingPicsToInternalStorage(getApplicationContext(),bitmap, imgFile_name);
                                DrinkingGallery.add(new DrinkingPic(imgFile_name, path));
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }

                }
            }
        }
        else if(requestCode ==requestCode_alcohol){ //술 선택하기에서 술을 선택해서 술 리스트에서 그 술의 포지션 값을 가져오는 거
            if(resultCode ==RESULT_OK)
            {int mposition = data.getIntExtra("alcohol_position", -1);
                Alcohol drankAlcohol;
                 drankAlcohol = alcohol_fridge.get(mposition);

                String alcohol_amount = data.getStringExtra("alcohol_amount"); //마신 양
                String key = drankAlcohol.key; //키값을 가진 리스트
                String name = drankAlcohol.name; //이름
                String path = drankAlcohol.path; // 이미지 파일 저장된 곳
                String img_file = drankAlcohol.imgFile_name; //이미지 파일 이름


                int has = 0;
                for(int i =0; i <SelectedDrinksList.size(); i++){ // 같은 술의 객체가 있으면 그 객체의 양 값만 수정한다.
                    if(key.equals(SelectedDrinksList.get(i).key)){
                        SelectedDrinksList.get(i).amount = alcohol_amount;
                        has++;
                        Toast.makeText(getApplicationContext(), "key가 같음",Toast.LENGTH_LONG).show();
                        break;
                    }
                }
                if(has==0){
                    SelectedDrinksList.add(new SelectedDrinks(name,key,path, img_file, alcohol_amount)); // 같은 술 객체가 없으면 새로운 객체 추가
                    Toast.makeText(getApplicationContext(), "selected list 추가",Toast.LENGTH_LONG).show();

                }
                adapter_selectedDrinks.notifyDataSetChanged();
            }
        }
    }



}