package com.vindroidtech.saletracker.usertype;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class RequestUserTypeBody {

    @SerializedName("usertypeid")
    @Expose
    private Integer usertypeid;
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
    public RequestUserTypeBody() {
    }

    /**
     *
     * @param msg
     * @param updatedby
     * @param usertypeid
     */
    public RequestUserTypeBody(Integer usertypeid, Integer updatedby, String msg) {
        super();
        this.usertypeid = usertypeid;
        this.updatedby = updatedby;
        this.msg = msg;
    }

    public Integer getUsertypeid() {
        return usertypeid;
    }

    public void setUsertypeid(Integer usertypeid) {
        this.usertypeid = usertypeid;
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