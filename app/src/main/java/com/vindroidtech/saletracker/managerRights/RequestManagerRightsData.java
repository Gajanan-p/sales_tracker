package com.vindroidtech.saletracker.managerRights;



import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RequestManagerRightsData {

    @SerializedName("Userid")
    @Expose
    private String userid;

    /**
     * No args constructor for use in serialization
     *
     */
    public RequestManagerRightsData() {
    }

    /**
     *
     * @param userid
     */
    public RequestManagerRightsData(String userid) {
        super();
        this.userid = userid;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

}
