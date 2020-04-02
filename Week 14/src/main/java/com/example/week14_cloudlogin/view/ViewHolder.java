package com.example.week14_cloudlogin.view;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.week14_cloudlogin.R;
import com.example.week14_cloudlogin.storage.NoteStorage;

public class ViewHolder extends RecyclerView.ViewHolder {

    public TextView textView;

    public ViewHolder(@NonNull View itemView) {
        super(itemView);
        LinearLayout linearLayout = (LinearLayout) itemView;
        textView = linearLayout.findViewById(R.id.custom_row_textView);
    }

    public void setData(int row){
        textView.setText(NoteStorage.list.get(row).getHeadline());
    }

}
