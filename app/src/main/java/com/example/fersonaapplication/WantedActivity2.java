package com.example.fersonaapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class WantedActivity2 extends AppCompatActivity {

    private String TAG = WantedActivity.class.getSimpleName();

    ImageView logo;

    private GridView gridview;
    private GridViewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wanted2);

        gridview = findViewById(R.id.wantedGv);
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

        //리스트뷰에 Adapter 설정
        gridview.setAdapter(adapter);


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


                ImageView wantedImg = convertView.findViewById(R.id.wantedItemImg);
                TextView wantedTv = convertView.findViewById(R.id.wantedItemTv);


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