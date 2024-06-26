package com.vindroidtech.saletracker.managerRights;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ManagerRightsModel {

    @SerializedName("User_Id")
    @Expose
    private String userId;
    @SerializedName("Led_key")
    @Expose
    private String ledKey;
    @SerializedName("Fname")
    @Expose
    private String fname;
    @SerializedName("Lname")
    @Expose
    private String lname;
    @SerializedName("Mname")
    @Expose
    private String mname;
    @SerializedName("mbno")
    @Expose
    private String mbno;
    @SerializedName("Emailid")
    @Expose
    private String emailid;
    @SerializedName("CreateBy")
    @Expose
    private String createBy;
    @SerializedName("created_dt")
    @Expose
    private String createdDt;

    /**
     * No args constructor for use in serialization
     *
     */
    public ManagerRightsModel() {
    }

    /**
     *
     * @param fname
     * @param lname
     * @param createBy
     * @param createdDt
     * @param mbno
     * @param emailid
     * @param mname
     * @param userId
     * @param ledKey
     */
    public ManagerRightsModel(String userId, String ledKey, String fname, String lname, String mname, String mbno, String emailid, String createBy, String createdDt) {
        super();
        this.userId = userId;
        this.ledKey = ledKey;
        this.fname = fname;
        this.lname = lname;
        this.mname = mname;
        this.mbno = mbno;
        this.emailid = emailid;
        this.createBy = createBy;
        this.createdDt = createdDt;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getLedKey() {
        return ledKey;
    }

    public void setLedKey(String ledKey) {
        this.ledKey = ledKey;
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getLname() {
        return lname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }

    public String getMname() {
        return mname;
    }

    public void setMname(String mname) {
        this.mname = mname;
    }

    public String getMbno() {
        return mbno;
    }

    public void setMbno(String mbno) {
        this.mbno = mbno;
    }

    public String getEmailid() {
        return emailid;
    }

    public void setEmailid(String emailid) {
        this.emailid = emailid;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public String getCreatedDt() {
        return createdDt;
    }

    public void setCreatedDt(String createdDt) {
        this.createdDt = createdDt;
    }

}