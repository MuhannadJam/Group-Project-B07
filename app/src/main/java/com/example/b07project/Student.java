package com.example.b07project;

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

        if (!(coursesPlanned.contains(course))) {
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
            for (Course course: coursesPlanned) {
                if (canTake(course)) {
                    timetable.add(course);
                    coursesPlanned.remove(course);
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
