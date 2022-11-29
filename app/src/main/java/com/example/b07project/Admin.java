package com.example.b07project;

import java.util.ArrayList;

public class Admin extends User{

    static ArrayList<Admin> admins = new ArrayList<Admin>();

    public Admin(String name, String email) {
        this.email = email;
    }

    public void addAdmin() {
        admins.add(this);
    }
}
