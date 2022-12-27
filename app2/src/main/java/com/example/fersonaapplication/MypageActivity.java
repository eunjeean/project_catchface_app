package com.example.fersonaapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.util.ArrayList;

public class MypageActivity extends AppCompatActivity implements View.OnClickListener {

    // RecyclerView
    ArrayList<MyReportListVO> data;
    private RecyclerView wantedListRv;
    private MyReportAdapter adapter;

    ImageView logo;
    Button mypageBtn, myReportBtn;
    ConstraintLayout myPageCl,myReportCl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mypage);
        // 메뉴
        final DrawerLayout drawerLayout = findViewById(R.id.drawlayout);

        findViewById(R.id.setting).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerLayout.openDrawer(GravityCompat.END);
            }
        });

        data = new ArrayList<>();
        wantedListRv = findViewById(R.id.wantedListRv);

        logo = findViewById(R.id.logoImg);
        mypageBtn = findViewById(R.id.mypageBtn);
        myReportBtn = findViewById(R.id.myReportBtn);

        myPageCl = findViewById(R.id.myPageCl);
        myReportCl = findViewById(R.id.myReportCl);

        myPageCl.setVisibility(View.VISIBLE);
        wantedListRv.setVisibility(View.INVISIBLE);

        logo.setOnClickListener(this);
        mypageBtn.setOnClickListener(this);
        myReportBtn.setOnClickListener(this);

        // RecyclerView
        for(int i=0;i<5;i++){
            addItem("reportCate","reportDate");
        }

        adapter = new MyReportAdapter(data);
        wantedListRv.setAdapter(adapter);
        wantedListRv.setLayoutManager(new LinearLayoutManager(this));
    }

    public void addItem(String reportCate, String reportDate){
        MyReportListVO item = new MyReportListVO(reportCate,reportDate);

        item.setWantedCate(reportCate);
        item.setReportDate(reportDate);

        data.add(item);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.logoImg:
                Log.d("ReportPage","click_reportLogo");
                Intent logoIntent = new Intent(MypageActivity.this, MainActivity.class);
                startActivity(logoIntent);
                finish();
                break;
            case R.id.mypageBtn:
                Log.d("ReportPage","click_내정보");
                wantedListRv.setVisibility(View.INVISIBLE);
                if(myPageCl.getVisibility()==View.VISIBLE){
                    wantedListRv.setVisibility(View.INVISIBLE);
                }else{
                    myPageCl.setVisibility(View.VISIBLE);
                }
                break;
            case R.id.myReportBtn:
                Log.d("ReportPage","click_나의신고");
                myPageCl.setVisibility(View.INVISIBLE);
                if(wantedListRv.getVisibility()==View.VISIBLE){
                    myPageCl.setVisibility(View.INVISIBLE);
                }else{
                    wantedListRv.setVisibility(View.VISIBLE);
                }
                break;
        }
    }
}