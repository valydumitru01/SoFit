package com.example.sofit;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.example.sofit.data.ProgressDataSource;
import com.example.sofit.data.UserDataSource;
import com.example.sofit.model.ModelProgress;
import com.example.sofit.model.User;
import com.google.android.material.snackbar.Snackbar;

public class AddDataForToday extends BaseActivity {

    private EditText editTextWeight;
    private EditText editTextFat;
    private EditText editTextMuscle;
    private EditText editTextWater;
    private float weightData;
    private float waterData;
    private float muscleData;
    private float fatData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_data_user_graph);
        createDrawer(this);
        setTitle("Add data for today");

        //Views
        Button b1 = findViewById(R.id.button_actualizar_datos);
        Button b2 = findViewById(R.id.button_fotoPerfil);
        editTextWeight = findViewById(R.id.addData_EditText_weight);
        editTextFat = findViewById(R.id.addData_EditText_fat);
        editTextMuscle = findViewById(R.id.addData_EditText_muscle);
        editTextWater = findViewById(R.id.addData_EditText_water);


        //Listeners
        b1.setOnClickListener(view -> {
            getValuesFromForm();
            if (validateFields()) {
                startActivity(new Intent(AddDataForToday.this, MyProgress.class));
                addDataForToday();
            }
            else Snackbar.make(findViewById(R.id.addData_layout), "Data must be in adequate range",
                    Snackbar.LENGTH_LONG).show();
        });
        b2.setOnClickListener(
                view -> startActivity(new Intent(AddDataForToday.this, MyProgress.class)));

    }

    private void addDataForToday() {
        UserDataSource userDataSource = new UserDataSource(getApplicationContext());
        userDataSource.open();
        User user = userDataSource.getUserData();
        userDataSource.close();

        ModelProgress modelProgress = new ModelProgress();
        modelProgress.setWater(waterData);
        modelProgress.setWeight(weightData);
        modelProgress.setFat(fatData);
        modelProgress.setMuscle(muscleData);
        modelProgress.setUser(user.getName());

        ProgressDataSource progressDataSource = new ProgressDataSource(getApplicationContext());
        progressDataSource.open();
        progressDataSource.addProgressData(modelProgress);
        progressDataSource.close();

    }

    private boolean validateFields() {
        return weightData > 0 && waterData > 0 && waterData < 100 && muscleData > 0 && muscleData < 100 && fatData > 0 && fatData < 100;
    }

    private void getValuesFromForm() {
        String strWeight = String.valueOf(editTextWeight.getText());
        String strWater = String.valueOf(editTextWater.getText());
        String strFat = String.valueOf(editTextFat.getText());
        String strMuscle = String.valueOf(editTextMuscle.getText());
        boolean isEmpty =
                strWeight.length() == 0 || strWater.length() == 0 || strFat.length() == 0 || strMuscle.length() == 0;
        if (isEmpty) {
            Snackbar.make(findViewById(R.id.addData_layout), "Data can't be empty",
                    Snackbar.LENGTH_LONG).show();
        } else {
            weightData = Float.parseFloat(strWeight);
            waterData = Float.parseFloat(strWater);
            fatData = Float.parseFloat(strFat);
            muscleData = Float.parseFloat(strMuscle);
        }
    }
}
