package com.example.b07project;

import java.util.ArrayList;

public class Course {
        String str_name;
        String str_code;
        char char_session; //F, W, S for fall, winter and summer
        ArrayList<String> arrlst_prereq = new ArrayList<String>();

    public Course(String str_name, String str_code, char char_session, ArrayList<String> arrlst_prereq) {
        this.str_name = str_name;
        this.str_code = str_code;
        this.char_session = char_session;
        this.arrlst_prereq = arrlst_prereq;
    }
}
