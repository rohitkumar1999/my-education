package com.codingblocks.education;

import android.content.Context;
import android.graphics.Typeface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import androidx.core.view.LayoutInflaterCompat;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class expandableadapter extends BaseExpandableListAdapter {
    private List<String> header_titles;
    private HashMap<String,List<String>>  child_titles;
    private Context context;
    public   expandableadapter (Context context,List<String> header_titles,HashMap<String,List<String>> child_titles)
    {
        this.context= context;
        this.child_titles=child_titles;
        this.header_titles=header_titles;
    }


    @Override
    public int getGroupCount() {
        return header_titles.size();
    }

    @Override
    public int getChildrenCount(int i) {
        return child_titles.get(header_titles.get(i)).size();

    }


    @Override
    public Object getGroup(int i) {
        return header_titles.get(i);
    }

    @Override
    public Object getChild(int i, int i1) {
        return child_titles.get(header_titles.get(i)).get(i1);
    }

    @Override
    public long getGroupId(int i) {
        return i;
    }

    @Override
    public long getChildId(int i, int i1) {
        return i1;
    }

    @Override
    public boolean areAllItemsEnabled() {
        return true;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public View getGroupView(int i, boolean b, View view, ViewGroup viewGroup) {
        String main_heading=this.getGroup(i).toString();

        if(view==null)
        {
            LayoutInflater layoutInflater=(LayoutInflater)this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view=layoutInflater.inflate(R.layout.parent,null);

        }
        TextView textView=view.findViewById(R.id.expandable_list_top_heading);
        textView.setTypeface(null, Typeface.BOLD);
        textView.setText(" "+main_heading);
        return view;
    }

    @Override
    public View getChildView(int i, int i1, boolean b, View view, ViewGroup viewGroup) {
        String sub_heading=this.getChild(i,i1).toString();
        if(view==null)
        {
            LayoutInflater layoutInflater=(LayoutInflater)this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view=layoutInflater.inflate(R.layout.child,null);

        }
        TextView textView=view.findViewById(R.id.expandable_list_child_heading);
        textView.setText(" "+sub_heading);
        return view;
    }

    @Override
    public boolean isChildSelectable(int i, int i1) {
        return true;
    }

    public List<String> getHeader_titles() {
        return header_titles;
    }

    public void setHeader_titles(List<String> header_titles) {
        this.header_titles = header_titles;
    }

    public HashMap<String, List<String>> getChild_titles() {
        return child_titles;
    }

    public void setChild_titles(HashMap<String, List<String>> child_titles) {
        this.child_titles = child_titles;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }
}