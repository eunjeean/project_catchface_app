package com.example.fersonaapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;

public class MonFaceListActivity extends AppCompatActivity implements View.OnClickListener, NavigationView.OnNavigationItemSelectedListener {

    // RecyclerView
    ArrayList<MonFaceListVO> data;
    private RecyclerView wantedListRv;
    private MonRAdapter adapter;

    ImageView logoImg, watnedImg;
    TextView voiceTv;
    Button reportMoveBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mon_face_list);

        // 메뉴
        final DrawerLayout drawerLayout = findViewById(R.id.drawlayout);

        findViewById(R.id.menu).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerLayout.openDrawer(GravityCompat.END);
            }
        });

        NavigationView menuNv = findViewById(R.id.menuNv);
        menuNv.setNavigationItemSelectedListener(this);

        data = new ArrayList<>();
        wantedListRv = findViewById(R.id.wantedListRv);

        logoImg = findViewById(R.id.logoImg);
        watnedImg = findViewById(R.id.wantedImg);

        voiceTv = findViewById(R.id.voiceTv);

        reportMoveBtn = findViewById(R.id.reportMoveBtn);

        logoImg.setOnClickListener(this);
        reportMoveBtn.setOnClickListener(this);


        // RecyclerView
        for(int i=0;i<4;i++){
            addItem("img");
        }

        adapter = new MonRAdapter(data);
        wantedListRv.setAdapter(adapter);
        wantedListRv.setLayoutManager(new LinearLayoutManager(this,RecyclerView.HORIZONTAL, false));

    }

    public void addItem(String imgName){
        MonFaceListVO item = new MonFaceListVO();

        item.setMonList(imgName);

        data.add(item);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.logoImg:
                Log.d("MonFaceActivity","로고클릭");
                Intent logoIntent = new Intent(MonFaceListActivity.this, MainActivity.class);
                startActivity(logoIntent);
                finish();
                break;

            case R.id.reportMoveBtn:
                Log.d("MonFaceActivity","신고하기페이지 이동");
                Intent monfaceIntent = new Intent(this, ReportActivity.class);
                startActivity(monfaceIntent);
                finish();
                break;
        }

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
}