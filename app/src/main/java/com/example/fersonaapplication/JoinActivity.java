package com.example.fersonaapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

public class JoinActivity extends AppCompatActivity implements View.OnClickListener {

    TextView passwordCkTv,phoneTimeTv;
    EditText joinIdEt, joinPwEt, joinPwCkEt,joinNameEt, phoneEt,phoneCkEt;
    Button idCheckBtn, phoneBtn, submitBtn;
    DatePicker joinDate;
    ImageView logoImg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join);

        logoImg = findViewById(R.id.logoImg);

        passwordCkTv = findViewById(R.id.passwordCkTv);
        phoneTimeTv = findViewById(R.id.phoneTimeTv);

        joinIdEt = findViewById(R.id.joinIdEt);
        joinPwEt = findViewById(R.id.joinPwEt);
        joinPwCkEt = findViewById(R.id.joinPwCkEt);
        joinNameEt = findViewById(R.id.joinNameEt);
        phoneEt = findViewById(R.id.phoneEt);
        phoneCkEt = findViewById(R.id.phoneCkEt);

        joinDate = findViewById(R.id.joinDate);

        idCheckBtn = findViewById(R.id.idCheckBtn);
        phoneBtn = findViewById(R.id.phoneBtn);
        submitBtn = findViewById(R.id.submitBtn);

        logoImg.setOnClickListener(this);
        idCheckBtn.setOnClickListener(this);
        phoneBtn.setOnClickListener(this);
        submitBtn.setOnClickListener(this);


    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.logoImg:
                Log.d("Join","로고클릭");
                Intent logoIntent = new Intent(this, LoginActivity.class);
                startActivity(logoIntent);
                finish();
                break;
            case R.id.idCheckBtn:
                Log.d("Join","아이디체크");
                break;
            case R.id.phoneCkEt:
                Log.d("Join","핸드폰인증");
                break;
            case R.id.submitBtn:
                Log.d("Join","회원가입");
                break;
        }

    }
}