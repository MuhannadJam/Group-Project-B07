package com.example.b07project;

import android.app.Activity;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.b07project.databinding.AdminPageBinding;
import com.example.b07project.databinding.FragmentMainPageBinding;

public class admin_page extends Fragment {

    View view;
    private android.app.Fragment binding;
    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        view.findViewById(R.id.button_add_course).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(admin_page.this)
                        .navigate(R.id.action_admin_page_to_admin_add_course2);
            }
        });

    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        view = inflater.inflate(R.layout.admin_page, container, false);

        return view;



    }
}