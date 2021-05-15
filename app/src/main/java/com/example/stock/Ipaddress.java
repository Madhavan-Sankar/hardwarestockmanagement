package com.example.stock;

import android.app.Application;

public class Ipaddress extends Application {
    String ip;

    public Ipaddress() {

    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }
}
