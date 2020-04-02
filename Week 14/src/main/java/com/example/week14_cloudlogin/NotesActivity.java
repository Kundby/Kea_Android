package com.example.week14_cloudlogin;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.week14_cloudlogin.adapter.MyRecycleViewAdapter;
import com.example.week14_cloudlogin.auth.FirebaseManager;
import com.example.week14_cloudlogin.model.Note;
import com.example.week14_cloudlogin.storage.NoteStorage;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;

public class NotesActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageButton btn_add;
    private Button btn_Logout;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private MyRecycleViewAdapter adapter;
    private FirebaseManager firebaseManager;


    private final static String collectionRef = "notes";
    private static FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notes_activity);

        startNoteListener();
        firebaseManager = new FirebaseManager();

        recyclerView = findViewById(R.id.main_recycler);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new MyRecycleViewAdapter();
        recyclerView.setAdapter(adapter);

        btn_add = findViewById(R.id.but_add);
        btn_add.setOnClickListener(this);
        btn_Logout = findViewById(R.id.buttonLogout2);
        btn_Logout.setOnClickListener(this);

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){

            case R.id.but_add:
                Intent intent = new Intent(this, Edit_note.class);
                startActivity(intent);
                break;

            case R.id.buttonLogout2:
                System.out.println("logout");
                firebaseManager.logout();
                finish();
                Intent intentLogin = new Intent(this, MainActivity.class);
                startActivity(intentLogin);
                break;
        }
    }

    public static void editNote(Note note) {
        DocumentReference docRef = db.collection(collectionRef).document(note.getId());
        Map<String, String> map = new HashMap<>();
        map.put("head", note.getHeadline());
        map.put("body",  note.getBody());
        docRef.set(map);
    }

    public static void deleteNote(Note note) {
        DocumentReference docRef = db.collection(collectionRef).document(note.getId());
        docRef.delete();
        Log.i("NotesActivity","Deleted note: " + note.getId() + " with headline: " + note.getHeadline());
    }

    public void startNoteListener() {
        db.collection(collectionRef).addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot values, @Nullable FirebaseFirestoreException e) {
                NoteStorage.list.clear();
                for (DocumentSnapshot snap : values.getDocuments()){
                    Log.i("NotesActivity", "QuerySnapshot, Read from FireStore: " + snap.get("head") + " " + snap.get("body") + " " + snap.getId());
                    NoteStorage.list.add(new Note(snap.get("head").toString(), snap.get("body").toString(), snap.getId()));

                }
                adapter.notifyDataSetChanged();
            }
        });
    }

    public static void addNewNote(Note note) {
        DocumentReference docRef = db.collection(collectionRef).document();
        Map<String, String> map = new HashMap<>();
        map.put("head", note.getHeadline());
        map.put("body", note.getBody());
        docRef.set(map);
    }

}
