package com.peng.plant.wattstore;

import java.util.concurrent.ConcurrentHashMap;

public interface PowerClientObserver {

    void onOpen(boolean isConnected);
    void onGetAppLists(ConcurrentHashMap<Integer , AppData> data);
    void onTextMessage(String msg);
    void onConnectTimeOut(String msg);
    void onDisconnect(String msg);

}
