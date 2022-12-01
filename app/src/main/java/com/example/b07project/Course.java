package com.example.b07project;

import android.view.View;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class Course {
    public String name;
    public String code;
    public ArrayList<String> session= new ArrayList<>(); //Fall, Winter, Summer
    public ArrayList<Course> prereq = new ArrayList<>();

    public Course() {

    }
    public Course(String name, String code, ArrayList<String> session,
                  ArrayList<Course> prereq) {
        this.name = name;
        this.code = code;
        this.session.addAll(session);
        this.prereq.addAll(prereq);
    }

}
