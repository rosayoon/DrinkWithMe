package com.example.drinkwithme;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class DrinkingHelperFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.activity_fragment_drinkinghelper,null);

        Button btn_search_bars = view.findViewById(R.id.btn_search_bars);

        Button btn_search_for_drinks = view.findViewById(R.id.btn_search_for_drinks);

        btn_search_bars.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              Intent intent = new Intent(getContext(),SearchAboutBarActivity.class);
              startActivity(intent);

          }
        });


        btn_search_for_drinks.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              Intent intent = new Intent(getContext(),YouTubeAcyivity.class);
              intent.putExtra("content","안주 만들기");
              startActivity(intent);
          }
        });

        return view;
    }
}
