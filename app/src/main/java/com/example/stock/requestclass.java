package com.example.stock;

public class requestclass {
    String id,name,remarks,status,dateandtime;
    requestclass(String id, String name, String remarks, String status, String dateandtime)
    {
        this.id=id;
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
