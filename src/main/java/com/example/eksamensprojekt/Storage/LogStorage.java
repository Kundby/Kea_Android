package com.example.eksamensprojekt.Storage;

import com.example.eksamensprojekt.model.Logs;

import java.util.ArrayList;

//Used for temporary storage in an arraylist, when using the app.
public class LogStorage {

    public static ArrayList<Logs> list = new ArrayList<>();

    public static ArrayList<Logs> getList(){

        return list;
    }

}