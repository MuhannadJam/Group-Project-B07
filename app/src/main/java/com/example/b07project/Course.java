package com.example.b07project;

import java.util.ArrayList;

public class Course {
    public String name;
    public String code;
    public String level; //A, B, C respectively perhaps uneccesary because could use 4th char in code string
    public ArrayList<String> session= new ArrayList<>(); //F, W, S for fall, winter and summer
    public ArrayList<Course> prereq = new ArrayList<>();

    public Course(String name, String code, String level, ArrayList<String> session, ArrayList<Course> prereq) {
        this.name = name;
        this.code = code;
        this.level = level;
        this.session.addAll(session);
        this.prereq.addAll(prereq);
    }
}
