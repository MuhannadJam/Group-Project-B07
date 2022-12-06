package com.example.b07project;

import android.app.Dialog;
import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.example.b07project.databinding.FragmentMainPageBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class MainPageFragment extends Fragment {

    private FragmentMainPageBinding binding;

    FirebaseAuth mAuth;

    TextView displayName;
    String studentName;

    TableLayout stk;

    ArrayList <Course> coursesTaken = new ArrayList<>();
    ArrayList <Course> allCourses = new ArrayList<>();
    ArrayList <Course> timetable = new ArrayList<>();

    ArrayList <String> courses_avaliable = new ArrayList<>();
    ArrayList <String> fall_courses = new ArrayList<>();
    ArrayList <String> winter_courses = new ArrayList<>();
    ArrayList <String> summer_courses = new ArrayList<>();
    ArrayList <String> coursesAdd = new ArrayList<>();
    ArrayList <String> courses_codes = new ArrayList<>();
    ArrayList <String> courses_planned_to_take = new ArrayList<>();

    Button manageCourses;
    Button manageTimeline;
    Button logout;

    Student student;

    DatabaseReference cRef;

    View view;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        view = inflater.inflate(R.layout.fragment_main_page, container, false);


        cRef = FirebaseDatabase.
                getInstance("https://bo7-project-default-rtdb.firebaseio.com/").
                getReference("Courses");

        logout = (Button) view.findViewById(R.id.button_logout);
        manageCourses = (Button) view.findViewById(R.id.manage_courses_button);
        manageTimeline = (Button) view.findViewById(R.id.manage_timeline_button);


        mAuth = FirebaseAuth.getInstance();

        displayName =  view.findViewById(R.id.student_name);

        stk  = (TableLayout) view.findViewById(R.id.timeline);

        return view;


    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        DatabaseReference ref = FirebaseDatabase
                .getInstance("https://bo7-project-default-rtdb.firebaseio.com/")
                .getReference("Students").child(mAuth.getCurrentUser().getUid());
        ref.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (task.isSuccessful()) {
                    if(task.getResult().getValue() != null) {
                        student = task.getResult().getValue(Student.class);
                        displayName.setText(student.name);
                    }
                }
            }
        });

        DatabaseReference cTakenRef = FirebaseDatabase.
                getInstance("https://bo7-project-default-rtdb.firebaseio.com/").
                getReference("Students").child(mAuth.getCurrentUser().getUid()).
                child("coursesTaken");

        cTakenRef.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (task.isSuccessful()) {
                    for (DataSnapshot child: task.getResult().getChildren()) {
                        Course course = child.getValue(Course.class);
                        coursesTaken.add(course);
                        courses_codes.add(course.code);
                    }
                }
                else {
                    return;
                }
            }
        });

        cRef.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (task.isSuccessful()) {
                    for (DataSnapshot child: task.getResult().getChildren()) {
                        Course course = child.getValue(Course.class);
                        allCourses.add(course);
                    }
                }
            }
            });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mAuth.signOut();
                NavHostFragment.findNavController(MainPageFragment.this)
                        .navigate(R.id.action_SecondFragment_to_FirstFragment);
            }
        });

        manageCourses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(MainPageFragment.this).navigate(R.id
                        .action_SecondFragment_to_course_menu);
            }
        });

        manageTimeline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                courses_planned_to_take = new ArrayList<>();
                Dialog myDialog;
                myDialog = new Dialog(getContext());
                myDialog.setContentView(R.layout.fragment_edit_course_timeline);
                WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
                lp.width = WindowManager.LayoutParams.MATCH_PARENT;
                lp.height = WindowManager.LayoutParams.MATCH_PARENT;
                myDialog.show();
                myDialog.getWindow().setAttributes(lp);

                ListView course_available = myDialog.findViewById(R.id.courses_avaliable_list);
                ListView courses_planned = myDialog.findViewById(R.id.selected_courses_list);

                Button generate_timeline = myDialog.findViewById(R.id.generate_button);
                ImageView bt = myDialog.findViewById(R.id.back_button);

                bt.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        student.coursesPlanned = new ArrayList<>();
                        myDialog.dismiss();
                    }
                });

                ArrayList<Course> course_planned_list = new ArrayList<>();
                for (Course course : allCourses) {
                    if (!(courses_codes.contains(course.code))) {
                        courses_avaliable.add(course.code);
                        course_planned_list.add(course);

                    }
                }
                ArrayList <Course> planning_courses = new ArrayList<>();
                ArrayAdapter<String> itemsAdapter2 = new ArrayAdapter<String>(getContext(),
                        android.R.layout.simple_list_item_1, courses_avaliable);

                course_available.setAdapter(itemsAdapter2);
                course_available.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        courses_planned_to_take = new ArrayList<>();
                        for (Course course: allCourses) {
                            if (course.code .equals(courses_avaliable.get(i))) {
                                student.addCourseToPlanned(course);
                            }
                        }
                        for (Course c: student.coursesPlanned) {
                            courses_planned_to_take.add(c.code);
                            courses_avaliable.remove(c.code);
                        }

                        ArrayAdapter<String> itemsAdapter2 = new ArrayAdapter<String>(getContext(),
                                android.R.layout.simple_list_item_1, courses_avaliable);
                        ArrayAdapter<String> itemsAdapter3 = new ArrayAdapter<String>(getContext(),
                                android.R.layout.simple_list_item_1, courses_planned_to_take);
                        courses_planned.setAdapter(itemsAdapter3);
                        course_available.setAdapter(itemsAdapter2);

                        Log.i("Test", String.valueOf(student.coursesPlanned));

                    }

                });


                generate_timeline.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        student.generateTimetable();
                        //Log.i("Test" , String.valueOf(student.timetable));
                        for (Course c: student.timetable) {
                            if (c.session.contains("Fall")) {
                                fall_courses.add(c.code);
                            }
                            if (c.session.contains("Winter")) {
                                winter_courses.add(c.code);
                            }
                            if (c.session.contains("Summer")) {
                                summer_courses.add(c.code);
                            }
                        }
                        if(!student.timetable.isEmpty()) {
                            addtable();
                        }
                        courses_planned_to_take = new ArrayList<>();
                        myDialog.dismiss();
                    }
                });

            }

        });
    }

    public boolean canTakeTogether(int start, ArrayList <Course> timetable, Course course) {

        for (int i = start; i < timetable.size(); i++) {
            if (course.prereq.contains(timetable.get(i))) {
                return false;
            }
        }
        return true;
    }

    public String nextAvailableSession(String currentSession, int year, Course course) {
        if (currentSession.equals("Fall")) {
            if (course.session.contains("Winter")) {
                return "Winter " + String.valueOf(year + 1);
            }
            else if (course.session.contains("Summer")) {
                return "Summer " + String.valueOf(year + 1);
            }
        }
        else if (currentSession.equals("Winter")) {
            if (course.session.contains("Summer")) {
                return "Summer " + String.valueOf(year);
            }
            else if (course.session.contains("Fall")) {
                return "Fall " + String.valueOf(year);
            }
        }
        else if (currentSession.equals("Summer")) {
            if (course.session.contains("Fall")) {
                return "Fall " + String.valueOf(year);
            }
            else if (course.session.contains("Winter")) {
                return "Winter " + String.valueOf(year + 1);
            }
        }
        return currentSession + " " + String.valueOf(year + 1);
    }

    public void addtable(){

        String currentSem;
        int year;
        String session;

        TextView current;
        TextView courses_list;
        int start = 0;
        int num_courses = 1;

        Course course;
        String coursesList;

        ArrayList <Course> studentTimetable = student.timetable;

        course = studentTimetable.get(0);
        coursesList = course.code;
        if (course.session.contains("Fall")) {
            currentSem = "Fall";
            year = 2022;
        }
        else if (course.session.contains("Winter")) {
            currentSem = "Winter";
            year = 2023;
        }
        else {
            currentSem = "Summer";
            year = 2023;
        }

        session = currentSem + " " + String.valueOf(year);


        TableRow tbrow1 = new TableRow(getContext());
        current = new TextView(getContext());
        current.setWidth(135);
        current.setText(session);
        tbrow1.addView(current);
        courses_list = new TextView(getContext());
        courses_list.setText(coursesList);
        tbrow1.addView(courses_list);

        TableRow header_text =  view.findViewById(R.id.header);
        stk.removeAllViews();
        stk.addView(header_text);
        stk = view.findViewById(R.id.timeline);



        stk.addView(tbrow1);

        for (int i = 1; i < student.timetable.size(); i++) {
            if (canTakeTogether(start, student.timetable, studentTimetable.get(i)) &&
                    studentTimetable.get(i).session.contains(currentSem)) {
                coursesList += " " + studentTimetable.get(i).code;
                courses_list.setText(coursesList);
                num_courses++;
            }
            else {
                start = num_courses;
                num_courses++;
                session = nextAvailableSession(currentSem, year, studentTimetable.get(i));
                Log.i("test", session);
                currentSem = session.substring(0, session.indexOf(" "));
                year = Integer.parseInt(session.substring(session.indexOf(" ") + 1));
                course = studentTimetable.get(i);
                coursesList = course.code;
                tbrow1 = new TableRow(getContext());
                current = new TextView(getContext());
                current.setWidth(135);
                current.setText(session);
                tbrow1.addView(current);
                courses_list = new TextView(getContext());
                courses_list.setText(coursesList);
                tbrow1.addView(courses_list);
                stk.addView(tbrow1);
            }
        }

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        view = null;
    }

}