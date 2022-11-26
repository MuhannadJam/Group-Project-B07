package com.example.b07project;

import java.util.ArrayList;

public class Admin extends User{

    ArrayList<Admin> admins = new ArrayList<Admin>();

    public Admin(String name, String username, String password) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void addAdmin() {
        admins.add(this);
    }
}
