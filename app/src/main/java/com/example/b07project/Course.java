package com.example.b07project;

public class Course {
        String str_name;
        String str_code;
        char char_session; //F, W, S for fall, winter and summer
        String str_prereq;

        public Course(String str_name, String str_code, char char_session, String str_prereq) {
            this.str_name = str_name;
            this.str_code = str_code;
            this.char_session = char_session;
            this.str_prereq = str_prereq;
        }
}
