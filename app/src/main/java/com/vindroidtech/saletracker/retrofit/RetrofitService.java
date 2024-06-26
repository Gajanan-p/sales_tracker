package com.vindroidtech.saletracker.retrofit;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitService {
    static Retrofit retrofit;
    public static Retrofit getRetrofit() {
        retrofit = new Retrofit.Builder()
                .baseUrl("http://location.greenorb.in/api/")
                // as we are sending data in json format so
                // we have to add Gson converter factory
                .addConverterFactory(GsonConverterFactory.create())
                // at last we are building our retrofit builder.
                .build();
        return retrofit;
    }



}
