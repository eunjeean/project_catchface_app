package com.example.fersonaapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MyReportAdapter  extends RecyclerView.Adapter<MyReportAdapter.ViewHolder>{
    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView myreportNumtv;
        TextView myreportCatetv;
        TextView myreportDatetv;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            myreportNumtv = itemView.findViewById(R.id.myreportNum_tv);
            myreportCatetv = itemView.findViewById(R.id.myreportCate_tv);
            myreportDatetv = itemView.findViewById(R.id.myreportDate_tv);
        }
    }

    private ArrayList<MyReportListVO> mList = null;

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
        holder.myreportNumtv.setText("1");
        holder.myreportCatetv.setText("사기");
        holder.myreportDatetv.setText("22.09.20");
    }


    @Override
    public int getItemCount() {
        return mList.size();
    }

}
