package com.vindroidtech.saletracker.registration.userdata;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.vindroidtech.AppPreference;
import com.vindroidtech.saletracker.MainActivity;
import com.vindroidtech.saletracker.R;
import com.vindroidtech.saletracker.dashboard.DashboardActivity;
import com.vindroidtech.saletracker.map.BodyData;
import com.vindroidtech.saletracker.registration.RetrofitRegistrationAPI;
import com.vindroidtech.saletracker.reports.OnPagination;
import com.vindroidtech.saletracker.reports.ReportUserWiseAdapter;
import com.vindroidtech.saletracker.retrofit.RetrofitService;
import com.vindroidtech.saletracker.usertype.RetrofitUserTypeAPI;
import com.vindroidtech.saletracker.usertype.UserTypeModel;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserDataActivity extends AppCompatActivity implements UserDataDeleteListener, OnPagination {

    private RecyclerView viewUserDataList;
    private List<UserDataModel> userDataModels;
    private UserDataAdapter userDataAdapter;

    //    start Pagination
    private AppCompatButton buttonFirst;
    private AppCompatButton buttonPre;
    private AppCompatButton buttonNext;
    private AppCompatButton buttonLast;
    private AppCompatTextView textViewPageNo;

    int currentPageNo=1;
    int countPerPage=5;
    int totalPages=0;
    //    end Pagination


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_data);
        viewUserDataList = findViewById(R.id.view_use_list);
