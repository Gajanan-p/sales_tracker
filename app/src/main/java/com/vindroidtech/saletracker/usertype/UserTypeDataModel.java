package com.vindroidtech.saletracker.usertype;

public class UserTypeDataModel {
     String Usertype_name;
     String CreateBy;
     String Msg;

     public UserTypeDataModel(){}
    public UserTypeDataModel(String usertype_name, String createBy, String msg) {
        Usertype_name = usertype_name;
        CreateBy = createBy;
        Msg = msg;
    }


    public String getUsertype_name() {
        return Usertype_name;
    }

    public void setUsertype_name(String usertype_name) {
        Usertype_name = usertype_name;
    }

    public String getCreateBy() {
        return CreateBy;
    }

    public void setCreateBy(String createBy) {
        CreateBy = createBy;
    }

    public String getMsg() {
        return Msg;
    }

    public void setMsg(String msg) {
        Msg = msg;
    }
}
