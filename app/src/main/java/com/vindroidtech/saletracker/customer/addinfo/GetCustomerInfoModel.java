package com.vindroidtech.saletracker.customer.addinfo;

public class GetCustomerInfoModel {

    String User_Id;
    String Fname;

    public GetCustomerInfoModel() {
    }

    public GetCustomerInfoModel(String user_Id, String fname) {
        User_Id = user_Id;
        Fname = fname;
    }

    public String getUser_Id() {
        return User_Id;
    }

    public void setUser_Id(String user_Id) {
        User_Id = user_Id;
    }

    public String getFname() {
        return Fname;
    }

    public void setFname(String fname) {
        Fname = fname;
    }
}
