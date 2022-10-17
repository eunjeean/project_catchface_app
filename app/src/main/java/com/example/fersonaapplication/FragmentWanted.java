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

    // volley
    RequestQueue requestQueue;
    public static String shared = "fersona";
    public static String id, pw, name;
    StringRequest request;
    public static String url;
    public static String wantId1, wantName1, wantAge1, wantGen1, wantImg1, repCate1, adminAdr1, wantOpen1;
    public static String wantId2, wantName2, wantAge2, wantGen2, wantImg2, repCate2, adminAdr2, wantOpen2;
    public static String wantId3, wantName3, wantAge3, wantGen3, wantImg3, repCate3, adminAdr3, wantOpen3;
    public static String wantId4, wantName4, wantAge4, wantGen4, wantImg4, repCate4, adminAdr4, wantOpen4;
    public static String wantId5, wantName5, wantAge5, wantGen5, wantImg5, repCate5, adminAdr5, wantOpen5;
    public static String wantId6, wantName6, wantAge6, wantGen6, wantImg6, repCate6, adminAdr6, wantOpen6;
    public static String wantId7, wantName7, wantAge7, wantGen7, wantImg7, repCate7, adminAdr7, wantOpen7;
    public static String wantId8, wantName8, wantAge8, wantGen8, wantImg8, repCate8, adminAdr8, wantOpen8;
    public static String wantId9, wantName9, wantAge9, wantGen9, wantImg9, repCate9, adminAdr9, wantOpen9;
    public static String wantId10, wantName10, wantAge10, wantGen10, wantImg10, repCate10, adminAdr10, wantOpen10;




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

        if(requestQueue==null){
            requestQueue = Volley.newRequestQueue(getActivity().getApplicationContext());
        }

        url = "http://121.147.52.96:5000/wantSelectALL";

        StringRequest request = new StringRequest(
                Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(getActivity().getApplicationContext(), "몽타주 생성 성공😊", Toast.LENGTH_SHORT).show();
                        try {
                            JSONArray array = new JSONArray(response);

                            wantId1 = array.getJSONArray(0).getString(0);

                        } catch (JSONException e) {  e.printStackTrace(); }

                        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(shared, Context.MODE_PRIVATE);    // test 이름의 기본모드 설정
                        SharedPreferences.Editor editor = sharedPreferences.edit(); //sharedPreferences를 제어할 editor를 선언

//                        editor.putString("monId1", monId1);

                        editor.commit();    //최종 커밋. 커밋을 해야 저장이 된다.

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getActivity().getApplicationContext(), "몽타주 생성 실패😣", Toast.LENGTH_SHORT).show();
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
//                params.put("mon_char", mon_char);
//                params.put("mem_pw", pw);

                return params;
            }
        };

        request.setShouldCache(false);
        requestQueue.add(request);










//        wList.add(new WantedVO("1", R.drawable.wantedimg1));
//        wList.add(new WantedVO("2", R.drawable.wantedimg2));
//        wList.add(new WantedVO("3", R.drawable.wantedimg3));
//        wList.add(new WantedVO("4", R.drawable.wantedimg4));
//        wList.add(new WantedVO("5", R.drawable.wantedimg5));
//        Log.d("테스트", String.valueOf(wList.size()));

        // RecyclerView
        for (int i = 0; i < 5; i++) {
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