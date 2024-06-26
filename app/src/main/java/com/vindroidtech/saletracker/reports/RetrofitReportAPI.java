package com.vindroidtech.saletracker.reports;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface RetrofitReportAPI {
    @POST("User/Reportuserwise")
    Call<ArrayList<ReportUserWiseModel>> fetchReportUserWiseData(@Body RequestReportUserWiseModel data);

    @POST("User/GetKMdata")
    Call<ArrayList<GetKMdataModel>> fetchAllGetKMData(@Body RequestReportUserWiseModel data);

}
