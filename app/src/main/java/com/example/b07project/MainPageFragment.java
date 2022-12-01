package com.example.b07project;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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

public class MainPageFragment extends Fragment {

    private FragmentMainPageBinding binding;

    FirebaseAuth mAuth;

    TextView displayName;
    String studentName;

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




        return view;


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
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        view = null;
    }

}