package com.vindroidtech.saletracker.map;

public class BodyData {
    String Userid;
    String fromdate;

    public BodyData() {
    }
    public BodyData(String userid, String fromdate) {
        Userid = userid;
        fromdate = fromdate;
    }

    public String getUserid() {
        return Userid;
    }

    public void setUserid(String userid) {
        Userid = userid;
    }

    public String getFromdate() {
        return fromdate;
    }

    public void setFromdate(String fromdate) {
        this.fromdate = fromdate;
    }
}
