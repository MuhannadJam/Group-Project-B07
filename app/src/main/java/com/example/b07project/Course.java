package com.example.b07project;

import android.view.View;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Course course = (Course) o;
        return code.equals(course.code);
    }

    @Override
    public int hashCode() {
        return Objects.hash(code);
    }
}
