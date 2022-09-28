package com.example.fersonaapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button report_btn, wanted_btn, notice_btn, police_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        report_btn = findViewById(R.id.wanted10);
        wanted_btn = findViewById(R.id.wanted11);
        notice_btn = findViewById(R.id.wanted12);
        police_btn = findViewById(R.id.wanted13);

        report_btn.setOnClickListener(this);
        wanted_btn.setOnClickListener(this);
        notice_btn.setOnClickListener(this);
        police_btn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.wanted10:
                Log.d("Main","click_report_btn");
                Intent reportIntent = new Intent(MainActivity.this, ReportActivity.class);
                startActivity(reportIntent);
                break;

            case R.id.wanted11:
                Log.d("Main","click_wanted_btn");
                Intent wantedIntent = new Intent(MainActivity.this, WantedActivity.class);
                startActivity(wantedIntent);
                break;

            case R.id.wanted12:
                Log.d("Main","click_notice_btn");
                Intent noticeIntent = new Intent(MainActivity.this, NoticeActivity.class);
                startActivity(noticeIntent);
                break;

            case R.id.wanted13:
                Log.d("Main","click_police_btn");
                Intent policeIntent = new Intent(MainActivity.this, PoliceFindActivity.class);
                startActivity(policeIntent);
                break;
        }
    }
}