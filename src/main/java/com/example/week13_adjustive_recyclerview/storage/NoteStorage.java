package com.example.week13_adjustive_recyclerview.storage;

import androidx.annotation.NonNull;

import com.example.week13_adjustive_recyclerview.MainActivity;
import com.example.week13_adjustive_recyclerview.model.Note;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class NoteStorage {
    public static List<Note> list = new ArrayList<>();
    private static FirebaseFirestore db = FirebaseFirestore.getInstance();
    private static MainActivity mainActivity;
    private static DocumentSnapshot lastVisible;
    private static int LIMIT = 5;
    private final static String path = "notes";
    public static void init(MainActivity activity){
        mainActivity = activity;
        getInitialNotes();
    }

    private static void getInitialNotes(){
        list.clear(); // because this is asking for the "fresh" list
        addOneTimeQuery(db.collection(path)
                .orderBy("head")
                .limit(LIMIT));
    }

    private static void addOneTimeQuery(Query query){
        // will only fetch data ONCE
        query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    for (DocumentSnapshot snap : task.getResult()) {
                        if (snap.contains("head")){
                            if (snap.contains("body")){
                                if (snap.contains("image")){
                                    list.add(new Note(snap.get("head").toString(), snap.get("body").toString(), snap.get("image").toString(), snap.getId()));
                                }else{
                                    list.add(new Note(snap.get("head").toString(), snap.get("body").toString(), snap.getId()));
                                }
                            }else{
                                list.add(new Note(snap.get("head").toString(),snap.getId()));

                            }

                        }
                    }
                    // notify the adapter
                    mainActivity.notifyAdapter();
                    if(task.getResult().size() > 0) {
                        lastVisible = task.getResult().getDocuments().get(task.getResult().size() - 1);
                    }
                    if(task.getResult().size() < LIMIT){
                        mainActivity.setIsLastItemReached(true);
                    }
                }
            }
        });

    }

    public static void getNextNotes() {
        addOneTimeQuery(db.collection(path)
                .orderBy("head")
                .startAfter(lastVisible)
                .limit(LIMIT));
    }
}
