package com.example.b07project;

import java.util.ArrayList;

public class Student {

    public String name;
    public String email;

    public ArrayList <String> courses= new ArrayList<String>();

    static ArrayList<Student> students = new ArrayList<Student>();

    public Student() {

    }

    public Student(String name, String email, ArrayList courses) {
        this.name = name;
        this.email = email;
        this.courses.addAll(courses);

    }

}
