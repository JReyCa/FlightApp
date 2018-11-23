package com.example.doudy.flightapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class WelcomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
    }

    public void loginBtn_Click(View view) {
        Intent loginPage = new Intent(this, LoginActivity.class);
        startActivity(loginPage);
    }

    public void signupBtn_Click(View view) {
        Intent signupPage = new Intent(this, SignUpActivity.class);
        startActivity(signupPage);
    }

    public void guestBtn_Click(View view) {
        // This button should lead directly into the main application
        // Insert some other activity path here
    }
}
