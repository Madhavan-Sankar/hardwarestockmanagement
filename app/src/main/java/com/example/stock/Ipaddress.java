package com.example.stock;

import android.app.Application;

import java.util.List;

public class Ipaddress extends Application {
    String ip;
    List<String> department,cse,eee,ece,civil,mech;

    public Ipaddress() {

    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public List<String> getDepartment() {
        return department;
    }

    public void setDepartment(List<String> department) {
        this.department = department;
    }

    public List<String> getCse() {
        return cse;
    }

    public void setCse(List<String> cse) {
        this.cse = cse;
    }

    public List<String> getEee() {
        return eee;
    }

    public void setEee(List<String> eee) {
        this.eee = eee;
    }

    public List<String> getEce() {
        return ece;
    }

    public void setEce(List<String> ece) {
        this.ece = ece;
    }

    public List<String> getCivil() {
        return civil;
    }

    public void setCivil(List<String> civil) {
        this.civil = civil;
    }

    public List<String> getMech() {
        return mech;
    }

    public void setMech(List<String> mech) {
        this.mech = mech;
    }
}
