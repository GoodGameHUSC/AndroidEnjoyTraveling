package com.example.myapplication.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.R;
import com.example.myapplication.contracts.AppActivity;
import com.example.myapplication.contracts.LoginContract;
import com.example.myapplication.presenter.LoginPresenter;

public class LoginScreen extends AppCompatActivity implements LoginContract.View, AppActivity {

    LoginScreen instance = null;
    TextView mbtnGotoSignup = null;
    Button mbtnLogin = null;
    EditText mTextUsername = null;
    EditText mTextPassword = null;

    LoginPresenter loginPresenter = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initView();
        registerListener();
        initPresenter();


    }


    public void initView() {
        instance = this;
        mbtnGotoSignup = findViewById(R.id.gotoSignUp);
        mbtnLogin = findViewById(R.id.signup_button);
        mTextUsername = findViewById(R.id.signup_username);
        mTextPassword = findViewById(R.id.signup_password);
    }

    public void registerListener() {

        mbtnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });

        mbtnGotoSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent startSignUp = new Intent(instance, SignupScreen.class);
                startActivity(startSignUp);
            }
        });
    }

    public void initPresenter() {
        loginPresenter = new LoginPresenter();
        loginPresenter.setmView(this);
    }


    private void login() {
        String username = mTextUsername.getText().toString();
        String password = mTextPassword.getText().toString();
        if (username.isEmpty() || password.isEmpty()) {
            Toast.makeText(this,
                    "Username or Password must not be empty!", Toast.LENGTH_SHORT).show();
            return;
        }
        loginPresenter.handleLogin(username, password);
    }

    @Override
    public void loginSuccess() {
        Log.d("TAG", "Logged in");
        Toast.makeText(this, "Loggined", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void loginFail(String error) {
        Log.d("TAG", error);
        Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
    }
}
