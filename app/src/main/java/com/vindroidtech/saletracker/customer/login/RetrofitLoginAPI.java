package com.vindroidtech.saletracker.customer.login;

import com.vindroidtech.saletracker.customer.master.CustomerMasterDataModel;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface RetrofitLoginAPI {
    @POST("User/Login")
        //on below line we are creating a method to post our data.
    Call<ArrayList<LoginDataModel>> loginPost(@Body LoginDataModel loginDataModel);
}
