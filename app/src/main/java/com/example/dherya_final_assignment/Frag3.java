package com.example.dherya_final_assignment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class Frag3 extends Fragment {

    public static TextView frag3_value,frag3_key;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View myInflatedView = inflater.inflate(R.layout.fragment_frag3, container, false);

        frag3_value = myInflatedView.findViewById(R.id.frag_value);
        fetchJsonData process = new fetchJsonData();
        process.execute();
        return myInflatedView;
    }
}