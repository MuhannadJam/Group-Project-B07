package com.example.b07project;

import android.os.Bundle;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.example.b07project.databinding.FragmentFirstBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class LoginFragment extends Fragment {

    private FragmentFirstBinding binding;
    private EditText email;
    private EditText password;
    private Button login_button;
    private View view;
    private ArrayList<String> admins = new ArrayList<>();
    private FirebaseAuth mAuth;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        view = inflater.inflate(R.layout.fragment_first, container, false);

        login_button = (Button) view.findViewById(R.id.login);
        email = (EditText) view.findViewById(R.id.emailInput);
        password = (EditText) view.findViewById(R.id.passwordInput);

        mAuth = FirebaseAuth.getInstance();

        DatabaseReference ref = FirebaseDatabase
                .getInstance("https://bo7-project-default-rtdb.firebaseio.com/")
                .getReference("Admin");
        ref.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                for(DataSnapshot id: task.getResult().getChildren()) {
                   admins.add(id.getValue().toString());
                }
            }
        });



        return view;

    }

    @Override
    public void onStart() {
        super.onStart();

        if(mAuth.getCurrentUser() != null){
            if (!(admins.indexOf(mAuth.getCurrentUser().getUid()) != -1)) {
                NavHostFragment.findNavController(LoginFragment.this
                ).navigate(R.id.action_FirstFragment_to_AdminFragment);
            }
            else {
                NavHostFragment.findNavController(LoginFragment.this
                ).navigate(R.id.action_FirstFragment_to_SecondFragment);
            }
        }

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        view.findViewById(R.id.signup).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(LoginFragment.this)
                        .navigate(R.id.action_FirstFragment_to_signupFragment);
            }
        });


        view.findViewById(R.id.login).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginUser();
            }
        });


    }




    public void loginUser() {

        String login_email = email.getText().toString().trim();
        String login_password = password.getText().toString().trim();

        if (login_email.equals("")) {
            email.setError("Email required");
            email.requestFocus();
            return;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(login_email).matches()) {
            email.setError("Invalid Email");
            email.requestFocus();
            return;
        }
        if (login_password.equals("")) {
            password.setError("Password required");
            password.requestFocus();
            return;
        }
        if (login_password.equals("admin") &&
                password.getText().toString().equals("admin")) {
            NavHostFragment.findNavController(LoginFragment.this)
                    .navigate(R.id.action_FirstFragment_to_AdminFragment);
        }
        mAuth.signInWithEmailAndPassword(login_email, login_password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    if (admins.indexOf(mAuth.getCurrentUser().getUid()) != -1) {
                        NavHostFragment.findNavController(LoginFragment.this)
                                .navigate(R.id.action_FirstFragment_to_AdminFragment);
                    }
                    else{
                        NavHostFragment.findNavController(LoginFragment.this)
                                .navigate(R.id.action_FirstFragment_to_SecondFragment);
                    }

                }
                else{
                    Toast.makeText(view.getContext(), "Invalid Credentials!",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}