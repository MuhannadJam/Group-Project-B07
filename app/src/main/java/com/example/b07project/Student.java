package com.example.b07project;

import android.util.Log;

import java.util.ArrayList;

public class Student {

    public String name;
    public String email;

    public ArrayList <Course> coursesTaken= new ArrayList<Course>();
    public ArrayList <Course> coursesPlanned= new ArrayList<Course>();
    public ArrayList <Course> timetable = new ArrayList<>();

    static ArrayList<Student> students = new ArrayList<Student>();

    public Student() {

    }

    public Student(String name, String email, ArrayList coursesTaken) {
        this.name = name;
        this.email = email;
        this.coursesTaken.addAll(coursesTaken);

    }

    public void addCourseToPlanned(Course course){


        if (!(coursesPlanned.contains(course) || coursesTaken.contains(course))) {
            coursesPlanned.add(course);
        }

        if (course.prereq.isEmpty()) {
            return;
        }

        for (Course prereq: course.prereq) {
            addCourseToPlanned(prereq);
        }

    }

    public void generateTimetable() {
        timetable = new ArrayList<Course>();


        int j = coursesPlanned.size();

        while (j > 0) {
            for (int i = 0; i < coursesPlanned.size(); i++) {
                if (coursesPlanned.get(i).prereq.isEmpty() || canTake(coursesPlanned.get(i))) {
                    timetable.add(coursesPlanned.get(i));
                    coursesPlanned.remove(coursesPlanned.get(i));
                    i--;
                }
            }
            j = coursesPlanned.size();
        }


    }

    public boolean canTake(Course course) {

        for (Course prereq: course.prereq) {
            if (!(coursesTaken.contains(prereq) || timetable.contains(prereq))) {
                return false;
            }
        }
        return true;

    }


}
