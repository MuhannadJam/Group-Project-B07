package com.example.b07project;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class AdminAddCourseFragment extends Fragment {


    View view;

    EditText courseName;
    EditText courseCode;

    Button fall_button;
    Button winter_button;
    Button summer_button;
    Button addCourse;

    ArrayList <String> sessions = new ArrayList<>();
    ArrayList <Course> prereq = new ArrayList<>();

    boolean fall_clicked;
    boolean winter_clicked;
    boolean summer_clicked;

    String[] items = {"BO7", "BO7", "BO7", "BO7", "BO7", "BO7", "BO7"};


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        fall_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (fall_clicked) {
                    fall_button.setBackgroundResource(R.drawable.roundstyle);
                    fall_button.setBackgroundTintList(getContext().getResources()
                            .getColorStateList(R.color.MediumBlue));
                    fall_clicked = false;
                } else {
                    fall_button.setBackgroundResource(R.drawable.roundstyle);
                    fall_button.setBackgroundTintList(getContext().getResources()
                            .getColorStateList(R.color.SelectedBlue));
                    fall_clicked = true;
                }
            }
        });

        winter_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (winter_clicked) {
                    winter_button.setBackgroundResource(R.drawable.roundstyle);
                    winter_button.setBackgroundTintList(getContext().getResources()
                            .getColorStateList(R.color.MediumBlue));
                    winter_clicked = false;
                } else {
                    winter_button.setBackgroundResource(R.drawable.roundstyle);
                    winter_button.setBackgroundTintList(getContext().getResources()
                            .getColorStateList(R.color.SelectedBlue));
                    winter_clicked = true;
                }
            }
        });

        summer_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (summer_clicked) {
                    summer_button.setBackgroundResource(R.drawable.roundstyle);
                    summer_button.setBackgroundTintList(getContext().getResources()
                            .getColorStateList(R.color.MediumBlue));
                    summer_clicked = false;
                } else {
                    summer_button.setBackgroundResource(R.drawable.roundstyle);
                    summer_button.setBackgroundTintList(getContext().getResources()
                            .getColorStateList(R.color.SelectedBlue));
                    summer_clicked = true;
                }
            }
        });




        addCourse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String name = courseName.getText().toString().trim();
                String code = courseCode.getText().toString().trim();


                if (name.equals("")) {
                    courseName.setError("Course Name Required");
                    courseName.requestFocus();
                    return;
                }
                if (code.equals("")) {
                    courseCode.setError("Course Code Required");
                    courseCode.requestFocus();
                    return;
                }

                if (!(fall_clicked || winter_clicked || summer_clicked)) {
                    Snackbar.make(view, "Please Select a Session", Snackbar.LENGTH_SHORT)
                            .show();
                    return;
                }
                if (fall_clicked) {
                    sessions.add("Fall");
                }
                if (winter_clicked) {
                    sessions.add("Winter");
                }
                if (summer_clicked) {
                    sessions.add("Winter");
                }


                Course course = new Course(name, code, sessions, prereq);

                FirebaseDatabase.getInstance().getReference("Courses").child(code).
                        setValue(course).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    NavHostFragment.findNavController(AdminAddCourseFragment.this)
                                            .navigate(R.id.action_admin_add_course_to_admin_page);
                                }
                                else {
                                    Toast.makeText(getContext(), "Failed to Add Course", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });


            }

        });

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        view = inflater.inflate(R.layout.admin_add_course, container, false);

        courseName = (EditText) view.findViewById(R.id.addcourse_name);
        courseCode = (EditText) view.findViewById(R.id.addcourse_code);

        fall_button = (Button) view.findViewById(R.id.button_admin_fall);
        winter_button = (Button) view.findViewById(R.id.button_admin_winter);
        summer_button = (Button) view.findViewById(R.id.button_admin_summer);
        addCourse = (Button) view.findViewById(R.id.button_add);

        ArrayAdapter<String> itemsAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, items);
        ListView listView = (ListView) view.findViewById(R.id.prereq_list);
        listView.setAdapter(itemsAdapter);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

}


