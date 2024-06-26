package com.vindroidtech.saletracker.usertype;

import com.google.gson.JsonObject;
import com.vindroidtech.saletracker.map.BodyData;
import com.vindroidtech.saletracker.registration.RegistrationDataModel;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface RetrofitUserTypeAPI {
    @POST("User/Usertype")
        //on below line we are creating a method to post our data.
    Call<UserTypeDataModel> userTypePost(@Body UserTypeDataModel userTypeDataModel);

//    User/GetUsertypeList
    @GET("User/GetUsertypeList")
//on below line we are creating a method to post our data.
    Call<ArrayList<UserTypeModel>> userTypeData();

    @POST("User/deleteUsertype")
    Call<DeleteUserTypeModel> deleteUserTypeData(@Body RequestUserTypeBody data);
}
