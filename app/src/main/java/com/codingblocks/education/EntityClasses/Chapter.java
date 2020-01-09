package com.codingblocks.education.EntityClasses;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "Chapter_Info")
public class Chapter {
    @PrimaryKey @NonNull
    public String chapter_name ;
    public String chapter_subject ;

    public String getChapter_name() {
        return chapter_name;
    }

    public void setChapter_name(String chapter_name) {
        this.chapter_name = chapter_name;
    }

    public String getChapter_subject() {
        return chapter_subject;
    }

    public void setChapter_subject(String chapter_subject) {
        this.chapter_subject = chapter_subject;
    }
}
