package com.vindroidtech.saletracker.location.service;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface RetrofitLocaionTrackingAPI {
    @POST("User/TrackingTb")
        //on below line we are creating a method to post our data.
    Call<LocationTrackingModel> postLocationTracking(@Body LocationTrackingModel locationTrackingModel);
}
