package com.vindroidtech.saletracker.managerRights;

import com.vindroidtech.saletracker.registration.RegistrationDataModel;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface RetrofitManagerRightAPI {
    @GET("User/Getmerchantlist")
        //on below line we are creating a method to post our data.
    Call<ArrayList<ManagerRightsModel>> getManagerRightsData();
}
