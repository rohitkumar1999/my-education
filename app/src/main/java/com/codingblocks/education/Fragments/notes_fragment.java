package com.codingblocks.education.Fragments;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.codingblocks.education.R;

public class notes_fragment extends Fragment {

    ImageButton backButton,listenButton ;
    TextView chapterName,chapterScannedTreanslatedNotes ;


    public notes_fragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
       View view =  inflater.inflate(R.layout.fragment_notes_fragment, container, false);
       backButton = view.findViewById(R.id.frag_translate_notes_back_arrow) ;
       listenButton = view.findViewById(R.id.frag_translate_notes_mic) ;
       chapterName = view.findViewById(R.id.frag_translate_notes_file_name) ;
       chapterScannedTreanslatedNotes = view.findViewById(R.id.frag_translate_notes_notes) ;
       backButton.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {

           }
       });
       listenButton.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {

           }
       });
        return view;
    }

}
