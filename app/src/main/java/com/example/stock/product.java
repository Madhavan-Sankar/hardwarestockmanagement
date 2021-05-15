package com.example.stock;

public class product {
    private String type,name;
    private Integer quantity;

    public product (String type, String name, Integer quantity){

        this.type=type;
        this.name=name;
        this.quantity=quantity;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}
