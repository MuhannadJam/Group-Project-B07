package com.example.b07project;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class CourseMenuFragment extends Fragment {

    String[] items = {"BO7", "BO7", "BO7", "BO7", "BO7", "BO7", "BO7"};
    View view;

    ListView courseTakenList;
    ListView courseAddList;

    ArrayList <String> courses;
    ArrayList <Course> coursesTaken;
    ArrayList <String> courseTakenDisplay;
    ArrayList <String> addCoursesDisplay;

    ImageView backButton;

    DatabaseReference cRef;
    DatabaseReference cTakenRef;
    FirebaseAuth mAuth;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.course_menu, container, false);

        mAuth = FirebaseAuth.getInstance();

        cRef = FirebaseDatabase.
                getInstance("https://bo7-project-default-rtdb.firebaseio.com/").
                getReference("Courses");

        cTakenRef = FirebaseDatabase.
                getInstance("https://bo7-project-default-rtdb.firebaseio.com/").
                getReference("Students").child(mAuth.getCurrentUser().getUid()).
                child("coursesTaken");

        backButton = (ImageView) view.findViewById(R.id.back_button);

        coursesTaken = new ArrayList<Course>();
        courses = new ArrayList<String>();
        courseTakenDisplay = new ArrayList<>();
        addCoursesDisplay = new ArrayList<>();

        courseTakenList = (ListView) view.findViewById(R.id.course_taken_list);
        courseAddList = (ListView) view.findViewById(R.id.add_courses_list);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(CourseMenuFragment.this)
                        .navigate(R.id.action_course_menu_to_SecondFragment);
            }
        });

        cTakenRef.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (task.isSuccessful()) {
                    for (DataSnapshot child: task.getResult().getChildren()) {
                        Course course = child.getValue(Course.class);
                        coursesTaken.add(course);
                        courseTakenDisplay.add(course.code);
                    }

                    ArrayAdapter<String> itemsAdapter = new ArrayAdapter<String>(getContext(),
                            android.R.layout.simple_list_item_1, courseTakenDisplay);

                    courseTakenList.setAdapter(itemsAdapter);
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
                        courses.add(course.code);
                    }

                    for (String course: courses) {
                        if (!(coursesTaken.contains(course))) {
                            addCoursesDisplay.add(course);
                        }
                    }

                    ArrayAdapter<String> itemsAdapter = new ArrayAdapter<String>(getContext(),
                            android.R.layout.simple_list_item_1, addCoursesDisplay);

                    courseAddList.setAdapter(itemsAdapter);
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
    private android.app.Fragment binding;


}
