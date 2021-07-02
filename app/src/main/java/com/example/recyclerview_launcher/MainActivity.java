package com.example.recyclerview_launcher;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    PackageManager pm;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        pm = getPackageManager();

        Intent intent = new Intent(Intent.ACTION_MAIN , null);
        intent.addCategory(Intent.CATEGORY_LAUNCHER);
        List<ResolveInfo> list = pm.queryIntentActivities(intent,0);
        //item 간격
        ItemDecoration spaceDecoration = new ItemDecoration(60);
        recyclerView = (RecyclerView)findViewById(R.id.adapter);

        //LinearLayout 형식으로 앱 정렬
        //LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);

        //GridView  형식으로 앱 정렬
        //좌우이동
        GridLayoutManager layoutManager = new GridLayoutManager(this,2,GridLayoutManager.HORIZONTAL,false);
        recyclerView.setLayoutManager(layoutManager);
        //recyclerView.setHasFixedSize(false);
        recyclerView.addItemDecoration(spaceDecoration);
        AppAdapter adapter = new AppAdapter(list,pm);


        recyclerView.setAdapter(adapter);
        adapter.setOnClickListener(new AppAdapter.OnItemClicklistener() {
            @Override
            public void onItemClick(View v, int pos) {
                //아이템 클릭시 앱 정보 확인
                ResolveInfo checkedResolveInfo =
                        (ResolveInfo) list.get(pos);
                //확인한 정보를 액티비티에 담기?
                ActivityInfo clickedActivityInfo =
                        checkedResolveInfo.activityInfo;
                //새 액티비티로 보내기 앱실행
                Intent intent = new Intent(Intent.ACTION_MAIN);
                intent.addCategory(Intent.CATEGORY_LAUNCHER);
                intent.setClassName(
                        clickedActivityInfo.applicationInfo.packageName,
                        clickedActivityInfo.name);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK |
                        Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
                startActivity(intent);

            }

        });

        

//        for (ApplicationInfo packageInfo : packages) {
//            Log.d("ApplicationInfo", "Installed package :" + packageInfo.packageName);
//            Log.d("ApplicationInfo", "Source dir : " + packageInfo.sourceDir);
//            Log.d("ApplicationInfo", "Launch Activity :" + pm.getLaunchIntentForPackage(packageInfo.packageName));
//
//            // 화면에 설치된 앱 정보만 가져온다
//            if(packageInfo.sourceDir.contains("/data/app/")){
//                list.add(packageInfo);
//            }
//        }


//        ((RecyclerView)findViewById(R.id.adapter)).setAdapter(new AppAdapter(pm,list));
//        ((RecyclerView)findViewById(R.id.adapter)).setHasFixedSize(true);
//        ((RecyclerView)findViewById(R.id.adapter)).setLayoutManager(new LinearLayoutManager(this));

    }



}