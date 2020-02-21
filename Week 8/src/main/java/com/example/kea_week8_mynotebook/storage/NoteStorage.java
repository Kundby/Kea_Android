package com.example.kea_week8_mynotebook.storage;

import com.example.kea_week8_mynotebook.model.Note;

import java.util.ArrayList;

public class NoteStorage {

    private static ArrayList<Note> list;

    static {
        list = new ArrayList<>();
    }

    public static Note getNote(int index){
        return list.get(index);
    }

    public static int getLength(){
        return list.size();
    }

    public static ArrayList<Note> getList(){
        return list;
    }

}
