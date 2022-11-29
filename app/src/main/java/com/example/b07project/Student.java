package com.example.b07project;

import java.util.ArrayList;

public class Student extends User{

    static ArrayList <Student> students = new ArrayList<Student>();

    public Student () {

    }

    public Student(String name, String email)  {
        this.name = name;
        this.email = email;

    }

    public void addStudent() {
        students.add(this);
    }
}
