package com.peng.plant.wattstore;

import android.os.Environment;

public class Define {



    public static final String EVENT_MSG_GETAPPLISTS = "getAppLists";
    public static final String EVENT_MSG_SOCKET_CONNECTED = "socketconnected";


    public static final String APP_PACKAGE_NAME_SETTINGS = "com.android.settings";
    public static final String APP_PACKAGE_NAME_CAMERA = "com.realwear.camera";
    public static final String APP_PACKAGE_NAME_FILEBROWSER = "com.realwear.filebrowser";



    public static final String POWERSTORE_SERVER_ADDRESS = "https://wattcloud.powertalk.kr"; // watt
    public static final String POWERSTORE_SERVER_PORT = "8352";
    private static final String SERVER_URL = "https://watt.powertalk.kr" + ":8357/" + "powerstore" + "/";


    public static final String LOCAL_DOWNLOAD_PATH = Environment.getExternalStorageDirectory().getAbsolutePath() + "/Download/";

    public static final String SERVER_APP_DOWNLOAD_URL = SERVER_URL + "apps" + "/";
    public static final String SERVER_LOGO_DOWNLOAD_URL = SERVER_URL + "icons" + "/";

}
