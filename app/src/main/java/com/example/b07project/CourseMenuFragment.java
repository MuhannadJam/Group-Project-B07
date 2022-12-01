package com.example.b07project;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class CourseMenuFragment extends Fragment {
    String[] items = {"BO7", "BO7", "BO7", "BO7", "BO7", "BO7", "BO7"};
    View view;

    public CourseMenuFragment() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.course_menu, container, false);
        ArrayAdapter<String> itemsAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, items);
        ListView listView = (ListView) view.findViewById(R.id.add_courses_list);
        listView.setAdapter(itemsAdapter);
        return view;
    }
}