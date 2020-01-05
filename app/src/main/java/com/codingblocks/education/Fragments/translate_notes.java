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
public class translate_notes extends Fragment {


    public translate_notes() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_translate_notes, container, false);
    }

}
