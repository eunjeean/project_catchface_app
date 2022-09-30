package com.example.fersonaapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.google.android.material.navigation.NavigationView;

public class PoliceFindActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {

    ImageView logo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_police_find);

        // 메뉴
        final DrawerLayout drawerLayout = findViewById(R.id.drawlayout);

        findViewById(R.id.menu).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerLayout.openDrawer(GravityCompat.END);
            }
        });

        NavigationView menu_nv = findViewById(R.id.menu_nv);
        menu_nv.setNavigationItemSelectedListener(this);

        logo = findViewById(R.id.logoImg);

        logo.setOnClickListener(this);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu1:
                Log.d("MainActivity", "우리동네알림");
                Intent alterIntent = new Intent(this, NoticeActivity.class);
                startActivity(alterIntent);
                finish();
                break;
            case R.id.menu2:
                Log.d("MainActivity", "파출소찾기");
                Intent policeIntent = new Intent(this, PoliceFindActivity.class);
                startActivity(policeIntent);
                finish();
                break;
            case R.id.menu3:
                Log.d("MainActivity", "공개수배");
                Intent wantedIntent = new Intent(this, WantedActivity.class);
                startActivity(wantedIntent);
                finish();
                break;
            case R.id.menu4:
                Log.d("MainActivity", "신고하기");
                Intent reportIntent = new Intent(this, ReportActivity.class);
                startActivity(reportIntent);
                finish();
                break;
            case R.id.menu5:
                Log.d("MainActivity", "마이페이지");
                Intent myIntent = new Intent(this, MypageActivity.class);
                startActivity(myIntent);
                finish();
                break;
            case R.id.menu6:
                Log.d("MainActivity", "로그아웃");
                break;
        }
        return true;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.logoImg:
                Log.d("MonFaceActivity", "로고클릭");
                Intent logoIntent = new Intent(this, MainActivity.class);
                startActivity(logoIntent);
                finish();
                break;
        }
    }
}