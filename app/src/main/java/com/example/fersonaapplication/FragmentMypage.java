package com.example.fersonaapplication;

import android.content.Intent;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;

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
    Button mypageBtn, myReportBtn;
    ConstraintLayout myPageCl,myReportCl;
    TextView myIdTv, phoneTv;
    EditText nameTv, addressTv;
    public static String a, id, pw, name, date, city, dong, phone;


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

        // RecyclerView
        for(int i=0;i<5;i++){
            addItem("reportCate","reportDate");
        }



        adapter = new MyReportAdapter(data);
        wantedListRv.setAdapter(adapter);
        wantedListRv.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext()));

        return view;
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
                break;
        }
    }
}