package com.codingblocks.education.Fragments;


import android.app.Dialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.codingblocks.education.EntityClasses.Chapter;
import com.codingblocks.education.MainActivity;
import com.codingblocks.education.R;
import com.muddzdev.styleabletoast.StyleableToast;

/**
 * A simple {@link Fragment} subclass.
 */
public class start_chapter extends Fragment {

    EditText name_of_chapter ;
    AutoCompleteTextView name_of_subject;
    Button  lets_start ;

    public start_chapter() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_start_chapter, container, false);
        name_of_chapter = v.findViewById(R.id.frag_start_chapter_txtview_name_of_chapter) ;
        name_of_subject = v.findViewById(R.id.frag_start_chapter_txtview_subject_of_chapter) ;
        lets_start = v.findViewById(R.id.frag_start_chapter_btn_done) ;
        String subjects[]={"Physics","Chemistry","Mathematics","Computer Science","English","Hindi","Sanskrit",
                "Geography","History","Accounts","Economics","Sociology","Political Science","Biology","Information practice",
                "others"};
        ArrayAdapter<String> arrayAdapter=new ArrayAdapter(getActivity(),android.R.layout.select_dialog_item,subjects);

        name_of_subject.setThreshold(1);//will start working from first character
        name_of_subject.setAdapter(arrayAdapter);
        lets_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String chapterName = name_of_chapter.getText().toString() ;
                String subjectName = name_of_subject.getText().toString() ;
                if(chapterName.equals(null)||subjectName.equals(null))
                    StyleableToast.makeText(getContext(), "Please Provide complete information", Toast.LENGTH_SHORT, R.style.mytoast).show();
                else
                {
                    Chapter chapter = new Chapter() ;
                    chapter.setChapter_name(chapterName);
                    chapter.setChapter_subject(subjectName);
                    MainActivity.myappdatabaseclass.myDaoforchapter().addChapter(chapter);
                    StyleableToast.makeText(getActivity(),"Chapter Created Successfully",Toast.LENGTH_SHORT).show();
                    Fragment fragment = new start_chapter_second_page() ;
                    MainActivity.fragmentManager.beginTransaction().replace(R.id.new_container,fragment)
                            .addToBackStack(null).commit() ;

                    Bundle args = new Bundle();
                    args.putString("chapterName", chapterName);
                    args.putString("chapeterSubject",subjectName);
                    fragment.setArguments(args);


                }
            }
        });




        return v ;  }

}
