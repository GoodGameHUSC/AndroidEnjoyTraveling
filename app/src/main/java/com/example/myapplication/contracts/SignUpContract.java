package com.example.myapplication.contracts;

public class SignUpContract {

    public interface View {
        void signupSuccess();

        void signupFail(String error);
    }

    public interface Presenter {

        void handleSignup(String username, String email,  String password);
    }
}
