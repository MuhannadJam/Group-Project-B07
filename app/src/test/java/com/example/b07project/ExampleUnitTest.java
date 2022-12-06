package com.example.b07project;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import android.util.Patterns;
import android.widget.EditText;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */

@RunWith(MockitoJUnitRunner.class)
public class ExampleUnitTest {
    @Mock
    LoginFragment view;

    @Mock
    Model model;

    @Mock
    EditText email;

    @Mock
    EditText password;

    @Test
    public void testEmptyEmail() {
        when(view.getEmail()).thenReturn("");
        when(view.getEmailField()).thenReturn(email);

        Presenter presenter = new Presenter(model, view);
        presenter.checkEmail();
        verify(view).displayMessage(email, "Email Required");
    }

    @Test
    public void testEmailPattern() {
        when(view.getEmail()).thenReturn("abc");
        when(view.checkPattern("abc")).thenReturn(false);
        when(view.getEmailField()).thenReturn(email);

        Presenter presenter = new Presenter(model, view);
        presenter.checkEmail();
        verify(view).displayMessage(email, "Invalid Email");
    }

    @Test
    public void testInvalidEmail() {
        when(view.getEmail()).thenReturn("abc@gmail.com");
        when(view.checkPattern("abc@gmail.com")).thenReturn(true);
        when(model.isFound("abc@gmail.com")).thenReturn(false);
        when(view.getEmailField()).thenReturn(email);

        Presenter presenter = new Presenter(model, view);
        presenter.checkEmail();
        verify(view).displayMessage(email, "Email not registered");
    }

    @Test
    public void testEmptyPassword() {
        when(view.getPassword()).thenReturn("");
        when(view.getPasswordField()).thenReturn(password);

        Presenter presenter = new Presenter(model, view);
        presenter.checkPassword();
        verify(view).displayMessage(password, "Password Required");
    }

}