package com.example.myapplication.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.myapplication.R;

public class LoginScreen extends AppCompatActivity {

    TextView gotoSignup = null;
    LoginScreen instance = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        gotoSignup = findViewById(R.id.gotoSignUp);


        gotoSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent startSignUp = new Intent(instance, SignupScreen.class);
                startActivity(startSignUp);
            }
        });

    }

}
