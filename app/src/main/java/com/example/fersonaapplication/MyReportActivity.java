package com.example.fersonaapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class MyReportActivity extends AppCompatActivity {

    RadioButton rd1,rd2,rd3,rd4,rd5;
    TextView wantedTv,nameTv,phoneTv, reportGetTv, reportGetAdrTv,wantedcontentTv;
    ImageView monResultImg;
    public static String shared = "fersona";
    RequestQueue requestQueue;

    public static Context mContext; // Context 변수 선언

    public static String url;
    public static String position; // Adapter 로 전달할 변수 선언
    public static String rep_no_1, rep_cate_1, rep_con_1, rep_date_1, rep_time_1, rep_adr_1, mon_id_1, want_id_1, rep_pro_1, rep_wri_1;
    public static String rep_no_2, rep_cate_2, rep_con_2, rep_date_2, rep_time_2, rep_adr_2, mon_id_2, want_id_2, rep_pro_2, rep_wri_2;
    public static String rep_no_3, rep_cate_3, rep_con_3, rep_date_3, rep_time_3, rep_adr_3, mon_id_3, want_id_3, rep_pro_3, rep_wri_3;
    public static String rep_no_4, rep_cate_4, rep_con_4, rep_date_4, rep_time_4, rep_adr_4, mon_id_4, want_id_4, rep_pro_4, rep_wri_4;
    public static String rep_no_5, rep_cate_5, rep_con_5, rep_date_5, rep_time_5, rep_adr_5, mon_id_5, want_id_5, rep_pro_5, rep_wri_5;
    public static String position_1, position_2, position_3, position_4, position_5;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_report);

        rd1 = findViewById(R.id.rd1);
        rd2 = findViewById(R.id.rd2);
        rd3 = findViewById(R.id.rd3);
        rd4 = findViewById(R.id.rd4);
        rd5 = findViewById(R.id.rd5);

        wantedTv = findViewById(R.id.wantedTv); // 범죄 유형
        nameTv = findViewById(R.id.nameTv); // 이름
        phoneTv = findViewById(R.id.phoneTv); // 핸드폰 번호
        reportGetTv = findViewById(R.id.reportGetTv); // 신고 내용
        reportGetAdrTv = findViewById(R.id.reportGetAdrTv); // 신고 발생 위치
        wantedcontentTv = findViewById(R.id.wantedcontentTv); // 몽타주 상세 내용

        monResultImg = findViewById(R.id.monResultImg); // 몽타주 이미지

        mContext = this; // oncreate 에 this(는 액티비티 클래스 자체를 의미) 할당

        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences(shared, Context.MODE_PRIVATE);
        rep_no_1 = sharedPreferences.getString("rep_no_1", "");
        rep_cate_1 = sharedPreferences.getString("rep_cate_1", "");
        rep_con_1 = sharedPreferences.getString("rep_con_1", "");
        rep_date_1 = sharedPreferences.getString("rep_date_1", "");
        rep_time_1 = sharedPreferences.getString("rep_time_1", "");
        rep_adr_1 = sharedPreferences.getString("rep_adr_1", "");
        mon_id_1 = sharedPreferences.getString("mon_id_1", "");
        want_id_1 = sharedPreferences.getString("want_id_1", "");
        rep_pro_1 = sharedPreferences.getString("rep_pro_1", "");
        rep_wri_1 = sharedPreferences.getString("rep_wri_1", "");
        position_1 = sharedPreferences.getString("position_1", "");

        rep_no_2 = sharedPreferences.getString("rep_no_2", "");
        rep_cate_2 = sharedPreferences.getString("rep_cate_2", "");
        rep_con_2 = sharedPreferences.getString("rep_con_2", "");
        rep_date_2 = sharedPreferences.getString("rep_date_2", "");
        rep_time_2 = sharedPreferences.getString("rep_time_2", "");
        rep_adr_2 = sharedPreferences.getString("rep_adr_2", "");
        mon_id_2 = sharedPreferences.getString("mon_id_2", "");
        want_id_2 = sharedPreferences.getString("want_id_2", "");
        rep_pro_2 = sharedPreferences.getString("rep_pro_2", "");
        rep_wri_2 = sharedPreferences.getString("rep_wri_2", "");
        position_2 = sharedPreferences.getString("position_2", "");

        rep_no_3 = sharedPreferences.getString("rep_no_3", "");
        rep_cate_3 = sharedPreferences.getString("rep_cate_3", "");
        rep_con_3 = sharedPreferences.getString("rep_con_3", "");
        rep_date_3 = sharedPreferences.getString("rep_date_3", "");
        rep_time_3 = sharedPreferences.getString("rep_time_3", "");
        rep_adr_3 = sharedPreferences.getString("rep_adr_3", "");
        mon_id_3 = sharedPreferences.getString("mon_id_3", "");
        want_id_3 = sharedPreferences.getString("want_id_3", "");
        rep_pro_3 = sharedPreferences.getString("rep_pro_3", "");
        rep_wri_3 = sharedPreferences.getString("rep_wri_3", "");
        position_3 = sharedPreferences.getString("position_3", "");

        rep_no_4 = sharedPreferences.getString("rep_no_4", "");
        rep_cate_4 = sharedPreferences.getString("rep_cate_4", "");
        rep_con_4 = sharedPreferences.getString("rep_con_4", "");
        rep_date_4 = sharedPreferences.getString("rep_date_4", "");
        rep_time_4 = sharedPreferences.getString("rep_time_4", "");
        rep_adr_4 = sharedPreferences.getString("rep_adr_4", "");
        mon_id_4 = sharedPreferences.getString("mon_id_4", "");
        want_id_4 = sharedPreferences.getString("want_id_4", "");
        rep_pro_4 = sharedPreferences.getString("rep_pro_4", "");
        rep_wri_4 = sharedPreferences.getString("rep_wri_4", "");
        position_4 = sharedPreferences.getString("position_4", "");

        rep_no_5 = sharedPreferences.getString("rep_no_5", "");
        rep_cate_5 = sharedPreferences.getString("rep_cate_5", "");
        rep_con_5 = sharedPreferences.getString("rep_con_5", "");
        rep_date_5 = sharedPreferences.getString("rep_date_5", "");
        rep_time_5 = sharedPreferences.getString("rep_time_5", "");
        rep_adr_5 = sharedPreferences.getString("rep_adr_5", "");
        mon_id_5 = sharedPreferences.getString("mon_id_5", "");
        want_id_5 = sharedPreferences.getString("want_id_5", "");
        rep_pro_5 = sharedPreferences.getString("rep_pro_5", "");
        rep_wri_5 = sharedPreferences.getString("rep_wri_5", "");
        position_5 = sharedPreferences.getString("position_5", "");

        Log.d("myreport", mon_id_5);


    }
}