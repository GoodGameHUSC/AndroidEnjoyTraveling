package com.example.myapplication.contracts;

public class LoginContract {

    public interface View {
        void loginSuccess();

        void loginFail(String error);
    }

    public interface Presenter {

        void handleLogin(String username, String password);
    }
}
