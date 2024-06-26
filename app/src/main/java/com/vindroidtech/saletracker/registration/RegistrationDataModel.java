package com.vindroidtech.saletracker.registration;

public class RegistrationDataModel {
        String Fname;
            String Mname;
            String Lname;
            String MbNo;
            String Emailid;
            String DOj;
            String DOB;
        String Password;
            String CreateBy;
            String created_dt;
    String UpdatedBy;
    String UpdartedDt;
    String managerId;
    String UserTypeId;
    String Msg;
    public RegistrationDataModel(){};
    public RegistrationDataModel(String fname, String mname, String lname, String mbNo, String emailid, String DOj, String DOB, String password, String createBy, String created_dt, String updatedBy, String updartedDt, String managerId, String userTypeId, String msg) {
        Fname = fname;
        Mname = mname;
        Lname = lname;
        MbNo = mbNo;
        Emailid = emailid;
        this.DOj = DOj;
        this.DOB = DOB;
        Password = password;
        CreateBy = createBy;
        this.created_dt = created_dt;
        UpdatedBy = updatedBy;
        UpdartedDt = updartedDt;
        this.managerId = managerId;
        UserTypeId = userTypeId;
        Msg = msg;
    }

    public String getFname() {
        return Fname;
    }

    public void setFname(String fname) {
        Fname = fname;
    }

    public String getMname() {
        return Mname;
    }

    public void setMname(String mname) {
        Mname = mname;
    }

    public String getLname() {
        return Lname;
    }

    public void setLname(String lname) {
        Lname = lname;
    }

    public String getMbNo() {
        return MbNo;
    }

    public void setMbNo(String mbNo) {
        MbNo = mbNo;
    }

    public String getEmailid() {
        return Emailid;
    }

    public void setEmailid(String emailid) {
        Emailid = emailid;
    }

    public String getDOj() {
        return DOj;
    }

    public void setDOj(String DOj) {
        this.DOj = DOj;
    }

    public String getDOB() {
        return DOB;
    }

    public void setDOB(String DOB) {
        this.DOB = DOB;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getCreateBy() {
        return CreateBy;
    }

    public void setCreateBy(String createBy) {
        CreateBy = createBy;
    }

    public String getCreated_dt() {
        return created_dt;
    }

    public void setCreated_dt(String created_dt) {
        this.created_dt = created_dt;
    }

    public String getUpdatedBy() {
        return UpdatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        UpdatedBy = updatedBy;
    }

    public String getUpdartedDt() {
        return UpdartedDt;
    }

    public void setUpdartedDt(String updartedDt) {
        UpdartedDt = updartedDt;
    }

    public String getManagerId() {
        return managerId;
    }

    public void setManagerId(String managerId) {
        this.managerId = managerId;
    }

    public String getUserTypeId() {
        return UserTypeId;
    }

    public void setUserTypeId(String userTypeId) {
        UserTypeId = userTypeId;
    }

    public String getMsg() {
        return Msg;
    }

    public void setMsg(String msg) {
        Msg = msg;
    }
}
