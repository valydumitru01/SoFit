package com.example.sofit;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sofit.data.ImageConverter;
import com.example.sofit.data.SessionDataSource;
import com.example.sofit.model.ModelSession;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class AddSession extends AppCompatActivity {

    private ModelSession session;
    private static final int PICK_IMAGE = 1;
    String routineName;
    private ImageView imageView;
    private ArrayList<String> exercises = new ArrayList<>();
    private RecyclerView recyclerExercises;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        session=new ModelSession();
        setContentView(R.layout.activity_add_session);
        //Get extras (name of the routine we're introducing the session)
        Bundle extras = getIntent().getExtras();
        routineName=extras.getString("routineName");


        Button btnConfirm = findViewById(R.id.btn_addsession_confirm);
        btnConfirm.setOnClickListener(view -> {
            EditText et = findViewById(R.id.editTextTituloSesion);
            if(!et.getText().toString().isEmpty()){
                session.setName(et.getText().toString());
                session.setRoutine(routineName);
                insertSessionInDatabase(session);
            }
            Intent intent=new Intent(AddSession.this, MyCurrentRoutine.class);
            intent.putExtra("routineName",routineName);
            startActivity(intent);
        });

        imageView = findViewById(R.id.imageView_add_session);

        imageView.setImageResource(R.drawable.default_session);


        ImageButton btnPhoto = findViewById(R.id.btn_change_pic_session);
        btnPhoto.setOnClickListener(view -> openGallery());
    }

    private void insertSessionInDatabase(ModelSession s){
        SessionDataSource sds = new SessionDataSource(getApplicationContext());
        sds.open();
        sds.createSession(s);
        sds.close();
    }

    private void openGallery() {
        Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        gallery=Intent.createChooser(gallery,"Choose photo");
        requestPermissions();
        startActivityForResult(gallery, PICK_IMAGE);
    }
    private void requestPermissions(){
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
        }
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
            session.setImage(getBytesFromUri(imageUri));
            imageView.setImageURI(imageUri);
        }
    }





}
