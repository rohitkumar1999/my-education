package com.codingblocks.education.Fragments;


import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.codingblocks.education.R;
import com.codingblocks.education.signup;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * A simple {@link Fragment} subclass.
 */
public class personal_data extends Fragment {



    public personal_data() {
        // Required empty public constructor

    }
    ArrayList<String> strings= signup.arrayList;

    CircleImageView imageView;
    TextView user_id,name,designation,email,language,phone,myclass;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //  assert getArguments() != null;


        String email1=strings.get(1);

        String designation1=strings.get(2);
        String language1=strings.get(5);
        String myclass1=strings.get(6);


        String phone1=strings.get(4);
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_personal_data, container, false);

        user_id=view.findViewById(R.id.frag_personal_data_user_id);
        myclass=view.findViewById(R.id.frag_personal_data_class);
        name=view.findViewById(R.id.frag_personal_data_name);
        designation=view.findViewById(R.id.frag_personal_data_designation);
        email=view.findViewById(R.id.frag_personal_data_mail);
        language=view.findViewById(R.id.frag_personal_data_language);
        phone=view.findViewById(R.id.frag_personal_data_contact);
        imageView=view.findViewById(R.id.frag_personal_data_image);
        //
        //
        //
        //
        //
        //
        //
        //
        //
        //
        //
        // imageView.setImageBitmap(bmp);



        name.setText(strings.get(0));
        email.setText(email1);
        designation.setText(designation1);
//
        language.setText(language1);
        phone.setText(phone1);
        myclass.setText(myclass1);
        // Log.d("data", name1);
//        Log.d("data", designation1);
//
        Log.d("data", email1);
//        Log.d("data",pass1);
//        Log.d("data", repass1);
        //Log.d("data",phone1);
        return view;
    }

}
