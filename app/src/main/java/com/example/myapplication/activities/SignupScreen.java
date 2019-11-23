package com.example.myapplication.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.myapplication.R;

public class SignupScreen extends AppCompatActivity {

    TextView gotoSignIn = null;
    SignupScreen instance = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_screen);

        instance = this;
        ;

        gotoSignIn = findViewById(R.id.gotoSignIn);

        gotoSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gotoSignIn = new Intent(instance, LoginScreen.class);
                startActivity(gotoSignIn);
            }
        });
    }
}
