package com.example.b07project;

import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class CourseMenuFragment extends Fragment {

    String[] items = {"BO7", "BO7", "BO7", "BO7", "BO7", "BO7", "BO7"};
    View view;
    View popupwindow;
    ListView courseTakenList;
    ListView courseAddList;

    ArrayList <Course> allCourses;
    ArrayList <String> courses;
    ArrayList <Course> coursesTaken;
    ArrayList <String> courseTakenDisplay;
    ArrayList <String> addCoursesDisplay;
    ArrayList <String> prereqs;

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

        popupwindow = inflater.inflate(R.layout.fragment_add_course_popup,null);
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


        TextView pop;
        courseAddList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Dialog myDialog;
                myDialog = new Dialog(getContext());
                myDialog.setContentView(R.layout.fragment_add_course_popup);
                WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
                lp.width = WindowManager.LayoutParams.MATCH_PARENT;
                lp.height = WindowManager.LayoutParams.MATCH_PARENT;
                myDialog.show();
                myDialog.getWindow().setAttributes(lp);

                Button bt = myDialog.findViewById(R.id.done_button);
                ImageView back = myDialog.findViewById(R.id.back_button);
                TextView course_code = myDialog.findViewById(R.id.edit_course_course_code);
                TextView course_sessions = myDialog.findViewById(R.id.Avaliable_sessions);
                TextView course_name = myDialog.findViewById(R.id.edit_course_course_name);
                TextView name_display = myDialog.findViewById(R.id.name_display);
                TextView code_display = myDialog.findViewById(R.id.code_display);
                TextView session_1 = myDialog.findViewById(R.id.session_1);
                TextView session_2 = myDialog.findViewById(R.id.session_2);
                TextView session_3 = myDialog.findViewById(R.id.session_3);
                ListView prereq = myDialog.findViewById(R.id.prereq);


                for (Course course: allCourses) {
                    if (course.code.equals(addCoursesDisplay.get(i))) {
                        name_display.setText(course.name);
                        code_display.setText(course.code);

                        if (course.session.size() == 1) {
                            session_1.setText(course.session.get(0));
                            session_2.setText("");
                            session_3.setText("");
                        }
                        else if (course.session.size() == 2){
                            session_1.setText(course.session.get(0));
                            session_2.setText(course.session.get(1));
                            session_3.setText("");
                        }
                        else {
                            session_1.setText(course.session.get(0));
                            session_2.setText(course.session.get(1));
                            session_3.setText(course.session.get(2));
                        }

                        ArrayList <Course> pre = course.prereq;
                        prereqs = new ArrayList<>();
                        for(Course c: pre) {
                            prereqs.add(c.code);
                        }

                        ArrayAdapter<String> itemsAdapter5 = new ArrayAdapter<String>(getContext(),
                                android.R.layout.simple_list_item_1,prereqs);

                        prereq.setAdapter(itemsAdapter5);
                    }

                }


                back.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        myDialog.dismiss();
                    }
                });
                bt.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (!canTake(courseTakenDisplay, prereqs)) {
                            Toast.makeText(getContext(), "Not all Prerequisites taken",
                                    Toast.LENGTH_SHORT).show();
                            return;
                        }
                        myDialog.dismiss();
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

            }
        });

        courseTakenList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                Dialog myDialog;
                myDialog = new Dialog(getContext());
                myDialog.setContentView(R.layout.fragment_add_course_popup);
                WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
                lp.width = WindowManager.LayoutParams.MATCH_PARENT;
                lp.height = WindowManager.LayoutParams.MATCH_PARENT;
                myDialog.show();
                myDialog.getWindow().setAttributes(lp);

                ListView prereq = (ListView) myDialog.findViewById(R.id.prereq);

                Button bt = myDialog.findViewById(R.id.done_button);
                ImageView back = myDialog.findViewById(R.id.back_button);
                TextView course_code = myDialog.findViewById(R.id.edit_course_course_code);
                TextView course_sessions = myDialog.findViewById(R.id.Avaliable_sessions);
                TextView course_name = myDialog.findViewById(R.id.edit_course_course_name);
                TextView session_1 = myDialog.findViewById(R.id.session_1);
                TextView session_2 = myDialog.findViewById(R.id.session_2);
                TextView session_3 = myDialog.findViewById(R.id.session_3);

                bt.setText("Remove");

                TextView name_display = myDialog.findViewById(R.id.name_display);
                TextView code_display = myDialog.findViewById(R.id.code_display);

                for (Course course: allCourses) {
                    if (course.code.equals(courseTakenDisplay.get(i))) {
                        name_display.setText(course.name);
                        code_display.setText(course.code);

                        if (course.session.size() == 1) {
                            session_1.setText(course.session.get(0));
                            session_2.setText("");
                            session_3.setText("");
                        }
                        else if (course.session.size() == 2){
                            session_1.setText(course.session.get(0));
                            session_2.setText(course.session.get(1));
                            session_3.setText("");
                        }
                        else {
                            session_1.setText(course.session.get(0));
                            session_2.setText(course.session.get(1));
                            session_3.setText(course.session.get(2));
                        }

                        ArrayList <Course> pre = course.prereq;
                        prereqs = new ArrayList<>();
                        for(Course c: pre) {
                            prereqs.add(c.code);
                        }

                        ArrayAdapter<String> itemsAdapter5 = new ArrayAdapter<String>(getContext(),
                                android.R.layout.simple_list_item_1,prereqs);

                        prereq.setAdapter(itemsAdapter5);
                    }
                }

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
        });
    }

    public boolean canTake(ArrayList <String> coursesTaken, ArrayList <String> prereqs) {

        for (String course: prereqs) {
            if (!(coursesTaken.contains(course))) {
                return false;
            }
        }
        return true;
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
