package com.example.drinkwithme;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;


import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Looper;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;


import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;

public class PubLocationMapActivity extends AppCompatActivity implements  ActivityCompat.OnRequestPermissionsResultCallback,
        OnMapReadyCallback{
    private GoogleMap mMap;
    private Geocoder geocoder;
    private Button button_search;
    private Button btn_select_location_in_map;
    private EditText editText;
    private LatLng barPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pub_location_map);
        editText = (EditText) findViewById(R.id.editText);
        button_search=(Button)findViewById(R.id.button_search);
        btn_select_location_in_map =findViewById(R.id.btn_select_location_in_map);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(final GoogleMap googleMap) {
        mMap = googleMap;
        geocoder = new Geocoder(this);

       // // 맵 터치 이벤트 구현 //
       // mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener(){
       //     @Override
       //     public void onMapClick(LatLng point) {
       //         MarkerOptions mOptions = new MarkerOptions();
       //         // 마커 타이틀
       //         mOptions.title("마커 좌표");
       //         Double latitude = point.latitude; // 위도
       //         Double longitude = point.longitude; // 경도
       //         // 마커의 스니펫(간단한 텍스트) 설정
       //         mOptions.snippet(latitude.toString() + ", " + longitude.toString());
       //         // LatLng: 위도 경도 쌍을 나타냄
       //         mOptions.position(new LatLng(latitude, longitude));
       //         // 마커(핀) 추가
       //         googleMap.addMarker(mOptions);
       //     }
       // });
        ////////////////////

        // 버튼 이벤트
        button_search.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v){
                String str=editText.getText().toString();
                List<Address> addressList = null;
                try {
                    // editText에 입력한 텍스트(주소, 지역, 장소 등)을 지오 코딩을 이용해 변환
                    addressList = geocoder.getFromLocationName(
                            str, // 주소
                            20); // 최대 검색 결과 개수
                }
                catch (IOException e) {
                    e.printStackTrace();
                }
                if(addressList !=null) {
                    if (addressList.size() > 0) {
                        System.out.println(addressList.get(0).toString());
                        // 콤마를 기준으로 split
                        String[] splitStr = addressList.get(0).toString().split(",");
                        String address = splitStr[0].substring(splitStr[0].indexOf("\"") + 1, splitStr[0].length() - 2); // 주소
                        System.out.println(address);

                        String latitude = splitStr[10].substring(splitStr[10].indexOf("=") + 1); // 위도
                        String longitude = splitStr[12].substring(splitStr[12].indexOf("=") + 1); // 경도
                        System.out.println(latitude);
                        System.out.println(longitude);

                        // 좌표(위도, 경도) 생성
                        barPosition = new LatLng(Double.parseDouble(latitude), Double.parseDouble(longitude));
                        // 마커 생성
                        MarkerOptions mOptions2 = new MarkerOptions();
                        mOptions2.title("search result");
                        mOptions2.snippet(address);
                        mOptions2.position(barPosition);
                        // 마커 추가
                        mMap.clear(); // 그 전에 표시된 마커 제거
                        mMap.addMarker(mOptions2);
                        // 해당 좌표로 화면 줌
                        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(barPosition, 15));
                        String location = getLocation(barPosition);
                        Toast.makeText(getApplicationContext(), location, Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(getApplicationContext(), "위치가 검색되지 않습니다.", Toast.LENGTH_LONG).show();
                    }
                }
                else{

                }
            }
        });
        btn_select_location_in_map.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              if(barPosition ==null){
                  Toast.makeText(getApplicationContext(), "위치를 검색한 후 클릭해주세요",Toast.LENGTH_LONG).show();
              }
              else {
                  String location = getLocation(barPosition);

                  Intent intent = new Intent();
                  intent.putExtra("location", location);
                  setResult(RESULT_OK, intent);
                  finish();
              }

          }
        });
        ////////////////////

        // Add a marker in Sydney and move the camera
        LatLng Seoul = new LatLng(37.5698411, 126.9783927);
        mMap.addMarker(new MarkerOptions().position(Seoul).title("Marker in Seoul"));
        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(Seoul, 15);
        mMap.moveCamera(cameraUpdate);
    }
    public String getLocation(LatLng latLng){
        String str = null;
        Geocoder geocoder = new Geocoder(this, Locale.KOREA);

        List<Address> address;
        try {
            if (geocoder != null) {
                address = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1);
                if (address != null && address.size() > 0) {
                    str = address.get(0).getAddressLine(0).toString();
                }
            }
        } catch (IOException e) {
            Log.e("MainActivity", "주소를 찾지 못하였습니다.");
            e.printStackTrace();
        }

        return str;

    }



}