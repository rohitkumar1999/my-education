package com.codingblocks.education.Dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.codingblocks.education.EntityClasses.Chapter;
import com.codingblocks.education.EntityClasses.Notes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Dao
public interface MyDaoforchapter {

    @Insert
    public void addChapter(Chapter chapter) ;

    @Insert
    public void addNotes(Notes notes) ;

    @Query("Select * from Chapter_Info")
    public List<Chapter> fetch_data_of_chapter() ;

    @Query("Select * from Notes_Info")
    public List<Notes> fetch_data_of_notes() ;

    @Query("Select Distinct subject from Notes_Info")
    public List<String> fetch_all_different_subjects() ;



}
