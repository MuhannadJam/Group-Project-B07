package com.example.b07project;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class Model {

    ArrayList <String> emails = new ArrayList<>();
    ArrayList <String> admins = new ArrayList<>();

    public Model() {
        DatabaseReference studentRef = FirebaseDatabase
                .getInstance("https://bo7-project-default-rtdb.firebaseio.com/")
                .getReference("Students");

        studentRef.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                for(DataSnapshot id: task.getResult().getChildren()) {
                    emails.add(id.child("email").getValue().toString());
                }
            }
        });

        DatabaseReference ref = FirebaseDatabase
                .getInstance("https://bo7-project-default-rtdb.firebaseio.com/")
                .getReference("Admin");
        ref.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                for(DataSnapshot id: task.getResult().getChildren()) {
                    admins.add(id.getKey().toString());
                    emails.add(id.getValue().toString());
                }
            }
        });
    }

    public boolean isFound(String email) {
        return emails.contains(email);
    }

    public boolean isAdmin(String admin) {
        return admins.contains(admin);
    }


}
