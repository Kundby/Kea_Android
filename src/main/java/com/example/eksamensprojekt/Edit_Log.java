package com.example.eksamensprojekt;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.eksamensprojekt.Storage.ImageStorage;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

//Class used to edit/create logs
public class Edit_Log extends AppCompatActivity implements View.OnClickListener {

    private final static String collectionRef = "eksamensprojekt1";
    private static FirebaseFirestore db = FirebaseFirestore.getInstance();

    //Used with intent to MapsActivity, for passing data
    private static final String TAG = "Edit_Log";
    public static final String idKey = "ID_KEY";
    public static final String editingKey = "EDITING_KEY";

    //Various attributes and views
    private static EditText edit_headline, edit_log;
    private ImageView image;
    private static Button btn_image, btn_map, btn_back, btn_img_del, btn_map_del;
    private static Boolean isEditing = false;
    private Boolean hasImage = false;
    private String headline_text, description_text, Strlat, Strlon, idMap;
    private static String id = null, date, img_path, img_details;
    private Uri filepath;
    private Bitmap photo;
    private static double lat, lon;

    //Used for various methods in static context
    private static Context mContext;
    private static Activity activity;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_log);
        mContext = this;
        activity = this;

        //Assigning views and on click listeners
        edit_headline = findViewById(R.id.et_headline);
        edit_log = findViewById(R.id.et_log);
        image = findViewById(R.id.iv_log);
        btn_image = findViewById(R.id.but_img_add);
        btn_image.setOnClickListener(this);
        btn_img_del = findViewById(R.id.but_img_delete);
        btn_img_del.setOnClickListener(this);
        btn_map = findViewById(R.id.but_map_add);
        btn_map.setOnClickListener(this);
        btn_map_del = findViewById(R.id.but_map_delete);
        btn_map_del.setOnClickListener(this);
        btn_back = findViewById(R.id.but_update);
        btn_back.setOnClickListener(this);

        date = getDate();
        isEditing = false;

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        //If there's extras in the intent, then assign various attributes to the data from the intent extras
        if (extras != null){
            id = intent.getExtras().getString(MyRecyclerViewAdapter.idKey);
            idMap = id;
            headline_text = intent.getExtras().getString(MyRecyclerViewAdapter.headlineKey);
            edit_headline.setText(headline_text);
            description_text = intent.getExtras().getString(MyRecyclerViewAdapter.descriptionKey);
            edit_log.setText(description_text);
            img_details = intent.getExtras().getString(MyRecyclerViewAdapter.imageKey);
            Strlat = intent.getExtras().getString(MyRecyclerViewAdapter.markerLat);
            Strlon = intent.getExtras().getString(MyRecyclerViewAdapter.markerLon);
            lat = intent.getExtras().getDouble(MapsActivity.latKey);
            lon = intent.getExtras().getDouble(MapsActivity.lonKey);
            if (img_details != null && img_details.length() > 0){
                //If there's an image in the log, then download it, change some button txt and show a new button
                hasImage = true;
                ImageStorage.downloadImage(img_details, image);
                btn_image.setText("Replace Image");
                btn_img_del.setVisibility(View.VISIBLE);
            }
            if (id != null){
                //If there's an ID then set isEditing to true and change btn txt
                isEditing = true;
                btn_back.setText("Update");
            }
            if (Strlat != null){
                //If there's location data, change btn txt and show new button
                btn_map.setText("View Location");
                btn_map_del.setVisibility(View.VISIBLE);
            }
        }

    }

    //Handles on click events for all buttons based on their ID
    @Override
    public void onClick(View view) {
        switch (view.getId()){

            case R.id.but_img_add:
                //Add image
                Intent gallery = new Intent(Intent.ACTION_PICK); // Make an implicit intent which will allow the user to choose among different services
                gallery.setType("image/*"); // Sets type of content to pick
                startActivityForResult(gallery, 1); // start the activity, and expect an answer
                break;

            case R.id.but_img_delete:
                //Delete image
                if (img_details != null){
                    ImageStorage.deleteImage(img_details);
                    db.collection(collectionRef).document(id).update("image", null);
                    image.setImageResource(android.R.color.transparent);
                    btn_image.setText("Add Image");
                    btn_img_del.setVisibility(View.GONE);
                    hasImage = false;
                }
                break;

            case R.id.but_map_add:
                //Open Maps Activity
                Intent intent = new Intent(this, MapsActivity.class);
                    intent.putExtra(idKey, idMap);
                    intent.putExtra(editingKey, isEditing);
                startActivity(intent);
                break;

            case R.id.but_map_delete:
                //Deletes location
                if(Strlat != null){
                    db.collection(collectionRef).document(id).update(
                            "lat", null,
                            "lon", null
                    );
                    Strlat = null;
                    btn_map.setText("Add Location");
                    btn_map_del.setVisibility(View.GONE);
                }
                break;

            case R.id.but_update:
                //Update/Add log button in top left corner
                if (!edit_headline.getText().toString().isEmpty() && !edit_log.getText().toString().isEmpty()) {
                    if (hasImage && photo != null) {
                        //If log contains image, start uploading to Firebase Storage
                        Log.i(TAG, "Has Image - Starting upload");
                        img_path = ImageStorage.uploadImage(this, photo, isEditing);
                    } else {
                        //If no image, then either run editLog method or createLog method
                        if (isEditing){
                            Log.i(TAG, "No new Image - Editing existing log");
                            editLog();
                        }else{
                            Log.i(TAG, "No Image - Creating new Log");
                            createLog();
                        }
                    }
                }else {
                    //Displays toast if user haven't filled out headline & log
                    Toast.makeText(mContext, "Please add a headline & log before saving", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    //Handles gallery action - code ready if i were to add a camera option
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try { //Gallery
            backFromGallery(requestCode, resultCode, data);
        } catch (IOException e) {
            e.printStackTrace();
        }/*
        if (requestCode == 2){ // From camera
            if (resultCode == RESULT_OK){
                photo = (Bitmap) data.getExtras().get("data");
                System.out.println("Success");
                Bitmap bitmap = (Bitmap) data.getExtras().get("data");
                image.setImageBitmap(bitmap);
            }else{
                System.out.println("Failed to get camera");
            }
        }*/
    }

    private void backFromGallery(int requestCode, int resultCode, @Nullable Intent data) throws IOException {
        if (requestCode == 1){
            if (resultCode == RESULT_OK){
                //Saves the filepath, displays chosen image, changes some txt and show a button
                filepath = data.getData();
                Log.i(TAG, "" + filepath);
                photo = MediaStore.Images.Media.getBitmap(this.getContentResolver(), filepath);
                image.setImageBitmap(photo);
                hasImage = true;
                btn_image.setText("Replace Image");
                btn_img_del.setVisibility(View.VISIBLE);

            }
        }
    }

    //Method used to get current date in danish format (Day/Month/Year)
    private String getDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
        return sdf.format(new Date());
    }

    //Used for editing an existing log
    public static void editLog(){
        db.collection(collectionRef).document(id).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    //Exisiting document / log
                    if (document.exists()) {
                        if (img_path == null && img_details != null && btn_img_del.getVisibility() == View.VISIBLE) {
                            //If there's already an image, but it hasn't been replaced with a new one
                            img_path = img_details;
                        }
                        //Updates the attributes on firestore, if there's an update in the value
                        db.collection(collectionRef).document(id).update(
                                "headline", edit_headline.getText().toString(),
                                "description", edit_log.getText().toString(),
                                "date", date,
                                "image", img_path
                        );
                        //Ends the activity and start a new intent
                        activity.finish();
                        mContext.startActivity(new Intent(mContext, LogOverview.class));
                    }
                }
            }

        });
    }

    //Method for creating new log and ending activity
    public static void createLog() {
        if (btn_img_del.getVisibility() != View.VISIBLE){
            img_path = null;
        }
        String lat1 = String.valueOf(lat);
        String lon1 = String.valueOf(lon);
        String headline = edit_headline.getText().toString();
        String description = edit_log.getText().toString();
        DocumentReference docRef = db.collection(collectionRef).document();
        Map<String, String> map = new HashMap<>();
        map.put("headline", headline);
        map.put("description",  description);
        map.put("image", img_path);
        map.put("lat", lat1);
        map.put("lon", lon1);
        map.put("date", date);
        docRef.set(map);
        activity.finish();
        mContext.startActivity(new Intent(mContext, LogOverview.class));
    }

}
