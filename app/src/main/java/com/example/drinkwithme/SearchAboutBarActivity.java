package com.example.drinkwithme;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.github.mikephil.charting.data.BarData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class SearchAboutBarActivity extends AppCompatActivity {
    String clientId = "3uTEm5Qit8YzWg6WAoxe";
    String clientSecret = "sZTReWZyMg";
    EditText et_naver;
    ArrayList<SearchBar> barRecommendations = new ArrayList<SearchBar>();
    AsyncTask<?, ?, ?> searchTask;

    RecyclerView recyclerView;
    LinearLayoutManager linearLayoutManager;
    Adapter_Naver adapter_naver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_about_bar);
        et_naver = findViewById(R.id.et_search_bar_search_about_bar);

        findViewById(R.id.search_bar_search_about_bar).setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              searchTask = new searchTask().execute();
          }
        });
        findViewById(R.id.btn_back_search_about_bar).setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              finish();
          }
        });
    }

    public static StringBuilder stringBuilder;//

    static String getString(String input, String data) // API에서 필요한 문자 자르기.
    {
        String[] dataSplit = data.split("{" + input + "}");
        String[] dataSplit2 = dataSplit[1].split("\"" + input + "\"");
        return dataSplit2[0];
    }


    private class searchTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
        @Override
        protected Void doInBackground(Void... voids) {
            try {
                paringJsonData(getBar());
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            recyclerView = findViewById(R.id.recyclerview_Naver_List);
            recyclerView.setHasFixedSize(true);


            linearLayoutManager = new LinearLayoutManager(getApplicationContext());
            recyclerView.setLayoutManager(linearLayoutManager);

            adapter_naver = new Adapter_Naver(getApplicationContext(),barRecommendations);
            recyclerView.setAdapter(adapter_naver);
        }
    }

    public JSONObject getBar() {
        try {
            int display = 20;
            String text = et_naver.getText().toString() + "술집";
            String apiURL = "https://openapi.naver.com/v1/search/local?query=" + text + "&display=" + display + "&";
            URL url = new URL(apiURL);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            con.setRequestProperty("X-Naver-Client-Id", clientId);
            con.setRequestProperty("X-Naver-Client-Secret", clientSecret);
            int responseCode = con.getResponseCode();
            int b;
            BufferedReader br;
            if (responseCode == 200) {
                br = new BufferedReader(new InputStreamReader(con.getInputStream(),"UTF-8"));
            } else {
                br = new BufferedReader(new InputStreamReader(con.getErrorStream(),"UTF-8"));
            }
            stringBuilder = new StringBuilder();
            String line;

            while ((line = br.readLine()) != null) {
                stringBuilder.append(line + "\n");
            }

            br.close();
            con.disconnect();
        } catch (IOException malformedURLException) {
            malformedURLException.printStackTrace();
        }
        JSONObject jsonObject = new JSONObject();


        try {
            jsonObject = new JSONObject(stringBuilder.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void paringJsonData(JSONObject jsonObject) throws JSONException {
        barRecommendations.clear();

        JSONArray contacts = jsonObject.getJSONArray("items");
        //JSONArray는 JSONObject가 들어가는 배열

        for (int i = 0; i < contacts.length(); i++) {
            JSONObject c = contacts.getJSONObject(i);

            String title = c.getString("title"); //제목을 받아옵니다
            String changString = "";
            //try {
            //    changString = new String(title.getBytes("8859_1"), "UTF-8"); //한글이 깨져서 인코딩 해주었습니다
            //} catch (UnsupportedEncodingException e) {
            //    // TODO Auto-generated catch block
            //    e.printStackTrace();
            //}


            String description = c.getString("description"); //등록날짜

            String category = c.getString("category");
            String address = c.getString("address");

            barRecommendations.add(new SearchBar(title, category,description,address));
        }


    }



}