package com.example.drinkwithme;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

import static com.example.drinkwithme.MainFragment.initializeRankingList;

public class AlcoholChartActivity extends AppCompatActivity {
ArrayList<AlcoholRanking> rankingForChart = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alocohol_chart);

        PieChart alcohol_pie_chart = findViewById(R.id.alcohol_pie_chart);

        rankingForChart = initializeRankingList();

        ArrayList NoOfDri = new ArrayList();

        for(int i =0; i<rankingForChart.size(); i++){ //x축에 들어갈 값을 넣는다.
            AlcoholRanking ranking = rankingForChart.get(i);
            NoOfDri.add( new Entry(ranking.numberOfDrinking,i));
        }

        PieDataSet dataSet = new PieDataSet(NoOfDri, "Number Of Drinking");

        ArrayList Name = new ArrayList();

        for(int i=0; i <rankingForChart.size(); i++){
            AlcoholRanking ranking = rankingForChart.get(i);
            Name.add(ranking.name);
        }
        PieData data = new PieData(Name, dataSet);
        data.setValueTextSize(16);
        alcohol_pie_chart.setData(data);
        dataSet.setColors(ColorTemplate.COLORFUL_COLORS);

        alcohol_pie_chart.animateXY(4000,4000);
    }
}