package com.example.fersonaapplication;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class FragmentMypage extends Fragment implements View.OnClickListener {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    // RecyclerView
    ArrayList<MyReportListVO> data;
    private RecyclerView wantedListRv;
    private MyReportAdapter adapter;

    ImageView logo;
    Button mypageBtn, myReportBtn,logoutBtn;
    ConstraintLayout myPageCl,myReportCl;
    TextView myIdTv, phoneTv;
    EditText nameTv, addressTv;

    // volley
    public static String a, id, pw, name, date, city, dong, phone, url, mem_id;
    public static String shared = "fersona";
    public static String rep_no_1, rep_cate_1, rep_con_1, rep_date_1, rep_time_1, rep_adr_1, mon_id_1, want_id_1, rep_pro_1, rep_wri_1;
    public static String rep_no_2, rep_cate_2, rep_con_2, rep_date_2, rep_time_2, rep_adr_2, mon_id_2, want_id_2, rep_pro_2, rep_wri_2;
    public static String rep_no_3, rep_cate_3, rep_con_3, rep_date_3, rep_time_3, rep_adr_3, mon_id_3, want_id_3, rep_pro_3, rep_wri_3;
    public static String rep_no_4, rep_cate_4, rep_con_4, rep_date_4, rep_time_4, rep_adr_4, mon_id_4, want_id_4, rep_pro_4, rep_wri_4;
    public static String rep_no_5, rep_cate_5, rep_con_5, rep_date_5, rep_time_5, rep_adr_5, mon_id_5, want_id_5, rep_pro_5, rep_wri_5;
    public static String position_1, position_2, position_3, position_4, position_5;

    RequestQueue requestQueue;
    StringRequest request;


    public FragmentMypage() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mypage, container, false);

        data = new ArrayList<>();
        wantedListRv = view.findViewById(R.id.wantedListRv);

        mypageBtn = view.findViewById(R.id.mypageBtn);
        myReportBtn = view.findViewById(R.id.myReportBtn);
        logoutBtn = view.findViewById(R.id.logoutBtn);

        myPageCl = view.findViewById(R.id.myPageCl);
        myReportCl = view.findViewById(R.id.myReportCl);

        myIdTv = view.findViewById(R.id.myIdTv);
        nameTv = view.findViewById(R.id.nameTv);
        phoneTv = view.findViewById(R.id.phoneTv);
        addressTv = view.findViewById(R.id.addressTv);

        myPageCl.setVisibility(View.INVISIBLE);
        wantedListRv.setVisibility(View.VISIBLE);

        mypageBtn.setOnClickListener(this);
        myReportBtn.setOnClickListener(this);
        logoutBtn.setOnClickListener(this);
        wantedListRv.setOnClickListener(this);

        // LoginActivity에서 로그인 정보 불러오기
        loginContext();


        // RecyclerView
        for(int i=0;i<5;i++){
            Toast.makeText(getActivity(), "나의 신고목록 페이지입니다😊", Toast.LENGTH_SHORT).show();
//            addItem("reportCate","reportDate");
        }

        adapter = new MyReportAdapter(data);
        wantedListRv.setAdapter(adapter);
        wantedListRv.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext()));

        if(requestQueue==null){
            requestQueue = Volley.newRequestQueue(getActivity().getApplicationContext());
        }


        url = "http://121.147.52.96:5000/myreportALL";

        StringRequest request = new StringRequest(
                Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(getActivity().getApplicationContext(), "연결성공😊", Toast.LENGTH_SHORT).show();
//                        Intent mypageIntent = new Intent(getActivity().getApplicationContext(), MainActivity2.class);
//                                mypageIntent.putExtra("response", response);
                        try {
                            JSONArray array = new JSONArray(response);

                            // 서버에서 report 정보 가져오기
                            getReportContent(array);

                        } catch (JSONException e) {  e.printStackTrace(); }

                        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(shared, Context.MODE_PRIVATE);    // test 이름의 기본모드 설정
                        SharedPreferences.Editor editor = sharedPreferences.edit(); //sharedPreferences를 제어할 editor를 선언

                        // report 정보 SharedPreferences.Editor
                        editorPutReportContent(editor);

                        editor.putString("mem_id", id);

                        editor.commit();    //최종 커밋. 커밋을 해야 저장이 된다.
//                        startActivity(mypageIntent);

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getActivity().getApplicationContext(), "연결실패😣", Toast.LENGTH_SHORT).show();
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
                params.put("mem_id", id);
                params.put("mem_pw", pw);

                return params;
            }
        };

        request.setShouldCache(false);
        requestQueue.add(request);


        return view;
    }


    // 서버에서 report 정보 가져오기
    private void getReportContent(JSONArray array) throws JSONException {

        rep_no_1 = array.getJSONArray(0).getString(0);
        rep_cate_1 = array.getJSONArray(0).getString(1);
        rep_con_1 = array.getJSONArray(0).getString(2);
        rep_date_1 = array.getJSONArray(0).getString(3);
        rep_time_1 = array.getJSONArray(0).getString(4);
        mem_id = array.getJSONArray(0).getString(5);
        rep_adr_1 = array.getJSONArray(0).getString(6);
        mon_id_1 = array.getJSONArray(0).getString(7);
        want_id_1 = array.getJSONArray(0).getString(8);
        rep_pro_1 = array.getJSONArray(0).getString(9);
        rep_wri_1 = array.getJSONArray(0).getString(10);
        position_1 = array.getJSONArray(0).getString(11);

        rep_no_2 = array.getJSONArray(1).getString(0);
        rep_cate_2 = array.getJSONArray(1).getString(1);
        rep_con_2 = array.getJSONArray(1).getString(2);
        rep_date_2 = array.getJSONArray(1).getString(3);
        rep_time_2 = array.getJSONArray(1).getString(4);
        mem_id = array.getJSONArray(1).getString(5);
        rep_adr_2 = array.getJSONArray(1).getString(6);
        mon_id_2 = array.getJSONArray(1).getString(7);
        want_id_2 = array.getJSONArray(1).getString(8);
        rep_pro_2 = array.getJSONArray(1).getString(9);
        rep_wri_2 = array.getJSONArray(1).getString(10);
        position_2 = array.getJSONArray(1).getString(11);


        rep_no_3 = array.getJSONArray(2).getString(0);
        rep_cate_3 = array.getJSONArray(2).getString(1);
        rep_con_3 = array.getJSONArray(2).getString(2);
        rep_date_3 = array.getJSONArray(2).getString(3);
        rep_time_3 = array.getJSONArray(2).getString(4);
        mem_id = array.getJSONArray(2).getString(5);
        rep_adr_3 = array.getJSONArray(2).getString(6);
        mon_id_3 = array.getJSONArray(2).getString(7);
        want_id_3 = array.getJSONArray(2).getString(8);
        rep_pro_3 = array.getJSONArray(2).getString(9);
        rep_wri_3 = array.getJSONArray(2).getString(10);
        position_3 = array.getJSONArray(2).getString(11);


        rep_no_4 = array.getJSONArray(3).getString(0);
        rep_cate_4 = array.getJSONArray(3).getString(1);
        rep_con_4 = array.getJSONArray(3).getString(2);
        rep_date_4 = array.getJSONArray(3).getString(3);
        rep_time_4 = array.getJSONArray(3).getString(4);
        mem_id = array.getJSONArray(3).getString(5);
        rep_adr_4 = array.getJSONArray(3).getString(6);
        mon_id_4 = array.getJSONArray(3).getString(7);
        want_id_4 = array.getJSONArray(3).getString(8);
        rep_pro_4 = array.getJSONArray(3).getString(9);
        rep_wri_4 = array.getJSONArray(3).getString(10);
        position_4 = array.getJSONArray(3).getString(11);


        rep_no_5 = array.getJSONArray(4).getString(0);
        rep_cate_5 = array.getJSONArray(4).getString(1);
        rep_con_5 = array.getJSONArray(4).getString(2);
        rep_date_5 = array.getJSONArray(4).getString(3);
        rep_time_5 = array.getJSONArray(4).getString(4);
        mem_id = array.getJSONArray(4).getString(5);
        rep_adr_5 = array.getJSONArray(4).getString(6);
        mon_id_5 = array.getJSONArray(4).getString(7);
        want_id_5 = array.getJSONArray(4).getString(8);
        rep_pro_5 = array.getJSONArray(4).getString(9);
        rep_wri_5 = array.getJSONArray(4).getString(10);
        position_5 = array.getJSONArray(4).getString(11);

    }

    // report 정보 SharedPreferences.Editor
    private void editorPutReportContent(SharedPreferences.Editor editor) {
        editor.putString("rep_no_1", rep_no_1);
        editor.putString("rep_cate_1", rep_cate_1);
        editor.putString("rep_con_1", rep_con_1);
        editor.putString("rep_date_1", rep_date_1);
        editor.putString("rep_time_1", rep_time_1);
        editor.putString("rep_adr_1", rep_adr_1);
        editor.putString("mon_id_1", mon_id_1);
        editor.putString("want_id_1", want_id_1);
        editor.putString("rep_pro_1", rep_pro_1);
        editor.putString("rep_wri_1", rep_wri_1);

        editor.putString("rep_no_2", rep_no_2);
        editor.putString("rep_cate_2", rep_cate_2);
        editor.putString("rep_con_2", rep_con_2);
        editor.putString("rep_date_2", rep_date_2);
        editor.putString("rep_time_2", rep_time_2);
        editor.putString("rep_adr_2", rep_adr_2);
        editor.putString("mon_id_2", mon_id_2);
        editor.putString("want_id_2", want_id_2);
        editor.putString("rep_pro_2", rep_pro_2);
        editor.putString("rep_wri_2", rep_wri_2);

        editor.putString("rep_no_3", rep_no_3);
        editor.putString("rep_cate_3", rep_cate_3);
        editor.putString("rep_con_3", rep_con_3);
        editor.putString("rep_date_3", rep_date_3);
        editor.putString("rep_time_3", rep_time_3);
        editor.putString("rep_adr_3", rep_adr_3);
        editor.putString("mon_id_3", mon_id_3);
        editor.putString("want_id_3", want_id_3);
        editor.putString("rep_pro_3", rep_pro_3);
        editor.putString("rep_wri_3", rep_wri_3);

        editor.putString("rep_no_4", rep_no_4);
        editor.putString("rep_cate_4", rep_cate_4);
        editor.putString("rep_con_4", rep_con_4);
        editor.putString("rep_date_4", rep_date_4);
        editor.putString("rep_time_4", rep_time_4);
        editor.putString("rep_adr_4", rep_adr_4);
        editor.putString("mon_id_4", mon_id_4);
        editor.putString("want_id_4", want_id_4);
        editor.putString("rep_pro_4", rep_pro_4);
        editor.putString("rep_wri_4", rep_wri_4);

        editor.putString("rep_no_5", rep_no_5);
        editor.putString("rep_cate_5", rep_cate_5);
        editor.putString("rep_con_5", rep_con_5);
        editor.putString("rep_date_5", rep_date_5);
        editor.putString("rep_time_5", rep_time_5);
        editor.putString("rep_adr_5", rep_adr_5);
        editor.putString("mon_id_5", mon_id_5);
        editor.putString("want_id_5", want_id_5);
        editor.putString("rep_pro_5", rep_pro_5);
        editor.putString("rep_wri_5", rep_wri_5);

        for(int i=1; i<6; i++){
            MyReportListVO item = new MyReportListVO();

            String rep_cate = "rep_cate_" + i;
            // 목록 순번
            item.setNum(i+"");
//            item.setNum(rep_no_5+"");
            // Flask 연결하기
            //범죄유형
//                item.setWantedCate("강도");
                item.setWantedCate(rep_cate);
            //신고날짜
//                item.setReportDate("22.01.01");
                item.setReportDate(rep_wri_4);

                if(i==1){
                    item.setWantedCate(rep_cate_1);
                    item.setReportDate(rep_wri_1);
                }
                if(i==2){
                    item.setWantedCate(rep_cate_2);
                    item.setReportDate(rep_wri_2);
                }
                if(i==3){
                    item.setWantedCate(rep_cate_3);
                    item.setReportDate(rep_wri_3);
                }
                if(i==4){
                    item.setWantedCate(rep_cate_4);
                    item.setReportDate(rep_wri_4);
                }
                if(i==5){
                    item.setWantedCate(rep_cate_5);
                    item.setReportDate(rep_wri_5);
                }

            // 데이터 등록
            adapter.addItem(item);
        }



        editor.commit();
    }

    // LoginActivity에서 로그인 정보 불러오기
    private void loginContext() {
        SharedPreferences sharedPreferences = getContext().getSharedPreferences(shared, Context.MODE_PRIVATE);
        id = sharedPreferences.getString("id", "");
        pw = sharedPreferences.getString("pw", "");
        name = sharedPreferences.getString("name", "");
        date = sharedPreferences.getString("date", "");
        city = sharedPreferences.getString("city", "");
        dong = sharedPreferences.getString("dong", "");
        phone = sharedPreferences.getString("phone", "");
        myIdTv.setText(id);
        nameTv.setText(name);
        phoneTv.setText(phone);
        addressTv.setText(city + " " + dong);
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
            case R.id.mypageBtn:
                Log.d("ReportPage","click_내정보");
                wantedListRv.setVisibility(View.INVISIBLE);
                if(myPageCl.getVisibility()==View.VISIBLE){
                    wantedListRv.setVisibility(View.INVISIBLE);
                }else{
                    myPageCl.setVisibility(View.VISIBLE);
                }
                break;
            case R.id.myReportBtn:
                Log.d("ReportPage","click_나의신고");
                myPageCl.setVisibility(View.INVISIBLE);
                if(wantedListRv.getVisibility()==View.VISIBLE){
                    myPageCl.setVisibility(View.INVISIBLE);
                }else{
                    wantedListRv.setVisibility(View.VISIBLE);
                }
                myReportAni();
                break;
            case R.id.logoutBtn:
                Toast.makeText(getActivity(), "로그아웃되었습니다😀", Toast.LENGTH_SHORT).show();
                Intent logoutIntent = new Intent(getActivity().getApplicationContext(),LoginActivity.class);
                startActivity(logoutIntent);
                break;
        }
    }

    private void myReportAni() {
        Log.d("FragmentMypage","myReportAni");
        if(adapter.mList.size()>=5){
            Log.d("FragmentMypage","목록5이상");
        }else{
            for(int i=1; i<6; i++){
                MyReportListVO item = new MyReportListVO();
                // 목록 순번
                item.setNum(i+"");
                // Flask 연결하기
                //범죄유형
//                item.setWantedCate("강도");
//                item.setWantedCate(rep_cate_4);
                //신고날짜
//                item.setReportDate("22.01.01");
//                item.setReportDate(rep_wri_4);

                // 데이터 등록
                adapter.addItem(item);
            }
        }

        // 적용
        adapter.notifyDataSetChanged();
        // 애니메이션 실행
        wantedListRv.startLayoutAnimation();

    }
}