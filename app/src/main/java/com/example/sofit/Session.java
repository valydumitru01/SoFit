package com.example.sofit;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.ImageView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sofit.adapters.ListaEjerciciosViewAdapter;
import com.example.sofit.data.ExerciseDataSource;
import com.example.sofit.data.SessionDataSource;
import com.example.sofit.model.ModelExercise;
import com.example.sofit.model.ModelSession;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class Session extends BaseActivity {

    private List<ModelExercise> exercises;
    private ExerciseDataSource exerciseDataSource;
    private SessionDataSource sessionDataSource;
    private List<ModelExercise> exerciseList;
    private RecyclerView exerciseRecycler;
    private String sessionName;
    private ModelSession session;
    private String image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Initialization
        setContentView(R.layout.activity_session);


    }

    @Override
    protected void onResume() {
        super.onResume();
        exerciseList = new ArrayList<>();
        Bundle extras = getIntent().getExtras();


        //Get the session we're in
        sessionName = "";
        if (extras != null) {
            sessionName = extras.getString("idSession");
        }
        setTitle(sessionName + " Session");

        //Drawer
        createDrawer(this);


        //Prepare the database
        exerciseDataSource = new ExerciseDataSource(getApplicationContext());
        sessionDataSource= new SessionDataSource(getApplicationContext());

        //Get exercises from the database
        exerciseDataSource.open();
        exercises = exerciseDataSource
                .getExercisesForSession(sessionName);
        exerciseDataSource.close();

        //Get current session
        sessionDataSource.open();
        session=sessionDataSource.getSessionByName(sessionName);
        sessionDataSource.close();


        // Set the image
        updateImage();

        //----Init the recycler----
        exerciseRecycler = findViewById(R.id.recycler_sessionExercises);

        //Set the layout manager
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        exerciseRecycler.setLayoutManager(layoutManager);

        //Set has fixed size, increases performance for
        //Recyclers that get items added or removed frequently
        exerciseRecycler.setHasFixedSize(true);

        fillRecycler(exercises);

        //--------------------------


        //Create the event for the button to add new exercise
        createEventAddExercise();
    }
    private void updateImage(){
        ImageView image= findViewById(R.id.imageView_session_image);
        System.out.println(session.getImage().length);
        if(session.getImage().length>10) {
            Bitmap bitmap =
                    BitmapFactory.decodeByteArray(session.getImage(), 0, session.getImage().length);
            image.setImageBitmap(bitmap);
            return;
        }

        image.setImageResource(R.drawable.default_session);
    }
    private void createEventAddExercise(){
        FloatingActionButton b = findViewById(R.id.btn_session_addExercise);
        b.setOnClickListener(view -> {

            Intent i = new Intent(Session.this, AddExercise.class);
            i.putExtra("idSession", sessionName);
            startActivity(i);
        });
    }



    private void fillRecycler(List<ModelExercise> exercises) {


        List<ModelExercise> exercisesButtons = new ArrayList<>();
        for (ModelExercise ex : exercises) {
            exercisesButtons.add(ex);
        }
        ListaEjerciciosViewAdapter lpAdapter = new ListaEjerciciosViewAdapter(exercisesButtons,
                new ListaEjerciciosViewAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(ModelExercise item) {
                        Intent i = new Intent(Session.this, Exercise.class);
                        i.putExtra("exerciseId", item.getName());
                        i.putExtra("exercisePhoto", item.getImage());
                        startActivity(i);
                    }
                },new ListaEjerciciosViewAdapter.DeleteListener() {
            @Override
            public void deleteItem(ModelExercise item) {
                AlertDialog.Builder builder = new AlertDialog.Builder(Session.this);
                builder.setMessage("Do you want to delete this routine?").setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        deleteExercise(item);
                        Intent i = new Intent(Session.this, Session.class);
                        i.putExtra("idSession", session.getName());
                        startActivity(i);
                    }
                }).setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                }).show();
            }
        });

        exerciseRecycler.setAdapter(lpAdapter);


    }

    private void deleteExercise(ModelExercise item) {
        ExerciseDataSource eds = new ExerciseDataSource(getApplicationContext());
        eds.open();
        eds.deleteExercise(item);
        eds.close();
    }

}