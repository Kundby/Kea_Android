package com.example.week13_adjustive_recyclerview.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.week13_adjustive_recyclerview.R;
import com.example.week13_adjustive_recyclerview.model.Note;
import com.example.week13_adjustive_recyclerview.storage.NoteStorage;
import com.example.week13_adjustive_recyclerview.view.ViewHolder;

public class MyRecycleViewAdapter extends RecyclerView.Adapter<ViewHolder> {

    private static final int WITH_IMAGE = 1;
    private static final int TEXT_ONLY = 2;
    private ImageView imageView;

    private NoteStorage noteStorage;

    public MyRecycleViewAdapter(NoteStorage noteStorage) {
        this.noteStorage = noteStorage;
    }

    @NonNull
    @Override                                               // parent is "this" of MainActivity
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LinearLayout ll = null;
        if(viewType == TEXT_ONLY){
            ll = (LinearLayout) LayoutInflater.from(parent.getContext()).
                    inflate(R.layout.custom_row_text, parent, false); //SKIFT TIL KUN AT VÆRE MED TEKST
        }else if(viewType == WITH_IMAGE){
            ll = (LinearLayout) LayoutInflater.from(parent.getContext()).
                    inflate(R.layout.custom_row_with_image, parent, false);//SKIFT CUSTOM ROW TIL AT VÆRE MED BILLEDE
        }

        return new ViewHolder(ll); // pass the linearLayout as constructor arg. to MyViewHolder
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.setData(position);
    }

    @Override
    public int getItemCount() {
        return noteStorage.list.size(); // start with dummy number of items
    }

    @Override  // allow us to specify what type of content is at a given position
    public int getItemViewType(int position) {
         Note note = NoteStorage.list.get(position);
         note.hasImage();
         if (note.hasImage()){
             return WITH_IMAGE;
         }else{
             return TEXT_ONLY;
         }
    }
}
