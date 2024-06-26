package com.vindroidtech.saletracker.registration.userdata;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class UserDataModel {

    @SerializedName("User_id")
    @Expose
    private String userId;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("MbNo")
    @Expose
    private String mbNo;
    @SerializedName("Emailid")
    @Expose
    private String emailid;
    @SerializedName("doj")
    @Expose
    private String doj;
    @SerializedName("managerId")
    @Expose
    private String managerId;
    @SerializedName("UserTypeId")
    @Expose
    private String userTypeId;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMbNo() {
        return mbNo;
    }

    public void setMbNo(String mbNo) {
        this.mbNo = mbNo;
    }

    public String getEmailid() {
        return emailid;
    }

    public void setEmailid(String emailid) {
        this.emailid = emailid;
    }

    public String getDoj() {
        return doj;
    }

    public void setDoj(String doj) {
        this.doj = doj;
    }

    public String getManagerId() {
        return managerId;
    }

    public void setManagerId(String managerId) {
        this.managerId = managerId;
    }

    public String getUserTypeId() {
        return userTypeId;
    }

    public void setUserTypeId(String userTypeId) {
        this.userTypeId = userTypeId;
    }

}
