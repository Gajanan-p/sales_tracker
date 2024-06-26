package com.vindroidtech.saletracker.reports;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class ReportUserWiseModel {

    @SerializedName("Custname")
    @Expose
    private String custname;
    @SerializedName("Address")
    @Expose
    private String address;
    @SerializedName("MbNo")
    @Expose
    private String mbNo;
    @SerializedName("Brand")
    @Expose
    private String brand;
    @SerializedName("Remark")
    @Expose
    private String remark;
    @SerializedName("Counter_Approx")
    @Expose
    private String counterApprox;
    @SerializedName("Response")
    @Expose
    private String response;
    @SerializedName("fname")
    @Expose
    private String fname;
    @SerializedName("CreateBy")
    @Expose
    private String createBy;
    @SerializedName("Msg")
    @Expose
    private Object msg;

    /**
     * No args constructor for use in serialization
     *
     */
    public ReportUserWiseModel() {
    }

    /**
     *
     * @param msg
     * @param fname
     * @param createBy
     * @param address
     * @param mbNo
     * @param response
     * @param counterApprox
     * @param remark
     * @param brand
     * @param custname
     */
    public ReportUserWiseModel(String custname, String address, String mbNo, String brand, String remark, String counterApprox, String response, String fname, String createBy, Object msg) {
        super();
        this.custname = custname;
        this.address = address;
        this.mbNo = mbNo;
        this.brand = brand;
        this.remark = remark;
        this.counterApprox = counterApprox;
        this.response = response;
        this.fname = fname;
        this.createBy = createBy;
        this.msg = msg;
    }

    public String getCustname() {
        return custname;
    }

    public void setCustname(String custname) {
        this.custname = custname;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getMbNo() {
        return mbNo;
    }

    public void setMbNo(String mbNo) {
        this.mbNo = mbNo;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getCounterApprox() {
        return counterApprox;
    }

    public void setCounterApprox(String counterApprox) {
        this.counterApprox = counterApprox;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public Object getMsg() {
        return msg;
    }

    public void setMsg(Object msg) {
        this.msg = msg;
    }

}