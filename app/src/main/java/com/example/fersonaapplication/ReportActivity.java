package com.example.fersonaapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
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
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

import java.io.IOException;

public class ReportActivity extends AppCompatActivity implements View.OnClickListener, NavigationView.OnNavigationItemSelectedListener {

    ImageView logo, wantedImg;
    RadioButton rd1, rd2, rd3, rd4, rd5,rd6, rd7, rd8;
    Spinner wantedSpin;
    EditText reportConEt;
    DatePicker repDate;
    TimePicker repTime;
    ImageButton galleryBtn;
    TextView dateTv,timeTv,nameTv;
    Button repAdrBtn, monMakeBtn, wantedviewBtn, infoViewBtn, submitBtn;
    CheckBox wantedCk, infoCk;

    String reportCont = null;
    String reportWanted = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //        상단 시간 및 배터리 부분 감추기
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
//                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_report);

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

//        초기화
        logo = findViewById(R.id.logoImg);

        wantedImg = findViewById(R.id.wantedImg);

        rd1 = findViewById(R.id.rd1);
        rd2 = findViewById(R.id.rd2);
        rd3 = findViewById(R.id.rd3);
        rd4 = findViewById(R.id.rd4);
//        rd5 = findViewById(R.id.rd5);
        rd6 = findViewById(R.id.rd6);
        rd7 = findViewById(R.id.rd7);
        rd8 = findViewById(R.id.rd8);

        wantedSpin = findViewById(R.id.wantedSpin);
        reportConEt = findViewById(R.id.reportConEt);
        repDate = findViewById(R.id.repDate);
        repTime = findViewById(R.id.repTime);

        dateTv = findViewById(R.id.dateTv);
        timeTv = findViewById(R.id.timeTv);
        nameTv = findViewById(R.id.nameTv2);

//        galleryBtn = findViewById(R.id.gallery_btn);
        repAdrBtn = findViewById(R.id.repAdrBtn);
        monMakeBtn = findViewById(R.id.monMakeBtn);
        wantedviewBtn = findViewById(R.id.wantedviewBtn);
        infoViewBtn = findViewById(R.id.infoViewBtn);
        submitBtn = findViewById(R.id.submitBtn);

        wantedCk = findViewById(R.id.wantedCk);
        infoCk = findViewById(R.id.infoCk);

        logo.setOnClickListener(this);
//        galleryBtn.setOnClickListener(this);
        repAdrBtn.setOnClickListener(this);
        monMakeBtn.setOnClickListener(this);
        wantedviewBtn.setOnClickListener(this);
        infoViewBtn.setOnClickListener(this);
        submitBtn.setOnClickListener(this);

        // 범죄유형 체크
        WantedCheck();

        // 신고내용 작성
        if(reportConEt.getText().toString().length() < 5){
            Toast.makeText(this, "신고내용을 작성해주세요😊", Toast.LENGTH_SHORT).show();
        }else if(180 < reportConEt.getText().toString().length()){
            Toast.makeText(this, "신고내용이 초과하였습니다.😥", Toast.LENGTH_SHORT).show();
        }else{
            rd2.setChecked(true);
            rd2.setButtonTintList(ColorStateList.valueOf(ContextCompat.getColor(ReportActivity.this, R.color.pointOrange)));
            reportCont = reportConEt.getText().toString();
            Log.d("ReportActivity","신고내용 : "+reportCont);
        }

        // 사건발생일자
        ReportDate();

