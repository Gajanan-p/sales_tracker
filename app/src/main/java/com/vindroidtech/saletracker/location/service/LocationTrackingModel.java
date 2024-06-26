package com.vindroidtech.saletracker.location.service;

public class LocationTrackingModel {
    String userid;
    String latituede;
    String longtitude;
    String CreateBy;
    String msg;
    String created_dt;

    public  LocationTrackingModel(){}

    public LocationTrackingModel(String userid, String latituede, String longtitude, String createBy, String msg, String created_dt) {
        this.userid = userid;
        this.latituede = latituede;
        this.longtitude = longtitude;
        CreateBy = createBy;
        this.msg = msg;
        this.created_dt = created_dt;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getLatituede() {
        return latituede;
    }

    public void setLatituede(String latituede) {
        this.latituede = latituede;
    }

    public String getLongtitude() {
        return longtitude;
    }

    public void setLongtitude(String longtitude) {
        this.longtitude = longtitude;
    }

    public String getCreateBy() {
        return CreateBy;
    }

    public void setCreateBy(String createBy) {
        CreateBy = createBy;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getCreated_dt() {
        return created_dt;
    }

    public void setCreated_dt(String created_dt) {
        this.created_dt = created_dt;
    }
}
