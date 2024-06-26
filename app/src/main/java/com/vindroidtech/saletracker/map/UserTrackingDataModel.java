package com.vindroidtech.saletracker.map;

public class UserTrackingDataModel {
   String user_id;
   String Custname;
   String MbNo;
   String Msg;
   String latituede;
   String longtitude;

   public UserTrackingDataModel(String user_id, String custname, String mbNo, String msg, String latituede, String longtitude) {
      this.user_id = user_id;
      Custname = custname;
      MbNo = mbNo;
      Msg = msg;
      this.latituede = latituede;
      this.longtitude = longtitude;
   }

   public String getUser_id() {
      return user_id;
   }

   public void setUser_id(String user_id) {
      this.user_id = user_id;
   }

   public String getCustname() {
      return Custname;
   }

   public void setCustname(String custname) {
      Custname = custname;
   }

   public String getMbNo() {
      return MbNo;
   }

   public void setMbNo(String mbNo) {
      MbNo = mbNo;
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
}
