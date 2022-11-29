package com.example.b07project;

import java.util.ArrayList;

public class Student {

    public String name;
    public String email;

    static ArrayList<Student> students = new ArrayList<Student>();

    public Student() {

    }

    public Student(String name, String email) {
        this.name = name;
        this.email = email;

    }

}
