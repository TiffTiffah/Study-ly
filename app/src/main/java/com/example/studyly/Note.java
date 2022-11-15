package com.example.studyly;

public class Note {

    String note, title;
    public Note(){

    }
    public Note(String title, String note){
        this.note = note;
        this.title = title;
    }

    public String getNote() {
        return note;
    }

    public String getTitle() {
        return title;
    }
}
