package com.example.b07project;

import static com.example.b07project.R.color.*;

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
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class AdminAddCourseFragment extends Fragment {


    View view;

    ListView listView;

    EditText courseName;
    EditText courseCode;
    EditText prereqCode;

    Button fall_button;
    Button winter_button;
    Button summer_button;
    Button addCourse;
    ImageButton addPrereq;

    ArrayList <String> sessions = new ArrayList<>();
    ArrayList <Course> courses  = new ArrayList<>();
    ArrayList <Course> prereq = new ArrayList<>();
    ArrayList <String> prereqDisplay;
    ArrayList <String> courseCodes;
    DatabaseReference ref;


    boolean fall_clicked;
    boolean winter_clicked;
    boolean summer_clicked;

    String[] items = {"BO7", "BO7", "BO7", "BO7", "BO7", "BO7", "BO7"};


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ref.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (task.isSuccessful()) {
                    for (DataSnapshot child: task.getResult().getChildren()) {
                        Course course = child.getValue(Course.class);
                        courses.add(course);
                        courseCodes.add(course.code);
                    }
                }
                else {
                    return;
                }
            }
        });
        view.findViewById(R.id.back_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(AdminAddCourseFragment.this)
                        .navigate(R.id.action_admin_add_course_to_admin_page);
            }});
        fall_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (fall_clicked) {
                    fall_button.setBackgroundResource(R.drawable.roundstyle);
                    fall_button.setBackgroundTintList(getContext().getResources()
                            .getColorStateList(main_button_medium_blue));
                    fall_button.setTextColor(getContext().getResources()
                            .getColorStateList(black));
                    fall_clicked = false;
                } else {
                    fall_button.setBackgroundResource(R.drawable.roundstyle);
                    fall_button.setBackgroundTintList(getContext().getResources()
                            .getColorStateList(selected_button_dark_blue));
                    fall_button.setTextColor(getContext().getResources()
                            .getColorStateList(R.color.selected_button_text_grey));
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
                            .getColorStateList(main_button_medium_blue));
                    winter_button.setTextColor(getContext().getResources()
                            .getColorStateList(black));
                    winter_clicked = false;
                } else {
                    winter_button.setBackgroundResource(R.drawable.roundstyle);
                    winter_button.setBackgroundTintList(getContext().getResources()
                            .getColorStateList(selected_button_dark_blue));
                    winter_button.setTextColor(getContext().getResources()
                            .getColorStateList(R.color.selected_button_text_grey));
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
                            .getColorStateList(main_button_medium_blue));
                    summer_button.setTextColor(getContext().getResources()
                            .getColorStateList(black));
                    summer_clicked = false;
                } else {
                    summer_button.setBackgroundResource(R.drawable.roundstyle);
                    summer_button.setBackgroundTintList(getContext().getResources()
                            .getColorStateList(selected_button_dark_blue));
                    summer_button.setTextColor(getContext().getResources()
                            .getColorStateList(R.color.selected_button_text_grey));
                    summer_clicked = true;
                }
            }
        });

        addPrereq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String prereq_name = prereqCode.getText().toString().trim();
                if (courseCode.getText().toString().trim().equals("")) {
                    prereqCode.setError("Course name and Code required");
                    prereqCode.requestFocus();
                    return;
                }
                if (prereq_name.equals("")) {
                    prereqCode.setError("Prerequisite Course Code required");
                    prereqCode.requestFocus();
                    return;
                }
                if (prereq_name.equals(courseCode.getText().toString().trim())) {
                    prereqCode.setError("Course cannot be prerequisite of itself");
                    prereqCode.requestFocus();
                    return;
                }
                if (prereqDisplay.indexOf(prereq_name) != -1) {
                    prereqCode.setError("Prerequisite already added");
                    prereqCode.requestFocus();
                    return;
                }
                if (courseCodes.indexOf(prereq_name) != -1) {

                    prereqDisplay.add(prereq_name);

                    ArrayAdapter<String> itemsAdapter = new ArrayAdapter<String>(getContext(),
                            android.R.layout.simple_list_item_1, prereqDisplay);
                    listView.setAdapter(itemsAdapter);
                }
                else {
                    prereqCode.setError("Course Code does not exist");
                    prereqCode.requestFocus();
                    return;
                }

                prereqCode.setText("");
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
                    sessions.add("Summer");
                }

                for (String prereqs: prereqDisplay) {
                    prereq.add(courses.get(courseCodes.indexOf(prereqs)));
                }

                Course course = new Course(name, code, sessions, prereq);

                FirebaseDatabase.getInstance().getReference("Courses").child(code).
                        setValue(course).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    NavHostFragment
                                            .findNavController(AdminAddCourseFragment.this)
                                            .navigate(R.id.action_admin_add_course_to_admin_page);
                                }
                                else {
                                    Toast.makeText(getContext(), "Failed to Add Course",
                                            Toast.LENGTH_SHORT).show();
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

        ref = FirebaseDatabase.getInstance("https://bo7-project-default-rtdb.firebaseio.com/").
                getReference("Courses");

        courseCodes = new ArrayList<>();

        courseName = (EditText) view.findViewById(R.id.addcourse_name);
        courseCode = (EditText) view.findViewById(R.id.addcourse_code);
        prereqCode = (EditText) view.findViewById(R.id.addcourse_prereq);

        fall_button = (Button) view.findViewById(R.id.button_admin_fall);
        winter_button = (Button) view.findViewById(R.id.button_admin_winter);
        summer_button = (Button) view.findViewById(R.id.button_admin_summer);
        addCourse = (Button) view.findViewById(R.id.button_add);
        addPrereq = (ImageButton) view.findViewById(R.id.add_prereq_button);

        listView = (ListView) view.findViewById(R.id.prereq_list);

        prereqDisplay = new ArrayList<>();




        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

}


