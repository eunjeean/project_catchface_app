package com.example.fersonaapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

public class MyReportActivity extends AppCompatActivity {

    RadioButton rd1,rd2,rd3,rd4,rd5;
    TextView wantedTv,nameTv,phoneTv,reportGetAdrTv,wantedcontentTv;
    ImageView monResultImg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_report);

        rd1 = findViewById(R.id.rd1);
        rd2 = findViewById(R.id.rd2);
        rd3 = findViewById(R.id.rd3);
        rd4 = findViewById(R.id.rd4);
        rd5 = findViewById(R.id.rd5);

        wantedTv = findViewById(R.id.wantedTv);
        nameTv = findViewById(R.id.nameTv);
        phoneTv = findViewById(R.id.phoneTv);
        reportGetAdrTv = findViewById(R.id.reportGetAdrTv);
        wantedcontentTv = findViewById(R.id.wantedcontentTv);

        monResultImg = findViewById(R.id.monResultImg);






    }
}