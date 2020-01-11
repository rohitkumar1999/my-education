package com.codingblocks.education.Fragments;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;

import com.codingblocks.education.Fragments.personal_data;
import com.codingblocks.education.Fragments.start_chapter;
import com.codingblocks.education.Fragments.study_result;
import com.codingblocks.education.Fragments.translate_notes;
import com.codingblocks.education.Fragments.view_notes;
import com.codingblocks.education.MainActivity;
import com.codingblocks.education.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class home extends Fragment implements View.OnClickListener {


    public home() {
        // Required empty public constructor
    }

    LinearLayout personal,start_chapter1,view_notes1,study_result1,translate,test_knowledge;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragmentSu
        getActivity().getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);


        View view= inflater.inflate(R.layout.fragment_home, container, false);



        personal=view.findViewById(R.id.personal_data);
        start_chapter1=view.findViewById(R.id.start_chapter);
        view_notes1=view.findViewById(R.id.view_notes);
        study_result1=view.findViewById(R.id.study_result);
        translate=view.findViewById(R.id.translate_notes);
        test_knowledge=view.findViewById(R.id.test);


        personal.setOnClickListener(this);
        start_chapter1.setOnClickListener(this);
        view_notes1.setOnClickListener(this);
        study_result1.setOnClickListener(this);
        translate.setOnClickListener(this);
        test_knowledge.setOnClickListener(this);
        return  view;
    }
    Fragment fragment = null ;

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.personal_data :
                fragment=new personal_data();
                break;

            case R.id.start_chapter :
                fragment=new start_chapter();
                break;
            case R.id.view_notes:
                fragment=new view_notes();
                break;
            case R.id.study_result :
                fragment=new study_result();
                break;
            case R.id.translate_notes :
                fragment=new translate_notes();
                break;
            case R.id.test :
                fragment=new test_knowledge();
                break;


        }

        MainActivity.fragmentManager.beginTransaction().replace(R.id.new_container, fragment).addToBackStack(null).commit();
    }
}