package com.example.kea_week9_cloud_notebook.storage;

import com.example.kea_week9_cloud_notebook.model.Note;

import java.util.ArrayList;

public class NoteStorage {

    public static ArrayList<Note> list = new ArrayList<>();

    public static ArrayList<Note> getList(){

        return list;
    }

}
