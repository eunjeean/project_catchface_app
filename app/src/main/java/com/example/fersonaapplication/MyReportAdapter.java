package com.example.fersonaapplication;

import android.app.Activity;
import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MyReportAdapter  extends RecyclerView.Adapter<MyReportAdapter.ViewHolder>{

    private int sendPosition = -1;
    private int mContext;
    public static String shared = "fersona";
    public static String rep_no_1, rep_cate_1, rep_con_1, rep_date_1, rep_time_1, rep_adr_1, mon_id_1, want_id_1, rep_pro_1, rep_wri_1;
    public static String position_1, position_2, position_3, position_4, position_5;

    public class ViewHolder extends RecyclerView.ViewHolder {

        CardView myReportCv;
        TextView myreportNumTv;
        TextView myreportCateTv;
        TextView myreportDateTv;
        String shared = "fersona";

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            myReportCv = itemView.findViewById(R.id.myReportCv);
            myreportNumTv = itemView.findViewById(R.id.myreportNumTv);
            myreportCateTv = itemView.findViewById(R.id.myreportCateTv);
            myreportDateTv = itemView.findViewById(R.id.myreportDateTv);
        }
        public void setItem(MyReportListVO item){
            myreportNumTv.setText(item.getNum());
            myreportCateTv.setText(item.getWantedCate());
            myreportDateTv.setText(item.getReportDate());
        }
    }

//    private ArrayList<MyReportListVO> mList = null;
    ArrayList<MyReportListVO> mList = new ArrayList<MyReportListVO>();

    public MyReportAdapter(ArrayList<MyReportListVO> mList) {
        this.mList = mList;
    }

    // 아이템 뷰를 위한 뷰홀더 객체를 생성하여 리턴
    @NonNull
    @Override
    public MyReportAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view = inflater.inflate(R.layout.mypage_item, parent, false);
        MyReportAdapter.ViewHolder vh = new MyReportAdapter.ViewHolder(view);
        return vh;
    }
    // position에 해당하는 데이터를 뷰홀더의 아이템뷰에 표시
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        MyReportListVO item = mList.get(position);

//        String pos1 = ((MyReportActivity) Activity.mContext).position_1;


//        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences(shared, Context.MODE_PRIVATE);
//        rep_no_1 = sharedPreferences.getString("rep_no_1", "");
//        rep_cate_1 = sharedPreferences.getString("rep_cate_1", "");
//        rep_con_1 = sharedPreferences.getString("rep_con_1", "");
//        rep_date_1 = sharedPreferences.getString("rep_date_1", "");
//        rep_time_1 = sharedPreferences.getString("rep_time_1", "");
//        rep_adr_1 = sharedPreferences.getString("rep_adr_1", "");
//        mon_id_1 = sharedPreferences.getString("mon_id_1", "");
//        want_id_1 = sharedPreferences.getString("want_id_1", "");
//        rep_pro_1 = sharedPreferences.getString("rep_pro_1", "");
//        rep_wri_1 = sharedPreferences.getString("rep_wri_1", "");
//        position_1 = sharedPreferences.getString("position_1", "");



//        holder.myreportNumTv.setText("1");
//        holder.myreportCateTv.setText("사기");
//        holder.myreportDateTv.setText("22.09.20");

        holder.setItem(item);
        holder.myReportCv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("MyReportAdapter item","클릭"+position);
                sendPosition = position;



                notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public void addItem(MyReportListVO item){
        mList.add(item);
    }

    public void removeAllItem(){
        mList.clear();
    }


}
