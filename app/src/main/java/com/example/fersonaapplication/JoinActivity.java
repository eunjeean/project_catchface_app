package com.example.fersonaapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Spinner;
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

public class JoinActivity extends AppCompatActivity implements View.OnClickListener {

    TextView passwordCkTv,joinTv;
    EditText joinIdEt, joinPwEt, joinPwCkEt,joinNameEt, phoneEt;
    Button idCheckBtn, submitBtn;
    DatePicker joinDate;
    ImageView logoImg;
    RequestQueue requestQueue;
    Spinner citySpin, dongSpin;
    RadioButton dateRb, liveRb;
    public static String id, pw, name, yy, mm, dd, date, city, dong, phone, date2;
    public static boolean check=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join);

        logoImg = findViewById(R.id.logoImg);

        passwordCkTv = findViewById(R.id.passwordCkTv);
        joinTv = findViewById(R.id.joinTv);

        joinIdEt = findViewById(R.id.joinIdEt);
        joinPwEt = findViewById(R.id.joinPwEt);
        joinPwCkEt = findViewById(R.id.joinPwCkEt);
        joinNameEt = findViewById(R.id.joinNameEt);
        phoneEt = findViewById(R.id.phoneEt);

        joinDate = findViewById(R.id.joinDate);
        citySpin = findViewById(R.id.citySpin);
        dongSpin = findViewById(R.id.dongSpin);

        idCheckBtn = findViewById(R.id.idCheckBtn);
        submitBtn = findViewById(R.id.submitBtn);

        dateRb = findViewById(R.id.dateRb);
        liveRb = findViewById(R.id.liveRb);

        logoImg.setOnClickListener(this);
        idCheckBtn.setOnClickListener(this);
        submitBtn.setOnClickListener(this);

        joinDate_show(); // 회원생년월일 스피너

        liveAdr_show(); // 회원주소 스피너

        password_check(); // 비밀번호 동일한지 확인

        if(requestQueue==null){
            requestQueue = Volley.newRequestQueue(getApplicationContext());
        }



    }



    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.logoImg:
                Log.d("Join","로고클릭");
                Intent logoIntent = new Intent(this, LoginActivity.class);
                startActivity(logoIntent);
                finish();
                break;

            case R.id.idCheckBtn:
                Log.d("Join","아이디체크");
                id_check(); // 아이디 중복 확인
                break;

            case R.id.submitBtn:
                Log.d("Join","회원가입");
                submit_join(); // 회원가입 버튼 눌렀을 때
                break;
        }

    }

    // 회원생년월일 스피너
    private void joinDate_show() {
        joinDate.init(joinDate.getYear(), joinDate.getMonth(), joinDate.getDayOfMonth(),
                new DatePicker.OnDateChangedListener() {
                    @Override
                    public void onDateChanged(DatePicker datePicker, int i, int i1, int i2) {
                        yy = Integer.toString(i);
                        mm = Integer.toString(i1+1); // 핸드폰 연결해서 확인해봐야 함!
                        if(mm.length()==1){ mm = "0"+mm; }
                        dd = Integer.toString(i2);
                        if(dd.length()==1){ dd = "0"+dd; }
                        check=true;
                        date = yy + mm + dd;
                        date2 = yy+"."+mm+"."+dd+".";
                        joinTv.setVisibility(View.VISIBLE);
                        dateRb.setChecked(true);
                        dateRb.setButtonTintList(getResources().getColorStateList(R.color.pointOrange));
                        joinTv.setText(date2);
                    }
                });
    }

    // 회원주소 스피너
    private void liveAdr_show() {
        citySpin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(!citySpin.getSelectedItem().toString().equals("선택(시)") && !dongSpin.getSelectedItem().toString().equals("선택(동)")){
                    liveRb.setChecked(true);
                    liveRb.setButtonTintList(getResources().getColorStateList(R.color.pointOrange));
                }else{
                    liveRb.setChecked(false);
                    liveRb.setButtonTintList(getResources().getColorStateList(R.color.btnGray));
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    // 아이디 중복 확인
    private void id_check() {
        id = joinIdEt.getText().toString();

        String url = "http://121.147.52.96:5000/idCheck";

        StringRequest request = new StringRequest(
                Request.Method.POST,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(JoinActivity.this, "아이디 사용 가능😊", Toast.LENGTH_SHORT).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(JoinActivity.this, "아이디 사용 불가능😢", Toast.LENGTH_SHORT).show();
                    }
                }
        ){

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("id", id);
                return params;
            }
        };
        request.setShouldCache(false);
        requestQueue.add(request);
    }

    // 비밀번호 동일한지 확인
    private void password_check() {
        joinNameEt.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if(joinPwEt.getText().toString().equals(joinPwCkEt.getText().toString())){
                    passwordCkTv.setVisibility(View.VISIBLE);
                    passwordCkTv.setText("🤗비밀번호가 동일합니다🤗");
                }else if(!joinPwEt.getText().toString().equals(joinPwCkEt.getText().toString())){
                    passwordCkTv.setVisibility(View.VISIBLE);
                    passwordCkTv.setText("😥비밀번호를 다시 확인해주세요😥");
                }else{
                    passwordCkTv.setVisibility(View.GONE);
                }
            }
        });
    }

    // 회원가입 버튼 눌렀을 때
    private void submit_join() {
        id = joinIdEt.getText().toString();
        pw = joinPwEt.getText().toString();
        name = joinNameEt.getText().toString();
        city = citySpin.getSelectedItem().toString();
        dong = dongSpin.getSelectedItem().toString();
        phone = phoneEt.getText().toString();

        String url = "http://121.147.52.96:5000/join";

        // 요청 만들기
        StringRequest request = new StringRequest(
                Request.Method.POST,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(JoinActivity.this, "회원가입 연결 성공😊", Toast.LENGTH_SHORT).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(JoinActivity.this, "회원가입 연결 실패😣", Toast.LENGTH_SHORT).show();
                    }
                }
        ){ // getParams 라는 메서드 오버라이딩 : alt + insert
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("id", id);
                params.put("pw", pw);
                params.put("name", name);
                params.put("date", date);
                params.put("city", city);
                params.put("dong", dong);
                params.put("phone", phone);
                return params;
            }
        };
        request.setShouldCache(false);
        requestQueue.add(request);
        finish();
    }



}