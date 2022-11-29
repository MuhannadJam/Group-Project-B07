package com.example.b07project;

import java.util.ArrayList;

public class Course {
        String name;
        String code;
        char session; //F, W, S for fall, winter and summer
        ArrayList<String> prereq;

    public Course(String name, String code, char session, ArrayList<String> prereq) {
        this.name = name;
        this.code = code;
        this.session = session;
        this.prereq.addAll(prereq);
    }
}
