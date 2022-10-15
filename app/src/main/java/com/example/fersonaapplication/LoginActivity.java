package com.example.fersonaapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    EditText loginIdEt, loginPwEt;
    Button loginBtn, joinBtn;
    RequestQueue requestQueue;
    public static String loginAll, id, pw, url, name, date, city, dong, phone;
    public static String shared = "fersona";

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
                        Request.Method.POST, url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                Toast.makeText(LoginActivity.this, "연결성공😊", Toast.LENGTH_SHORT).show();
                                Intent reportIntent = new Intent(LoginActivity.this, MainActivity2.class);
//                                reportIntent.putExtra("response", response);
                                try {
                                    JSONArray array = new JSONArray(response);
                                    loginAll = response;
                                    id = array.getString(1);
                                    pw = array.getString(2);
                                    name = array.getString(3);
                                    date = array.getString(4);
                                    city = array.getString(5);
                                    dong = array.getString(6);
                                    phone = array.getString(7);
                                } catch (JSONException e) {  e.printStackTrace(); }

                                SharedPreferences sharedPreferences = getSharedPreferences(shared, MODE_PRIVATE);    // test 이름의 기본모드 설정
                                SharedPreferences.Editor editor = sharedPreferences.edit(); //sharedPreferences를 제어할 editor를 선언
                                editor.putString("loginALl", loginAll);
                                editor.putString("id", id);
                                editor.putString("pw", pw);
                                editor.putString("name", name);
                                editor.putString("date", date);
                                editor.putString("city", city);
                                editor.putString("dong", dong);
                                editor.putString("phone", phone);
                                editor.commit();    //최종 커밋. 커밋을 해야 저장이 된다.
                                startActivity(reportIntent);
//                                FragmentView(1);

                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Toast.makeText(LoginActivity.this, "연결실패😣", Toast.LENGTH_SHORT).show();
                            }
                        }
                ){
                    // 안드로이드에서 한글 인코딩
                    @Override
                    protected Response<String> parseNetworkResponse(NetworkResponse response) {
                        try {
                            String utf8String = new String(response.data, "UTF-8");
                            return Response.success(utf8String, HttpHeaderParser.parseCacheHeaders(response));
                        } catch (UnsupportedEncodingException e) {
                            // log error
                            return Response.error(new ParseError(e));
                        } catch (Exception e) {
                            // log error
                            return Response.error(new ParseError(e));
                        }
                    }

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
                break;

            case R.id.joinBtn:
                Log.d("login","click_joinBtn");
                Intent joinIntent = new Intent(LoginActivity.this, JoinActivity.class);
                startActivity(joinIntent);
                break;

        }
    }

}