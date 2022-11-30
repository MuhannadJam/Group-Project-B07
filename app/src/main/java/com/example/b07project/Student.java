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

    public void addCourseToPlanned(Course course){
        coursesPlanned.add(course);
        if (!(coursesTaken.containsAll(course.prereq))) {
            for (int k = 0; k < course.prereq.size(); k++) {
                if ((!(coursesPlanned.contains(course.prereq.get(k)))) && !(coursesTaken.contains(course.prereq.get(k)))) {
                    coursesPlanned.add((course.prereq.get(k)));
                }
            }
        }

    }
    public ArrayList<Course> generateTimetable(Student s) {
        ArrayList<Course> timetable = new ArrayList<Course>();
        int j = s.coursesPlanned.size();
        while (j > 0) {
            for (int i =0; i < s.coursesPlanned.size(); i++) {
                if ((s.coursesPlanned.get(i).prereq.isEmpty() || s.coursesTaken.containsAll(s.coursesPlanned.get(i).prereq)) && (s.coursesPlanned.get(i).session.contains("F")) && !(timetable.contains(s.coursesPlanned.get(i)))) {
                    //assume that user is making a timetable before Fall Session
                    s.coursesTaken.add(s.coursesPlanned.get(i));//instead of doing this i can duplicate courses taken and use another one
                    timetable.add(s.coursesPlanned.get(i));
                    s.coursesPlanned.remove(s.coursesPlanned.get(i));
                    j--;
                }
            }
            for (int i =0; i < s.coursesPlanned.size(); i++) {
                if ((s.coursesPlanned.get(i).prereq.isEmpty() || s.coursesTaken.containsAll(s.coursesPlanned.get(i).prereq)) && (s.coursesPlanned.get(i).session.contains("W"))&& !(timetable.contains(s.coursesPlanned.get(i)))) {
                    s.coursesTaken.add(s.coursesPlanned.get(i));
                    timetable.add(s.coursesPlanned.get(i));
                    s.coursesPlanned.remove(s.coursesPlanned.get(i));
                    j--;
                }
            }
            for (int i =0; i < s.coursesPlanned.size(); i++) {
                if ((s.coursesPlanned.get(i).prereq.isEmpty() || s.coursesTaken.containsAll(s.coursesPlanned.get(i).prereq)) && (s.coursesPlanned.get(i).session.contains("S"))&& !(timetable.contains(s.coursesPlanned.get(i)))) {
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