//        AppPreference.clearLoginDataPreferences(UserDataActivity.this);
        //    start Pagination
        buttonFirst=findViewById(R.id.report_km_wise_buttonFirst);
        buttonPre=findViewById(R.id.report_km_wise_buttonPre);
        buttonNext=findViewById(R.id.report_km_wise_buttonNext);
        buttonLast=findViewById(R.id.report_km_wise_buttonLast);
        textViewPageNo=findViewById(R.id.report_km_wise_textPageNo);
        buttonFirst.setOnClickListener(this::onFirstClicked);
        buttonPre.setOnClickListener(this::onPreClicked);
        buttonNext.setOnClickListener(this::onNextClicked);
        buttonLast.setOnClickListener(this::onLastClicked);
        //    end Pagination

        fetchUserDataFromServer();
    }

    private void fetchUserDataFromServer() {
        RetrofitRegistrationAPI registrationAPI = RetrofitService.getRetrofit().create(RetrofitRegistrationAPI.class);
        Call<ArrayList<UserDataModel>> apiCallUserdata = registrationAPI.fetchUserData();
        apiCallUserdata.enqueue(new Callback<ArrayList<UserDataModel>>() {
            @Override
            public void onResponse(Call<ArrayList<UserDataModel>> call, Response<ArrayList<UserDataModel>> response) {
                if (response.isSuccessful()){
                    userDataModels = response.body();
                    //    start Pagination
                    totalPages=userDataModels.size()/countPerPage;
                    if(countPerPage<=userDataModels.size()){
                        userDataAdapter = new UserDataAdapter(userDataModels.subList(currentPageNo-1,countPerPage), UserDataActivity.this);
                        viewUserDataList.setAdapter(userDataAdapter);
                        viewUserDataList.setLayoutManager(new LinearLayoutManager(UserDataActivity.this));

                    }else{
                        userDataAdapter = new UserDataAdapter(userDataModels, UserDataActivity.this);
                        viewUserDataList.setAdapter(userDataAdapter);
                        viewUserDataList.setLayoutManager(new LinearLayoutManager(UserDataActivity.this));
                    }
                    textViewPageNo.setText(String.valueOf(currentPageNo));
                    //    end Pagination


                }
            }

            @Override
            public void onFailure(Call<ArrayList<UserDataModel>> call, Throwable t) {
                Log.e("Error",t.getMessage());
            }
        });

    }

    @Override
    public void onUserDeleteClicked(UserDataModel userDataModel) {
        RequestBody data = new RequestBody();
        data.setUserid(Integer.parseInt(userDataModel.getUserId()));
        RetrofitRegistrationAPI registrationAPI = RetrofitService.getRetrofit().create(RetrofitRegistrationAPI.class);
        Call<DeleteUserDataModel> deleteUserCall = registrationAPI.deleteUserData(data);
        deleteUserCall.enqueue(new Callback<DeleteUserDataModel>() {
            @Override
            public void onResponse(Call<DeleteUserDataModel> call, Response<DeleteUserDataModel> response) {
                if (response.isSuccessful()){
                    Toast.makeText(UserDataActivity.this,response.body().getMsg(),Toast.LENGTH_LONG).show();
                    userDataAdapter.notifyDataSetChanged();
                    fetchUserDataFromServer();
                }
            }

            @Override
            public void onFailure(Call<DeleteUserDataModel> call, Throwable t) {
                Log.e("Error",t.getMessage());
            }
        });
    }

    @Override
    public void onUserClicked(UserDataModel userDataModel) {

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId())
        {
            case R.id.menu_logout:
                Intent intent=new Intent(UserDataActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
                break;
        }
        return true;
    }

    //    start Pagination

    @Override
    public void onFirstClicked(View view) {
        currentPageNo=1;
        int firstIndex=(currentPageNo-1);
        int secondIndex=firstIndex + countPerPage;
        if(secondIndex>= userDataModels.size()){
            secondIndex=userDataModels.size();
        }
        userDataAdapter = new UserDataAdapter(userDataModels.subList(firstIndex,secondIndex),this);
        viewUserDataList.setAdapter(userDataAdapter);
        viewUserDataList.setLayoutManager(new LinearLayoutManager(UserDataActivity.this));

        textViewPageNo.setText(String.valueOf(currentPageNo));
    }

    @Override
    public void onPreClicked(View view) {
        currentPageNo = currentPageNo-1;

        int firstIndex= (currentPageNo) * countPerPage;

        if(firstIndex <= 0){
            firstIndex=0;
            currentPageNo=1;
        }
        int secondIndex= firstIndex + countPerPage;
        if(secondIndex>= userDataModels.size()){
            secondIndex=userDataModels.size();
        }
        userDataAdapter = new UserDataAdapter(userDataModels.subList(firstIndex,secondIndex),this);
        viewUserDataList.setAdapter(userDataAdapter);
        viewUserDataList.setLayoutManager(new LinearLayoutManager(UserDataActivity.this));

        textViewPageNo.setText(String.valueOf(currentPageNo));
    }

    @Override
    public void onNextClicked(View view) {
        currentPageNo=currentPageNo + 1;
        int firstIndex = (currentPageNo) * countPerPage;
        if(currentPageNo>=totalPages){
            currentPageNo=totalPages;
            firstIndex = (currentPageNo) * countPerPage;
        }
        int secondIndex = firstIndex + countPerPage;
        if(secondIndex >= userDataModels.size()){
            secondIndex = userDataModels.size();
        }
        userDataAdapter = new UserDataAdapter(userDataModels.subList(firstIndex,secondIndex),this);
        viewUserDataList.setAdapter(userDataAdapter);
        viewUserDataList.setLayoutManager(new LinearLayoutManager(UserDataActivity.this));

        textViewPageNo.setText(String.valueOf(currentPageNo));
    }

    @Override
    public void onLastClicked(View view) {
        currentPageNo=totalPages;
        int firstIndex = (currentPageNo) * countPerPage;
        int secondIndex = firstIndex + countPerPage;

        if(firstIndex >= userDataModels.size()){
            firstIndex=userDataModels.size()-countPerPage;
            currentPageNo=totalPages;
        }
        if(secondIndex >= userDataModels.size()){
            secondIndex = userDataModels.size();
        }

        userDataAdapter = new UserDataAdapter(userDataModels.subList(firstIndex,secondIndex),this);
        viewUserDataList.setAdapter(userDataAdapter);
        viewUserDataList.setLayoutManager(new LinearLayoutManager(UserDataActivity.this));

        textViewPageNo.setText(String.valueOf(currentPageNo));
    }
    //    End Pagination
}