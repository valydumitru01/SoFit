package com.example.sofit;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sofit.adapters.ListaEjerciciosPredefinidosAdapter;
import com.example.sofit.model.ModelExercise;
import com.example.sofit.remote.ApiUtils;
import com.example.sofit.remote.ExerciseDBAPI;
import com.example.sofit.server.ServerDataMapper;
import com.example.sofit.server.exerciselist.ExerciseData;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SelectPredefinedExercises extends AppCompatActivity {
    Spinner bodyPartsSpinner;
    RecyclerView recycler_predefinedExercises;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_predefined_exercise);
        //Init variables
        this.bodyPartsSpinner = (Spinner) findViewById(R.id.bodyPart_spinner);
        this.recycler_predefinedExercises = (RecyclerView) findViewById(R.id.recycler_predefinedExercises);
        this.recycler_predefinedExercises.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        //Init values
        requestBodyParts(ApiUtils.createExerciseDBAPI());


        //Init events
        bodyPartsSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                System.out.println(bodyPartsSpinner.getSelectedItem().toString());

                requestExercisesByBodyPart(ApiUtils.createExerciseDBAPI(), bodyPartsSpinner.getSelectedItem().toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                //Nothing
            }

        });

    }


    public void requestBodyParts(ExerciseDBAPI ExerciseDBAPIClient) {
        //Create the call to the api
        Call<List<String>> call = ExerciseDBAPIClient.getListBodyParts(ApiUtils.API_KEY, ApiUtils.HOST);

        // Wait asynchronously for it to end to fill the recycler
        call.enqueue(new Callback<List<String>>() {
            @Override
            public void onResponse(Call<List<String>> call, Response<List<String>> response) {
                switch (response.code()) {
                    case 200:
                        //Get the mapped data as list (THE JSON IS A LIST [] WITH NO NAME)
                        List<String> bodyParts = response.body();
                        //Use the array to fill the session
                        fillBodyPartsSpinner(bodyParts);

                        break;
                    default:
                        call.cancel();
                        break;
                }
            }

            @Override
            public void onFailure(Call<List<String>> call, Throwable t) {
                Log.e("List - error", t.toString());
            }
        });
    }

    private void fillBodyPartsSpinner(List<String> bodyParts) {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.spinner_item, bodyParts);
        adapter.setDropDownViewResource(R.layout.spinner_item);

        bodyPartsSpinner.setAdapter(adapter);

    }

    public void requestExercisesByBodyPart(ExerciseDBAPI ExerciseDBAPIClient, String bodyPart) {
        //Create the call to the api
        Call<List<ExerciseData>> call = ExerciseDBAPIClient.getListExercisesByBodyPart(bodyPart, ApiUtils.API_KEY, ApiUtils.HOST);

        // Wait asynchronously for it to end to fill the recycler
        call.enqueue(new Callback<List<ExerciseData>>() {
            @Override
            public void onResponse(Call<List<ExerciseData>> call, Response<List<ExerciseData>> response) {
                switch (response.code()) {
                    case 200:
                        //Get the mapped data as list (THE JSON IS A LIST [] WITH NO NAME)
                        List<ExerciseData> data = response.body();
                        //Convert the mapped data to domain
                        List<ModelExercise> exercises = ServerDataMapper.convertExerciseDataListToDomain(data);
                        //Use the array to fill the session
                        fillExerciseByBodyPartRecycler(exercises);


                        break;
                    default:
                        call.cancel();
                        break;
                }
            }

            @Override
            public void onFailure(Call<List<ExerciseData>> call, Throwable t) {
                Log.e("List - error", t.toString());
            }
        });
    }

    private void fillExerciseByBodyPartRecycler(List<ModelExercise> exercises) {

        recycler_predefinedExercises.setAdapter(new ListaEjerciciosPredefinidosAdapter(exercises,item -> {
            Intent i = new Intent(SelectPredefinedExercises.this, AddExercise.class);
            i.putExtra("predefinedExercise",item);
            i.putExtra("idSession",getIntent().getExtras().getString("idSession"));
            startActivity(i);
        }));

    }

}