package com.example.fersonaapplication;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentWanted#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentWanted extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    // GridView
//    ArrayList<WantedVO> data = new ArrayList<>();
//    private GridView wantedGridview;
//    private GridViewAdapter adapter;

    // RecyclerView
    ArrayList<WantedVO> wList = new ArrayList<>();
    private RecyclerView wantedRv;
    private WantedAdapter wadapter;
    private GridLayoutManager gManager;


    public FragmentWanted() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Fragment1.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentWanted newInstance(String param1, String param2) {
        FragmentWanted fragment = new FragmentWanted();
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
        View view = inflater.inflate(R.layout.fragment_wanted, container, false);

//        wantedGridview = view.findViewById(R.id.wantedGv);
//        adapter = new GridViewAdapter();
        wantedRv = view.findViewById(R.id.wantedRv);
        int numberOfColumns = 3; // 한줄에 3개의 컬럼을 추가합니다.
        gManager = new GridLayoutManager(getActivity().getApplicationContext(), numberOfColumns);
        wantedRv.setLayoutManager(gManager);

        wList.add(new WantedVO("1", R.drawable.wantedimg1));
        wList.add(new WantedVO("2", R.drawable.wantedimg2));
        wList.add(new WantedVO("3", R.drawable.wantedimg3));
        wList.add(new WantedVO("4", R.drawable.wantedimg4));
        wList.add(new WantedVO("5", R.drawable.wantedimg5));
        Log.d("테스트", String.valueOf(wList.size()));

        // GridView 안에 아이템의 정보 담기
//        adapter.addItem(new WantedVO("1", "신고하기", R.drawable.wantedimg1));
//        adapter.addItem(new WantedVO("2", "신고하기", R.drawable.wantedimg2));
//        adapter.addItem(new WantedVO("3", "신고하기", R.drawable.wantedimg3));
//        adapter.addItem(new WantedVO("4", "신고하기", R.drawable.wantedimg4));
//        adapter.addItem(new WantedVO("5", "신고하기", R.drawable.wantedimg5));
//        adapter.addItem(new WantedVO("6", "신고하기", R.drawable.wantedimg1));
//        adapter.addItem(new WantedVO("7", "신고하기", R.drawable.wantedimg2));
//        adapter.addItem(new WantedVO("8", "신고하기", R.drawable.wantedimg3));
//        adapter.addItem(new WantedVO("9", "신고하기", R.drawable.wantedimg4));
//        adapter.addItem(new WantedVO("10", "신고하기", R.drawable.wantedimg5));
//        adapter.addItem(new WantedVO("11", "신고하기", R.drawable.wantedimg1));
//        adapter.addItem(new WantedVO("12", "신고하기", R.drawable.wantedimg2));
//        adapter.addItem(new WantedVO("13", "신고하기", R.drawable.wantedimg3));
//        adapter.addItem(new WantedVO("14", "신고하기", R.drawable.wantedimg4));


        // RecyclerView

        // 주석풀면 에러뜸
//        for (int i = 0; i < wList.size(); i++) {
//            if(wList != null){
//                addItem("imgName",(i+1));
//            }else{
//                Log.d("FragmentWanted","item null");
//            }
//        }

        //리스트뷰에 Adapter 설정
        // GridView
//        wantedGridview.setAdapter(adapter);

        wadapter = new WantedAdapter(wList);
        wantedRv.setAdapter(wadapter);
        Log.d("FragmentWanted","WantedAdapter 연결");

        return view;
    }


    public void addItem(String imgName,int imgId) {
        Log.d("FragmentWanted","addItem");
        WantedVO item = new WantedVO("imgName",imgId);
        item.setName(imgName);
        item.setResId(imgId);
        if(item != null){
            wList.add(item);
        }else{
            Log.d("FragmentWanted","item null");
        }
    }

    /* 그리드뷰 어댑터 */
//    class GridViewAdapter extends BaseAdapter {
//        ArrayList<WantedVO> items = new ArrayList<WantedVO>();
//
//        public void addItem(WantedVO item) {
//            items.add(item);
//        }
//
//        @Override
//        public Object getItem(int position) {
//            return items.get(position);
//        }
//
//        @Override
//        public long getItemId(int position) {
//            return position;
//        }
//
//        @Override
//        public View getView(int position, View convertView, ViewGroup viewGroup) {
//            final Context context = viewGroup.getContext();
//            final WantedVO bearItem = items.get(position);
//            ImageView wantedImg;
//            TextView wantedTv;
//
//            Log.d("FragmentWanted","getView");
//            if(convertView == null) {
//                LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//                convertView = inflater.inflate(R.layout.wanted_ltem, viewGroup, false);
//
//                wantedImg = convertView.findViewById(R.id.wantedItemImg);
//                wantedTv = convertView.findViewById(R.id.wantedItemTv);
//
//                wantedImg.setImageResource(bearItem.getResId());
//                wantedTv.setText(bearItem.getName());
//
//                Log.d("FragmentWanted", "getView() - [ "+position+" ] "+bearItem.getName());
//
//            } else {
//                View view = new View(context);
//                view = (View) convertView;
//            }
//
//            convertView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    Log.d("FragmentWanted","convertView Onclick"+position);
//                }
//            });
//            return convertView;  //뷰 객체 반환
//        }
//
//        @Override
//        public int getCount() {
//            return items.size();
//        }
//    }

}