package com.vindroidtech.saletracker.customer.addinfo;

public class AddCustomerInformationDataModel {
        String Brand;
        String Remark;
        String Counter_Approx;
        String Response;
        String custid;
        Double CreateBy;
        String Msg;
        String latituede;
        String longtitude;
        String created_dt;

        public AddCustomerInformationDataModel(){}

    public AddCustomerInformationDataModel(String brand, String remark, String counter_Approx, String response, String custid, Double createBy, String msg, String latituede, String longtitude, String created_dt) {
        Brand = brand;
        Remark = remark;
        Counter_Approx = counter_Approx;
        Response = response;
        this.custid = custid;
        CreateBy = createBy;
        Msg = msg;
        this.latituede = latituede;
        this.longtitude = longtitude;
        this.created_dt = created_dt;
    }

    public String getBrand() {
        return Brand;
    }

    public void setBrand(String brand) {
        Brand = brand;
    }

    public String getRemark() {
        return Remark;
    }

    public void setRemark(String remark) {
        Remark = remark;
    }

    public String getCounter_Approx() {
        return Counter_Approx;
    }

    public void setCounter_Approx(String counter_Approx) {
        Counter_Approx = counter_Approx;
    }

    public String getResponse() {
        return Response;
    }

    public void setResponse(String response) {
        Response = response;
    }

    public String getCustid() {
        return custid;
    }

    public void setCustid(String custid) {
        this.custid = custid;
    }

    public Double getCreateBy() {
        return CreateBy;
    }

    public void setCreateBy(Double createBy) {
        CreateBy = createBy;
    }

    public String getMsg() {
        return Msg;
    }

    public void setMsg(String msg) {
        Msg = msg;
    }

    public String getLatituede() {
        return latituede;
    }

    public void setLatituede(String latituede) {
        this.latituede = latituede;
    }

    public String getLongtitude() {
        return longtitude;
    }

    public void setLongtitude(String longtitude) {
        this.longtitude = longtitude;
    }

    public String getCreated_dt() {
        return created_dt;
    }

    public void setCreated_dt(String created_dt) {
        this.created_dt = created_dt;
    }
}
