package com.example.fersonaapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    EditText loginIdEt, loginPwEt;
    Button loginBtn, joinBtn;
    RequestQueue requestQueue;
    public static String id, pw, url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        loginIdEt = findViewById(R.id.loginIdEt);
        loginPwEt = findViewById(R.id.loginPwEt);
        loginBtn = findViewById(R.id.loginBtn);
        joinBtn = findViewById(R.id.joinBtn);

        loginBtn.setOnClickListener(this);
        joinBtn.setOnClickListener(this);

        if(requestQueue==null){
            requestQueue = Volley.newRequestQueue(getApplicationContext());
        }

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.loginBtn:
                Log.d("login","click_loginBtn");

                id = loginIdEt.getText().toString();
                pw = loginPwEt.getText().toString();

                url = "http://121.147.52.96:5000/login";

                StringRequest request = new StringRequest(
                        Request.Method.POST,
                        url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                Toast.makeText(LoginActivity.this, "연결성공😊", Toast.LENGTH_SHORT).show();

                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Toast.makeText(LoginActivity.this, "연결실패😣", Toast.LENGTH_SHORT).show();
                            }
                        }
                ){
                    // getParams 라는 메서드 오버라이딩 : alt + insert
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        // Map : dictionary, json과 비슷한 key, value로 이루어져 있음
                        Map<String, String> params = new HashMap<>();
                        params.put("id", id);
                        params.put("pw", pw);

                        return params;
                    }
                };

                request.setShouldCache(false);
                requestQueue.add(request);
                Intent mainIntent = new Intent(LoginActivity.this, MainActivity2.class);
                startActivity(mainIntent);

                break;

            case R.id.joinBtn:
                Log.d("login","click_joinBtn");
                Intent joinIntent = new Intent(LoginActivity.this, JoinActivity.class);
                startActivity(joinIntent);
                break;

        }
    }
}