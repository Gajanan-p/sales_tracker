package com.vindroidtech.saletracker.customer.master;

public class CustomerMasterDataModel {
     String Custname;
    String Address;
    String MbNo;
    String CreateBy;
    String Msg;

    public CustomerMasterDataModel(){};
    public CustomerMasterDataModel(String custname, String address, String mbNo, String createBy, String msg) {
        Custname = custname;
        Address = address;
        MbNo = mbNo;
        CreateBy = createBy;
        Msg = msg;
    }

    public String getCustname() {
        return Custname;
    }

    public void setCustname(String custname) {
        Custname = custname;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getMbNo() {
        return MbNo;
    }

    public void setMbNo(String mbNo) {
        MbNo = mbNo;
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
