package com.example.fersonaapplication;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;


public class WantedActivity extends AppCompatActivity implements View.OnClickListener, NavigationView.OnNavigationItemSelectedListener {
    private String TAG = WantedActivity.class.getSimpleName();

    ImageView logo;

    private GridView gridview;
    private GridViewAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wanted);

        // 메뉴
        final DrawerLayout drawerLayout = findViewById(R.id.drawlayout);

        findViewById(R.id.menu).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerLayout.openDrawer(GravityCompat.END);
            }
        });

        NavigationView menu_nv = findViewById(R.id.menu_nv);
        menu_nv.setNavigationItemSelectedListener(this);

        logo = findViewById(R.id.logoImg);
        gridview = findViewById(R.id.wnatedGv);

        logo.setOnClickListener(this);

        adapter = new GridViewAdapter();

        //Adapter 안에 아이템의 정보 담기
        adapter.addItem(new WantedVO("1", "신고하기", R.drawable.wantedimg1));
        adapter.addItem(new WantedVO("2", "신고하기", R.drawable.wantedimg2));
        adapter.addItem(new WantedVO("3", "신고하기", R.drawable.wantedimg3));
        adapter.addItem(new WantedVO("4", "신고하기", R.drawable.wantedimg4));
        adapter.addItem(new WantedVO("5", "신고하기", R.drawable.wantedimg5));
        adapter.addItem(new WantedVO("6", "신고하기", R.drawable.wantedimg1));
        adapter.addItem(new WantedVO("7", "신고하기", R.drawable.wantedimg2));
        adapter.addItem(new WantedVO("8", "신고하기", R.drawable.wantedimg3));
        adapter.addItem(new WantedVO("9", "신고하기", R.drawable.wantedimg4));
        adapter.addItem(new WantedVO("10", "신고하기", R.drawable.wantedimg5));
        adapter.addItem(new WantedVO("11", "신고하기", R.drawable.wantedimg1));
        adapter.addItem(new WantedVO("12", "신고하기", R.drawable.wantedimg2));
        adapter.addItem(new WantedVO("13", "신고하기", R.drawable.wantedimg3));
        adapter.addItem(new WantedVO("14", "신고하기", R.drawable.wantedimg4));
        adapter.addItem(new WantedVO("15", "신고하기", R.drawable.wantedimg5));
        adapter.addItem(new WantedVO("16", "신고하기", R.drawable.wantedimg1));
        adapter.addItem(new WantedVO("17", "신고하기", R.drawable.wantedimg2));
        adapter.addItem(new WantedVO("18", "신고하기", R.drawable.wantedimg3));
        adapter.addItem(new WantedVO("19", "신고하기", R.drawable.wantedimg4));
        adapter.addItem(new WantedVO("20", "신고하기", R.drawable.wantedimg5));

        //리스트뷰에 Adapter 설정
        gridview.setAdapter(adapter);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.logoImg:
                Log.d("ReportPage","click_reportLogo");
                Intent logoIntent = new Intent(WantedActivity.this, MainActivity.class);
                startActivity(logoIntent);
                finish();
                break;
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu1:
                Log.d("MainActivity", "우리동네알림");
                Intent alterIntent = new Intent(this, NoticeActivity.class);
                startActivity(alterIntent);
                finish();
                break;
            case R.id.menu2:
                Log.d("MainActivity", "파출소찾기");
                Intent policeIntent = new Intent(this, PoliceFindActivity.class);
                startActivity(policeIntent);
                finish();
                break;
            case R.id.menu3:
                Log.d("MainActivity", "공개수배");
                Intent wantedIntent = new Intent(this, WantedActivity.class);
                startActivity(wantedIntent);
                finish();
                break;
            case R.id.menu4:
                Log.d("MainActivity", "신고하기");
                Intent reportIntent = new Intent(this, ReportActivity.class);
                startActivity(reportIntent);
                finish();
                break;
            case R.id.menu5:
                Log.d("MainActivity", "마이페이지");
                Intent myIntent = new Intent(this, MypageActivity.class);
                startActivity(myIntent);
                finish();
                break;
            case R.id.menu6:
                Log.d("MainActivity", "로그아웃");
                break;
        }
        return true;
    }

    /* 그리드뷰 어댑터 */
    class GridViewAdapter extends BaseAdapter {
        ArrayList<WantedVO> items = new ArrayList<WantedVO>();

        @Override
        public int getCount() {
            return items.size();
        }

        public void addItem(WantedVO item) {
            items.add(item);
        }

        @Override
        public Object getItem(int position) {
            return items.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup viewGroup) {
            final Context context = viewGroup.getContext();
            final WantedVO bearItem = items.get(position);

            if(convertView == null) {
                LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = inflater.inflate(R.layout.wanted_ltem, viewGroup, false);


                ImageView wantedImg = convertView.findViewById(R.id.wantedItem_img);
                TextView wantedTv = convertView.findViewById(R.id.wantedItem_tv);


                wantedImg.setImageResource(bearItem.getResId());
                wantedTv.setText(bearItem.getName());

                Log.d(TAG, "getView() - [ "+position+" ] "+bearItem.getName());

            } else {
                View view = new View(context);
                view = (View) convertView;
            }

            return convertView;  //뷰 객체 반환
        }
    }

}