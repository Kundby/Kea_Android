package com.example.week12_imageresize;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.week12_imageresize.controller.ImageController;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.URI;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {


    public ImageView imageView;
    Button but_camera, but_gallery;

    private ImageController ic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imageView = findViewById(R.id.imageView);

        but_camera = findViewById(R.id.btn_camera);
        but_camera.setOnClickListener(this);
        but_gallery = findViewById(R.id.btn_gallery);
        but_gallery.setOnClickListener(this);

        ic = new ImageController(this);
        isStoragePermissionGranted();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != -1) return;
        ic.handleImageReturn(requestCode, data, this);

    }

    private boolean isStoragePermissionGranted(){
        if (Build.VERSION.SDK_INT >= 23){
            if ((this.checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED)){
                System.out.println("Permission Granted");
                return true;
            }else{
                System.out.println("Permission revoked");
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                return false;
            }
        }else{
            System.out.println("Permission is granted");
            return true;
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){

            case R.id.btn_camera:
                Intent intentCamera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intentCamera, 1);
                break;

            case R.id.btn_gallery:
                Intent intentGallery = new Intent(Intent.ACTION_PICK);
                intentGallery.setType("image/*");
                startActivityForResult(intentGallery, 0);
                break;

        }
    }
}
