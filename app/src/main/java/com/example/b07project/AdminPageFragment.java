package com.example.b07project;

import android.app.Dialog;
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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.sql.Ref;
import java.util.ArrayList;

public class AdminPageFragment extends Fragment {

    View view;
    FirebaseAuth mAuth;
    ArrayList <String> courseCode;

    ArrayList <Course> courses;

    ListView listView;

    DatabaseReference ref;

    private android.app.Fragment binding;
    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        readDatabase();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                Dialog myDialog;
                myDialog = new Dialog(getContext());
                myDialog.setContentView(R.layout.fragment_admin_edit_course_popup);
                myDialog.show();

                ArrayList <Course> pre = courses.get(i).prereq;
                ArrayList <String> prereqs = new ArrayList<>();
                for(Course c: pre) {
                    prereqs.add(c.code);
                }

                EditText name_edit = (EditText) myDialog
                        .findViewById(R.id.course_name_edit);
                EditText code_edit = (EditText) myDialog
                        .findViewById(R.id.course_code_edit);
                Button delete = myDialog.findViewById(R.id.delete_button);
                Button bt = myDialog.findViewById(R.id.done_button);
                ImageView back = myDialog.findViewById(R.id.back_button);
                TextView course_code = myDialog.findViewById(R.id.edit_course_course_name);
                TextView course_desc = myDialog.findViewById(R.id.edit_course_course_code);
                ListView prereq = myDialog.findViewById(R.id.prereq_list);
                ArrayAdapter<String> itemsAdapter3 = new ArrayAdapter<String>(getContext(),
                        android.R.layout.simple_list_item_1, prereqs);
                prereq.setAdapter(itemsAdapter3);

                name_edit.setText(courses.get(i).name);
                code_edit.setText(courses.get(i).code);

                String newName = name_edit.getText().toString().trim();
                String newCode = code_edit.getText().toString().trim();

                delete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        readDatabase();
                        myDialog.dismiss();
                    }
                });
                back.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        myDialog.dismiss();
                    }
                });
                bt.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Course updatedCourse = new Course();
                        //courseRef.setValue(updatedCourse);
                        readDatabase();
                        myDialog.dismiss();

                    }
                });
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

        ref = FirebaseDatabase.getInstance("https://bo7-project-default-rtdb.firebaseio.com/").
                getReference("Courses");

        return view;

    }

    public void readDatabase() {
        courses = new ArrayList<>();
        courseCode = new ArrayList<>();

        ref.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {

                if (task.isSuccessful()) {
                    for (DataSnapshot child : task.getResult().getChildren()) {
                        Course course = child.getValue(Course.class);
                        courses.add(course);
                    }

                    for (Course c : courses) {
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
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}
