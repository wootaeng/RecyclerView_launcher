package com.example.recyclerview_launcher;

import androidx.annotation.NonNull;
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
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupMenu;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity implements TiltScrollController.ScrollListener {

    private PackageManager pm;
    private RecyclerView recyclerView;
    private List<ResolveInfo> list;
    private AppAdapter adapter;
    private Button btn_menu;
    private GridLayoutManager layoutManager;
    private TiltScrollController mTiltScrollController;
    private ScrollZoomLayoutManager scrollZoomLayoutManager;








    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        pm = getPackageManager();

        Intent intent = new Intent(Intent.ACTION_MAIN , null);
        intent.addCategory(Intent.CATEGORY_LAUNCHER);
        list = pm.queryIntentActivities(intent,0);
        //item 간격
        ItemDecoration spaceDecoration = new ItemDecoration(60);
        recyclerView = (RecyclerView)findViewById(R.id.adapter);






        //LinearLayout 형식으로 앱 정렬
        //LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);

        //GridView  형식으로 앱 정렬
        //좌우이동
        layoutManager = new GridLayoutManager(this,2,GridLayoutManager.HORIZONTAL,false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.addItemDecoration(spaceDecoration);
        adapter = new AppAdapter(list,pm);

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

        btn_menu =(Button)findViewById(R.id.Button_menu);
        btn_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final PopupMenu popupMenu = new PopupMenu(getApplicationContext(), view);
                getMenuInflater().inflate(R.menu.popup, popupMenu.getMenu());
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuitem) {

                        if (menuitem.getItemId() == R.id.action_menu1){
                            layoutManager = new GridLayoutManager(MainActivity.this,1,GridLayoutManager.HORIZONTAL,false);
                            recyclerView.setLayoutManager(layoutManager);
                            recyclerView.setHasFixedSize(true);
                            mTiltScrollController = new TiltScrollController(getApplicationContext(), MainActivity.this::onTilt);
                        } else if (menuitem.getItemId() == R.id.action_menu2){
                            layoutManager = new GridLayoutManager(MainActivity.this,2,GridLayoutManager.HORIZONTAL,false);
                            recyclerView.setLayoutManager(layoutManager);
                            recyclerView.setHasFixedSize(true);
                        } else if (menuitem.getItemId() == R.id.action_menu3){
                            layoutManager = new GridLayoutManager(MainActivity.this,3,GridLayoutManager.HORIZONTAL,false);
                            recyclerView.setLayoutManager(layoutManager);
                            recyclerView.setHasFixedSize(true);
                        }

                        return false;
                    }
                });
                popupMenu.show();
            }
        });

    }


    @Override
    public void onTilt(int x, int y, float delta) {
        //        recyclerView.smoothScrollBy(x, y);
        if (Math.abs(delta) > 0.6) {
            recyclerView.smoothScrollBy(x * (scrollZoomLayoutManager.getEachItemWidth()), 0);
//            smoothScrollBy(x * (layoutManager.getEachItemWidth()), 0);
        } else
            recyclerView.smoothScrollBy(x * (scrollZoomLayoutManager.getEachItemWidth() / 6), 0);
//            smoothScrollBy(x * (layoutManager.getEachItemWidth() / 6), 0)
    }
}