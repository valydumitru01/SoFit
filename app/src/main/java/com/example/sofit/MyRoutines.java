package com.example.sofit;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sofit.adapters.ListRutinasViewAdapter;
import com.example.sofit.data.RoutineDataSource;
import com.example.sofit.data.UserDataSource;
import com.example.sofit.model.Routine;
import com.example.sofit.model.User;

import java.util.ArrayList;

public class MyRoutines extends BaseActivity {

    ArrayList<Routine> routines = new ArrayList<Routine>();
    private RecyclerView listRutinasView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_routines);

        setTitle("My Routines");
        createDrawer(this);

    }

    @Override
    protected void onResume() {
        super.onResume();

        loadRoutines();

        Button btnCrear = findViewById(R.id.btnCrearRutia);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        listRutinasView = findViewById(R.id.recylcerViewRutinas);


        listRutinasView.setHasFixedSize(true);
        listRutinasView.setLayoutManager(layoutManager);

        ListRutinasViewAdapter lpAdapter = new ListRutinasViewAdapter(routines,
                rutina -> clickOnRoutine(rutina),
                rutina -> deleteItem(rutina)
        );

        listRutinasView.setAdapter(lpAdapter);

        btnCrear.setOnClickListener(
                view -> startActivity(new Intent(MyRoutines.this, CreateRoutine.class))
        );
    }

    /**
     * Get all routines from database
     * Save them in an attribute
     */
    private void loadRoutines() {
        RoutineDataSource routineDataSource = new RoutineDataSource(getApplicationContext());
        routineDataSource.open();
        routines = routineDataSource.getAllRoutines();
        routineDataSource.close();
    }

    /**
     * Routine recycler onClick event
     * @param routine
     */
    public void clickOnRoutine(Routine routine) {
        Intent intent = new Intent(MyRoutines.this, MyCurrentRoutine.class);
        UserDataSource userDataSource=new UserDataSource(getApplicationContext());

        userDataSource.open();

        User user = userDataSource.getUserData();
        user.setCurrentRoutine(routine.getName());
        userDataSource.updateData(user);

        userDataSource.close();

        intent.putExtra("routineName", routine.getName());
        startActivity(intent);
    }

    /**
     * OnClick event the delete element of the routines recycler
     * @param routine
     */
    public void deleteItem(Routine routine) {
        AlertDialog.Builder builder = new AlertDialog.Builder(MyRoutines.this);
        builder.setMessage("Do you want to delete this routine?").setPositiveButton("Yes",
                (dialog, which) -> {
                    deleteRoutineFromDataBase(routine);
                    startActivity(new Intent(MyRoutines.this, MyRoutines.class));
                }).setNegativeButton("NO",
                (dialog, which) -> {
                    //Nothing
                }).show();
    }

    /**
     * Deletes routine from database given a routine model object
     * @param routine
     */
    private void deleteRoutineFromDataBase(Routine routine) {
        RoutineDataSource routineDataSource = new RoutineDataSource(getApplicationContext());
        routineDataSource.open();
        routineDataSource.deleteRoutine(routine);
        routineDataSource.close();
    }

}