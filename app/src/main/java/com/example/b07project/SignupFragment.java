package com.example.b07project;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.b07project.databinding.FragmentSignupBinding;

public class SignupFragment extends Fragment {

    private SignupFragment binding;

    private View view;
    private EditText name, username, password, c_password;
    private Button signup;

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
        if (name.getText().toString().equals("")) {
            name.setError("Name required");
            name.requestFocus();
            return;
        }
        if (username.getText().toString().equals("")) {
            username.setError("Name required");
            username.requestFocus();
            return;
        }
        if (password.getText().toString().equals("")) {
            password.setError("Name required");
            password.requestFocus();
            return;
        }
        if (c_password.getText().toString().equals("")) {
            c_password.setError("Name required");
            c_password.requestFocus();
            return;
        }
        if (!(password.getText().toString().equals(c_password.getText().toString()))){
            c_password.setError("Passwords do not match");
            c_password.requestFocus();
            return;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_signup, container, false);

        name = (EditText) view.findViewById(R.id.signup_name);
        username = (EditText) view.findViewById(R.id.signup_username);
        password = (EditText) view.findViewById(R.id.signup_password);
        c_password = (EditText) view.findViewById(R.id.signup_passwordconfirmation);

        signup = (Button) view.findViewById(R.id.signup_button);

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}