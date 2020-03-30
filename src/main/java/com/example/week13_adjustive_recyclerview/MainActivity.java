package com.example.week13_adjustive_recyclerview;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.AbsListView;

import com.example.week13_adjustive_recyclerview.adapter.MyRecycleViewAdapter;
import com.example.week13_adjustive_recyclerview.storage.NoteStorage;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private MyRecycleViewAdapter myAdapter;
    private boolean isLastItemReached;
    private boolean isScrolling;
    private LinearLayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        NoteStorage.init(this);
        recyclerView = findViewById(R.id.main_recycler);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        myAdapter = new MyRecycleViewAdapter(new NoteStorage());
        recyclerView.setAdapter(myAdapter);
        setupScrollListener();
    }

    private void setupScrollListener(){
        RecyclerView.OnScrollListener scrollListener = new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL){
                    isScrolling = true;
                }
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition();
                int visibleItemCount = layoutManager.getChildCount();
                int totalItemCount = layoutManager.getItemCount();
                if (isScrolling && (firstVisibleItemPosition + visibleItemCount == totalItemCount) && !isLastItemReached){
                    isScrolling = false;
                    NoteStorage.getNextNotes();
                }
            }
        };
        recyclerView.addOnScrollListener(scrollListener);
    }

    public void notifyAdapter() {
        myAdapter.notifyDataSetChanged();
    }

    public void setIsLastItemReached(boolean b) {
        isLastItemReached = b;
    }
}
