package com.codingblocks.education.EntityClasses;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "Notes_Info")

public class Notes {
    @PrimaryKey
    @NonNull
    public String chapter_name ;
    public String generated_notes ;
    public String scanned_notes ;
    public String scanned_test ;
    public String subject ;

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    @NonNull
    public String getChapter_name() {
        return chapter_name;
    }

    public void setChapter_name(@NonNull String chapter_name) {
        this.chapter_name = chapter_name;
    }

    public String getGenerated_notes() {
        return generated_notes;
    }

    public void setGenerated_notes(String generated_notes) {
        this.generated_notes = generated_notes;
    }

    public String getScanned_notes() {
        return scanned_notes;
    }

    public void setScanned_notes(String scanned_notes) {
        this.scanned_notes = scanned_notes;
    }

    public String getScanned_test() {
        return scanned_test;
    }

    public void setScanned_test(String scanned_test) {
        this.scanned_test = scanned_test;
    }
}
