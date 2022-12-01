package com.example.b07project;

import android.content.Intent;
import android.graphics.CornerPathEffect;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.text.method.SingleLineTransformationMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;



import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.b07project.databinding.AdminPageBinding;
import com.example.b07project.databinding.FragmentMainPageBinding;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class AdminPageFragment extends Fragment {

    View view;
    FirebaseAuth mAuth;
    ArrayList <String> courseCode;

    ArrayList <Course> courses;

    ListView listView;

    private android.app.Fragment binding;
    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        DatabaseReference ref = FirebaseDatabase.
                getInstance("https://bo7-project-default-rtdb.firebaseio.com/").
                getReference("Courses");



        ref.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (task.isSuccessful()) {
                    for (DataSnapshot child: task.getResult().getChildren()) {
                        Course course = child.getValue(Course.class);
                        courses.add(course);
                    }

                    for (Course c: courses) {
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


        view.findViewById(R.id.button_add_course).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(AdminPageFragment.this)
                        .navigate(R.id.action_admin_page_to_admin_add_course2);
            }
        });
        view.findViewById(R.id.log_out_button_admin).setOnClickListener(new View.OnClickListener() {
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

        return view;



    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}