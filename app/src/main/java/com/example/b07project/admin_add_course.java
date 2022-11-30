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

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link admin_add_course#newInstance} factory method to
 * create an instance of this fragment.
 */
public class admin_add_course extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    View view;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    EditText courseName;
    EditText courseCode;
    Button fall_button;
    ArrayList <Course> prereq;


    public admin_add_course() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment admin_add_course.
     */
    // TODO: Rename and change types and number of parameters
    public static admin_add_course newInstance(String param1, String param2) {
        admin_add_course fragment = new admin_add_course();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        fall_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (true) {
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
}