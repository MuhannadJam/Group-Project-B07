package com.example.b07project;

import android.os.Bundle;
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
import com.google.firebase.auth.FirebaseAuth;

public class FirstFragment extends Fragment {

    private FragmentFirstBinding binding;
    private EditText email;
    private EditText password;
    private Button login_button;
    private View view;

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

        return view;

    }

    @Override
    public void onStart() {
        super.onStart();

        if (mAuth.getCurrentUser() != null) {
            return;
        }
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        view.findViewById(R.id.signup).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(FirstFragment.this)
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

        if (email.getText().toString().equals("")) {
            email.setError("Email required");
            email.requestFocus();
            return;
        }
        if (password.getText().toString().equals("")) {
            password.setError("Password required");
            password.requestFocus();
            return;
        }
        if (email.getText().toString().equals("admin") &&
                password.getText().toString().equals("admin")) {
            NavHostFragment.findNavController(FirstFragment.this)
                    .navigate(R.id.action_FirstFragment_to_blankFragment);
        }
        if (email.getText().toString().equals("name")) {
            NavHostFragment.findNavController(FirstFragment.this
            ).navigate(R.id.action_FirstFragment_to_SecondFragment);
        } else {
            Toast.makeText(view.getContext(), "Invalid Credentials!",
                    Toast.LENGTH_SHORT).show();
        }


    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}