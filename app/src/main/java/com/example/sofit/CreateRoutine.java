package com.example.sofit;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.sofit.data.ImageConverter;
import com.example.sofit.data.RoutineDataSource;
import com.example.sofit.model.Routine;

import java.io.IOException;
import java.io.InputStream;

public class CreateRoutine extends AppCompatActivity {

    private static final int PICK_IMAGE = 1;
    private ImageView imageView;
    private Routine routine;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_routine);

        setTitle("Create Routine");

        Button btnAceptar = findViewById(R.id.btnAceptar);
        EditText name = findViewById(R.id.editTextNombreRutina);
        routine=new Routine();
        btnAceptar.setOnClickListener(view -> {
            if(name.getText().toString().matches("^\\s*$")) {
                Toast.makeText(getApplicationContext(),
                        getString(R.string.complete), Toast.LENGTH_SHORT).show();
                return;
            }
            insertRoutine();
            startActivity(new Intent(CreateRoutine.this, MyRoutines.class));
        });

        imageView = findViewById(R.id.imageView3);
        imageView.setImageResource(R.drawable.default_routine);

        ImageButton btnPhoto = findViewById(R.id.btn_change_pic_routine);
        btnPhoto.setOnClickListener(view -> openGallery());
    }

    private void insertRoutine(){
        EditText routineName = (EditText)findViewById(R.id.editTextNombreRutina);
        RoutineDataSource routineDataSource =
                new RoutineDataSource(getApplicationContext());


        routine.setName(routineName.getText().toString());
        routine.setUser(" ");
        if(routine.getImage()==null)
            routine.setImage(new byte[]{});

        routineDataSource.open();
        routineDataSource.createRoutine(routine);
        routineDataSource.close();

    }
    private void openGallery() {
        Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        gallery=Intent.createChooser(gallery,"Choose photo");
        requestPermissions();
        startActivityForResult(gallery, PICK_IMAGE);
    }


    public byte[] getBytesFromUri(Uri uri) {
        ImageConverter ic=new ImageConverter();
        try {
            InputStream iStream = getContentResolver().openInputStream(uri);
            byte[] inputData = ic.getBytes(iStream);
            return inputData;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == PICK_IMAGE) {
            Uri imageUri = data.getData();
            routine.setImage(getBytesFromUri(imageUri));
            imageView.setImageURI(imageUri);
        }
    }

    public void requestPermissions() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
            return;
        }
    }

}