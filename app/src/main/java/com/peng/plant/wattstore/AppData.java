package com.peng.plant.wattstore;

public class AppData {

    String appnamekor;
    String appnameeng;
    String apppkgname;
    String appdetailjson;
    int useonglass;
    int useonpc;

    public AppData(String appnamekor, String appnameeng, String apppkgname, String appdetailjson, int useonglass, int useonpc) {
        this.appnamekor = appnamekor;
        this.appnameeng = appnameeng;
        this.apppkgname = apppkgname;
        this.appdetailjson = appdetailjson;
        this.useonglass = useonglass;
        this.useonpc = useonpc;
    }

    public String getAppnamekor() {
        return appnamekor;
    }

    public void setAppnamekor(String appnamekor) {
        this.appnamekor = appnamekor;
    }

    public String getAppnameeng() {
        return appnameeng;
    }

    public void setAppnameeng(String appnameeng) {
        this.appnameeng = appnameeng;
    }

    public String getApppkgname() {
        return apppkgname;
    }

    public void setApppkgname(String apppkgname) {
        this.apppkgname = apppkgname;
    }

    public String getAppdetailjson() {
        return appdetailjson;
    }

    public void setAppdetailjson(String appdetailjson) {
        this.appdetailjson = appdetailjson;
    }

    public int getUseonglass() {
        return useonglass;
    }

    public void setUseonglass(int useonglass) {
        this.useonglass = useonglass;
    }

    public int getUseonpc() {
        return useonpc;
    }

    public void setUseonpc(int useonpc) {
        this.useonpc = useonpc;
    }
}
