package com.example.sofit;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sofit.adapters.ListSessionViewAdapter;
import com.example.sofit.data.RoutineDataSource;
import com.example.sofit.data.SessionDataSource;
import com.example.sofit.data.UserDataSource;
import com.example.sofit.model.ModelSession;
import com.example.sofit.model.Routine;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class MyCurrentRoutine extends BaseActivity {

    List<ModelSession> sessions = new ArrayList<ModelSession>();
    private RecyclerView listDiasView;
    private Routine routine;
    private String routineName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_current_routine);
        Bundle extras = getIntent().getExtras();
        UserDataSource userDataSource=new UserDataSource(getApplicationContext());
        userDataSource.open();
        if(extras!=null)
            routineName=extras.getString("routineName");
        else
            routineName=userDataSource.getUserData().getCurrentRoutine();
        userDataSource.close();

        if(routineName==null) {
            routineName = "";
            setTitle("No routine selected");
            createDrawer(this);
            return;
        }
        setTitle("My current routine: " + routineName);

        getRoutineFromDB();
        updateImage();
        createDrawer(this);

    }
    private void getRoutineFromDB(){
        RoutineDataSource routineDataSource=new RoutineDataSource(getApplicationContext());
        routineDataSource.open();
        routine =routineDataSource.getRoutineFromName(routineName);
        routineDataSource.close();
    }
    private void updateImage(){
        ImageView image= findViewById(R.id.imageView_my_current_routine);
        System.out.println(routine.getImage().length);
        if(routine.getImage().length>10) {
            Bitmap bitmap =
                    BitmapFactory.decodeByteArray(routine.getImage(), 0, routine.getImage().length);
            image.setImageBitmap(bitmap);
        }
        else{
            image.setImageResource(R.drawable.default_routine);
        }

    }
    public void loadSessions() {
        SessionDataSource sds = new SessionDataSource(getApplicationContext());
        sds.open();
        sessions = sds.getSessionsForRoutine(routineName);
        sds.close();
    }


    @Override
    protected void onResume() {
        super.onResume();
        loadSessions();

        listDiasView = findViewById(R.id.recycler_sessionExercises);
        listDiasView.setHasFixedSize(true);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        listDiasView.setLayoutManager(layoutManager);

        ListSessionViewAdapter lpAdapter = new ListSessionViewAdapter(sessions,
                item -> {
            /* Change current routine to the one clicked */
            Intent i = new Intent(MyCurrentRoutine.this, Session.class);

            i.putExtra("idSession", item.getName());
            System.out.println("\n Name of session "+item.getName());
            startActivity(i);
        }, item -> {

            AlertDialog.Builder builder = new AlertDialog.Builder(MyCurrentRoutine.this);
            builder.setMessage("Do you want to delete this routine?").setPositiveButton("Yes",
                    (dialog, which) -> {
                        deleteSession(item);
                        startActivity(new Intent(MyCurrentRoutine.this,MyCurrentRoutine.class));
                    }).setNegativeButton("NO", (dialog, which) -> {
                    }).show();
        });

        listDiasView.setAdapter(lpAdapter);

        //Floating on click, to add new session
        FloatingActionButton b = findViewById(R.id.my_current_routine_add_session);
        b.setOnClickListener(view -> {
            Intent intentToGoToAddSession=new Intent(MyCurrentRoutine.this, AddSession.class);
            intentToGoToAddSession.putExtra("routineName",routineName);
            startActivity(intentToGoToAddSession);
        });
    }

    private void deleteSession(ModelSession item) {
        SessionDataSource sds = new SessionDataSource(getApplicationContext());
        sds.open();
        sds.deleteSession(item);
        sds.close();
    }
}