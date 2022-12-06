package com.example.b07project;

import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class AdminPageFragment extends Fragment {

    View view;
    FirebaseAuth mAuth;
    ArrayList <String> courseCode;
    ArrayList <Course> studentCourses = new ArrayList<Course>();
    ArrayList <Course> courses;
    ArrayList <Course> pre;

    ListView listView;

    DatabaseReference ref;
    DatabaseReference sRef;

    boolean fall_selected;
    boolean winter_selected;
    boolean summer_selected;

    String newName;
    String newCode;
    String storedCode;

    private ArrayList<String> students = new ArrayList<>();

    
    private android.app.Fragment binding;
    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        readDatabase();



        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                Dialog myDialog;
                myDialog = new Dialog(getContext());
                myDialog.setContentView(R.layout.fragment_admin_edit_course_popup);
                WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
                lp.width = WindowManager.LayoutParams.MATCH_PARENT;
                lp.height = WindowManager.LayoutParams.MATCH_PARENT;
                myDialog.show();
                myDialog.getWindow().setAttributes(lp);

                pre = courses.get(i).prereq;
                ArrayList <String> prereqs = new ArrayList<>();
                for(Course c: pre) {
                    prereqs.add(c.code);
                }

                storedCode = courses.get(i).code;

                EditText name_edit = (EditText) myDialog
                        .findViewById(R.id.course_name_edit);
                EditText code_edit = (EditText) myDialog
                        .findViewById(R.id.course_code_edit);
                EditText prereq_code = (EditText) myDialog.findViewById(R.id.addcourse_prereq);

                ImageView back = myDialog.findViewById(R.id.back_button);
                TextView course_code = myDialog.findViewById(R.id.edit_course_course_name);
                TextView course_desc = myDialog.findViewById(R.id.edit_course_course_code);
                ListView prereq = myDialog.findViewById(R.id.prereq_list);

                ArrayAdapter<String> itemsAdapter3 = new ArrayAdapter<String>(getContext(),
                        android.R.layout.simple_list_item_1, prereqs);
                prereq.setAdapter(itemsAdapter3);

                name_edit.setText(courses.get(i).name);
                code_edit.setText(courses.get(i).code);

                newName = name_edit.getText().toString().trim();
                newCode = code_edit.getText().toString().trim();

                Button fall_button = (Button) myDialog.findViewById(R.id.button_admin_fall);
                Button winter_button = (Button) myDialog.findViewById(R.id.button_admin_winter);
                Button summer_button = (Button) myDialog.findViewById(R.id.button_admin_summer);
                Button delete = myDialog.findViewById(R.id.delete_button);
                Button bt = myDialog.findViewById(R.id.done_button);
                ImageButton add_prereq = (ImageButton)myDialog.findViewById(R.id.add_prereq_button);

                fall_selected = false;
                winter_selected = false;
                summer_selected = false;

                for (String session: courses.get(i).session) {
                    if (session.equals("Fall")) {
                        fall_button.setBackgroundResource(R.drawable.roundstyle);
                        fall_button.setBackgroundTintList(getContext().getResources()
                                .getColorStateList(R.color.selected_button_dark_blue));
                        fall_button.setTextColor(getContext().getResources()
                                .getColorStateList(R.color.white));
                        fall_selected = true;
                    }
                    if (session.equals("Winter")) {
                        winter_button.setBackgroundResource(R.drawable.roundstyle);
                        winter_button.setBackgroundTintList(getContext().getResources()
                                .getColorStateList(R.color.selected_button_dark_blue));
                        winter_button.setTextColor(getContext().getResources()
                                .getColorStateList(R.color.white));
                        winter_selected = true;
                    }
                    if (session.equals("Summer")) {
                        summer_button.setBackgroundResource(R.drawable.roundstyle);
                        summer_button.setBackgroundTintList(getContext().getResources()
                                .getColorStateList(R.color.selected_button_dark_blue));
                        summer_button.setTextColor(getContext().getResources()
                                .getColorStateList(R.color.white));
                        summer_selected = true;
                    }
                }

                fall_button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (fall_selected) {
                            fall_button.setBackgroundResource(R.drawable.roundstyle);
                            fall_button.setBackgroundTintList(getContext().getResources()
                                    .getColorStateList(R.color.pop_up_background_light_blue));
                            fall_button.setTextColor(getContext().getResources()
                                    .getColorStateList(R.color.black));
                            fall_selected= false;
                        } else {
                            fall_button.setBackgroundResource(R.drawable.roundstyle);
                            fall_button.setBackgroundTintList(getContext().getResources()
                                    .getColorStateList(R.color.selected_button_dark_blue));
                            fall_button.setTextColor(getContext().getResources()
                                    .getColorStateList(R.color.white));
                            fall_selected = true;
                        }
                    }
                });

                winter_button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (winter_selected) {
                            winter_button.setBackgroundResource(R.drawable.roundstyle);
                            winter_button.setBackgroundTintList(getContext().getResources()
                                    .getColorStateList(R.color.pop_up_background_light_blue));
                            winter_button.setTextColor(getContext().getResources()
                                    .getColorStateList(R.color.black));
                            winter_selected = false;
                        } else {
                            winter_button.setBackgroundResource(R.drawable.roundstyle);
                            winter_button.setBackgroundTintList(getContext().getResources()
                                    .getColorStateList(R.color.selected_button_dark_blue));
                            winter_button.setTextColor(getContext().getResources()
                                    .getColorStateList(R.color.white));
                            winter_selected = true;
                        }
                    }
                });

                summer_button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (summer_selected) {
                            summer_button.setBackgroundResource(R.drawable.roundstyle);
                            summer_button.setBackgroundTintList(getContext().getResources()
                                    .getColorStateList(R.color.pop_up_background_light_blue));
                            summer_button.setTextColor(getContext().getResources()
                                    .getColorStateList(R.color.black));
                            summer_selected = false;
                        } else {
                            summer_button.setBackgroundResource(R.drawable.roundstyle);
                            summer_button.setBackgroundTintList(getContext().getResources()
                                    .getColorStateList(R.color.selected_button_dark_blue));
                            summer_button.setTextColor(getContext().getResources()
                                    .getColorStateList(R.color.white));
                            summer_selected = true;
                        }
                    }
                });

                add_prereq.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String pre_code = prereq_code.getText().toString().trim();

                        if (code_edit.getText().toString().trim().equals("")) {
                            prereq_code.setError("Course name and Code required");
                            prereq_code.requestFocus();
                            return;
                        }
                        if (pre_code.equals("")) {
                            prereq_code.setError("Prerequisite Course Code required");
                            prereq_code.requestFocus();
                            return;
                        }
                        if (pre_code.equals(code_edit.getText().toString().trim())) {
                            prereq_code.setError("Course cannot be prerequisite of itself");
                            prereq_code.requestFocus();
                            return;
                        }
                        if (prereqs.indexOf(pre_code) != -1) {
                            prereq_code.setError("Prerequisite already added");
                            prereq_code.requestFocus();
                            return;
                        }
                        if (courseCode.indexOf(pre_code) != -1) {

                            prereqs.add(pre_code);

                            ArrayAdapter<String> itemsAdapter = new ArrayAdapter<String>(getContext(),
                                    android.R.layout.simple_list_item_1, prereqs);
                            prereq.setAdapter(itemsAdapter);

                            for (Course c: courses) {
                                if (c.code.equals(pre_code)) {
                                    pre.add(c);
                                }
                            }
                        }
                        else {
                            prereq_code.setError("Course Code does not exist");
                            prereq_code.requestFocus();
                            return;
                        }

                        prereq_code.setText("");
                    }
                });

                Course to_change = courses.get(i);

                prereq.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int j, long l) {

                        for (Course course: courses) {
                            if (course.code.equals(prereqs.get(j))) {
                                for (int n = 0; n < pre.size(); n++) {
                                    if (pre.get(n).code.equals(prereqs.get(j))) {
                                        pre.remove(n);
                                    }
                                }
                            }
                        }
                        prereqs.remove(j);

                        ArrayAdapter<String> itemsAdapter = new ArrayAdapter<String>(getContext(),
                                android.R.layout.simple_list_item_1, prereqs);
                        prereq.setAdapter(itemsAdapter);
                    }
                });



                delete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        deleteStudentCourse(storedCode);
                        deleteFromPrerequisites(storedCode);
                        ref.child(courses.get(i).code).removeValue();
                        readDatabase();
                        myDialog.dismiss();
                    }
                });

                back.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        myDialog.dismiss();
                    }
                });

                bt.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        newName = name_edit.getText().toString().trim();
                        newCode = code_edit.getText().toString().trim();

                        ArrayList <String> sessions = new ArrayList<>();

                        if (newName.equals("")) {
                            name_edit.setError("Course Name Required");
                            name_edit.requestFocus();
                            return;
                        }
                        if (newCode.equals("")) {
                            code_edit.setError("Course Code Required");
                            code_edit.requestFocus();
                            return;
                        }

                        if (!(fall_selected || winter_selected || summer_selected)) {
                            Snackbar.make(view, "Please Select a Session",
                                            Snackbar.LENGTH_SHORT).show();
                            return;
                        }

                        if (fall_selected) {
                            sessions.add("Fall");
                        }
                        if (winter_selected) {
                            sessions.add("Winter");
                        }
                        if (summer_selected) {
                            sessions.add("Summer");
                        }

                        Course updatedCourse = new Course(newName, newCode, sessions, pre);
                        editStudentCourse(storedCode, updatedCourse);
                        ref.child(courses.get(i).code).removeValue();
                        ref.child(newCode).setValue(updatedCourse);

                        readDatabase();
                        myDialog.dismiss();

                    }
                });
            }

        });


        view.findViewById(R.id.button_add_course).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        NavHostFragment.findNavController(AdminPageFragment.this)
                                .navigate(R.id.action_admin_page_to_admin_add_course2);
                    }
                });
                view.findViewById(R.id.log_out_button_admin)
                        .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        mAuth.signOut();
                        NavHostFragment.findNavController(AdminPageFragment.this)
                                .navigate(R.id.action_admin_page_to_FirstFragment);
                    }
                });
            }





    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        view = inflater.inflate(R.layout.admin_page, container, false);

        mAuth = FirebaseAuth.getInstance();

        courses = new ArrayList<Course>();

        courseCode = new ArrayList<>();

        listView = (ListView) view.findViewById(R.id.course_list);

        ref = FirebaseDatabase.getInstance("https://bo7-project-default-rtdb.firebaseio.com/").
                getReference("Courses");

        sRef = FirebaseDatabase.getInstance("https://bo7-project-default-rtdb.firebaseio.com/")
                .getReference("Students");
        sRef.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                for(DataSnapshot id: task.getResult().getChildren()) {
                    students.add(id.getKey());
                }
            }
        });

        return view;

    }
    public void deleteFromPrerequisites(String code) {
        for (Course course: courses) {
            for (Course prereq: course.prereq) {
                if (prereq.code.equals(code)) {
                    course.prereq.remove(prereq);
                    ref.child(course.code).child("prereq").setValue(course.prereq);
                }
            }
        }
    }


    public void editStudentCourse(String code, Course updated) {
        for (String student: students) {
            sRef.child(student).child("coursesTaken")
                    .get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DataSnapshot> task) {
                    if (task.isSuccessful()) {
                        for (DataSnapshot child: task.getResult().getChildren()) {
                            Course course = child.getValue(Course.class);
                            if (!(course.code.equals(code))) {
                                studentCourses.add(course);
                            }
                            else {
                                studentCourses.add(updated);
                            }
                        }

                        sRef.child(student).child("coursesTaken").setValue(studentCourses);
                        studentCourses = new ArrayList<Course>();
                    }
                    else {
                        return;
                    }
                }
            });

        }
    }

    public void deleteStudentCourse(String code) {

        for (String student: students) {
            sRef.child(student).child("coursesTaken")
                    .get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DataSnapshot> task) {
                    if (task.isSuccessful()) {
                        for (DataSnapshot child: task.getResult().getChildren()) {
                            Course course = child.getValue(Course.class);
                            if (!(course.code.equals(code))) {
                                studentCourses.add(course);
                            }
                        }

                        sRef.child(student).child("coursesTaken").setValue(studentCourses);
                        studentCourses = new ArrayList<Course>();
                    }
                    else {
                        return;
                    }
                }
            });

        }
    }

    public void readDatabase() {
        courses = new ArrayList<>();
        courseCode = new ArrayList<>();

        ref.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {

                if (task.isSuccessful()) {
                    for (DataSnapshot child : task.getResult().getChildren()) {
                        Course course = child.getValue(Course.class);
                        courses.add(course);
                    }

                    for (Course c : courses) {
                        courseCode.add(c.code);
                    }

                    ArrayAdapter<String> itemsAdapter = new ArrayAdapter<String>(getContext(),
                            android.R.layout.simple_list_item_1, courseCode);

                    listView.setAdapter(itemsAdapter);

                }
                else {
                    return;
                }
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}
