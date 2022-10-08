package com.example.fersonaapplication;

import android.app.Activity;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import net.daum.mf.map.api.MapPOIItem;
import net.daum.mf.map.api.MapPoint;
import net.daum.mf.map.api.MapView;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class FragmentMap extends Fragment implements AdapterView.OnItemSelectedListener {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    MapView mapView;
    ViewGroup mapViewContainer;
    EditText mSearchEdit;
    Spinner policeSpin;
    RecyclerView recyclerView;
    ArrayList<Document> documentArrayList = new ArrayList<>(); //지역명 검색 결과 리스트
    String policeSelect = null;
    MapPOIItem marker;

    public FragmentMap() {
        // Required empty public constructor
    }

    public static FragmentMap newInstance(String param1, String param2) {
        FragmentMap fragment = new FragmentMap();
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
        View view = inflater.inflate(R.layout.fragment_map, container, false);

        mSearchEdit = view.findViewById(R.id.map_et_search);
        // map 검색
        mapViewContainer = view.findViewById(R.id.mapView);

        recyclerView = view.findViewById(R.id.map_recyclerview);
        // mapList Adapter 연결
        LocationAdapter locationAdapter = new LocationAdapter(documentArrayList, getActivity().getApplicationContext(), mSearchEdit, recyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity().getApplicationContext(), LinearLayoutManager.VERTICAL, false); //레이아웃매니저 생성
        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity().getApplicationContext(), DividerItemDecoration.VERTICAL)); //아래구분선 세팅
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(locationAdapter);

        // Spinner
        policeSpin = view.findViewById(R.id.policeSpin);
        policeSpin.setOnItemSelectedListener(this);

        // editText 검색 텍스처이벤트
        mSearchEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                // 입력하기 전에
                recyclerView.setVisibility(View.VISIBLE);
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
                if (charSequence.length() >= 1) {
                    Log.d("FragmentMap", "charSequence.length : " + charSequence.length());
                } else {
                    if (charSequence.length() <= 0) {
                        Log.d("FragmentMap", "charSequence.length : " + charSequence.length());
                        recyclerView.setVisibility(View.GONE);
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
                // 입력이 끝났을 때
            }

        });

        mSearchEdit.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (hasFocus) {
                } else {
                    recyclerView.setVisibility(View.GONE);
                }
            }
        });

        mSearchEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity().getApplicationContext(), "검색리스트에서 장소를 선택해주세요", Toast.LENGTH_SHORT).show();
            }
        });


        mapView = new MapView(getContext());
        mapViewContainer.addView(mapView);
        mapView.setCurrentLocationTrackingMode(MapView.CurrentLocationTrackingMode.TrackingModeOnWithHeading);

        // 중심점
        // 스마트인재개발원_CGI
        mapView.setMapCenterPoint(MapPoint.mapPointWithGeoCoord(35.1104947, 126.8777619), true);

        // 마커찍기
        // 마커 아이콘 추가
        marker = new MapPOIItem();
        // 위도 경도 설정
        MapPoint markPoint = MapPoint.mapPointWithGeoCoord(35.1104947, 126.8777619);
        marker.setTag(0);
        // 좌표를 입력받아 현 위치로 출력
        marker.setMapPoint(markPoint);
        marker.setMarkerType(MapPOIItem.MarkerType.BluePin); // 기본으로 제공하는 BluePin 마커 모양.
        marker.setSelectedMarkerType(MapPOIItem.MarkerType.RedPin); // 마커를 클릭했을때, 기본으로 제공하는 RedPin 마커 모양.
        mapView.addPOIItem(marker);

        return view;
    }

    public void showMap(Uri geoLocation) {
        Intent intent;
        try {
            Toast.makeText(getActivity().getApplicationContext(), "카카오맵으로 길찾기를 시도합니다.", Toast.LENGTH_SHORT).show();
            intent = new Intent(Intent.ACTION_VIEW, geoLocation);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        } catch (Exception e) {
            Toast.makeText(getActivity().getApplicationContext(), "길찾기에는 카카오맵이 필요합니다. 다운받아주시길 바랍니다.", Toast.LENGTH_SHORT).show();
            intent = new Intent(Intent.ACTION_VIEW).setData(Uri.parse("https://play.google.com/store/apps/details?id=net.daum.android.map&hl=ko"));
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
        if (!policeSpin.getItemAtPosition(position).toString().equals(R.string.policeSelect)) {
            policeSelect = (String) policeSpin.getItemAtPosition(position);
//            Log.d("FragmentMap", "경찰서 " + policeSelect);
            if (policeSelect.equals("광주광산경찰서")) {
                Log.d("FragmentMap", "광주광산경찰서");
                mapView.setMapCenterPoint(MapPoint.mapPointWithGeoCoord(35.152538, 126.782772), true);
            } else if (policeSelect.equals("광주동부경찰서")) {
                Log.d("FragmentMap", "광주동부경찰서");
                mapView.setMapCenterPoint(MapPoint.mapPointWithGeoCoord(35.149267, 126.919882), true);
            } else if (policeSelect.equals("광주서부경찰서")) {
                Log.d("FragmentMap", "광주서부경찰서");
                mapView.setMapCenterPoint(MapPoint.mapPointWithGeoCoord(35.150738, 126.842349), true);
            } else if (policeSelect.equals("광주남부경찰서")) {
                Log.d("FragmentMap", "광주남부경찰서");
                mapView.setMapCenterPoint(MapPoint.mapPointWithGeoCoord(35.122814, 126.920868), true);
            } else if (policeSelect.equals("광주북부경찰서")) {
                Log.d("FragmentMap", "광주북부경찰서");
                mapView.setMapCenterPoint(MapPoint.mapPointWithGeoCoord(35.1866522, 126.8988133), true);
            }
            marker.setItemName(policeSelect);
        } else {
            policeSpin.setBackgroundResource(R.color.white);
            Log.d("FragmentMap", "경찰서 else " + policeSelect);
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
        Log.d("FragmentMap", "onNothingSelected " + adapterView);
    }
}
