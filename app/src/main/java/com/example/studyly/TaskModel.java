package com.example.studyly;

public class TaskModel {
    String task;

    public TaskModel(){

    }

    public TaskModel(String task){
        this.task = task;
    }

    public String getTask() {
        return task;
    }

    public void setTask(String task) {
        this.task = task;
    }
}
