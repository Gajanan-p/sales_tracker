package com.vindroidtech.saletracker.reports;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RequestReportUserWiseModel {

    @SerializedName("Userid")
    @Expose
    private String userid;
    @SerializedName("managerid")
    @Expose
    private String managerid;
    @SerializedName("fromdate")
    @Expose
    private String fromdate;
    @SerializedName("todate")
    @Expose
    private String todate;

    /**
     * No args constructor for use in serialization
     *
     */
    public RequestReportUserWiseModel() {
    }

    public RequestReportUserWiseModel(String userid, String managerid, String fromdate, String todate) {
        this.userid = userid;
        this.managerid = managerid;
        this.fromdate = fromdate;
        this.todate = todate;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getManagerid() {
        return managerid;
    }

    public void setManagerid(String managerid) {
        this.managerid = managerid;
    }

    public String getFromdate() {
        return fromdate;
    }

    public void setFromdate(String fromdate) {
        this.fromdate = fromdate;
    }

    public String getTodate() {
        return todate;
    }

    public void setTodate(String todate) {
        this.todate = todate;
    }
}