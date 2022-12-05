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
    
    public ArrayList<Course> generateTimetable() {
        ArrayList<Course> timetable = new ArrayList<Course>();
        int j = coursesPlanned.size();
        while (j > 0) {
            for (int i =0; i < coursesPlanned.size(); i++) {
                if ((coursesPlanned.get(i).prereq.isEmpty() ||
                        coursesTaken.containsAll(coursesPlanned.get(i).prereq) ||
                        !(timetable.containsAll(coursesPlanned.get(i).prereq))) &&
                        (coursesPlanned.get(i).session.contains("Fall")) &&
                        !(timetable.contains(coursesPlanned.get(i)))) {
                    //assume that user is making a timetable before Fall Session
                    timetable.add(coursesPlanned.get(i));
                    coursesPlanned.remove(coursesPlanned.get(i));
                    j--;
                }
            }
            for (int i =0; i < coursesPlanned.size(); i++) {
                if ((coursesPlanned.get(i).prereq.isEmpty() ||
                        coursesTaken.containsAll(coursesPlanned.get(i).prereq)  ||
                        !(timetable.containsAll(coursesPlanned.get(i).prereq))) &&
                        (coursesPlanned.get(i).session.contains("Winter"))  &&
                        !(timetable.contains(coursesPlanned.get(i)))) {
                    timetable.add(coursesPlanned.get(i));
                    coursesPlanned.remove(coursesPlanned.get(i));
                    j--;
                }
            }
            for (int i =0; i < coursesPlanned.size(); i++) {
                if ((coursesPlanned.get(i).prereq.isEmpty() ||
                        coursesTaken.containsAll(coursesPlanned.get(i).prereq)  ||
                        !(timetable.containsAll(coursesPlanned.get(i).prereq))) &&
                        (coursesPlanned.get(i).session.contains("Summer"))  &&
                        !(timetable.contains(coursesPlanned.get(i)))) {
                    timetable.add(coursesPlanned.get(i));
                    coursesPlanned.remove(coursesPlanned.get(i));
                    j--;
                }
            }

        }
        return timetable;
    }




}
