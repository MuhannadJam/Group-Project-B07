package com.example.b07project;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.example.b07project.databinding.FragmentMainPageBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class MainPageFragment extends Fragment {

    private FragmentMainPageBinding binding;

    FirebaseAuth mAuth;

    TextView displayName;
    String studentName;

    ArrayList <Course> coursesTaken;
    ArrayList <String> courseTakenDisplay;

    Button manageCourses;
    Button manageTimeline;
    Button logout;


    View view;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        view = inflater.inflate(R.layout.fragment_main_page, container, false);

        logout = (Button) view.findViewById(R.id.button_logout);
        manageCourses = (Button) view.findViewById(R.id.manage_courses_button);
        manageTimeline = (Button) view.findViewById(R.id.manage_timeline_button);


        mAuth = FirebaseAuth.getInstance();

        displayName =  view.findViewById(R.id.student_name);

        addtable();


        return view;


    }
    public void addtable(){
        TableLayout stk = (TableLayout) view.findViewById(R.id.timeline);
        TableRow tbrow = new TableRow(getContext());

        String csession = "Winter";
        TextView session = new TextView(getContext());
        session.setWidth(135);
        session.setText(csession);
        tbrow.addView(session);
        String courses = "12";
        TextView courses_list = new TextView(getContext());
        /*for(Course course : coursesTaken){
            if(course.session.contains("Winter")
                courses.concat(course.code + " ");
        }*/

        tbrow.addView(courses_list);
        stk.addView(tbrow);


        TableRow tbrow1 = new TableRow(getContext());
        String csession1 = "Fall";
        TextView session2 = new TextView(getContext());
        session2.setWidth(135);
        session2.setText(csession1);
        tbrow1.addView(session2);
        String courses2 = "12";
        TextView courses_list2 = new TextView(getContext());

        /*for(Course course : coursesTaken){
            if(course.session.contains("Fall")
                courses2.concat(course.code + " ");
        }*/
        tbrow1.addView(courses_list2);
        stk.addView(tbrow1);

        TableRow tbrow2 = new TableRow(getContext());
        String csession3 = "Summer";
        TextView session3 = new TextView(getContext());
        session3.setWidth(135);
        session3.setText(csession3);
        tbrow2.addView(session3);
        String courses3 = "";
        TextView courses_list3 = new TextView(getContext());

        /*for(Course course : coursesTaken){
            if(course.session.contains("Fall")
                courses3.concat(course.code + " ");
        }*/

        tbrow2.addView(courses_list3);
        stk.addView(tbrow2);
    }
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        DatabaseReference ref = FirebaseDatabase
                .getInstance("https://bo7-project-default-rtdb.firebaseio.com/")
                .getReference("Students").child(mAuth.getCurrentUser().getUid()).child("name");
        ref.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (task.isSuccessful()) {
                    if(task.getResult().getValue() != null)
                        displayName.setText(task.getResult().getValue().toString());
                }
            }
        });


        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mAuth.signOut();
                NavHostFragment.findNavController(MainPageFragment.this)
                        .navigate(R.id.action_SecondFragment_to_FirstFragment);
            }
        });

        manageCourses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(MainPageFragment.this).navigate(R.id
                        .action_SecondFragment_to_course_menu);
            }
        });

        manageTimeline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(MainPageFragment.this).navigate(R.id.action_SecondFragment_to_editCourseTimeline);
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        view = null;
    }

}