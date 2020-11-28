package com.example.drinkwithme;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import org.json.JSONObject;

import java.io.InputStream;

public class FoodYoutubeActivity extends Activity {

    //@Override
    //protected void onCreate(Bundle savedInstanceState) {
    //    super.onCreate(savedInstanceState);
    //    setContentView(R.layout.activity_food_youtube);
    //    String keky ="AIzaSyCLwib6X69YwR1Oh-BQGsa-NLAZFZ0UeZY";
    //    et = (EditText) findViewById(R.id.eturl);
//
    //    Button search = (Button) findViewById(R.id.search);
    //    search.setOnClickListener(new View.OnClickListener() {
    //        @Override
    //        public void onClick(View v) {
    //            searchTask = new StartActivity.searchTask().execute();
//
    //        }
    //    });
//
    //}
//
    //private class searchTask extends AsyncTask<Void, Void, Void> {
    //    @Override
    //    protected void onPreExecute() {
    //        super.onPreExecute();
    //    }
//
    //    @Override
    //    protected Void doInBackground(Void... params) {
    //        try {
    //            paringJsonData(getUtube());
    //        } catch (JSONException e) {
    //            // TODO Auto-generated catch block
    //            e.printStackTrace();
    //        }
//
    //        return null;
    //    }
//
    //    @Override
    //    protected void onPostExecute(Void result) {
//
    //        ListView searchlist = (ListView) findViewById(R.id.searchlist);
//
    //        StartActivity.StoreListAdapter mAdapter = new StartActivity.StoreListAdapter(
    //                StartActivity.this, R.layout.listview_start, sdata); //Json파싱해서 가져온 유튜브 데이터를 이용해서 리스트를 만들어줍니다.
//
    //        searchlist.setAdapter(mAdapter);
//
    //    }
    //}
//
    //public JSONObject getUtube() {
//ng alcohol ="";
    //    HttpGet httpGet = new HttpGet(
    //            "https://www.googleapis.com/youtube/v3/search?"
    //                    + "part=snippet&q=" + alcohol+"안주 만들기"
    //                    + "&key="+ "AIzaSyCLwib6X69YwR1Oh-BQGsa-NLAZFZ0UeZY"+"&maxResults=50");  //EditText에 입력된 값으로 겁색을 합니다.
    //    // part(snippet),  q(검색값) , key(서버키)
    //    HttpClient client = new DefaultHttpClient();
    //    HttpResponse response;
    //    StringBuilder stringBuilder = new StringBuilder();
//
    //    try {
    //        response = client.execute(httpGet);
    //        HttpEntity entity = response.getEntity();
    //        InputStream stream = entity.getContent();
    //        int b;
    //        while ((b = stream.read()) != -1) {
    //            stringBuilder.append((char) b);
    //        }
    //    } catch (ClientProtocolException e) {
    //    } catch (IOException e) {
    //    }
//
    //    JSONObject jsonObject = new JSONObject();
    //    try {
    //        jsonObject = new JSONObject(stringBuilder.toString());
    //    } catch (JSONException e) {
    //        // TODO Auto-generated catch block
    //        e.printStackTrace();
    //    }
//
    //    return jsonObject;
    //}
//
    ////파싱을 하면 여러가지 값을 얻을 수 있는데 필요한 값들을 세팅하셔서 사용하시면 됩니다.
    //private void paringJsonData(JSONObject jsonObject) throws JSONException {
    //    sdata.clear();
//
    //    JSONArray contacts = jsonObject.getJSONArray("items");
//
    //    for (int i = 0; i < contacts.length(); i++) {
    //        JSONObject c = contacts.getJSONObject(i);
    //        String kind =  c.getJSONObject("id").getString("kind"); // 종류를 체크하여 playlist도 저장
    //        if(kind.equals("youtube#video")){
    //            vodid = c.getJSONObject("id").getString("videoId"); // 유튜브
    //            // 동영상
    //            // 아이디
    //            // 값입니다.
    //            // 재생시
    //            // 필요합니다.
    //        }else{
    //            vodid = c.getJSONObject("id").getString("playlistId"); // 유튜브
    //        }
//
    //        String title = c.getJSONObject("snippet").getString("title"); //유튜브 제목을 받아옵니다
    //        String changString = "";
    //        try {
    //            changString = new String(title.getBytes("8859_1"), "utf-8"); //한글이 깨져서 인코딩 해주었습니다
    //        } catch (UnsupportedEncodingException e) {
    //            // TODO Auto-generated catch block
    //            e.printStackTrace();
    //        }
//
    //        String date = c.getJSONObject("snippet").getString("publishedAt") //등록날짜
    //                .substring(0, 10);
    //        String imgUrl = c.getJSONObject("snippet").getJSONObject("thumbnails")
    //                .getJSONObject("default").getString("url");  //썸내일 이미지 URL값
//
    //        sdata.add(new SearchData(vodid, changString, imgUrl, date));
    //    }
//
    //}
//
    //String vodid = "";
//
    //public class StoreListAdapter extends ArrayAdapter<SearchData> {
    //    private ArrayList<SearchData> items;
    //    SearchData fInfo;
//
    //    public StoreListAdapter(Context context, int textViewResourseId,
    //                            ArrayList<SearchData> items) {
    //        super(context, textViewResourseId, items);
    //        this.items = items;
    //    }
//
    //    public View getView(int position, View convertView, ViewGroup parent) {// listview
//
    //        // 출력
    //        View v = convertView;
    //        fInfo = items.get(position);
//
    //        LayoutInflater vi = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//
    //        v = vi.inflate(R.layout.listview_start, null);
    //        ImageView img = (ImageView) v.findViewById(R.id.img);
//
    //        String url = fInfo.getUrl();
//
    //        String sUrl = "";
    //        String eUrl = "";
    //        sUrl = url.substring(0, url.lastIndexOf("/") + 1);
    //        eUrl = url.substring(url.lastIndexOf("/") + 1, url.length());
    //        try {
    //            eUrl = URLEncoder.encode(eUrl, "EUC-KR").replace("+", "%20");
    //        } catch (UnsupportedEncodingException e) {
    //            // TODO Auto-generated catch block
    //            e.printStackTrace();
    //        }
    //        String new_url = sUrl + eUrl;
//
    //        Glide.with(MomMemoDetailActivity.this).load(new_url).into(img);
//
    //        v.setTag(position);
    //        v.setOnClickListener(new View.OnClickListener() {
    //            @Override
    //            public void onClick(View v) {
    //                int pos = (Integer) v.getTag();
//
    //                Intent intent = new Intent(StartActivity.this,
    //                        MainActivity.class);
    //                intent.putExtra("id", items.get(pos).getVideoId());
    //                startActivity(intent); //리스트 터치시 재생하는 엑티비티로 이동합니다. 동영상 아이디를 넘겨줍니다..
    //            }
    //        });
//
    //        ((TextView) v.findViewById(R.id.title)).setText(fInfo.getTitle());
    //        ((TextView) v.findViewById(R.id.date)).setText(fInfo
    //                .getPublishedAt());
//
    //        return v;
    //    }
    //}
//
   //// https://www.googleapis.com/youtube/v3/videos?id={A5H8zBb3iao}&key={AIzaSyCUriG-LgMYQ4nv2eArlYuIA-jQHvTd3NA}&part=snippet
}//