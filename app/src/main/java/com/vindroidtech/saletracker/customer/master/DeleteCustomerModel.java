package com.vindroidtech.saletracker.customer.master;




import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DeleteCustomerModel {

    @SerializedName("custid")
    @Expose
    private Integer custid;
    @SerializedName("Updatedby")
    @Expose
    private Integer updatedby;
    @SerializedName("Msg")
    @Expose
    private String msg;

    public Integer getCustid() {
        return custid;
    }

    public void setCustid(Integer custid) {
        this.custid = custid;
    }

    public Integer getUpdatedby() {
        return updatedby;
    }

    public void setUpdatedby(Integer updatedby) {
        this.updatedby = updatedby;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

}