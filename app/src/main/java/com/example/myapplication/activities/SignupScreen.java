package com.example.myapplication.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.R;
import com.example.myapplication.contracts.AppActivity;
import com.example.myapplication.contracts.SignUpContract;
import com.example.myapplication.presenter.SignUpPresenter;

public class SignupScreen extends AppCompatActivity implements SignUpContract.View, AppActivity {

    SignUpPresenter mPresenter;
    TextView gotoSignIn = null;
    SignupScreen instance = null;

    EditText txtUsername = null;
    EditText txtEmail = null;
    EditText txtPassword = null;
    EditText txtRePassword = null;

    Button btnSignUp = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_screen);

        initView();
        initPresenter();
        registerListener();

    }

    @Override
    public void initView() {
        instance = this;
        gotoSignIn = findViewById(R.id.gotoSignIn);

        txtUsername = findViewById(R.id.signup_username);
        txtEmail = findViewById(R.id.signup_email);
        txtPassword = findViewById(R.id.signup_password);
        txtRePassword = findViewById(R.id.signup_repassword);

        btnSignUp = findViewById(R.id.signup_button);
    }

    @Override
    public void initPresenter() {

        mPresenter = new SignUpPresenter();
        mPresenter.setmView(this);
    }

    @Override
    public void registerListener() {
        gotoSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gotoSignIn = new Intent(instance, LoginScreen.class);
                startActivity(gotoSignIn);
            }
        });

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signup();
            }
        });

    }

    void signup() {
        String username = txtUsername.getText().toString();
        String email = txtEmail.getText().toString();
        String password = txtPassword.getText().toString();
        String repassword = txtRePassword.getText().toString();

        if (username.isEmpty() || password.isEmpty() || email.isEmpty() || repassword.isEmpty()) {
            Toast.makeText(this,
                    "Please fill out all field", Toast.LENGTH_SHORT).show();
            return;
        }
        if (!password.equals(repassword)) {
            Toast.makeText(this,
                    "Password confirmation must be same with password", Toast.LENGTH_SHORT).show();
            return;
        }
        mPresenter.handleSignup(username, email, password);
    }

    @Override
    public void signupSuccess() {

        Toast.makeText(this, "Welcome", Toast.LENGTH_SHORT).show();
        Intent intentHome= new Intent(this, HomeScreen.class);
        startActivity(intentHome);
    }

    @Override
    public void signupFail(String error) {

        Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
    }
}
