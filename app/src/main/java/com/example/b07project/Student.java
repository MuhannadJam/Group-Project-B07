package com.example.b07project;

import android.util.Log;

import java.util.ArrayList;

public class Student {

    public String name;
    public String email;

    public ArrayList <Course> coursesTaken= new ArrayList<Course>();
    public ArrayList <Course> coursesPlanned= new ArrayList<Course>();
    public ArrayList <Course> timetable = new ArrayList<>();
    public ArrayList <Course> sorted = new ArrayList<>();

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

    public void sort() {

        sorted = new ArrayList<>();

        for (int i = 0; i < coursesPlanned.size(); i++) {
            Course course = coursesPlanned.get(i);
            if ((course.session.contains("Fall") && canTake(course)) || sorted.contains(course)) {
                sorted.add(course);
                coursesPlanned.remove(i);
                i--;
            }
        }

        for (int j = 0; j < coursesPlanned.size(); j++) {
            Course course = coursesPlanned.get(j);
            if ((course.session.contains("Winter") && canTake(course)) || sorted.contains(course)) {
                sorted.add(course);
                coursesPlanned.remove(j);
                j--;
            }
        }
        for (int k = 0; k < coursesPlanned.size(); k++) {
            Course course = coursesPlanned.get(k);
            if ((course.session.contains("Summer") && canTake(course)) || sorted.contains(course)) {
                sorted.add(course);
                coursesPlanned.remove(k);
                k--;
            }
        }

        coursesPlanned.addAll(sorted);
    }


    public void generateTimetable() {
        timetable = new ArrayList<Course>();

        sort();

        for (int n = 0; n < coursesPlanned.size(); n++) {
            if (coursesPlanned.get(n).prereq.isEmpty()) {
                timetable.add(coursesPlanned.get(n));
                coursesPlanned.remove(n);
                n--;
            }
        }

        boolean containsAll = true;
        for (int y = 0; y < coursesPlanned.size(); y++) {
            for (Course prereq: coursesPlanned.get(y).prereq) {
                if (!(coursesTaken.contains(prereq) && prereq.session.contains("Fall"))) {
                    containsAll = false;
                }
            }
            if (containsAll) {
                timetable.add(coursesPlanned.get(y));
                coursesPlanned.remove(coursesPlanned.get(y));
                y--;
            }
            containsAll = true;
        }

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
            if (!(coursesTaken.contains(prereq) || timetable.contains(prereq))){
                return false;
            }
        }
        return true;

    }


}
