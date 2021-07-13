package com.peng.plant.wattstore;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.PopupMenu;

import com.peng.recyclerview_launcher.R;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;


public class MainActivity extends AppCompatActivity implements TiltScrollController.ScrollListener {
    public static final String TAG = "MainActivity";



    //private PackageManager pm;
    private RecyclerView recyclerView;
    //private List<ResolveInfo> list;
    private ArrayList<AppData> list;
    private AppAdapter adapter;
    private Button btn_menu;
    private GridLayoutManager layoutManager;
    private TiltScrollController mTiltScrollController;
    private ConcurrentHashMap<Integer , AppData> apps = new ConcurrentHashMap<>();
    private PowerClient powerClient;
    private PowerClientObserver powerObserver;








    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        pm = getPackageManager();

        Intent intent = new Intent(Intent.ACTION_MAIN , null);
        intent.addCategory(Intent.CATEGORY_LAUNCHER);
//        list = pm.queryIntentActivities(intent,0);

        //item 간격
        ItemDecoration spaceDecoration = new ItemDecoration(60);
        recyclerView = (RecyclerView)findViewById(R.id.recyclerV);








        //LinearLayout 형식으로 앱 정렬
        //LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);

        //GridView  형식으로 앱 정렬
        //좌우이동
        layoutManager = new GridLayoutManager(this,2,GridLayoutManager.HORIZONTAL,false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.addItemDecoration(spaceDecoration);
        adapter = new AppAdapter(MainActivity.this,list,powerObserver);
        Log.d(TAG, "onCreate: adapter ");

        recyclerView.setAdapter(adapter);
        Log.d(TAG, "onCreate: setAdapter");
//        adapter.setOnClickListener(new AppAdapter.OnItemClicklistener() {
//            @Override
//            public void onItemClick(View v, int pos) {
//                //아이템 클릭시 앱 정보 확인
////                ResolveInfo checkedResolveInfo =
////                        (ResolveInfo) list.get(pos);
//                //확인한 정보를 액티비티에 담기?
////                ActivityInfo clickedActivityInfo =
////                        checkedResolveInfo.activityInfo;
//                //새 액티비티로 보내기 앱실행
//                Intent intent = new Intent(Intent.ACTION_MAIN);
//                intent.addCategory(Intent.CATEGORY_LAUNCHER);
//                intent.setClassName(
//                        clickedActivityInfo.applicationInfo.packageName,
//                        clickedActivityInfo.name);
//                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK |
//                        Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
//                startActivity(intent);
//
//            }
//
//        });

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
        recyclerView.smoothScrollBy(x, y);
//        if (Math.abs(delta) > 0.6) {
//            recyclerView.smoothScrollBy(x * (scrollZoomLayoutManager.getEachItemWidth()), 0);
//        smoothScrollBy(x * (layoutManager.getEachItemWidth()), 0);
//        } else
//            recyclerView.smoothScrollBy(x * (scrollZoomLayoutManager.getEachItemWidth() / 6), 0);
//        smoothScrollBy(x * (layoutManager.getEachItemWidth() / 6), 0);
    }


    private class PowerObserver implements PowerClientObserver{

        @Override
        public void onOpen(boolean isConnected) {

            Log.d(TAG , "Noah PowerObserver onOpen: " + isConnected);
            if(isConnected)
            {
                eventHandler.sendEmptyMessageDelayed(500, 1000);
            }
            else
            {
                eventHandler.sendEmptyMessage(101);
            }
        }

        @Override
        public void onGetAppLists(ConcurrentHashMap<Integer, AppData> data) {
            Message msg = new Message();
            msg.what = 1;
            msg.obj = data;
            eventHandler.sendMessage(msg);

        }

        @Override
        public void onTextMessage(String msg) {

        }

        @Override
        public void onConnectTimeOut(String msg) {

        }

        @Override
        public void onDisconnect(String msg) {

        }
    }

    @SuppressLint("HandlerLeak")
    public Handler eventHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            AppData data;
            int result;
            switch (msg.what){
                case 1: //onGetAppLists
                    ConcurrentHashMap<Integer , AppData> appLists = new ConcurrentHashMap<Integer, AppData>((ConcurrentHashMap<Integer , AppData>)msg.obj);
                    if(apps != null) apps.clear();
//                    Log.d(TAG , "Noah aaa : "+ appLists.size() + " / " + apps.size());
                    for (int i = 0; i < appLists.size() ; i++) {
                        addApplicationUI(appLists.get(i));
                    }
                    eventHandler.sendEmptyMessageDelayed(1000 , 500);
                    break;

            }
        }
    };

    private void addApplicationUI(AppData data) {
        recyclerView.removeAllViews();
        Message msg = new Message();
        msg.what = 0;
        msg.obj = data;
        eventHandler.sendMessageDelayed(msg,100);

    }

}