package com.example.b07project;

import java.util.ArrayList;

public class Course {
    public String name;
    public String code;
    public String session; //F, W, S for fall, winter and summer
    public ArrayList<String> prereq = new ArrayList<>();

    public Course(String name, String code, String session, ArrayList<String> prereq) {
        this.name = name;
        this.code = code;
        this.session = session;
        this.prereq.addAll(prereq);
    }
}
