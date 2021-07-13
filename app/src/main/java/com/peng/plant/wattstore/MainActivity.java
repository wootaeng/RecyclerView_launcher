package com.peng.plant.wattstore;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.PopupMenu;
import android.widget.Toast;


import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity implements TiltScrollController.ScrollListener {


    private RecyclerView recyclerView;
    private AppAdapter adapter;
    private ArrayList<AppData> list;
    private Button btn_menu;
    private GridLayoutManager layoutManager;
    private TiltScrollController mTiltScrollController;
    private String tmp =
            "[{\"appnamekor\":\"파워톡\",\"appnameeng\":\"PowerTalk\",\"apppkgname\":\"com.peng.plant.facetalk\",\"appversion\":\"1.5.3\"," +
                    "\"appdetailjson\":\"{\\\"googCpuOveruseDetection\\\":\\\"true\\\",\\\"serverUrl01\\\":\\\"https://watt.powertalk.kr:8353@1\\\"," +
                    "\\\"serverUrl02\\\":\\\"tcp://106.10.33.94:8354\\\",\\\"serverUrl03\\\":\\\"coturn1\\\",\\\"resolution\\\":\\\"1280 x 720\\\"}\"," +
                    "\"useonglass\":1,\"useonpc\":1,\"sortnum\":0}," +
                    "{\"appnamekor\":\"파워콜\",\"appnameeng\":\"PowerCall\",\"apppkgname\":\"com.peng.powercall\"," +
                    "\"appversion\":\"1.2.16\",\"appdetailjson\":\"{\\\"\\\"}\",\"useonglass\":1,\"useonpc\":1,\"sortnum\":1}," +
                    "{\"appnamekor\":\"파워메모\",\"appnameeng\":\"PowerMemo\",\"apppkgname\":\"com.peng.power.memo\"," +
                    "\"appversion\":\"1.0.27\",\"appdetailjson\":\"{\\\"socket_url\\\":\\\"http://wattcloud.powertalk.kr:8351\\\"," +
                    "\\\"sftp_host\\\":\\\"106.10.33.94\\\",\\\"sftp_user\\\":\\\"sftpuser01\\\",\\\"sftp_user_pw\\\":\\\"Powereng#2501\\\"," +
                    "\\\"sftp_port\\\":\\\"22\\\",\\\"sftp_root_folder\\\":\\\"watt\\\",\\\"sftp_url\\\":\\\"https://wattcloud.powertalk.kr/sftp/watt/\\\"}\"," +
                    "\"useonglass\":1,\"useonpc\":1,\"sortnum\":2},{\"appnamekor\":\"설정\",\"appnameeng\":\"Settings\"," +
                    "\"apppkgname\":\"com.android.settings\",\"appversion\":\"10\",\"appdetailjson\":\"{\\\"\\\"}\"," +
                    "\"useonglass\":1,\"useonpc\":1,\"sortnum\":3},{\"appnamekor\":\"파워톡\",\"appnameeng\":\"PowerTalk\"," +
                    "\"apppkgname\":\"com.peng.plant.facetalk\",\"appversion\":\"1.5.3\",\"appdetailjson\":\"{\\\"googCpuOveruseDetection\\\":\\\"true\\\"," +
                    "\\\"serverUrl01\\\":\\\"https://watt.powertalk.kr:8353@1\\\",\\\"serverUrl02\\\":\\\"tcp://106.10.33.94:8354\\\"," +
                    "\\\"serverUrl03\\\":\\\"coturn1\\\",\\\"resolution\\\":\\\"1280 x 720\\\"}\",\"useonglass\":1,\"useonpc\":1,\"sortnum\":0}," +
                    "{\"appnamekor\":\"파워콜\",\"appnameeng\":\"PowerCall\",\"apppkgname\":\"com.peng.powercall\",\"appversion\":\"1.2.16\"," +
                    "\"appdetailjson\":\"{\\\"\\\"}\",\"useonglass\":1,\"useonpc\":1,\"sortnum\":1},{\"appnamekor\":\"파워메모\",\"appnameeng\":\"PowerMemo\"," +
                    "\"apppkgname\":\"com.peng.power.memo\",\"appversion\":\"1.0.27\",\"appdetailjson\":\"{\\\"socket_url\\\":\\\"http://wattcloud.powertalk.kr:8351\\\"," +
                    "\\\"sftp_host\\\":\\\"106.10.33.94\\\",\\\"sftp_user\\\":\\\"sftpuser01\\\",\\\"sftp_user_pw\\\":\\\"Powereng#2501\\\",\\\"sftp_port\\\":\\\"22\\\"," +
                    "\\\"sftp_root_folder\\\":\\\"watt\\\",\\\"sftp_url\\\":\\\"https://wattcloud.powertalk.kr/sftp/watt/\\\"}\",\"useonglass\":1,\"useonpc\":1,\"sortnum\":2}," +
                    "{\"appnamekor\":\"설정\",\"appnameeng\":\"Settings\",\"apppkgname\":\"com.android.settings\",\"appversion\":\"10\"," +
                    "\"appdetailjson\":\"{\\\"\\\"}\",\"useonglass\":1,\"useonpc\":1,\"sortnum\":3},{\"appnamekor\":\"파워톡\",\"appnameeng\":\"PowerTalk\"," +
                    "\"apppkgname\":\"com.peng.plant.facetalk\",\"appversion\":\"1.5.3\",\"appdetailjson\":\"{\\\"googCpuOveruseDetection\\\":\\\"true\\\"," +
                    "\\\"serverUrl01\\\":\\\"https://watt.powertalk.kr:8353@1\\\",\\\"serverUrl02\\\":\\\"tcp://106.10.33.94:8354\\\",\\\"serverUrl03\\\":\\\"coturn1\\\"," +
                    "\\\"resolution\\\":\\\"1280 x 720\\\"}\",\"useonglass\":1,\"useonpc\":1,\"sortnum\":0},{\"appnamekor\":\"파워콜\",\"appnameeng\":\"PowerCall\"," +
                    "\"apppkgname\":\"com.peng.powercall\",\"appversion\":\"1.2.16\",\"appdetailjson\":\"{\\\"\\\"}\",\"useonglass\":1,\"useonpc\":1,\"sortnum\":1}," +
                    "{\"appnamekor\":\"파워메모\",\"appnameeng\":\"PowerMemo\",\"apppkgname\":\"com.peng.power.memo\",\"appversion\":\"1.0.27\"," +
                    "\"appdetailjson\":\"{\\\"socket_url\\\":\\\"http://wattcloud.powertalk.kr:8351\\\",\\\"sftp_host\\\":\\\"106.10.33.94\\\"," +
                    "\\\"sftp_user\\\":\\\"sftpuser01\\\",\\\"sftp_user_pw\\\":\\\"Powereng#2501\\\",\\\"sftp_port\\\":\\\"22\\\"," +
                    "\\\"sftp_root_folder\\\":\\\"watt\\\",\\\"sftp_url\\\":\\\"https://wattcloud.powertalk.kr/sftp/watt/\\\"}\"," +
                    "\"useonglass\":1,\"useonpc\":1,\"sortnum\":2},{\"appnamekor\":\"설정\",\"appnameeng\":\"Settings\",\"apppkgname\":\"com.android.settings\"," +
                    "\"appversion\":\"10\",\"appdetailjson\":\"{\\\"\\\"}\",\"useonglass\":1,\"useonpc\":1,\"sortnum\":3}," +
                    "{\"appnamekor\":\"파워톡\",\"appnameeng\":\"PowerTalk\",\"apppkgname\":\"com.peng.plant.facetalk\",\"appversion\":\"1.5.3\"," +
                    "\"appdetailjson\":\"{\\\"googCpuOveruseDetection\\\":\\\"true\\\",\\\"serverUrl01\\\":\\\"https://watt.powertalk.kr:8353@1\\\"," +
                    "\\\"serverUrl02\\\":\\\"tcp://106.10.33.94:8354\\\",\\\"serverUrl03\\\":\\\"coturn1\\\",\\\"resolution\\\":\\\"1280 x 720\\\"}\"," +
                    "\"useonglass\":1,\"useonpc\":1,\"sortnum\":0},{\"appnamekor\":\"파워콜\",\"appnameeng\":\"PowerCall\",\"apppkgname\":\"com.peng.powercall\"," +
                    "\"appversion\":\"1.2.16\",\"appdetailjson\":\"{\\\"\\\"}\",\"useonglass\":1,\"useonpc\":1,\"sortnum\":1}," +
                    "{\"appnamekor\":\"파워메모\",\"appnameeng\":\"PowerMemo\",\"apppkgname\":\"com.peng.power.memo\",\"appversion\":\"1.0.27\"," +
                    "\"appdetailjson\":\"{\\\"socket_url\\\":\\\"http://wattcloud.powertalk.kr:8351\\\",\\\"sftp_host\\\":\\\"106.10.33.94\\\"," +
                    "\\\"sftp_user\\\":\\\"sftpuser01\\\",\\\"sftp_user_pw\\\":\\\"Powereng#2501\\\",\\\"sftp_port\\\":\\\"22\\\",\\\"sftp_root_folder\\\":\\\"watt\\\"," +
                    "\\\"sftp_url\\\":\\\"https://wattcloud.powertalk.kr/sftp/watt/\\\"}\",\"useonglass\":1,\"useonpc\":1,\"sortnum\":2}," +
                    "{\"appnamekor\":\"설정\",\"appnameeng\":\"Settings\",\"apppkgname\":\"com.android.settings\",\"appversion\":\"10\"," +
                    "\"appdetailjson\":\"{\\\"\\\"}\",\"useonglass\":1,\"useonpc\":1,\"sortnum\":3}]";










    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
        setData();
    }

    private void init() {
        recyclerView = findViewById(R.id.adapter);
    }

    private void setData(){
        Gson gson = new Gson();
        list = new ArrayList<>();

        AppData[] items = gson.fromJson(tmp, AppData[].class);

        for(AppData item : items) {
            list.add(item);
        }

        adapter = new AppAdapter(list,getApplicationContext());
        recyclerView.setLayoutManager(new GridLayoutManager(this,2,GridLayoutManager.HORIZONTAL,false));
        recyclerView.setAdapter(adapter);
        adapter.setOnClickListener(new AppAdapter.OnItemClicklistener() {
            @Override
            public void onItemClick(View v, int pos) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);
                dialog.setTitle("개발세발");
                dialog.setMessage("노력하겠습니다");
                dialog.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(MainActivity.this, "앱이 종료되었습니다",Toast.LENGTH_SHORT).show();
                    }
                });
                dialog.show();
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

