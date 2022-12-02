package com.example.b07project;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

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

    ArrayList <Course> allCourses;
    ArrayList <String> courses;
    ArrayList <Course> coursesTaken;
    ArrayList <String> courseTakenDisplay;
    ArrayList <String> addCoursesDisplay;

    ArrayAdapter<String> itemsAdapter1;
    ArrayAdapter<String> itemsAdapter2;

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
        allCourses = new ArrayList<Course>();

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

                    itemsAdapter1 = new ArrayAdapter<String>(getContext(),
                            android.R.layout.simple_list_item_1, courseTakenDisplay);

                    courseTakenList.setAdapter(itemsAdapter1);
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
                        courses.add(course.code);
                    }

                    for (String code: courses) {
                        if(!(courseTakenDisplay.contains(code))) {
                            addCoursesDisplay.add(code);
                        }
                    }

                    itemsAdapter2 = new ArrayAdapter<String>(getContext(),
                            android.R.layout.simple_list_item_1, addCoursesDisplay);

                    courseAddList.setAdapter(itemsAdapter2);
                }
                else {
                    return;
                }
            }
        });

        courseAddList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                courseTakenDisplay.add(addCoursesDisplay.get(i));
                addCoursesDisplay.remove(addCoursesDisplay.get(i));

                itemsAdapter1 = new ArrayAdapter<String>(getContext(),
                        android.R.layout.simple_list_item_1, courseTakenDisplay);

                courseTakenList.setAdapter(itemsAdapter1);

                itemsAdapter2 = new ArrayAdapter<String>(getContext(),
                        android.R.layout.simple_list_item_1, addCoursesDisplay);

                courseAddList.setAdapter(itemsAdapter2);

                updateDatabase();
            }
        });

        courseTakenList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                addCoursesDisplay.add(courseTakenDisplay.get(i));
                courseTakenDisplay.remove(courseTakenDisplay.get(i));


                itemsAdapter1 = new ArrayAdapter<String>(getContext(),
                        android.R.layout.simple_list_item_1, courseTakenDisplay);

                courseTakenList.setAdapter(itemsAdapter1);

                itemsAdapter2 = new ArrayAdapter<String>(getContext(),
                        android.R.layout.simple_list_item_1, addCoursesDisplay);

                courseAddList.setAdapter(itemsAdapter2);

                updateDatabase();
            }
        });
    }

    public void updateDatabase() {

        ArrayList <Course> cTaken = new ArrayList<Course>();

        for (String code: courseTakenDisplay) {
            for (Course course: allCourses) {
                if (course.code.equals(code)) {
                    cTaken.add(course);
                }
            }
        }

        cTakenRef.setValue(cTaken);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
    private android.app.Fragment binding;


}
