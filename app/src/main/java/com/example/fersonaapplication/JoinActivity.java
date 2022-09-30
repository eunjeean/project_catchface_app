package com.example.fersonaapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

public class JoinActivity extends AppCompatActivity implements View.OnClickListener {

    TextView passwordCk_tv,phoneTime_tv;
    EditText id_et, pw_et, pwck_et, name_et, phone_et,phoneck_et;
    Button idck_btn, phoneck_btn,join_btm;
    DatePicker joindate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join);

        passwordCk_tv = findViewById(R.id.passwordCk_tv);
        phoneTime_tv = findViewById(R.id.phoneTime_tv);

        id_et = findViewById(R.id.joinId_et);
        pw_et = findViewById(R.id.joinPw_et);
        pwck_et = findViewById(R.id.joinPwCK_et);
        name_et = findViewById(R.id.joinName_et);
        phone_et = findViewById(R.id.phone_et);
        phoneck_et = findViewById(R.id.phoneCk_et);

        joindate = findViewById(R.id.join_date);

        idck_btn = findViewById(R.id.check_btn);
        phoneck_btn = findViewById(R.id.phone_btn);
        join_btm = findViewById(R.id.submit_btn);

        idck_btn.setOnClickListener(this);
        phoneck_btn.setOnClickListener(this);
        join_btm.setOnClickListener(this);


    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.check_btn:
                Log.d("Join","아이디체크");
                break;
            case R.id.phone_btn:
                Log.d("Join","핸드폰인증");
                break;
            case R.id.submit_btn:
                Log.d("Join","회원가입");
                break;
        }

    }
}