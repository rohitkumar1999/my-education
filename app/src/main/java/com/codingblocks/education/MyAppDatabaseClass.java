package com.codingblocks.education;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.codingblocks.education.Dao.MyDaoforchapter;
import com.codingblocks.education.EntityClasses.Chapter;

@Database(entities = {Chapter.class},version = 1)
public abstract class MyAppDatabaseClass extends RoomDatabase {
    public abstract MyDaoforchapter myDaoforchapter() ;



}
