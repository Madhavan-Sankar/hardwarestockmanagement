package com.example.stock;

public class serviceclass {
    String id,type,name,remarks,reason,status,dateandtime;

    public serviceclass(String id, String type, String name, String remarks, String reason, String status, String dateandtime) {
        this.id = id;
        this.type = type;
        this.name = name;
        this.remarks = remarks;
        this.reason = reason;
        this.status = status;
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

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDateandtime() {
        return dateandtime;
    }

    public void setDateandtime(String dateandtime) {
        this.dateandtime = dateandtime;
    }
}
