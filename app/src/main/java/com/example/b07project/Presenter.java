package com.example.b07project;

import android.util.Patterns;

public class Presenter {
    private Model model;
    private LoginFragment view;

    public Presenter(Model model, LoginFragment view) {
        this.model = model;
        this.view = view;
    }

    public boolean checkEmail() {
        String email = view.getEmail();
        if (email.equals("")) {
            view.displayMessage(view.getEmailField(), "Email Required");
            return false;
        }
        else if (!view.checkPattern(email)) {
            view.displayMessage(view.getEmailField(), "Invalid Email");
            return false;
        }
        else if (!(model.isFound(email))) {
            view.displayMessage(view.getEmailField(), "Email not registered");
            return false;
        }

        return true;
    }

    public boolean checkPassword() {
        String password = view.getPassword();

        if (password.equals("")) {
            view.displayMessage(view.getPasswordField(), "Password Required");
            return false;
        }
        return true;
    }

    public boolean checkAdmin() {
        String admin = view.getUID();

        return model.isAdmin(admin);
    }


}
