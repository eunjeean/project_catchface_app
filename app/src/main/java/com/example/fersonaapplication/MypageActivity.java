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
    private RecyclerView wantedList_rv;
    private MyReportAdapter adapter;

    ImageView logo;
    Button myPagebtn, myReportbtn;
    ConstraintLayout myPage_cl,myReport_cl;

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
        wantedList_rv = findViewById(R.id.wantedList_rv);

        logo = findViewById(R.id.logoImg);
        myPagebtn = findViewById(R.id.mypage_btn);
        myReportbtn = findViewById(R.id.myReport_btn);

        myPage_cl = findViewById(R.id.myPage_cl);
        myReport_cl = findViewById(R.id.myReport_cl);

        myPage_cl.setVisibility(View.VISIBLE);
        wantedList_rv.setVisibility(View.INVISIBLE);

        logo.setOnClickListener(this);
        myPagebtn.setOnClickListener(this);
        myReportbtn.setOnClickListener(this);

        // RecyclerView
        for(int i=0;i<5;i++){
            addItem("reportCate","reportDate");
        }

        adapter = new MyReportAdapter(data);
        wantedList_rv.setAdapter(adapter);
        wantedList_rv.setLayoutManager(new LinearLayoutManager(this));
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
            case R.id.mypage_btn:
                Log.d("ReportPage","click_내정보");
                wantedList_rv.setVisibility(View.INVISIBLE);
                if(myPage_cl.getVisibility()==View.VISIBLE){
                    wantedList_rv.setVisibility(View.INVISIBLE);
                }else{
                    myPage_cl.setVisibility(View.VISIBLE);
                }
                break;
            case R.id.myReport_btn:
                Log.d("ReportPage","click_나의신고");
                myPage_cl.setVisibility(View.INVISIBLE);
                if(wantedList_rv.getVisibility()==View.VISIBLE){
                    myPage_cl.setVisibility(View.INVISIBLE);
                }else{
                    wantedList_rv.setVisibility(View.VISIBLE);
                }
                break;
        }
    }
}