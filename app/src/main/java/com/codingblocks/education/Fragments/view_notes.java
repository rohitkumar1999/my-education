package com.codingblocks.education.Fragments;


import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ExpandableListView;
import android.widget.TextView;
import android.widget.Toast;

import com.codingblocks.education.EntityClasses.Chapter;
import com.codingblocks.education.MainActivity;
import com.codingblocks.education.R;
import com.codingblocks.education.expandableadapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 */

    public class view_notes extends Fragment {
        TextView textView;
        ExpandableListView expandableListView;
        androidx.appcompat.widget.Toolbar toolbar;



    public view_notes() {
        // Required empty public constructor
    }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {

            View view = inflater.inflate(R.layout.fragment_view_notes, container, false);
            //     List<Chapter> list = MainActivity.myappdatabaseclass.myDaoforchapter().fetch_data_of_chapter() ;
            expandableListView = view.findViewById(R.id.expandable_list);
            List<String> listgroup = new ArrayList<>();
            HashMap<String, List<String>> listitem = new HashMap<>();
            listgroup = MainActivity.myappdatabaseclass.myDaoforchapter().fetch_all_different_subjects();
            List<Chapter> listofchapter = MainActivity.myappdatabaseclass.myDaoforchapter().fetch_data_of_chapter();
            for (int i = 0; i < listofchapter.size(); i++) {
                addToMap(listitem, listofchapter.get(i).getChapter_subject(), listofchapter.get(i).getChapter_name());
            }
            for (Map.Entry<String, List<String>> entry : listitem.entrySet()) {
                String key = entry.getKey();
                List<String> value = entry.getValue();
                Log.d("checking hash", key + ":" + value.toString());
            }
            DisplayMetrics metrics = new DisplayMetrics();
            getActivity().getWindowManager().getDefaultDisplay().getMetrics(metrics);
            int width = metrics.widthPixels;

            expandableListView.setIndicatorBounds(width - GetPixelFromDips(50), width - GetPixelFromDips(10));
            toolbar = view.findViewById(R.id.frag_view_notes_toolbar);
            ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
            toolbar.setTitle("Your Notes");
            toolbar.setTitleTextColor(getResources().getColor(R.color.view_notes_title));

            toolbar.setLogo(R.drawable.ic_professions_and_jobs);
            toolbar.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));


            expandableadapter expandableadapter = new expandableadapter(getContext(), listgroup, listitem);
            expandableListView.setAdapter(expandableadapter);
            Log.d("child click", "outside adapter");
            expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
                @Override
                public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                    Log.d("child click", "into child");
                    int i = 0;
                    String s = (String) expandableadapter.getChild(groupPosition, childPosition);
 Fragment fragment=new notes_fragment();
                    MainActivity.fragmentManager.beginTransaction().replace(R.id.new_container,fragment)
                            .addToBackStack(null).commit() ;

                    return true;
                }
            });


            return view;
        }

        public void addToMap(HashMap<String, List<String>> map, String key, String value) {
            if (!map.containsKey(key)) {
                map.put(key, new ArrayList<>());
            }
            map.get(key).add(value);

        }

        public int GetPixelFromDips(float pixels) {
            // Get the screen's density scale
            final float scale = getResources().getDisplayMetrics().density;
            // Convert the dps to pixels, based on density scale
            return (int) (pixels * scale + 0.5f);
        }

}