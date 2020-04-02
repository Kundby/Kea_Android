package com.example.week14_cloudlogin.adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.week14_cloudlogin.Edit_note;
import com.example.week14_cloudlogin.NotesActivity;
import com.example.week14_cloudlogin.R;
import com.example.week14_cloudlogin.model.Note;
import com.example.week14_cloudlogin.storage.NoteStorage;
import com.example.week14_cloudlogin.view.ViewHolder;

import java.util.ArrayList;

public class MyRecycleViewAdapter extends RecyclerView.Adapter<ViewHolder> {

    private ArrayList<Note> list;
    public static final String headlineKey = "HEADLINE_KEY";
    public static final String bodyKey = "BODY_KEY";
    public static final String idKey = "ID_KEY";

    public MyRecycleViewAdapter() {
        this.list = NoteStorage.getList();
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LinearLayout ll = (LinearLayout) LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_row, parent, false);
        return new ViewHolder(ll);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        holder.setData(position);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), Edit_note.class);
                intent.putExtra(headlineKey, NoteStorage.getList().get(position).getHeadline());
                intent.putExtra(bodyKey, NoteStorage.getList().get(position).getBody());
                intent.putExtra(idKey, NoteStorage.getList().get(position).getId());
                v.getContext().startActivity(intent);
            }

        });
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                NotesActivity.deleteNote(NoteStorage.list.get(position));
                return false;
            }
        });
    }

    @Override
    public int getItemCount() {
        return NoteStorage.list.size();
    }
}
