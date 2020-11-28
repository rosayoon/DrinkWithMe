package com.example.drinkwithme;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;


import com.google.gson.JsonParser;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

public class YouTubeAcyivity extends AppCompatActivity {
    RecyclerView recyclerView;
    GridLayoutManager gridLayoutManager;
    Adapter_YouTube adapter_youTube;

    private ProgressDialog progressDialog;

    private String serverKey = "AIzaSyDwNlcFjVstu2-mHSScOyhnA538gEYXu2U";

    AsyncTask<?, ?, ?> searchTask;
    ArrayList<SearchData> sdata = new ArrayList<SearchData>();
    String content;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_youtube_acyivity);

        TextView textView = findViewById(R.id.network);
        //try {
        //    content ="술 추천";
        //    paringJsonData(getUtube(content));
        //} catch (JSONException e) {
        //    e.printStackTrace();
        //}
        //initialize();


        findViewById(R.id.search_about_drinks_youtube).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              int status =NetworkStatus.getConnectivityStatus(getApplicationContext());
              String about_network;
                if(status == NetworkStatus.TYPE_MOBILE){
                    about_network= "인터넷 연결";
                }else if (status == NetworkStatus.TYPE_WIFI){
                    about_network="인터넷 연결";
                }else {
                    about_network ="인터넷 연결 안됨.";
                }
                content="술추천";
              searchTask = new searchTask().execute();

            }
        });
        findViewById(R.id.search_about_snack_youtube).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int status =NetworkStatus.getConnectivityStatus(getApplicationContext());
                String about_network;
                if(status == NetworkStatus.TYPE_MOBILE){
                    about_network= "인터넷 연결";
                }else if (status == NetworkStatus.TYPE_WIFI){
                    about_network="인터넷 연결";
                }else {
                    about_network ="인터넷 연결 안됨.";
                }
                content="술안주요리";
                searchTask = new searchTask().execute();

            }
        });
        findViewById(R.id.search_about_youtuber).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int status =NetworkStatus.getConnectivityStatus(getApplicationContext());
                String about_network;
                if(status == NetworkStatus.TYPE_MOBILE){
                    about_network= "인터넷 연결";
                }else if (status == NetworkStatus.TYPE_WIFI){
                    about_network="인터넷 연결";
                }else {
                    about_network ="인터넷 연결 안됨.";
                }
                content="주류학개론";
                searchTask = new searchTask().execute();

            }
        });

    }


    private class searchTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected Void doInBackground(Void... params) {

            try {
                paringJsonData(getUtube(content));
            } catch (JSONException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            initialize();

        }
    }
    public void initialize(){
        recyclerView = findViewById(R.id.recyclerview_YouTube_List);
        recyclerView.setHasFixedSize(true);

        gridLayoutManager = new GridLayoutManager(getApplicationContext(),2);
        recyclerView.setLayoutManager(gridLayoutManager);

        adapter_youTube = new Adapter_YouTube(getApplicationContext(),sdata);
        recyclerView.setAdapter(adapter_youTube);
    }

    public JSONObject getUtube(String content) {


        StringBuilder stringBuilder=null;

            HttpGet httpGet = new HttpGet(  //검색된 키워드를 검색해서 나온 결과들
                    "https://www.googleapis.com/youtube/v3/search?"
                            + "part=snippet&q=" + content
                            + "&key=" + serverKey + "&maxResults=10");  //EditText에 입력된 값으로 겁색을 합니다.
            // part(snippet),  q(검색값) , key(서버키)
            HttpClient client = new DefaultHttpClient();
            HttpResponse response;
             stringBuilder = new StringBuilder();

        try {
            response = client.execute(httpGet);
            HttpEntity entity = response.getEntity();
            InputStream stream = entity.getContent();
            int b;
            while ((b = stream.read()) != -1) {
                //If no byte is available because the stream is at the
                //     * end of the file, the value <code>-1</code> is returned;
                stringBuilder.append((char) b);
            }
        } catch (ClientProtocolException e) {
            Toast.makeText(getApplicationContext(), "오류: ClientProtocolEsception",Toast.LENGTH_LONG).show();
            } catch (IOException e) {
            Toast.makeText(getApplicationContext(), "오류: IOException",Toast.LENGTH_LONG).show();
            }


        JSONObject jsonObject = new JSONObject();
        //JSON 코드가 우선 JSONoBJECT로 만들어져 있기 때문에 전체코드를 JSONOBJECT로 받음음
        try {
            jsonObject = new JSONObject(stringBuilder.toString());
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return jsonObject;
    }

    //파싱을 하면 여러가지 값을 얻을 수 있는데 필요한 값들을 세팅하셔서 사용하시면 됩니다.
    private void paringJsonData(JSONObject jsonObject) throws JSONException {
       sdata.clear();
       if(jsonObject !=null) {
           JSONArray contacts = jsonObject.getJSONArray("items");
           //JSONArray는 JSONObject가 들어가는 배열

           for (int i = 0; i < contacts.length(); i++) {
               JSONObject c = contacts.getJSONObject(i);
               String kind = c.getJSONObject("id").optString("kind"); // 종류를 체크하여 playlist도 저장
               if (kind.equals("youtube#video")) {
                   vodid = c.getJSONObject("id").optString("videoId"); // 유튜브
                   // 동영상아이디값. 재생시 필요.
               } else {
                   vodid = c.getJSONObject("id").optString("playlistId"); // 유튜브
               }

               String title = c.getJSONObject("snippet").optString("title"); //유튜브 제목을 받아옵니다
               String changString = "";
               try {
                   changString = new String(title.getBytes("8859_1"), "utf-8"); //한글이 깨져서 인코딩 해주었습니다
               } catch (UnsupportedEncodingException e) {
                   // TODO Auto-generated catch block
                   e.printStackTrace();
               }

               String date = c.getJSONObject("snippet").optString("publishedAt") //등록날짜
                       .substring(0, 10);
               String imgUrl = c.getJSONObject("snippet").getJSONObject("thumbnails")
                       .getJSONObject("default").optString("url");  //썸내일 이미지 URL값

               sdata.add(new SearchData(vodid, changString, imgUrl, date));
           }
       }
       else{
           Toast.makeText(getApplicationContext(), "검색결과가 없습니다.",Toast.LENGTH_LONG).show();
       }

    }

    String vodid = "";

    private void createThreadAndDialog(){
        progressDialog = new ProgressDialog(YouTubeAcyivity.this);
        progressDialog.show();
        Thread thread = new Thread(new Runnable(){

            @Override
            public void run() {
                while (getUtube(content) == null) {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                }
                mHandler.sendEmptyMessage(0);
            }
        });

        thread.start();
    }
    @SuppressLint("HandlerLeak")
    Handler mHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) { // handler에서 사용하는 메세지 객체를 받아옵니다
            progressDialog.dismiss();
        }
    };




}