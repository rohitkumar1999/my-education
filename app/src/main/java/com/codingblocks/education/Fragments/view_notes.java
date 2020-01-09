package com.codingblocks.education.Fragments;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ExpandableListView;
import android.widget.TextView;

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
    TextView textView ;
ExpandableListView  expandableListView;

    public view_notes() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_view_notes, container, false) ;
        List<Chapter> list = MainActivity.myappdatabaseclass.myDaoforchapter().fetch_data_of_chapter() ;
expandableListView=view.findViewById(R.id.expandable_list);
List<String> listgroup=new ArrayList<>();
        HashMap<String,List<String>> listitem=new HashMap<>();
        listgroup = MainActivity.myappdatabaseclass.myDaoforchapter().fetch_all_different_subjects() ;
        List<Chapter> listofchapter = MainActivity.myappdatabaseclass.myDaoforchapter().fetch_data_of_chapter() ;
        for(int i=0;i<listofchapter.size();i++)
        {
            addToMap(listitem,listofchapter.get(i).getChapter_subject(),listofchapter.get(i).getChapter_name()) ;
        }
        for (Map.Entry<String,List<String>> entry : listitem.entrySet()) {
            String key = entry.getKey();
            List<String> value = entry.getValue();
            Log.d("checking hash", key + ":" + value.toString());
        }


        expandableadapter expandableadapter =new expandableadapter(getContext(),listgroup,listitem);
        expandableListView.setAdapter(expandableadapter);
        return view;
    }
    public void addToMap(HashMap<String, List<String>> map, String key, String value){
        if(!map.containsKey(key)){
            map.put(key, new ArrayList<>());
        }
        map.get(key).add(value);

    }

}
