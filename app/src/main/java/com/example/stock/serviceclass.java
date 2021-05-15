package com.example.stock;

public class serviceclass {
    String id,type,name,remarks,status,dateandtime;
    serviceclass(String id,String type,String name,String remarks,String status, String dateandtime)
    {
        this.id=id;
        this.type=type;
        this.name=name;
        this.remarks=remarks;
        this.status=status;
        this.dateandtime=dateandtime;
    }

    public String getDateandtime() {
        return dateandtime;
    }

    public void setDateandtime(String dateandtime) {
        this.dateandtime = dateandtime;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
