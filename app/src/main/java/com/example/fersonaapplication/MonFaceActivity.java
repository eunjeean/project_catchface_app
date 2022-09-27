package com.example.fersonaapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;

public class MonFaceActivity extends AppCompatActivity implements View.OnClickListener {

    ImageView logo;
    EditText monMake_et;
    Button make_btn;
    ImageButton voice_btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mon_face);

        logo = findViewById(R.id.logo_img);
        monMake_et = findViewById(R.id.monMake_et);
        voice_btn = findViewById(R.id.voice_btn);
        make_btn = findViewById(R.id.make_btn);

        logo.setOnClickListener(this);
        monMake_et.setOnClickListener(this);
        voice_btn.setOnClickListener(this);
        make_btn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.logo_img:
                Log.d("MonFaceActivity","click_logo");
                Intent logoIntent = new Intent(MonFaceActivity.this, MainActivity.class);
                startActivity(logoIntent);
                finish();
                break;

            case R.id.monMake_et:
                Log.d("MonFaceActivity","click_monMake");

                break;

            case R.id.voice_btn:
                Log.d("MonFaceActivity","click_voice");
                break;

            case R.id.make_btn:
                Log.d("MonFaceActivity","click_makeMake");
                break;
        }
    }
}