        // 사건발생시간
        ReportTime();

    }

    // 범죄유형 체크
    private void WantedCheck() {
        wantedSpin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                Log.d("ReportActivity",""+wantedSpin.getItemAtPosition(position));
                if(!wantedSpin.getItemAtPosition(position).toString().equals("선택")){
                    rd1.setChecked(true);
                    rd1.setButtonTintList(ColorStateList.valueOf(ContextCompat.getColor(ReportActivity.this, R.color.pointOrange)));
                    reportWanted = (String) wantedSpin.getItemAtPosition(position);
                    Log.d("ReportActivity","범죄유형 선택 = "+reportWanted);
                }else{
                    rd1.setChecked(false);
                    rd1.setButtonTintList(ColorStateList.valueOf(ContextCompat.getColor(ReportActivity.this, R.color.btnGray)));
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    // 사건발생일자
    private void ReportDate() {
        repDate.init(repDate.getYear(), repDate.getMonth(), repDate.getDayOfMonth(), new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker view, int year, int month, int day) {
                // 값이 바뀔때마다 텍스트 뷰의 값을 바꿔준다
                dateTv.setVisibility(View.VISIBLE);
                rd3.setChecked(true);
                rd3.setButtonTintList(ColorStateList.valueOf(ContextCompat.getColor(ReportActivity.this, R.color.pointOrange)));
                dateTv.setText(String.format("%d/%d/%d",year,month+1,day));
            }
        });
    }

    // 사건발생시간
    private void ReportTime() {
        repTime.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker timePicker, int hour, int min) {
                timeTv.setVisibility(View.VISIBLE);
                rd4.setChecked(true);
                rd4.setButtonTintList(ColorStateList.valueOf(ContextCompat.getColor(ReportActivity.this, R.color.pointOrange)));

                if(Build.VERSION.SDK_INT>= Build.VERSION_CODES.M){
                    hour = repTime.getHour();
                    min = repTime.getMinute();
                }else{
                    hour = repTime.getCurrentHour();
                    min = repTime.getCurrentMinute();
                }

                if(hour>=12){
                    timeTv.setText(String.format("PM "+"%d : %d",hour,min));
                }else{
                    timeTv.setText(String.format("AM "+"%d : %d",hour,min));
                }

            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.logoImg:
                Log.d("ReportPage","로고");
                Intent logoIntent = new Intent(ReportActivity.this, MainActivity.class);
                startActivity(logoIntent);
                finish();
                break;

//            case R.id.gallery_btn:
//                Log.d("ReportPage","click_reportGalary");
//                Intent gallery = new Intent(Intent.ACTION_PICK);
//                gallery.setType("image/*");
//                gallery.setAction(Intent.ACTION_GET_CONTENT);
//                startActivityForResult(Intent.createChooser(gallery, "이미지를 선택하세요"), 0);
//                break;

            case R.id.repAdrBtn:
                Log.d("ReportPage","위치찾기");
                new AlertDialog.Builder(this)
                        .setTitle("위치찾기")
                        .setMessage("지도API 보여줄 예정😁")
                        // positive : 긍정 , setNegativeButton : 부정, setNeutralButton : 긍정도 부정도 아닌
                        .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dlg, int sumthin) {
                                rd6.setChecked(true);
                                rd6.setButtonTintList(ColorStateList.valueOf(ContextCompat.getColor(ReportActivity.this, R.color.pointOrange)));
                            }
                        })
                        .setNeutralButton("닫기", new DialogInterface.OnClickListener(){
                            public void onClick(DialogInterface dialog, int which){
                            }
                        })
                        .show(); // 팝업창 보여줌
                break;

            case R.id.monMakeBtn:
                Log.d("ReportPage","몽타주생성");
                Intent monMakeIntent = new Intent(ReportActivity.this, MonFaceActivity.class);
                monMakeIntent.putExtra("reportWanted",reportWanted);
                startActivity(monMakeIntent);
                break;

            case R.id.wantedviewBtn:
                Log.d("ReportPage","신고내용 공유동의");
                new AlertDialog.Builder(this)
                        .setTitle("신고내용동의서")
                        .setMessage("신고내용동의 내용 작성예정😁")
                        // positive : 긍정 , setNegativeButton : 부정, setNeutralButton : 긍정도 부정도 아닌
                        .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dlg, int sumthin) {
                                wantedCk.setChecked(true);
                            }
                        })
                        .setNeutralButton("닫기", new DialogInterface.OnClickListener(){
                            public void onClick(DialogInterface dialog, int which){
                                wantedCk.setChecked(false);
                            }
                        })
                        .show(); // 팝업창 보여줌

                break;

            case R.id.infoViewBtn:
                Log.d("ReportPage","개인정보 수집동의");
                new AlertDialog.Builder(this)
                        .setTitle("개인정보 수집동의")
                        .setMessage("개인정보 수집동의 내용 작성예정😁")
                        // positive : 긍정 , setNegativeButton : 부정, setNeutralButton : 긍정도 부정도 아닌
                        .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dlg, int sumthin) {
                                infoCk.setChecked(true);
                            }
                        })
                        .setNeutralButton("닫기", new DialogInterface.OnClickListener(){
                            public void onClick(DialogInterface dialog, int which){
                                infoCk.setChecked(false);
                            }
                        })
                        .show(); // 팝업창 보여줌
                break;

            case R.id.submitBtn:
                Log.d("ReportPage","제출하기");
                Log.d("ReportPage submitBtn",""+reportWanted+" , "+reportCont+" , "+dateTv.getText()+" , "+timeTv.getText());
                break;
        }
    }

    // 갤러리 사진 선택
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        //request코드가 0이고 ok를 선택했고 data에 뭔가가 들어있다면
        if (requestCode == 0 && resultCode == RESULT_OK) {
            Uri uri = data.getData();
            Log.d("ReportPage", "uri:" + String.valueOf(uri));
            try {
                //Uri파일을 Bitmap으로 만들어서 ImageView에 집어 넣는다.
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                wantedImg.setImageBitmap(bitmap);
            } catch (IOException e) {
                Toast.makeText(this, "로딩에 오류가 있습니다😥", Toast.LENGTH_LONG).show();
                e.printStackTrace();
            }
        } else {
            Toast.makeText(this, "취소 되었습니다.😣", Toast.LENGTH_SHORT).show();
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
