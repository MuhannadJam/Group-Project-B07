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
    public ArrayList<String> session= new ArrayList<>(); //F, W, S for fall, winter and summer
    public ArrayList<Course> prereq = new ArrayList<>();

    public Course(String name, String code, ArrayList<String> session,
                  ArrayList<Course> prereq) {
        this.name = name;
        this.code = code;
        this.session.addAll(session);
        this.prereq.addAll(prereq);
    }

    public void getCourses(ArrayList <Course> courses, View view) {
        DatabaseReference ref = FirebaseDatabase.
                getInstance("https://bo7-project-default-rtdb.firebaseio.com/").
                getReference("Courses");

        ref.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (task.isSuccessful()) {
                    for (DataSnapshot child: task.getResult().getChildren()) {
                        Course course = child.getValue(Course.class);
                        courses.add(course);
                    }
                }
                else {
                    return;
                }
            }
        });
    }


}
