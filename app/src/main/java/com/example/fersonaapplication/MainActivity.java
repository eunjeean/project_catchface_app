package com.example.fersonaapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    ImageButton reportBtn, wantedBtn, noticebtn, policeBtn;
    TextView noticeNumtv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // 메뉴
        final DrawerLayout drawerLayout = findViewById(R.id.drawlayout);

        findViewById(R.id.menu).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerLayout.openDrawer(GravityCompat.END);
            }
        });

        reportBtn = findViewById(R.id.reportBtn);
        wantedBtn = findViewById(R.id.wantedBtn);
        noticebtn = findViewById(R.id.noticeBtn);
        policeBtn = findViewById(R.id.policeBtn);

        noticeNumtv = findViewById(R.id.noticeNumtv);

        reportBtn.setOnClickListener(this);
        wantedBtn.setOnClickListener(this);
        noticebtn.setOnClickListener(this);
        policeBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.reportBtn:
                Log.d("MainActivity","신고하기");
                Intent reportIntent = new Intent(MainActivity.this, ReportActivity.class);
                startActivity(reportIntent);
                break;
            case R.id.wantedBtn:
                Log.d("MainActivity","공개수배");
                Intent wantedIntent = new Intent(MainActivity.this, WantedActivity.class);
                startActivity(wantedIntent);
                break;
            case R.id.noticeBtn:
                Log.d("MainActivity","우리동네알림");
                Intent noticeIntent = new Intent(MainActivity.this, NoticeActivity.class);
                startActivity(noticeIntent);
                break;
            case R.id.policeBtn:
                Log.d("MainActivity","파출소찾기");
//                Intent policeIntent = new Intent(MainActivity.this, PoliceFindActivity.class);
//                startActivity(policeIntent);

                Intent policeIntent = new Intent(MainActivity.this, MypageActivity.class);
                startActivity(policeIntent);
                break;
        }
    }
}