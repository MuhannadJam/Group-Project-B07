package com.example.b07project;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class SignupFragment extends Fragment {

    private SignupFragment binding;

    private View view;
    private EditText name, email, password, c_password;
    private Button signup;

    private FirebaseAuth mAuth;

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registerUser();
            }
        });
    }

    public void registerUser() {
        String signup_name = name.getText().toString().trim();
        String signup_email = email.getText().toString().trim();
        String signup_password = password.getText().toString().trim();

        if (signup_name.equals("")) {
            name.setError("Name required");
            name.requestFocus();
            return;
        }
        if (signup_email.equals("")) {
            email.setError("Email required");
            email.requestFocus();
            return;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(signup_email).matches()) {
            email.setError("Invalid Email");
            email.requestFocus();
            return;
        }
        if (signup_password.equals("")) {
            password.setError("Password required");
            password.requestFocus();
            return;
        }
        if (signup_password.length() < 6) {
            password.setError("Password too short");
            password.requestFocus();
            return;
        }
        if (c_password.getText().toString().equals("")) {
            c_password.setError("Password Confirmation required");
            c_password.requestFocus();
            return;
        }
        if (!(signup_password.equals(c_password.getText().toString().trim()))){
            c_password.setError("Password does not match");
            c_password.requestFocus();
            return;
        }


        mAuth.createUserWithEmailAndPassword(email.getText().toString().trim(),
                password.getText().toString().trim()).
                addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if(task.isSuccessful()) {

                    Student student = new Student(name.getText().toString().trim(),
                            email.getText().toString().trim(), new ArrayList<>());

                    FirebaseDatabase.getInstance().getReference("Students").child(FirebaseAuth.getInstance()
                                    .getCurrentUser().getUid()).
                            setValue(student).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Toast.makeText(view.getContext(),
                                                "User Registered Successfully",
                                                Toast.LENGTH_SHORT).show();
                                        NavHostFragment.findNavController(SignupFragment.
                                                this).navigate(R.id.
                                                action_signupFragment_to_SecondFragment);
                                    }
                                    else {
                                        Toast.makeText(view.getContext(),
                                                "User Registration Failed",
                                                Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }
                else {
                    Snackbar.make(view, "Email already registered, use a different Email",
                            Snackbar.LENGTH_SHORT).show();
                }
            }
        });

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_signup, container, false);

        name = (EditText) view.findViewById(R.id.signup_name);
        email = (EditText) view.findViewById(R.id.signup_email);
        password = (EditText) view.findViewById(R.id.signup_password);
        c_password = (EditText) view.findViewById(R.id.signup_passwordconfirmation);

        signup = (Button) view.findViewById(R.id.signup_button);

        mAuth = FirebaseAuth.getInstance();

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}