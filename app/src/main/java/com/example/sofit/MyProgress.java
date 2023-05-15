package com.example.sofit;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import com.example.sofit.data.ProgressDataSource;
import com.example.sofit.model.ModelProgress;
import com.google.android.material.tabs.TabLayout;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class MyProgress extends BaseActivity {

    // creating an attribute
    // for our graph view.
    GraphView graphView;
    List<ModelProgress> listOfData = new ArrayList<>();
    List<DataPoint> dataPoints = new ArrayList<>();
    private TabLayout tabs;

    @Override
    protected void onStart() {
        super.onStart();

    }

    @Override
    public void onActivityReenter(int resultCode, Intent data) {
        super.onActivityReenter(resultCode, data);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_progress);
        setTitle("My Progress");
        createDrawer(this);

        //Get data from database and store it to array attribute
        getProgressData();


        //Get views
        Button b = findViewById(R.id.my_progress_add_data);
        graphView = findViewById(R.id.idGraphView);
        //Tab views
        tabs = findViewById(R.id.progress_tabLayout);

        //Initialize the graph view
        prepareGraph();

        //Set listeners
        b.setOnClickListener(view -> startActivity(new Intent(MyProgress.this, AddDataForToday.class)));

        tabs.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (tab.getPosition()) {
                    case 0:
                        selectWeightTab();
                        break;
                    case 1:
                        selectFatTab();
                        break;
                    case 2:
                        selectMuscleTab();
                        break;
                    case 3:
                        selectWaterTab();
                        break;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        tabs.selectTab(tabs.getTabAt(0));
        selectWeightTab();
    }

    private void prepareGraph() {
        // after adding data to our line graph series.
        // on below line we are setting
        // title for our graph view.
        graphView.setTitle("My Graph View");

        // on below line we are setting
        // text color to our graph view.
        graphView.setTitleColor(R.color.purple_200);

        // on below line we are setting
        // our title text size.
        graphView.setTitleTextSize(18);

        graphView.onDataChanged(true, true);

        graphView.getViewport().setYAxisBoundsManual(true);
    }

    private void selectWeightTab() {
        ArrayList<Float> weightData=new ArrayList<>();
        for(ModelProgress mp:listOfData)
            weightData.add(mp.getWeight());
        if(weightData.size()>0)
            updateGraph(weightData);
    }

    private void selectFatTab() {
        ArrayList<Float> fatData=new ArrayList<>();
        for(ModelProgress mp:listOfData)
            fatData.add(mp.getFat());
        if(fatData.size()>0)
            updateGraph(fatData);
    }

    private void selectWaterTab() {
        ArrayList<Float> waterData=new ArrayList<>();
        for(ModelProgress mp:listOfData)
            waterData.add(mp.getWater());
        if(waterData.size()>0)
            updateGraph(waterData);
    }

    private void selectMuscleTab() {
        ArrayList<Float> muscleData=new ArrayList<>();
        for(ModelProgress mp:listOfData)
            muscleData.add(mp.getMuscle());
        if(muscleData.size()>0)
            updateGraph(muscleData);
    }
    private void updateGraph(ArrayList<Float> progressData){
        graphView.removeAllSeries();

        float minData= Collections.min(progressData);
        float maxData=Collections.max(progressData);
        graphView.getViewport().setMinY(minData-0.1*minData);
        graphView.getViewport().setMaxY(maxData+0.1*maxData);

        dataPoints = new ArrayList<>();

        for (int i = 0; i < progressData.size(); i++) {
            dataPoints.add(new DataPoint(i, progressData.get(i)));
        }

        DataPoint[] dataPointsArr = new DataPoint[]{};
        dataPointsArr = dataPoints.toArray(dataPointsArr);
        LineGraphSeries<DataPoint> series = new LineGraphSeries<>(dataPointsArr);
        // on below line we are adding
        // data series to our graph view.
        graphView.addSeries(series);
    }
    private void getProgressData() {
        ProgressDataSource progressDataSource = new ProgressDataSource(getApplicationContext());
        progressDataSource.open();
        listOfData = progressDataSource.getProgressData();
        progressDataSource.close();
    }
}