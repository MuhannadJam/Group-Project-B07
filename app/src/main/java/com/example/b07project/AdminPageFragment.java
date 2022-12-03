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
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

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
                            Button delete = myDialog.findViewById(R.id.delete_button);
                            Button bt = myDialog.findViewById(R.id.done_button);
                            ImageView back = myDialog.findViewById(R.id.back_button);
                            TextView course_code = myDialog.findViewById(R.id.course_name);
                            TextView course_desc = myDialog.findViewById(R.id.course_code);
                            ListView prereq = myDialog.findViewById(R.id.prereq_list);
                            ArrayAdapter<String> itemsAdapter3 = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, prereqs);
                            prereq.setAdapter(itemsAdapter3);





                            delete.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {

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
                                    myDialog.dismiss();

                                }
                            });
                        }

                    })
                    ;

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
