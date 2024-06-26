package com.vindroidtech.saletracker.customer.addinfo;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface RetrofitAddCustomerInformationAPI {
    @POST("User/CustomerData")
        //on below line we are creating a method to post our data.
    Call<AddCustomerInformationDataModel> addCustomerPost(@Body AddCustomerInformationDataModel addCustomerInformationDataModel);

    @GET("User/GetCustomerlist")
    Call<ArrayList<GetCustomerInfoModel>>fetchCustomerData();
}
