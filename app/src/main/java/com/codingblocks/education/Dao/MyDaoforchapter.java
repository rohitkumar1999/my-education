package com.codingblocks.education.Dao;

import androidx.room.Dao;
import androidx.room.Insert;

import com.codingblocks.education.EntityClasses.Chapter;

@Dao
public interface MyDaoforchapter {

    @Insert
    public void addChapter(Chapter chapter) ;
}
