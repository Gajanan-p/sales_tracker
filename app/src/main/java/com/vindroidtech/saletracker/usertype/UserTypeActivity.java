package com.vindroidtech.saletracker.usertype;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.vindroidtech.saletracker.R;

import com.vindroidtech.saletracker.map.BodyData;
import com.vindroidtech.saletracker.registration.RegistrationActivity;
import com.vindroidtech.saletracker.registration.RegistrationUserTypeDialog;
import com.vindroidtech.saletracker.reports.OnPagination;
import com.vindroidtech.saletracker.retrofit.RetrofitService;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserTypeActivity extends AppCompatActivity implements View.OnClickListener,
             DeleteUserTypeListener, OnPagination {

    TextInputEditText editAddUserType;
    AppCompatButton buttonSaveUserType;
    private RecyclerView recyclerViewUserTypeList;
    private DeleteUserTypeAdapter deleteUserTypeAdapter;
    private ArrayList<UserTypeModel> userTypeModels;


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
        setContentView(R.layout.activity_user_type);
        editAddUserType=findViewById(R.id.edit_add_user_type);
        buttonSaveUserType= findViewById(R.id.button_save_user_type);
        buttonSaveUserType.setOnClickListener(this);
        recyclerViewUserTypeList = findViewById(R.id.view_user_type_list);

        //    start Pagination
        buttonFirst=findViewById(R.id.user_type_buttonFirst);
        buttonPre=findViewById(R.id.user_type_buttonPre);
        buttonNext=findViewById(R.id.user_type_buttonNext);
        buttonLast=findViewById(R.id.user_type_buttonLast);
        textViewPageNo=findViewById(R.id.user_type_textPageNo);
        buttonFirst.setOnClickListener(this::onFirstClicked);
        buttonPre.setOnClickListener(this::onPreClicked);
        buttonNext.setOnClickListener(this::onNextClicked);
        buttonLast.setOnClickListener(this::onLastClicked);
        //    end Pagination

    }

    @Override
    protected void onResume() {
        super.onResume();
        getUserTypeDataToServer();
    }

    @Override
    public void onClick(View view) {
        saveDatToServer();
    }

    void saveDatToServer(){

        UserTypeDataModel userTypeDataModel=new UserTypeDataModel();
        userTypeDataModel.setUsertype_name(editAddUserType.getText().toString());
        if (isNetworkAvailable()) {
            RetrofitUserTypeAPI retrofitAddCustomerInformationAPI = RetrofitService.getRetrofit().create(RetrofitUserTypeAPI.class);
            Call<UserTypeDataModel> regCall = retrofitAddCustomerInformationAPI.userTypePost(userTypeDataModel);
            regCall.enqueue(new Callback<UserTypeDataModel>() {
                @Override
                public void onResponse(Call<UserTypeDataModel> call, Response<UserTypeDataModel> response) {
                    if (response.isSuccessful()) {
                        Toast.makeText(getApplicationContext(), response.body().getMsg(), Toast.LENGTH_LONG).show();
                        editAddUserType.setText(null);
                    }
                }

                @Override
                public void onFailure(Call<UserTypeDataModel> call, Throwable t) {
                    Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_LONG).show();
                }
            });
        }else {
            Toast.makeText(UserTypeActivity.this, "Network not available !", Toast.LENGTH_SHORT).show();
        }
    }
    public void getUserTypeDataToServer(){
        if (isNetworkAvailable()) {
            RetrofitUserTypeAPI userTypeAPI = RetrofitService.getRetrofit().create(RetrofitUserTypeAPI.class);
            Call<ArrayList<UserTypeModel>> apiCallUserType = userTypeAPI.userTypeData();
            apiCallUserType.enqueue(new Callback<ArrayList<UserTypeModel>>() {
                @Override
                public void onResponse(Call<ArrayList<UserTypeModel>> call, Response<ArrayList<UserTypeModel>> response) {
                    if (response.isSuccessful()) {
                        userTypeModels = response.body();
                        deleteUserTypeAdapter = new DeleteUserTypeAdapter(userTypeModels, UserTypeActivity.this);
                        recyclerViewUserTypeList.setAdapter(deleteUserTypeAdapter);
                        recyclerViewUserTypeList.setLayoutManager(new LinearLayoutManager(UserTypeActivity.this));
                    }

                }

                @Override
                public void onFailure(Call<ArrayList<UserTypeModel>> call, Throwable t) {
                    Log.e("Error", t.getMessage());
                }
            });
        }else {
            Toast.makeText(UserTypeActivity.this, "Network not available", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onUserTypeDelete(UserTypeModel userTypeModel) {
        RequestUserTypeBody data = new RequestUserTypeBody();
        data.setUsertypeid(Integer.parseInt(userTypeModel.getUsertypeId()));
        RetrofitUserTypeAPI retrofitUserTypeAPI= RetrofitService.getRetrofit().create(RetrofitUserTypeAPI.class);
        Call<DeleteUserTypeModel> deleteCallUserType = retrofitUserTypeAPI.deleteUserTypeData(data);
        deleteCallUserType.enqueue(new Callback<DeleteUserTypeModel>() {
            @Override
            public void onResponse(Call<DeleteUserTypeModel> call, Response<DeleteUserTypeModel> response) {
                if (response.isSuccessful()){
                    Toast.makeText(UserTypeActivity.this,response.body().getMsg(),Toast.LENGTH_LONG).show();
                    deleteUserTypeAdapter.notifyDataSetChanged();
                    getUserTypeDataToServer();
                }
            }

            @Override
            public void onFailure(Call<DeleteUserTypeModel> call, Throwable t) {
                Log.e("Error",t.getMessage());
            }
        });
    }

    //    start Pagination
    @Override
    public void onFirstClicked(View view) {
        currentPageNo=1;
        int firstIndex=(currentPageNo-1);
        int secondIndex=firstIndex + countPerPage;
        if(secondIndex>= userTypeModels.size()){
            secondIndex=userTypeModels.size();
        }
        deleteUserTypeAdapter = new DeleteUserTypeAdapter(userTypeModels.subList(firstIndex,secondIndex),UserTypeActivity.this);
        recyclerViewUserTypeList.setAdapter(deleteUserTypeAdapter);
        deleteUserTypeAdapter.notifyDataSetChanged();
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
        if(secondIndex>= userTypeModels.size()){
            secondIndex=userTypeModels.size();
        }
        deleteUserTypeAdapter = new DeleteUserTypeAdapter(userTypeModels.subList(firstIndex,secondIndex),UserTypeActivity.this);
        recyclerViewUserTypeList.setAdapter(deleteUserTypeAdapter);
        deleteUserTypeAdapter.notifyDataSetChanged();
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
        if(secondIndex >= userTypeModels.size()){
            secondIndex = userTypeModels.size();
        }
        deleteUserTypeAdapter = new DeleteUserTypeAdapter(userTypeModels.subList(firstIndex,secondIndex),UserTypeActivity.this);
        recyclerViewUserTypeList.setAdapter(deleteUserTypeAdapter);
        deleteUserTypeAdapter.notifyDataSetChanged();
        textViewPageNo.setText(String.valueOf(currentPageNo));
    }

    @Override
    public void onLastClicked(View view) {
        currentPageNo=totalPages;
        int firstIndex = (currentPageNo) * countPerPage;
        int secondIndex = firstIndex + countPerPage;

        if(firstIndex >= userTypeModels.size()){
            firstIndex=userTypeModels.size()-countPerPage;
            currentPageNo=totalPages;
        }
        if(secondIndex >= userTypeModels.size()){
            secondIndex = userTypeModels.size();
        }

        deleteUserTypeAdapter = new DeleteUserTypeAdapter(userTypeModels.subList(firstIndex,secondIndex),UserTypeActivity.this);
        recyclerViewUserTypeList.setAdapter(deleteUserTypeAdapter);
        deleteUserTypeAdapter.notifyDataSetChanged();
        textViewPageNo.setText(String.valueOf(currentPageNo));
    }
    //    End Pagination

//

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager != null ? connectivityManager.getActiveNetworkInfo() : null;
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}