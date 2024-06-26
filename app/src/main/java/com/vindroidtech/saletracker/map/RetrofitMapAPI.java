package com.vindroidtech.saletracker.map;
import com.vindroidtech.saletracker.customer.login.LoginDataModel;
import java.util.ArrayList;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;


public interface RetrofitMapAPI
 {
  @POST("User/listusertrackdata")
   //on below line we are creating a method to post our data.
  Call<ArrayList<UserTrackingDataModel>> getTrackingUserData(@Body BodyData bodyData);
}
