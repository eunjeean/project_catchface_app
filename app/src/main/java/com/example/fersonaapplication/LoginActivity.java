package com.example.fersonaapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    Button loginBtn, joinBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        loginBtn = findViewById(R.id.loginBtn);
        joinBtn = findViewById(R.id.joinBtn);

        loginBtn.setOnClickListener(this);
        joinBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.loginBtn:
                Log.d("login","click_loginBtn");
                Intent mainIntent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(mainIntent);
                break;

            case R.id.joinBtn:
                Log.d("login","click_joinBtn");
                Intent joinIntent = new Intent(LoginActivity.this, JoinActivity.class);
                startActivity(joinIntent);
                break;

        }
    }
}