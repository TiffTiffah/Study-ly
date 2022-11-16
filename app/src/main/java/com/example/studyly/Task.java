package com.example.studyly;


import java.util.Date;

public class Task {
    String Description;
    Date date;

    public Task(){

    }
    public Task(String Description, Date date){
        this.Description = Description;
        this.date = date;

    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
