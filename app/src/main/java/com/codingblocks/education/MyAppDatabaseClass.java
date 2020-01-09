package com.codingblocks.education;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.codingblocks.education.Dao.MyDaoforchapter;
import com.codingblocks.education.EntityClasses.Chapter;
import com.codingblocks.education.EntityClasses.Notes;

@Database(entities = {Chapter.class, Notes.class},version = 1)
public abstract class MyAppDatabaseClass extends RoomDatabase {
    public abstract MyDaoforchapter myDaoforchapter() ;



}
