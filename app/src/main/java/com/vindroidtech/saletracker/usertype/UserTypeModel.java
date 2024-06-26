package com.vindroidtech.saletracker.usertype;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserTypeModel {

    @SerializedName("Usertype_Id")
    @Expose
    private String usertypeId;
    @SerializedName("Usertype_name")
    @Expose
    private String usertypeName;
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
    public UserTypeModel() {
    }

    /**
     *
     * @param createBy
     * @param createdDt
     * @param usertypeId
     * @param usertypeName
     */
    public UserTypeModel(String usertypeId, String usertypeName, String createBy, String createdDt) {
        super();
        this.usertypeId = usertypeId;
        this.usertypeName = usertypeName;
        this.createBy = createBy;
        this.createdDt = createdDt;
    }

    public String getUsertypeId() {
        return usertypeId;
    }

    public void setUsertypeId(String usertypeId) {
        this.usertypeId = usertypeId;
    }

    public String getUsertypeName() {
        return usertypeName;
    }

    public void setUsertypeName(String usertypeName) {
        this.usertypeName = usertypeName;
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