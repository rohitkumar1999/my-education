package com.codingblocks.education.Fragments;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.codingblocks.education.R;
import com.muddzdev.styleabletoast.StyleableToast;

/**
 * A simple {@link Fragment} subclass.
 */
public class start_chapter extends Fragment {

    TextView name_of_chapter,name_of_subject ;
    Button  lets_start ;


    public start_chapter() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_start_chapter, container, false);
        name_of_chapter = v.findViewById(R.id.frag_start_chapter_txtview_name_of_chapter) ;
        name_of_subject = v.findViewById(R.id.frag_start_chapter_txtview_subject_of_chapter) ;
        lets_start = v.findViewById(R.id.frag_start_chapter_btn_done) ;
        lets_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String chapterName = name_of_chapter.getText().toString() ;
                String subjectName = name_of_subject.getText().toString() ;
                if(chapterName.equals(null)||subjectName.equals(null))
                    StyleableToast.makeText(getContext(), "Please Provide complete information", Toast.LENGTH_SHORT, R.style.mytoast).show();
                else
                {

                }
            }
        });




        return v ;  }

}
