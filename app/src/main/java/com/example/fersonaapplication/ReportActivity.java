package com.example.fersonaapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

public class ReportActivity extends AppCompatActivity implements View.OnClickListener {

    ImageView logo, menu;
    RadioButton rd1, rd2, rd3, rd4, rd5,rd6, rd7, rd8;
    Spinner wanted_spin;
    EditText report_et;
    DatePicker repDate;
    TimePicker repTime;
    ImageButton galleryBtn;
    TextView name_tv;
    Button repAdr_btn, monMake_btn, wantedView_btn, infoView_btn, submit_btn;
    CheckBox wanted_ck, info_ck;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //        상단 시간 및 배터리 부분 감추기
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
//                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_report);

        //        초기화
        logo = findViewById(R.id.logo_img);
        menu = findViewById(R.id.menu);

        rd1 = findViewById(R.id.rd1);
        rd2 = findViewById(R.id.rd2);
        rd3 = findViewById(R.id.rd3);
        rd4 = findViewById(R.id.rd4);
        rd5 = findViewById(R.id.rd5);
        rd6 = findViewById(R.id.rd6);
        rd7 = findViewById(R.id.rd7);
        rd8 = findViewById(R.id.rd8);

        wanted_spin = findViewById(R.id.wanted_spin);
        report_et = findViewById(R.id.report_con_et);
        repDate = findViewById(R.id.rep_date);
        repTime = findViewById(R.id.rep_time);
        name_tv = findViewById(R.id.name_tv2);

        galleryBtn = findViewById(R.id.gallery_btn);
        repAdr_btn = findViewById(R.id.repAdr_btn);
        monMake_btn = findViewById(R.id.monMake_btn);
        wantedView_btn = findViewById(R.id.wantedview_btn);
        infoView_btn = findViewById(R.id.infoView_btn);
        submit_btn = findViewById(R.id.submit_btn);

        wanted_ck = findViewById(R.id.wanted_ck);
        info_ck = findViewById(R.id.info_ck);

        logo.setOnClickListener(this);
        menu.setOnClickListener(this);
        galleryBtn.setOnClickListener(this);
        repAdr_btn.setOnClickListener(this);
        monMake_btn.setOnClickListener(this);
        wantedView_btn.setOnClickListener(this);
        infoView_btn.setOnClickListener(this);
        submit_btn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.logo_img:
                Log.d("ReportPage","click_reportLogo");
                Intent logoIntent = new Intent(ReportActivity.this, MainActivity.class);
                startActivity(logoIntent);
                finish();
                break;

            case R.id.menu:
                Log.d("ReportPage","click_reportMenu");

                break;

            case R.id.gallery_btn:
                Log.d("ReportPage","click_reportGalary");

                break;

            case R.id.repAdr_btn:
                Log.d("ReportPage","click_reportAddres");

                break;

            case R.id.monMake_btn:
                Log.d("ReportPage","click_reportMonMake");
                Intent monMakeIntent = new Intent(ReportActivity.this, MonFaceActivity.class);
                startActivity(monMakeIntent);
                break;

            case R.id.wantedview_btn:
                Log.d("ReportPage","click_reportWantedView");

                break;

            case R.id.infoView_btn:
                Log.d("ReportPage","click_reportInfoView");

                break;

            case R.id.submit_btn:
                Log.d("ReportPage","click_reportSubmit");

                break;
        }
    }
}