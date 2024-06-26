package com.vindroidtech.saletracker.customer.login;

public class LoginDataModel {

        String user_id;
        String login_Name;
        String User_Pwd;
        String MoNo;
        String msg;
        String usertypeid;
        String managerId;
        String WindowStatus;
        String IsLogin;
        public LoginDataModel(){}

    public LoginDataModel(String user_id, String login_Name, String user_Pwd, String moNo, String msg, String usertypeid, String managerId, String windowStatus, String isLogin) {
        this.user_id = user_id;
        this.login_Name = login_Name;
        User_Pwd = user_Pwd;
        MoNo = moNo;
        this.msg = msg;
        this.usertypeid = usertypeid;
        this.managerId = managerId;
        WindowStatus = windowStatus;
        IsLogin = isLogin;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getLogin_Name() {
        return login_Name;
    }

    public void setLogin_Name(String login_Name) {
        this.login_Name = login_Name;
    }

    public String getUser_Pwd() {
        return User_Pwd;
    }

    public void setUser_Pwd(String user_Pwd) {
        User_Pwd = user_Pwd;
    }

    public String getMoNo() {
        return MoNo;
    }

    public void setMoNo(String moNo) {
        MoNo = moNo;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getUsertypeid() {
        return usertypeid;
    }

    public void setUsertypeid(String usertypeid) {
        this.usertypeid = usertypeid;
    }

    public String getManagerId() {
        return managerId;
    }

    public void setManagerId(String managerId) {
        this.managerId = managerId;
    }

    public String getWindowStatus() {
        return WindowStatus;
    }

    public void setWindowStatus(String windowStatus) {
        WindowStatus = windowStatus;
    }

    public String getIsLogin() {
        return IsLogin;
    }

    public void setIsLogin(String isLogin) {
        IsLogin = isLogin;
    }
}
