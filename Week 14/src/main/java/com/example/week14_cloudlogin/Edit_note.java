package com.example.week14_cloudlogin;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.week14_cloudlogin.adapter.MyRecycleViewAdapter;
import com.example.week14_cloudlogin.model.Note;

public class Edit_note extends AppCompatActivity implements View.OnClickListener {

    private EditText headline_input, body_input;
    private Button but_back;
    private Boolean isEditing = false;
    private String headline_text, body_text, id;

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
            headline_text = intent.getExtras().getString(MyRecycleViewAdapter.headlineKey);
            headline_input.setText(headline_text);
            body_text = intent.getExtras().getString(MyRecycleViewAdapter.bodyKey);
            body_input.setText(body_text);
            id = intent.getExtras().getString(MyRecycleViewAdapter.idKey);
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
                    NotesActivity.addNewNote(new Note(headline_input.getText().toString(), body_input.getText().toString()));
                }else{
                    NotesActivity.editNote(new Note(headline_input.getText().toString(), body_input.getText().toString(), id));
                }
                Intent intent = new Intent(this, NotesActivity.class);
                startActivity(intent);
                break;

        }
    }
}
