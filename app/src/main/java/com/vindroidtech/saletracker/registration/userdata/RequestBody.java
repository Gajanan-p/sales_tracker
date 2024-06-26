package com.vindroidtech.saletracker.registration.userdata;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class RequestBody {

    @SerializedName("userid")
    @Expose
    private Integer userid;
    @SerializedName("Updatedby")
    @Expose
    private Integer updatedby;
    @SerializedName("Msg")
    @Expose
    private String msg;

    /**
     * No args constructor for use in serialization
     *
     */
    public RequestBody() {
    }

    /**
     *
     * @param msg
     * @param updatedby
     * @param userid
     */
    public RequestBody(Integer userid, Integer updatedby, String msg) {
        super();
        this.userid = userid;
        this.updatedby = updatedby;
        this.msg = msg;
    }

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