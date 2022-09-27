package com.example.fersonaapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class JoinActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join);

        // 1. 시 선택 데이터
        String[] sis = {"선택(시)   ▼","광주광역시","나주시"};
        String[] dongs = {"선택(동)   ▼","동명동","계림동"};

        // 2. 시 객체 생성
        Spinner si = findViewById(R.id.si_spin);

        // 3. 배열 어뎁터 생성
        ArrayAdapter<String> arrayAdapter1 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, sis);

        // 4. 배열 어뎁터 설정
        arrayAdapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // 5 설정한 어뎁터 스피너에 셋팅
        si.setAdapter((arrayAdapter1));

        // 2. 시 객체 생성
        Spinner dong = findViewById(R.id.dong_spin);

        // 3. 배열 어뎁터 생성
        ArrayAdapter<String> arrayAdapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, dongs);

        // 4. 배열 어뎁터 설정
        arrayAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // 5 설정한 어뎁터 스피너에 셋팅
        dong.setAdapter((arrayAdapter2));

    }}