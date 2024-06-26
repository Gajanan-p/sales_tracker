package com.vindroidtech.saletracker.registration;

import com.google.gson.JsonObject;
import com.vindroidtech.saletracker.map.BodyData;
import com.vindroidtech.saletracker.registration.userdata.DeleteUserDataModel;
import com.vindroidtech.saletracker.registration.userdata.RequestBody;
import com.vindroidtech.saletracker.registration.userdata.UserDataModel;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface RetrofitRegistrationAPI {
    @POST("User/Registration")
        //on below line we are creating a method to post our data.
    Call<RegistrationDataModel> userRegistrationPost(@Body RegistrationDataModel dataModal);

    @POST("User/deleteuser")
    Call<DeleteUserDataModel> deleteUserData(@Body RequestBody body);

    @GET("User/GetRegistrationlistfordelete")
    Call<ArrayList<UserDataModel>>fetchUserData();
}
