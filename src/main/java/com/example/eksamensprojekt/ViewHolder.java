package com.example.eksamensprojekt;

import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.eksamensprojekt.Storage.LogStorage;

//Controls which data will be displayed in the recycler view, in LogOverview.
public class ViewHolder extends RecyclerView.ViewHolder {

    private TextView title, date;

    //assigns the 2 TextViews to the views in my relative layout that makes out the LogOverview.
    public ViewHolder(@NonNull View itemView) {
        super(itemView);
        RelativeLayout relativeLayout = (RelativeLayout) itemView;
        title = relativeLayout.findViewById(R.id.tv_title);
        date = relativeLayout.findViewById(R.id.tv_date);
    }

    //Sets the data of the 2 sets of data to be displayed in the LogOverview. A title(headline) and date of last view(date)
    public void setData(int row){
        title.setText(LogStorage.list.get(row).getHeadline());
        date.setText(LogStorage.list.get(row).getDate());
    }

}
