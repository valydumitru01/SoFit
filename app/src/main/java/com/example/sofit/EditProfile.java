package com.example.sofit;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.sofit.data.UserDataSource;
import com.example.sofit.model.User;

import java.util.ArrayList;

public class EditProfile extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        setTitle("Edit Profile");

        Button btnConfirm = findViewById(R.id.btn_editprofile_confirm);
        btnConfirm.setOnClickListener(view -> {
            UserDataSource uds = new UserDataSource(getApplicationContext());
            uds.open();
            User user = uds.getUserData();
            EditText height = findViewById(R.id.editText_edit_profile_Height);
            EditText weight = findViewById(R.id.editText_edit_profile_Weight);
            EditText age = findViewById(R.id.editText_edit_profile_Age);
            EditText sex = findViewById(R.id.editText_edit_profile_Sex);

            user.setHeight(Integer.parseInt( height.getText().toString()));
            user.setWeight(Integer.parseInt( weight.getText().toString()));
            user.setAge(Integer.parseInt(age.getText().toString()));
            user.setSex(sex.getText().toString());

            uds.updateData(user);
            startActivity(new Intent(EditProfile.this, MyProfile.class));
        });
        Button btnCancel = findViewById(R.id.btn_editprofile_cancel);
        btnCancel.setOnClickListener(
                view -> startActivity(new Intent(EditProfile.this, MyProfile.class)));
    }
}