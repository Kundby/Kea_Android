package com.example.eksamensprojekt;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.eksamensprojekt.Storage.LogStorage;
import com.example.eksamensprojekt.model.Logs;

import java.util.ArrayList;

public class MyRecyclerViewAdapter extends RecyclerView.Adapter<ViewHolder> {

    private ArrayList<Logs> list;
    public static final String headlineKey = "HEADLINE_KEY";
    public static final String descriptionKey = "DESCRIPTION_KEY";
    public static final String imageKey = "IMAGE_KEY";
    public static final String markerLat = "MARKER_LAT_KEY";
    public static final String markerLon = "MARKER_LON_KEY";
    public static final String idKey = "ID_KEY";

    //Constructor which assign the list of logs to show, to the list found in LogStorage
    public MyRecyclerViewAdapter() {
        this.list = LogStorage.getList();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //Handles which type of view to display - Here it's my custom row containing a RelativeLayout
        RelativeLayout rl = null;
        rl = (RelativeLayout) LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_row, parent, false);
        return new ViewHolder(rl);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        holder.setData(position);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //When clicking an item(log) then start an intent with a bunch of extras(data) sent along with the intent
                //Which will be used for displaying certain data
                Intent intent = new Intent(v.getContext(), Edit_Log.class);
                intent.putExtra(headlineKey, LogStorage.getList().get(position).getHeadline());
                intent.putExtra(descriptionKey, LogStorage.getList().get(position).getDescription());
                intent.putExtra(imageKey, LogStorage.getList().get(position).getImage());
                intent.putExtra(idKey, LogStorage.getList().get(position).getId());
                intent.putExtra(markerLat, LogStorage.getList().get(position).getLat());
                intent.putExtra(markerLon, LogStorage.getList().get(position).getLon());
                v.getContext().startActivity(intent);
            }

        });
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                //If long pressed on an item(log) then run a delete method for that specific log
                LogOverview.deleteLog(LogStorage.list.get(position));
                return false;
            }
        });
    }

    //Number of items to show - Bases off of the size of the list in LogStorage
    @Override
    public int getItemCount() {
        return LogStorage.list.size();
    }
}

