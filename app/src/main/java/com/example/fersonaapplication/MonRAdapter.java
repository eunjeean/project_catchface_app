package com.example.fersonaapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MonRAdapter extends RecyclerView.Adapter<MonRAdapter.ViewHolder>{
    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView wantedItemImg;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            wantedItemImg = itemView.findViewById(R.id.wantedItemImg);
        }
    }

    private ArrayList<MonFaceListVO> mList = null;

    public MonRAdapter(ArrayList<MonFaceListVO> mList) {
        this.mList = mList;
    }

    // 아이템 뷰를 위한 뷰홀더 객체를 생성하여 리턴
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view = inflater.inflate(R.layout.monlist_item, parent, false);
        MonRAdapter.ViewHolder vh = new MonRAdapter.ViewHolder(view);
        return vh;
    }
    // position에 해당하는 데이터를 뷰홀더의 아이템뷰에 표시
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        MonFaceListVO item = mList.get(position);

        holder.wantedItemImg.setImageResource(R.drawable.wantedimg_list_test);   // 기본 파일로 이미지 띄움
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }


}