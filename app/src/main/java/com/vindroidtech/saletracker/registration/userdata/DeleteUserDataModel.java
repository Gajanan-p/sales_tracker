package com.vindroidtech.saletracker.registration.userdata;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DeleteUserDataModel {

    @SerializedName("userid")
    @Expose
    private Integer userid;
    @SerializedName("Updatedby")
    @Expose
    private Integer updatedby;
    @SerializedName("Msg")
    @Expose
    private String msg;

    public Integer getUserid() {
        return userid;
    }

    public void setUserid(Integer userid) {
        this.userid = userid;
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