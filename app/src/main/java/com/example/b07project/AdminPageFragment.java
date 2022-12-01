package com.example.b07project;

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

import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

public class AdminPageFragment extends Fragment {

    View view;
    FirebaseAuth mAuth;
    String[] items = {"BO7", "BO7", "BO7", "BO7", "BO7", "BO7", "BO7"};

    ArrayList <Course> courses = new ArrayList<Course>();

    private android.app.Fragment binding;
    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


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

        courses = Course.getCourses();

        ArrayAdapter<String> itemsAdapter = new ArrayAdapter<String>(getContext(),
                android.R.layout.simple_list_item_1, items);
        ListView listView = (ListView) view.findViewById(R.id.course_list);
        listView.setAdapter(itemsAdapter);
        return view;



    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}