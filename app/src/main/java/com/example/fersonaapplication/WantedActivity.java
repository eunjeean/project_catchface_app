package com.example.fersonaapplication;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import java.util.ArrayList;


public class WantedActivity extends AppCompatActivity implements View.OnClickListener {
    private String TAG = WantedActivity.class.getSimpleName();

    ImageView logo;

    private GridView gridview;
    private GridViewAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wanted);

        final DrawerLayout drawerLayout = findViewById(R.id.drawlayout);

        findViewById(R.id.menu).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerLayout.openDrawer(GravityCompat.END);
            }
        });

        logo = findViewById(R.id.logoImg);
        gridview = findViewById(R.id.gridview);

        logo.setOnClickListener(this);

        adapter = new GridViewAdapter();

        //Adapter 안에 아이템의 정보 담기
        adapter.addItem(new WantedVO("1", "신고하기", R.drawable.wantedimg1));
        adapter.addItem(new WantedVO("2", "신고하기", R.drawable.wantedimg2));
        adapter.addItem(new WantedVO("3", "신고하기", R.drawable.wantedimg3));
        adapter.addItem(new WantedVO("4", "신고하기", R.drawable.wantedimg4));
        adapter.addItem(new WantedVO("5", "신고하기", R.drawable.wantedimg5));

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
                convertView = inflater.inflate(R.layout.wanted_list, viewGroup, false);


                TextView tv_name = (TextView) convertView.findViewById(R.id.tv_name);
                ImageView iv_icon = (ImageView) convertView.findViewById(R.id.iv_icon);


                tv_name.setText(bearItem.getName());
                iv_icon.setImageResource(bearItem.getResId());
                Log.d(TAG, "getView() - [ "+position+" ] "+bearItem.getName());

            } else {
                View view = new View(context);
                view = (View) convertView;
            }

            return convertView;  //뷰 객체 반환
        }
    }

}