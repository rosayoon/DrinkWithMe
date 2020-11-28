package com.example.drinkwithme;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;

import static com.example.drinkwithme.PreferenceManager.loadAlcoholData;
import static com.example.drinkwithme.PreferenceManager.loadFavoriteScheduleData;
import static com.example.drinkwithme.PreferenceManager.loadScheduleData;

public class MainActivity extends AppCompatActivity {
    TabLayout tabLayout;
    ViewPager viewPager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        loadAlcoholData(getApplicationContext());
        loadScheduleData(getApplicationContext());
        loadFavoriteScheduleData(getApplicationContext());

        tabLayout = findViewById(R.id.main_tab);
        tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.icon_home).setText("홈"));
        tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.icon_beer_calender).setText("술 라이프"));
        tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.icon_drinking_party_two).setText("술"));
        tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.icon_mustache).setText("음주 헬퍼"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        viewPager = findViewById(R.id.main_pager);

        TabPagerAdapter pagerAdapter = new TabPagerAdapter(getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(pagerAdapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        startActivity( new Intent(this, SplashScreen.class));

    }

}