package com.example.stock;

import android.content.pm.PackageManager;

public class request {
    String username;
    String productname;
    int quantity;

    public request()
    {

    }
    public request(String username,String productname,int quantity)
    {
        this.username=username;
        this.productname=productname;
        this.quantity=quantity;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getProductname() {
        return productname;
    }

    public void setProductname(String productname) {
        this.productname = productname;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
