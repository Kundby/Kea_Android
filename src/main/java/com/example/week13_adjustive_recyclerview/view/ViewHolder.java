package com.example.week13_adjustive_recyclerview.view;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.week13_adjustive_recyclerview.R;
import com.example.week13_adjustive_recyclerview.model.Note;
import com.example.week13_adjustive_recyclerview.storage.NoteStorage;

public class ViewHolder extends RecyclerView.ViewHolder {

    public TextView textView;
    private ImageView imageView;

    public ViewHolder(@NonNull View itemView) {
        super(itemView);
        LinearLayout linearLayout = (LinearLayout) itemView;
        textView = linearLayout.findViewById(R.id.custom_row_textView);
        imageView = linearLayout.findViewById(R.id.custom_row_imageView);
    }

    public void setData(int row){
        textView.setText(NoteStorage.list.get(row).getHeadline());
        Note note = NoteStorage.list.get(row);
        if (note.hasImage()){
            imageView.setImageResource(R.drawable.ic_launcher_foreground);
        }

    }

}