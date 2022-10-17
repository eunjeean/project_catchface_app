package com.example.fersonaapplication;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
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

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentWanted#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentWanted extends Fragment implements View.OnClickListener {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    // RecyclerView
    ArrayList<WantedVO> wList = new ArrayList<>();
    private RecyclerView wantedRv;
    private WantedAdapter wadapter;
    private GridLayoutManager gManager;

    ImageView userImg;


    public static String id, pw, name;





    public FragmentWanted() {
        // Required empty public constructor
    }

    public static FragmentWanted newInstance(String param1, String param2) {
        FragmentWanted fragment = new FragmentWanted();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
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
        View view = inflater.inflate(R.layout.fragment_wanted, container, false);

        wantedRv = view.findViewById(R.id.wantedRv);
        int numberOfColumns = 3; // 한줄에 3개의 컬럼을 추가합니다.
        gManager = new GridLayoutManager(getActivity().getApplicationContext(), numberOfColumns);
        wantedRv.setLayoutManager(gManager);

        userImg = view.findViewById(R.id.userImg);



        // RecyclerView
        for (int i = 0; i < 12; i++) {
            if(wList != null){
                addItem("wantedimg",(i+1));
            }else{
                Log.d("FragmentWanted","item null");
            }
        }

        //리스트뷰에 Adapter 설정
        wadapter = new WantedAdapter(wList);
        wantedRv.setAdapter(wadapter);
        Log.d("FragmentWanted","WantedAdapter 연결");

        userImg.setOnClickListener(this);
        return view;
    }


    public void addItem(String imgName,int imgId) {
        int resId = getResources().getIdentifier(imgName + imgId, "drawable", getActivity().getPackageName());
        WantedVO item = new WantedVO("num", "이름", resId);
        if(item != null){
            wList.add(item);
        }else{
            Log.d("FragmentWanted","item null");
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.userImg:
                Log.d("FragmentWanted", "userImg");

                requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fl, new FragmentMypage()).commit();

                break;
        }
    }

}