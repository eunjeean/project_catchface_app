package com.example.fersonaapplication;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
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
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
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

public class FragmentMap extends Fragment implements AdapterView.OnItemSelectedListener, View.OnClickListener {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    MapView mapView;
    ViewGroup mapViewContainer;
    Spinner policeSpin;
    RecyclerView recyclerView;
    ArrayList<Document> documentArrayList = new ArrayList<>(); //지역명 검색 결과 리스트
    String policeSelect = null;
    MapPOIItem marker;
    ImageView callImg;

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

        // map 검색
        mapViewContainer = view.findViewById(R.id.mapView);
        // 112 전화
        callImg = view.findViewById(R.id.callImg);

        recyclerView = view.findViewById(R.id.map_recyclerview);

        // Spinner
        policeSpin = view.findViewById(R.id.policeSpin);
        policeSpin.setOnItemSelectedListener(this);


        mapView = new MapView(getActivity());
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
        MarkPoint(markPoint);

        callImg.setOnClickListener(this);

        return view;
    }

    private void MarkPoint(MapPoint markPoint) {
        marker.setItemName("Default Marker");
        marker.setTag(0);
        // 좌표를 입력받아 현 위치로 출력
        marker.setMapPoint(markPoint);
        marker.setMarkerType(MapPOIItem.MarkerType.BluePin); // 기본으로 제공하는 BluePin 마커 모양.
        marker.setSelectedMarkerType(MapPOIItem.MarkerType.RedPin); // 마커를 클릭했을때, 기본으로 제공하는 RedPin 마커 모양.
        mapView.addPOIItem(marker);
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
        if (!policeSpin.getItemAtPosition(position).toString().equals(R.string.policeSelect)) {
            policeSelect = (String) policeSpin.getItemAtPosition(position);
            if (policeSelect.equals("광주광산경찰서")) {
                Log.d("FragmentMap", "광주광산경찰서");
                mapView.setMapCenterPoint(MapPoint.mapPointWithGeoCoord(35.152538, 126.782772), true);
                MapPoint markPoint = MapPoint.mapPointWithGeoCoord(35.152538, 126.782772);
                MarkPoint(markPoint);
            } else if (policeSelect.equals("광주동부경찰서")) {
                Log.d("FragmentMap", "광주동부경찰서");
                mapView.setMapCenterPoint(MapPoint.mapPointWithGeoCoord(35.149267, 126.919882), true);
                MapPoint markPoint = MapPoint.mapPointWithGeoCoord(35.149267, 126.919882);
                MarkPoint(markPoint);
            } else if (policeSelect.equals("광주서부경찰서")) {
                Log.d("FragmentMap", "광주서부경찰서");
                mapView.setMapCenterPoint(MapPoint.mapPointWithGeoCoord(35.150738, 126.842349), true);
                MapPoint markPoint = MapPoint.mapPointWithGeoCoord(35.150738, 126.842349);
                MarkPoint(markPoint);
            } else if (policeSelect.equals("광주남부경찰서")) {
                Log.d("FragmentMap", "광주남부경찰서");
                mapView.setMapCenterPoint(MapPoint.mapPointWithGeoCoord(35.122814, 126.920868), true);
                MapPoint markPoint = MapPoint.mapPointWithGeoCoord(35.122814, 126.920868);
                MarkPoint(markPoint);
            } else if (policeSelect.equals("광주북부경찰서")) {
                Log.d("FragmentMap", "광주북부경찰서");
                mapView.setMapCenterPoint(MapPoint.mapPointWithGeoCoord(35.1866522, 126.8988133), true);
                MapPoint markPoint = MapPoint.mapPointWithGeoCoord(35.1866522, 126.8988133);
                MarkPoint(markPoint);
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

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.callImg:
                Intent intent = new Intent(Intent.ACTION_CALL,Uri.parse("tel:112"));
                //사용자에게 권한승인요청
                if ( ActivityCompat.checkSelfPermission(getActivity().getApplicationContext(), Manifest.permission.CALL_PHONE) !=
                        PackageManager.PERMISSION_GRANTED ) {
                    //권한 요청
                    ActivityCompat.requestPermissions(getActivity(),
                            new String[]{Manifest.permission.CALL_PHONE}, 0);
                    return;
                }
                startActivity(intent);
                break;
        }
    }
}
