package com.example.b07project;

import java.util.ArrayList;

public class Student {

    public String name;
    public String email;

    public ArrayList <Course> coursesTaken= new ArrayList<Course>();
    public ArrayList <Course> coursesPlanned= new ArrayList<Course>();

    static ArrayList<Student> students = new ArrayList<Student>();

    public Student() {

    }

    public Student(String name, String email, ArrayList coursesTaken) {
        this.name = name;
        this.email = email;
        this.coursesTaken.addAll(coursesTaken);

    }

    public ArrayList<Course> generateTimetable(Student s) {
        ArrayList<Course> timetable = new ArrayList<Course>();
        for (int i = 0; i < s.coursesPlanned.size(); i++) {
            for (int k = 0; k < s.coursesTaken.size(); k++) {
                if (!(s.coursesTaken.contains(s.coursesPlanned.get(i).prereq.get(k)))) {
                    if (!(s.coursesPlanned.contains(s.coursesPlanned.get(i).prereq.get(k)))) {
                        s.coursesPlanned.add((s.coursesPlanned.get(i).prereq.get(k)));
                    }
                }
            }
        }
        int j = s.coursesPlanned.size();
        while (j > 0) {
            for (int i =0; i < s.coursesPlanned.size(); i++) {
                if ((s.coursesPlanned.get(i).prereq.isEmpty() || s.coursesPlanned.get(i).prereq.containsAll(s.coursesTaken)) && (s.coursesPlanned.get(i).session.contains("F"))) {
                    //assume that user is making a timetable before Fall Session
                    s.coursesTaken.add(s.coursesPlanned.get(i));//
                    timetable.add(s.coursesPlanned.get(i));
                    s.coursesPlanned.remove(s.coursesPlanned.get(i));
                    j--;
                }
            }
            for (int i =0; i < s.coursesPlanned.size(); i++) {
                if ((s.coursesPlanned.get(i).prereq.isEmpty() || s.coursesPlanned.get(i).prereq.containsAll(s.coursesTaken)) && (s.coursesPlanned.get(i).session.contains("W"))) {
                    s.coursesTaken.add(s.coursesPlanned.get(i));
                    timetable.add(s.coursesPlanned.get(i));
                    s.coursesPlanned.remove(s.coursesPlanned.get(i));
                    j--;
                }
            }
            for (int i =0; i < s.coursesPlanned.size(); i++) {
                if ((s.coursesPlanned.get(i).prereq.isEmpty() || s.coursesPlanned.get(i).prereq.containsAll(s.coursesTaken)) && (s.coursesPlanned.get(i).session.contains("S"))) {
                    s.coursesTaken.add(s.coursesPlanned.get(i));
                    timetable.add(s.coursesPlanned.get(i));
                    s.coursesPlanned.remove(s.coursesPlanned.get(i));
                    j--;
                }
            }

        }
        return timetable;
    }


}
