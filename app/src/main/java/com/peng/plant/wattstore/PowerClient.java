package com.peng.plant.wattstore;

import android.util.Log;

import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.engineio.client.Transport;
import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Manager;
import com.github.nkzawa.socketio.client.Socket;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

public class PowerClient {
    private static final String TAG = "PowerClient";

    private int RECONNECTION_ATTEMPT = 0;
    private long CONNECTION_TIMEOUT = 30 * 1000;

    Socket mSocket;
    PowerClientObserver mObserver;


    public enum CONNECT_STATUS
    {
        UNKNOWN,
        CONNECTED,
        CONNECT_TIMEOUT,
        DISCONNECTED,


    }

    public CONNECT_STATUS connectStatus = CONNECT_STATUS.UNKNOWN;

    public void connectToServer()
    {
        if (isConnected())
            return;

        try {
            connectStatus = CONNECT_STATUS.DISCONNECTED;
            SSLContext mySSLContext = SSLContext.getInstance("TLS");
            TrustManager[] trustAllCerts= new TrustManager[] { new X509TrustManager() {
                public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                    return new java.security.cert.X509Certificate[] {};
                }

                public void checkClientTrusted(X509Certificate[] chain,
                                               String authType) throws CertificateException {
                }

                public void checkServerTrusted(X509Certificate[] chain,
                                               String authType) throws CertificateException {
                }
            } };

            mySSLContext.init(null, trustAllCerts, new SecureRandom());

            HostnameVerifier myHostnameVerifier = new HostnameVerifier() {
                @Override
                public boolean verify(String hostname, SSLSession session) {
                    return true;
                }
            };


            IO.Options opts = new IO.Options(); // server url : 45.119.147.108:8080
            opts.timeout = CONNECTION_TIMEOUT;
            opts.reconnection = true;
            opts.reconnectionAttempts = RECONNECTION_ATTEMPT;
            opts.reconnectionDelay = 1000;
            opts.forceNew = true;
            opts.secure = true;
            opts.port = 443;
            opts.sslContext = mySSLContext;
            opts.hostnameVerifier = myHostnameVerifier;

            String addr = String.format("%s:%s" , Define.POWERSTORE_SERVER_ADDRESS , Define.POWERSTORE_SERVER_PORT);
            mSocket = IO.socket(addr, opts);
            registerConnectionAttributes();
            mSocket.connect();

            Log.d(TAG, "Noah connecting.. " + addr);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean isConnected()
    {
        if (mSocket != null)
        {
            return mSocket.connected();
        }
        return false;
    }

    public void setListener(PowerClientObserver observer)
    {
        this.mObserver = observer;
    }

    public void registerConnectionAttributes() {
        try {
            if (mSocket != null) {
                mSocket.on(Manager.EVENT_TRANSPORT, onTransport);
                mSocket.on(Socket.EVENT_CONNECT, onConnect);
                mSocket.on(Socket.EVENT_CONNECT_ERROR, onConnectError);
                mSocket.on(Socket.EVENT_CONNECT_TIMEOUT, onConnectTimeOut);
                mSocket.on(Socket.EVENT_MESSAGE, onMessage);
                mSocket.on(Socket.EVENT_DISCONNECT, onDisconnect);
                mSocket.on(Define.EVENT_MSG_GETAPPLISTS, onGetAppLists);
                mSocket.on(Define.EVENT_MSG_SOCKET_CONNECTED, onSocketConnected);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Emitter.Listener onTransport = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            Log.d(TAG, "Noah onTransport");
            Transport transport = (Transport) args[0];
            // Adding headers when EVENT_REQUEST_HEADERS is called
            transport.on(Transport.EVENT_REQUEST_HEADERS, new Emitter.Listener() {
                @Override
                public void call(Object... args) {
                    Log.v(TAG, "Caught EVENT_REQUEST_HEADERS after EVENT_TRANSPORT, adding headers");
                    Map<String, List<String>> mHeaders = (Map<String, List<String>>)args[0];
                    mHeaders.put("Authorization", Arrays.asList("Basic bXl1c2VyOm15cGFzczEyMw=="));
                }
            });
        }
    };

    private Emitter.Listener onConnect = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            Log.d(TAG, "Noah onConnected");
            if(mObserver != null)
            {
                mObserver.onOpen(true);
                connectStatus = CONNECT_STATUS.CONNECTED;
            }
        }
    };

