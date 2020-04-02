package com.example.week14_cloudlogin.storage;

import com.example.week14_cloudlogin.model.Note;

import java.util.ArrayList;

public class NoteStorage {

    public static ArrayList<Note> list = new ArrayList<>();

    public static ArrayList<Note> getList(){

        return list;
    }

}
