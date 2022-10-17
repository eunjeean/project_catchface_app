package com.example.fersonaapplication;

import android.content.ClipData;
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

        TextView myreportNumTv;
        TextView myreportCateTv;
        TextView myreportDateTv;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
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

//        holder.myreportNumTv.setText("1");
//        holder.myreportCateTv.setText("사기");
//        holder.myreportDateTv.setText("22.09.20");

        holder.setItem(item);
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
