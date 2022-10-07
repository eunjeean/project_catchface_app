package com.example.fersonaapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.ViewGroup;

import net.daum.mf.map.api.MapPOIItem;
import net.daum.mf.map.api.MapPoint;
import net.daum.mf.map.api.MapView;

public class MapActivity extends AppCompatActivity {

    MapView mapView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        mapView = new MapView(this);

        ViewGroup mapViewContainer = (ViewGroup) findViewById(R.id.mapView);
        mapViewContainer.addView(mapView);

        // 중심점
        // 스마트인재개발원_CGI
        mapView.setMapCenterPoint(MapPoint.mapPointWithGeoCoord(35.1104947, 126.8777619), true);

        // 마커찍기
        MapPoint markPoint = MapPoint.mapPointWithGeoCoord(35.1104947, 126.8777619);
        // 마커 아이콘 추가
        MapPOIItem marker = new MapPOIItem();
        marker.setTag(0);
        // 좌표를 입력받아 현 위치로 출력
        marker.setMapPoint(markPoint);
        marker.setMarkerType(MapPOIItem.MarkerType.BluePin); // 기본으로 제공하는 BluePin 마커 모양.
        marker.setSelectedMarkerType(MapPOIItem.MarkerType.RedPin); // 마커를 클릭했을때, 기본으로 제공하는 RedPin 마커 모양.
        mapView.addPOIItem(marker);
    }


}