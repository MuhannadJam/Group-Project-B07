package com.example.b07project;

import android.os.Bundle;
import android.util.Log;
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
import com.google.android.material.snackbar.Snackbar;
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
    private Presenter presenter;

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

        presenter = new Presenter(new Model(), this);

        return view;

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

    public String getEmail() {
        return email.getText().toString().trim();
    }

    public String getPassword() {
        return password.getText().toString().trim();
    }

    public void displayMessage(EditText field, String message) {
        field.setError(message);
        field.requestFocus();
    }

    public EditText getEmailField() {
        return view.findViewById(R.id.emailInput);
    }

    public EditText getPasswordField() {
        return view.findViewById(R.id.passwordInput);
    }

    public String getUID() {
        return mAuth.getCurrentUser().getUid();
    }


    public void loginUser() {

        if (!presenter.checkEmail()) {
            return;
        } else if (!presenter.checkPassword()) {
            return;
        }

        mAuth.signInWithEmailAndPassword(getEmail(), getPassword())
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    if (presenter.checkAdmin()) {
                        NavHostFragment.findNavController(LoginFragment.this)
                                .navigate(R.id.action_FirstFragment_to_AdminFragment);
                    }
                    else{
                        NavHostFragment.findNavController(LoginFragment.this)
                                .navigate(R.id.action_FirstFragment_to_SecondFragment);
                    }

                }
                else{
                    displayMessage(password, "Incorrect Password");
                }
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        view = null;
    }

}