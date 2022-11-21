package com.example.studyly;

    public class ToDoModel {
        String task;
        public ToDoModel(){

        }

        public ToDoModel(String task){
            this.task = task;
        }
        public String getTask() {
            return task;
        }
        public void setTask(String task) {
            this.task = task;
        }
}
