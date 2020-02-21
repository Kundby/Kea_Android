package com.example.kea_week8_mynotebook;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.kea_week8_mynotebook.adapter.MyRecycleViewAdapter;
import com.example.kea_week8_mynotebook.model.Note;
import com.example.kea_week8_mynotebook.storage.NoteStorage;

public class Edit_note extends AppCompatActivity implements View.OnClickListener {

    private EditText headline_input, body_input;
    private Button but_back;
    private Boolean isEditing = false;
    private int position;
    private String headline_text, body_text;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_note);

        headline_input = findViewById(R.id.editText_headline);
        body_input = findViewById(R.id.editText_note);
        but_back = findViewById(R.id.btn_back);
        but_back.setOnClickListener(this);

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        if (extras == null){

        }else{
            position = intent.getExtras().getInt(MyRecycleViewAdapter.positionKey);
            headline_text = intent.getExtras().getString(MyRecycleViewAdapter.headlineKey);
            headline_input.setText(headline_text);
            body_text = intent.getExtras().getString(MyRecycleViewAdapter.bodyKey);
            body_input.setText(body_text);
            if (!headline_text.isEmpty() || body_text.isEmpty()){
                isEditing = true;
            }
        }

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){

            case R.id.btn_back:
                if (!isEditing) {
                    NoteStorage.getList().add(new Note(headline_input.getText().toString(), body_input.getText().toString()));
                }else{
                    NoteStorage.getList().set(position, new Note(headline_input.getText().toString(), body_input.getText().toString()));
                }
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                break;

        }
    }
}
