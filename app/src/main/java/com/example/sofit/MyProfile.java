package com.example.sofit;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.sofit.data.UserDataSource;
import com.example.sofit.model.User;

import java.util.ArrayList;

public class MyProfile extends BaseActivity {

    private static final int PICK_IMAGE = 1;
    private ImageView mImage;
    Uri mImageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_profile);

        setTitle("My Profile");
        createDrawer(this);

        UserDataSource uds = new UserDataSource(getApplicationContext());
        uds.open();
        ArrayList<User> user = uds.getAllUsers();
        uds.close();

        TextView sex = findViewById(R.id.profile_textView_sex);
        sex.setText(user.get(0).getSex());
        TextView weight = findViewById(R.id.profile_textView_weight);
        weight.setText(String.valueOf(user.get(0).getWeight()));
        TextView height = findViewById(R.id.profile_textView_height);
        height.setText(String.valueOf(user.get(0).getHeight()));
        TextView age = findViewById(R.id.profile_textView_age);
        age.setText(String.valueOf(user.get(0).getAge()));

        Button b = findViewById(R.id.button_actualizar_datos);
        b.setOnClickListener(view ->
                startActivity(new Intent(MyProfile.this, EditProfile.class)));

        Button b2 = findViewById(R.id.button_verProgreso);
        b2.setOnClickListener(view ->
                startActivity(new Intent(MyProfile.this, MyProgress.class)));

        mImage = findViewById(R.id.imageView);

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        String mImageUri = preferences.getString("image", null);

        if (mImageUri != null) {
            mImage.setImageURI(Uri.parse(mImageUri));
        } else {
            mImage.setImageResource(R.drawable.default_pfp);
        }

        Button btnImg = findViewById(R.id.button_fotoPerfil);
        btnImg.setOnClickListener(v -> imageSelect());
    }

    public void imageSelect() {
        permissionsCheck();
        Intent intent;
        if (Build.VERSION.SDK_INT < 19) {
            intent = new Intent(Intent.ACTION_GET_CONTENT);
        } else {
            intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
            intent.addCategory(Intent.CATEGORY_OPENABLE);
        }
        intent.setType("image/*");
        startActivityForResult(Intent.createChooser(intent, "Elige una foto"), PICK_IMAGE);
    }

    public void permissionsCheck() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
            return;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE) {
            if (resultCode == RESULT_OK) {
                if (data != null) {
                    mImageUri=data.getData();
                    SharedPreferences preferences=PreferenceManager.getDefaultSharedPreferences(this);
                    SharedPreferences.Editor editor=preferences.edit();
                    editor.putString("image", String.valueOf(mImageUri));
                    editor.commit();
                    mImage.setImageURI(mImageUri);
                    mImage.invalidate();
                }
            }
        }
    }
}