package com.example.pomodoro;

import android.graphics.Color;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Random;


public class StatisticsActivity extends AppCompatActivity {
    private PieChart pieChart;
    private BarChart stackedChart;
    private final ArrayList<Integer> colors = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics);

        generateColors();
        pieChart = findViewById(R.id.distribution_chart);
        stackedChart = findViewById(R.id.history_chart);


        setupPieChart();
        loadPieChartData();
        loadBarChartData();
    }

    private void generateColors() {
        for (int color : ColorTemplate.MATERIAL_COLORS) {
            colors.add(color);
        }
        for (int color : ColorTemplate.VORDIPLOM_COLORS) {
            colors.add(color);
        }
    }


    private void setupLegend(Legend legend) {
        legend.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
        legend.setOrientation(Legend.LegendOrientation.VERTICAL);
        legend.setDrawInside(false);
        legend.setEnabled(true);
    }

    private void setupPieChart() {
        pieChart.setDrawHoleEnabled(true);
        pieChart.setUsePercentValues(true);
        pieChart.setEntryLabelTextSize(12f);
        pieChart.setEntryLabelColor(Color.BLACK);
        pieChart.setCenterText("Pomodoro time");
        pieChart.setCenterTextSize(16f);
        pieChart.getDescription().setEnabled(false);

        setupLegend(pieChart.getLegend());
    }

    private int get_random_number(int max_value) {
        return new Random().nextInt(max_value);
    }

    private String get_random_text() {
        byte[] array = new byte[7]; // length is bounded by 7
        new Random().nextBytes(array);
        return new String(array, StandardCharsets.UTF_8);
    }

    private void loadPieChartData() {

        ArrayList<PieEntry> entries = new ArrayList<PieEntry>();
        for (int i = 0; i < 5; i++) {
            int random_number = get_random_number(100);
            String random_text = get_random_text();
            entries.add(new PieEntry(random_number, random_text));
        }

        PieDataSet dataSet = new PieDataSet(entries, "Pomodoros");
        dataSet.setColors(colors);

        PieData data = new PieData(dataSet);
        data.setDrawValues(true);
        data.setValueFormatter(new PercentFormatter(pieChart));
        data.setValueTextSize(10f);
        data.setValueTextColor(Color.BLACK);

        pieChart.setData(data);
        pieChart.invalidate();
        pieChart.animateY(1400, Easing.EaseInOutQuad);
    }

    private ArrayList<BarEntry> getValues() {
        ArrayList<BarEntry> dataValues = new ArrayList<>();
        for (int day = 0; day < 7; day++) {

            int daily_pomodoros = get_random_number(5);
            float[] daily_values = new float[daily_pomodoros];
            for (int i = 0; i < daily_pomodoros; i++) {
                int pomodoro_value = get_random_number(3);
                daily_values[i] = pomodoro_value;
            }
            dataValues.add(new BarEntry(day, daily_values));
        }
        return dataValues;
    }

    private void loadBarChartData() {
        BarDataSet barDataSet = new BarDataSet(getValues(), "Bar Set");
        barDataSet.setColors(colors);

        BarData barData = new BarData(barDataSet);
        stackedChart.setData(barData);
    }
}