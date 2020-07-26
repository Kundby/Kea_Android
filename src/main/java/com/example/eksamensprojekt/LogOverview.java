package com.example.eksamensprojekt;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.example.eksamensprojekt.Storage.ImageStorage;
import com.example.eksamensprojekt.Storage.LogStorage;
import com.example.eksamensprojekt.auth.FirebaseManager;
import com.example.eksamensprojekt.model.Logs;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

//Handles the overview of existing logs, aswell as adding new ones
public class LogOverview extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "LogOverview";

    //Various views, handlers etc.
    private ImageButton btn_add;
    private Button btn_Logout;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private MyRecyclerViewAdapter adapter;
    private FirebaseManager firebaseManager;

    private final static String collectionRef = "eksamensprojekt1";
    private static FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_overview);

        startLogListener();
        firebaseManager = new FirebaseManager();

        //Assigning recyclerview, adapters and layout manager
        recyclerView = findViewById(R.id.main_recycler);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new MyRecyclerViewAdapter();
        recyclerView.setAdapter(adapter);

        //Assigning views and on click listeners
        btn_add = findViewById(R.id.but_add);
        btn_add.setOnClickListener(this);
        btn_Logout = findViewById(R.id.buttonLogout);
        btn_Logout.setOnClickListener(this);

    }

    //Handles all on click events for the buttons
    @Override
    public void onClick(View v) {
        switch (v.getId()){

            case R.id.but_add:
                //Add new log
                Intent intent = new Intent(this, Edit_Log.class);
                intent.removeExtra(MyRecyclerViewAdapter.idKey);
                startActivity(intent);
                break;

            case R.id.buttonLogout:
                //Log out the user, finish activity and new intent to login
                Log.i(TAG,"logout");
                firebaseManager.logout();
                finish();
                Intent intentLogin = new Intent(this, MainActivity.class);
                startActivity(intentLogin);
                break;
        }
    }

    //Method for deleting a log
    public static void deleteLog(Logs log) {
        DocumentReference docRef = db.collection(collectionRef).document(log.getId());
        if (log.getImage() != null){
            //If log to be deleted has an image, delete it from storage aswell, to save space
            ImageStorage.deleteImage(log.getImage());
        }
        docRef.delete();
        Log.i(TAG,"Deleted Log: " + log.getId() + " with headline: " + log.getHeadline());
    }

    //Listener for detecting logs, and adding them to the arraylist which is used for all further usage of logs
    public void startLogListener() {
        db.collection(collectionRef).addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot values, @Nullable FirebaseFirestoreException e) {
                LogStorage.list.clear();
                for (DocumentSnapshot snap : values.getDocuments()){
                    if (snap.exists()) {
                        if (snap.get("headline") != null) {
                            if (snap.get("headline") != null && snap.get("description") != null && snap.get("lat") == null && snap.get("image") == null) {
                                Log.i(TAG, "Found doc no img, no map: " + snap.getId());
                                LogStorage.list.add(new Logs(snap.get("headline").toString(), snap.get("description").toString(), snap.get("date").toString(), snap.getId()));
                            } else if (snap.get("headline") != null && snap.get("description") != null && snap.get("image") != null && snap.get("lat") != null) {
                                Log.i(TAG, "Found doc + img + map: " + snap.getId());
                                LogStorage.list.add(new Logs(snap.get("headline").toString(), snap.get("description").toString(), snap.get("image").toString(), snap.get("lat").toString(), snap.get("lon").toString(), snap.get("date").toString(), snap.getId()));
                            } else if (snap.get("headline") != null && snap.get("description") != null && snap.get("lat") != null && snap.get("image") == null) {
                                Log.i(TAG, "Found doc, no img + map: " + snap.getId());
                                LogStorage.list.add(new Logs(snap.get("headline").toString(), snap.get("description").toString(), snap.get("lat").toString(), snap.get("lon").toString(), snap.get("date").toString(), snap.getId()));
                            }  else if (snap.get("headline") != null && snap.get("description") != null && snap.get("image") != null) {
                                Log.i(TAG, "Found doc + img, no map: " + snap.getId());
                                LogStorage.list.add(new Logs(snap.get("headline").toString(), snap.get("description").toString(), snap.get("image").toString(), snap.get("date").toString(), snap.getId()));
                            }
                        }
                    }
                }
                //Tells the adapter that data changed and updates the view
                adapter.notifyDataSetChanged();
            }
        });
    }

}
