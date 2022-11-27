package com.example.b07project;

import java.util.ArrayList;

public class Student extends User{

    ArrayList <Student> students = new ArrayList<Student>();

    public Student () {

    }

    public Student(String name, String username) {
        this.name = name;
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void addStudent() {
        students.add(this);
    }
}
