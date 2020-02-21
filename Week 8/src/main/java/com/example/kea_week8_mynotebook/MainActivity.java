package com.example.kea_week8_mynotebook;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import android.widget.ImageButton;

import com.example.kea_week8_mynotebook.adapter.MyRecycleViewAdapter;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageButton btn_add;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.main_recycler);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(new MyRecycleViewAdapter());

        btn_add = findViewById(R.id.but_add);
        btn_add.setOnClickListener(this);

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){

            case R.id.but_add:
                    Intent intent = new Intent(this, Edit_note.class);
                    startActivity(intent);
                break;


        }
    }



}
