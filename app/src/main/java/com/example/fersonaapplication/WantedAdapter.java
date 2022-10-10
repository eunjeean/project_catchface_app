package com.example.fersonaapplication;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class WantedAdapter extends RecyclerView.Adapter<WantedAdapter.ViewHolder>{
    private int selectedPosition = -1;

    public class ViewHolder extends RecyclerView.ViewHolder {
        LinearLayout wantedLl,wantedItemLl;
        ImageView wantedItemImg;
        TextView wantedItemTv;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            wantedLl = itemView.findViewById(R.id.wantedLl);
            wantedItemLl = itemView.findViewById(R.id.wantedItemLl);
            wantedItemImg = itemView.findViewById(R.id.wantedItemImg);
            wantedItemTv = itemView.findViewById(R.id.wantedItemTv);
        }
    }
    private ArrayList<WantedVO> mList = null;
    public WantedAdapter(ArrayList<WantedVO> mList) {
        this.mList = mList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view = inflater.inflate(R.layout.wanted_ltem, parent, false);
        WantedAdapter.ViewHolder vh = new WantedAdapter.ViewHolder(view);
        return vh;
    }

    // position에 해당하는 데이터를 뷰홀더의 아이템뷰에 표시
    @SuppressLint("RecyclerView")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        WantedVO item = mList.get(position);

        holder.wantedItemImg.setImageResource(mList.get(position).getResId());  // 위치에 해당 이미지 적용
//        holder.wantedItemImg.setImageResource(R.drawable.wantedimg_list_test);
        Log.d("테스트","position : "+mList.get(position).getResId());
        holder.wantedItemTv.setText("신고하기");

        if (selectedPosition == position) {
            holder.wantedItemLl.setBackgroundResource(R.color.pointOrange);
        } else {
            holder.wantedItemLl.setBackgroundResource(R.color.white);
        }

        holder.wantedItemImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectedPosition = position;
                notifyDataSetChanged();

            }
        });//End

        holder.wantedItemImg.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                Log.d("WantedAdapter","onLongClick");
                selectedPosition = position;
                notifyDataSetChanged();
//                requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fl, new FragmentReport()).commit();

                return true;
            }
        }); //End


    }

    @Override
    public int getItemCount() {
        return mList.size();
    }
}
