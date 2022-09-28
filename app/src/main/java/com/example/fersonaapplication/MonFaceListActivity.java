package com.example.fersonaapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class MonFaceListActivity extends AppCompatActivity implements View.OnClickListener {

    RecyclerView wantedList_rv;
    ImageView logoImg, watnedImg;
    TextView voiceTv;
    Button reportMoveBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mon_face_list);

        wantedList_rv = findViewById(R.id.wantedList_rv);

        logoImg = findViewById(R.id.logo_img);
        watnedImg = findViewById(R.id.wantedImg);

        voiceTv = findViewById(R.id.voice_tv);

        reportMoveBtn = findViewById(R.id.reportMove_btn);

        logoImg.setOnClickListener(this);
        reportMoveBtn.setOnClickListener(this);

    }


    @Override
    public void onClick(View view) {

    }
}