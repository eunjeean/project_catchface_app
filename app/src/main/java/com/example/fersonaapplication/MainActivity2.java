package com.example.fersonaapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class MainActivity2 extends AppCompatActivity{

    BottomNavigationView bnv;
    FrameLayout fl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        bnv = findViewById(R.id.bnv);
        fl = findViewById(R.id.fl);

        getSupportFragmentManager().beginTransaction().replace(R.id.fl, new FragmentReport()).commit();

        bnv.setSelectedItemId(R.id.tab2);
        bnv.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()){
                    case R.id.tab1:
//                        Toast.makeText(MainActivity2.this, "공개수배", Toast.LENGTH_SHORT).show();
                        getSupportFragmentManager().beginTransaction().replace(R.id.fl, new FragmentWanted()).commit();
                        break;
                    case R.id.tab2:
//                        Toast.makeText(MainActivity2.this, "신고하기", Toast.LENGTH_SHORT).show();
                        getSupportFragmentManager().beginTransaction().replace(R.id.fl,new FragmentReport()).commit();
                        break;
                    case R.id.tab3:
//                        Toast.makeText(MainActivity2.this, "지도", Toast.LENGTH_SHORT).show();
                        getSupportFragmentManager().beginTransaction().replace(R.id.fl, new FragmentMap()).commit();
                        break;
                }

                return true;
            }
        });


    }

}