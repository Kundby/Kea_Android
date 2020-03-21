package com.example.week12_imageresize.controller;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;

import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;

import com.example.week12_imageresize.MainActivity;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

public class ImageController {

    private MainActivity mainActivity;
    private Context context;

    public ImageController(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
    }


    public void handleImageReturn(int requestCode, @Nullable Intent data, Context context) {
        if (requestCode == 0){
            Uri uri = data.getData();
            try {
                InputStream is = mainActivity.getContentResolver().openInputStream(uri);
                Bitmap bitmap = BitmapFactory.decodeStream(is);
                mainActivity.imageView.setImageBitmap(bitmap);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        if (requestCode == 1){
            this.context = context;
           Bitmap photo = (Bitmap) data.getExtras().get("data");
           /* photo = Bitmap.createScaledBitmap(photo, 100, 100, false);
            ByteArrayOutputStream bytes = new ByteArrayOutputStream();
            photo.compress(Bitmap.CompressFormat.JPEG, 40, bytes);*/

            String root = Environment.getExternalStorageDirectory().toString();
            File myDir = new File(root, "/saved_images");
            if (!myDir.exists()){
                myDir.mkdirs();
            }
            String fname = "Image-" + UUID.randomUUID().toString() + ".jpg";
            File f = new File(myDir, fname);
            try {
                f.createNewFile();
                FileOutputStream fo = new FileOutputStream(f);
                photo.compress(Bitmap.CompressFormat.JPEG, 90, fo);
                fo.flush();
                fo.close();
                System.out.println("Saved Image");
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("Error, Image not saved");
                System.out.println(e.toString());
            }
            MediaScannerConnection.scanFile(context, new String[]{f.toString()}, new String[]{f.getName()}, null);
            mainActivity.imageView.setImageBitmap(photo);
        }
    }

}
