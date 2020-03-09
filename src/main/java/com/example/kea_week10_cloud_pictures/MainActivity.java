package com.example.kea_week10_cloud_pictures;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.kea_week10_cloud_pictures.repo.Repo;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private ImageView imageView;
    private Button btn_gallery, btn_camera, btn_upload;
    Uri filepath;
    Bitmap photo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imageView = findViewById(R.id.imageView);
        btn_gallery = findViewById(R.id.button_gallery);
        btn_gallery.setOnClickListener(this);
        btn_camera = findViewById(R.id.button_camera);
        btn_camera.setOnClickListener(this);
        btn_upload = findViewById(R.id.button_upload);
        btn_upload.setOnClickListener(this);

       // Repo.downloadImage("unnamed.jpg", imageView);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            backFromGallery(requestCode, resultCode, data);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (requestCode == 2){ // From camera
            if (resultCode == RESULT_OK){
                photo = (Bitmap) data.getExtras().get("data");
                System.out.println("Success");
                Bitmap bitmap = (Bitmap) data.getExtras().get("data");
                imageView.setImageBitmap(bitmap);
            }else{
                System.out.println("Failed to get camera");
            }
        }
    }

    private void backFromGallery(int requestCode, int resultCode, @Nullable Intent data) throws IOException {
        if (requestCode == 1){
            if (resultCode == RESULT_OK){
                filepath = data.getData();
                System.out.println(filepath);
                photo = MediaStore.Images.Media.getBitmap(this.getContentResolver(), filepath);
                imageView.setImageBitmap(photo);
            }
        }
    }
    
    @Override
    public void onClick(View v) {
        switch (v.getId()){

            case R.id.button_gallery:
                Intent gallery = new Intent(Intent.ACTION_PICK); // Make an implicit intent which will allow the user to choose among different services
                gallery.setType("image/*"); // Sets type of content to pick
                startActivityForResult(gallery, 1); // start the activity, and expect an answer
                break;

            case R.id.button_camera:
                Intent camera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(camera, 2);
                break;

            case R.id.button_upload:
                Repo.uploadImage(this, photo);
                break;

        }
    }

}
