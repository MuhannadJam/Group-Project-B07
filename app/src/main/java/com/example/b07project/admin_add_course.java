package com.example.b07project;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.TintableBackgroundView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.b07project.databinding.AdminAddCourseBinding;

import java.util.ArrayList;

public class admin_add_course extends Fragment {


    View view;

    EditText courseName;
    EditText courseCode;
    Button fall_button;
    ArrayList <Course> prereq;

    boolean fall_clicked;
    boolean winter_clicked;
    boolean summer_clicked;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        fall_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (fall_clicked) {
                    fall_button.setBackgroundResource(R.drawable.roundstyle);
                    fall_button.setBackgroundTintList(getContext().getResources().getColorStateList(R.color.MediumBlue));
                } else {
                    fall_button.setBackgroundResource(R.drawable.roundstyle);
                    fall_button.setBackgroundTintList(getContext().getResources().getColorStateList(R.color.DarkBlue));
                }
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

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

}


