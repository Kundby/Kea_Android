package com.example.week17_fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

public class ListFragment extends Fragment {

    private MainActivity mainActivity;

    public static ListFragment newInstance(MainActivity mainActivity){
        ListFragment listFragment = new ListFragment();
        listFragment.mainActivity = mainActivity;
        return listFragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.layout_list, container, false);

        ListView listView = v.findViewById(R.id.fragment_listView);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TextView tv = (TextView) view;
                if (tv != null) {
                   String text = tv.getText().toString();
                   DetailFragment detailFragment = new DetailFragment();
                   Bundle data = new Bundle();
                   FragmentTransaction ft = getFragmentManager().beginTransaction();
                   data.putString("key", text);
                   detailFragment.setArguments(data);
                   ft.replace(R.id.fragmentContainer, detailFragment).commit();
                }
            }
        });

        return v;
    }



}
