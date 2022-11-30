package com.example.b07project;

import java.util.ArrayList;

public class Course {
        public String name;
        public String code;
        public char session; //F, W, S for fall, winter and summer
        public ArrayList<String> prereq;

    public Course(String name, String code, char session, ArrayList<String> pre) {
        this.name = name;
        this.code = code;
        this.session = session;
        this.prereq.addAll(pre);
    }
}
