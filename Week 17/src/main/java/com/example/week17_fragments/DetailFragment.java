package com.example.week17_fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class DetailFragment extends Fragment {

    private TextView textView;
    Button btn_back;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
       View v = inflater.inflate(R.layout.layout_detail, container, false);
       textView = v.findViewById(R.id.detail_Text);
       btn_back = v.findViewById(R.id.but_back);
       btn_back.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               getFragmentManager().beginTransaction().replace(R.id.fragmentContainer, new ListFragment()).commit();
           }
       });

        Bundle data = getArguments();
        if (data != null){
            String text = data.getString("key");
            textView.setText("Recieved Data: " + text);
        }


       return v;
    }

}
