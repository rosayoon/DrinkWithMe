package com.example.drinkwithme;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import fragment.MyDrinkLifeFragment;
import fragment.AlcoholFragment;

public  class TabPagerAdapter extends FragmentStatePagerAdapter {
    private int tabCount;

    public TabPagerAdapter(@NonNull FragmentManager fm, int tabCount) {
        super(fm);
        this.tabCount =tabCount;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        //현재 탭을 돌려주는

        switch (position) {

            case 0:
                MainFragment mainfragment = new MainFragment();
                return mainfragment;

            case 1:
            MyDrinkLifeFragment drinkfragment = new MyDrinkLifeFragment();
            return drinkfragment;
            case 2:
                AlcoholFragment myPagefragment = new AlcoholFragment();
                return myPagefragment;
            case 3:
                DrinkingHelperFragment drinkingHelperFragment = new DrinkingHelperFragment();
                return drinkingHelperFragment;

            default:
                    return null;
        }

    }

    @Override
    public int getCount() {
        return tabCount;
    }
}