    private Emitter.Listener onSocketConnected = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            for (Object arg : args) {
//                Log.d(TAG, "Noah test onSocketConnected msg: " + String.valueOf(arg));
//            if(mObserver != null)
//                mObserver.onOpen(true);
            }
        }
    };

    private Emitter.Listener onMessage = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
//            mObserver.onTextMessage((String)args[0]);
            Log.d(TAG, "Noah onMessage");
            for (Object arg : args) {
                Log.d(TAG, "Noah onMessage msg: " + String.valueOf(arg));

                if(mObserver != null)
                    mObserver.onTextMessage(String.valueOf(arg));
            }
        }
    };

    private Emitter.Listener onConnectError = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            for (Object arg : args) {
                Log.d(TAG, "Noah onConnectError msg: " + String.valueOf(arg));
            }
            if(mObserver != null)
            {
                mObserver.onOpen(false);
                connectStatus = CONNECT_STATUS.CONNECT_TIMEOUT;
            }
        }
    };

    private Emitter.Listener onConnectTimeOut = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            String msg = "";
            for (Object arg : args) {
                msg = String.valueOf(arg);
                Log.d(TAG, "Noah onConnectTimeOut msg: " + msg);
            }
            if(mObserver != null)
            {
                connectStatus = CONNECT_STATUS.CONNECT_TIMEOUT;
                mObserver.onConnectTimeOut(msg);
            }
        }
    };

    private Emitter.Listener onDisconnect = new Emitter.Listener() {
        @Override
        public void call(Object... args) {

            String msg = "";
            for (Object arg : args) {
                msg = String.valueOf(arg);
                Log.d(TAG, "Noah onDisconnect msg: " + msg);
            }

            if(mObserver != null)
            {
                connectStatus = CONNECT_STATUS.DISCONNECTED;
                mObserver.onDisconnect(msg);
            }
        }
    };


    private Emitter.Listener onGetAppLists = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            for (Object arg : args) {
                Log.d(TAG, "Noah onGetAppLists msg: " + String.valueOf(arg));

                ConcurrentHashMap<Integer , AppData> apps = new ConcurrentHashMap<>();
                Log.d(TAG, "call: apps");
                try
                {

                    JSONObject jsonMsg = new JSONObject(String.valueOf(arg));
                    if(!jsonMsg.isNull("lists"))
                    {
                        JSONArray jArray = jsonMsg.getJSONArray("lists");
                        if (jArray != null)
                        {
                            for (int app_pos = 0 ; app_pos<jArray.length() ; app_pos++)
                            {
                                AppData data = new AppData();
                                JSONObject json = new JSONObject(jArray.getString(app_pos));
                                data.position = app_pos;
                                if(!json.isNull("appnamekor")) data.appNameKor = json.getString("appnamekor");
                                if(!json.isNull("appnameeng")) data.appNameEng = json.getString("appnameeng");
                                if(!json.isNull("apppkgname")) data.appPackageName = json.getString("apppkgname");
                                if(!json.isNull("appversion")) data.appUpdateVersion =  json.getString("appversion");
                                if(!json.isNull("appdetailjson")) data.appDetailJson = json.getString("appdetailjson");
                                if(!json.isNull("useronglass")) data.useOnGlass = json.getInt("useronglass");
                                if(!json.isNull("useronpc")) data.useOnPC = json.getInt("useronpc");
                                apps.put(app_pos , data);
                                Log.d(TAG, "call: apps put");
                            }
                        }
                    }
                    mObserver.onGetAppLists(apps);
                    Log.d(TAG, "call: observer app");
                }
                catch (JSONException e)
                {
                    e.printStackTrace();
                }
            }
        }
    };
}
