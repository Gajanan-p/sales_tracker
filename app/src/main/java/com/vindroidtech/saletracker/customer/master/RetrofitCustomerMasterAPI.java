package com.vindroidtech.saletracker.customer.master;

import com.google.gson.JsonObject;
import com.vindroidtech.saletracker.map.BodyData;
import com.vindroidtech.saletracker.registration.RegistrationDataModel;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface RetrofitCustomerMasterAPI
 {

  @POST("User/CustomerMaster")
   //on below line we are creating a method to post our data.
  Call<CustomerMasterDataModel> customerMasterPost(@Body CustomerMasterDataModel customerMasterDataModel);

  @POST("User/deleteCustomer")
  Call<DeleteCustomerModel> deleteCustomerData(@Body RequestCustBody data);


  @GET("User/Getmerchantlistfordelete")
  Call<ArrayList<GetCustomerDataModel>> fetchCustomerData();

}
