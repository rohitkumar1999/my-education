package com.codingblocks.education.Fragments;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.codingblocks.education.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class start_chapter extends Fragment {


    public start_chapter() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_start_chapter, container, false);
    }

}
