package com.vindroidtech.saletracker.customer.master;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class GetCustomerDataModel {

    @SerializedName("cust_id")
    @Expose
    private String custId;
    @SerializedName("Custname")
    @Expose
    private String custname;
    @SerializedName("Address")
    @Expose
    private String address;
    @SerializedName("mbno")
    @Expose
    private String mbno;

    public String getCustId() {
        return custId;
    }

    public void setCustId(String custId) {
        this.custId = custId;
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

    public String getMbno() {
        return mbno;
    }

    public void setMbno(String mbno) {
        this.mbno = mbno;
    }

}