//        pm = getPackageManager();
//
//        Intent intent = new Intent(Intent.ACTION_MAIN , null);
//        intent.addCategory(Intent.CATEGORY_LAUNCHER);
//        list = pm.queryIntentActivities(intent,0);
//        //item 간격
//        ItemDecoration spaceDecoration = new ItemDecoration(60);
//        recyclerView = (RecyclerView)findViewById(R.id.adapter);
//
//
//
//
//
//
//        //LinearLayout 형식으로 앱 정렬
//        //LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
//
//        //GridView  형식으로 앱 정렬
//        //좌우이동
//        layoutManager = new GridLayoutManager(this,2,GridLayoutManager.HORIZONTAL,false);
//        recyclerView.setLayoutManager(layoutManager);
//        recyclerView.setHasFixedSize(true);
//        recyclerView.addItemDecoration(spaceDecoration);
//        adapter = new AppAdapter(list,pm);
//
//        recyclerView.setAdapter(adapter);
//        adapter.setOnClickListener(new AppAdapter.OnItemClicklistener() {
//            @Override
//            public void onItemClick(View v, int pos) {
//                //아이템 클릭시 앱 정보 확인
//                ResolveInfo checkedResolveInfo =
//                        (ResolveInfo) list.get(pos);
//                //확인한 정보를 액티비티에 담기?
//                ActivityInfo clickedActivityInfo =
//                        checkedResolveInfo.activityInfo;
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
//





    @Override
    public void onTilt(int x, int y, float delta) {
        //        recyclerView.smoothScrollBy(x, y);
        if (Math.abs(delta) > 0.6) {
            recyclerView.smoothScrollBy(x , 0);
//            smoothScrollBy(x * (layoutManager.getEachItemWidth()), 0);
        } else
            recyclerView.smoothScrollBy(x , 0);
//            smoothScrollBy(x * (layoutManager.getEachItemWidth() / 6), 0)
    }
}