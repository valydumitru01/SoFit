package com.example.sofit;

import android.os.Bundle;
import android.widget.ImageView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sofit.adapters.ListSerieViewAdapter;
import com.example.sofit.data.SeriesDataSource;
import com.example.sofit.model.Serie;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class Exercise extends BaseActivity {
    SeriesDataSource seriesDataSource;
    private RecyclerView seriesRecycler;
    String exercise = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        seriesDataSource = new SeriesDataSource(getApplicationContext());
        Bundle extras = getIntent().getExtras();




        if (extras != null) {
            exercise = extras.getString("exerciseId");
        }
        setTitle(exercise+" Exercise");
        setContentView(R.layout.activity_exercise);

        ImageView imageViewExercise = findViewById(R.id.imageView_exercise);
        imageViewExercise.setImageResource(R.drawable.default_exercise);


        createDrawer(this);

    }

    @Override
    protected void onResume() {
        super.onResume();
        seriesRecycler = findViewById(R.id.seriesRecycler);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        seriesRecycler.setLayoutManager(layoutManager);

        seriesRecycler.setHasFixedSize(true);
        seriesDataSource.open();
        List<Serie>series = seriesDataSource.getSeriesForExercise(exercise);
        seriesDataSource.close();
        fillRecycler(series);
    }

    private void fillRecycler(List<Serie> series) {
        List<Serie> seriesAux = new ArrayList<>();
        for (Serie ex : series) {
            seriesAux.add(ex);
        }
        ListSerieViewAdapter lpAdapter = new ListSerieViewAdapter(seriesAux);

        seriesRecycler.setAdapter(lpAdapter);
    }
}
