package com.vindroidtech.saletracker.reports;



import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetKMdataModel {

    @SerializedName("userid")
    @Expose
    private String userid;
    @SerializedName("userName")
    @Expose
    private String userName;
    @SerializedName("Usertype")
    @Expose
    private String usertype;
    @SerializedName("kmdate")
    @Expose
    private String kmdate;
    @SerializedName("totalkm")
    @Expose
    private String totalkm;

    /**
     * No args constructor for use in serialization
     *
     */
    public GetKMdataModel() {
    }

    /**
     *
     * @param kmdate
     * @param usertype
     * @param totalkm
     * @param userName
     * @param userid
     */
    public GetKMdataModel(String userid, String userName, String usertype, String kmdate, String totalkm) {
        super();
        this.userid = userid;
        this.userName = userName;
        this.usertype = usertype;
        this.kmdate = kmdate;
        this.totalkm = totalkm;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUsertype() {
        return usertype;
    }

    public void setUsertype(String usertype) {
        this.usertype = usertype;
    }

    public String getKmdate() {
        return kmdate;
    }

    public void setKmdate(String kmdate) {
        this.kmdate = kmdate;
    }

    public String getTotalkm() {
        return totalkm;
    }

    public void setTotalkm(String totalkm) {
        this.totalkm = totalkm;
    }

}