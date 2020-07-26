package com.example.eksamensprojekt.Storage;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.eksamensprojekt.Edit_Log;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.util.UUID;

//Class for handling different use cases of images stored on Firebase
public class ImageStorage {

    static StorageReference storageReference;
    static FirebaseStorage storage = FirebaseStorage.getInstance();
    private static String path;

    //Method for uploading images to Firebase storage
    public static String uploadImage(final Context context, Bitmap photo, final Boolean isEditing) {

        if (photo != null) {
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            photo.compress(Bitmap.CompressFormat.JPEG, 100, stream);

            byte[] b = stream.toByteArray();
            storage = FirebaseStorage.getInstance();
            storageReference = storage.getReference();

            // Code for showing progressDialog while uploading
            final ProgressDialog progressDialog = new ProgressDialog(context);
            progressDialog.setTitle("Uploading...");
            progressDialog.show();
            //Generates unique id
            path = UUID.randomUUID().toString();
            // Defining the child of storageReference
            StorageReference ref = storageReference.child("Eksamensprojekt1/" + path);

            // adding listeners on upload or failure of image
            ref.putBytes(b).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {

                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    // Image uploaded successfully - Dismiss dialog
                    progressDialog.dismiss();
                    //Based on isEditing status trigger different methods in Edit_Log
                    if (isEditing){
                        Edit_Log.editLog();
                    }else{
                        Edit_Log.createLog();
                    }

                }
            })

                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            // Error, Image not uploaded
                            progressDialog.dismiss();
                            Toast.makeText(context, "Failed " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnProgressListener(
                            new OnProgressListener<UploadTask.TaskSnapshot>() {
                                // Progress Listener for loading percentage on the dialog box
                                @Override
                                public void onProgress(
                                        UploadTask.TaskSnapshot taskSnapshot) {
                                    double progress
                                            = (100.0
                                            * taskSnapshot.getBytesTransferred()
                                            / taskSnapshot.getTotalByteCount());
                                    progressDialog.setMessage("Uploaded " + (int)progress + "%");
                                }
                            });
        }else{
            System.out.println("No filePath found on upload");
        }
        return path;

    }

    //Method for downloading an image from Firebase Storage and assign to imageView
    public static void downloadImage(String name, final ImageView iv){
        // Get reference from file storage, given a file name
        storageReference = storage.getReference();
        StorageReference ref = storageReference.child("Eksamensprojekt1/" + name);
        int max = 1024 * 1024 * 10; // 10 megabytes maximum
        ref.getBytes(max).addOnSuccessListener(new OnSuccessListener<byte[]>() {
            @Override
            public void onSuccess(byte[] bytes) {
                Bitmap bm = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                iv.setImageBitmap(bm); // Set image data to imageView
                System.out.println("Downloaded image");
            }
        });
    }

    //Method for deleting an image in Firebase Storage
    public static void deleteImage(final String name){
        storageReference = storage.getReference();
        StorageReference ref = storageReference.child("Eksamensprojekt1/" + name);
        ref.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Log.i("ImageStorage", "Deleted image: " + name);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.i("ImageStorage", "Failed to delete image: " + name + " Exception: " + e);
            }
        });
    }



}
