package com.vindroidtech.saletracker.registration;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.vindroidtech.AppPreference;
import com.vindroidtech.saletracker.R;
import com.vindroidtech.saletracker.customer.login.LoginDataModel;
import com.vindroidtech.saletracker.managerRights.ManagerRightsModel;
import com.vindroidtech.saletracker.managerRights.RequestManagerRightsData;
import com.vindroidtech.saletracker.managerRights.RetrofitManagerRightAPI;
import com.vindroidtech.saletracker.retrofit.RetrofitService;
import com.vindroidtech.saletracker.usertype.RetrofitUserTypeAPI;
import com.vindroidtech.saletracker.usertype.UserTypeModel;
import com.vindroidtech.saletracker.utils.DateConstant;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegistrationActivity extends AppCompatActivity implements View.OnClickListener,
        RegistrationUserTypeListener, ManagerListener {

    TextInputEditText editFirstName;
    TextInputEditText editMiddleName;
    TextInputEditText editLastName;
    TextInputEditText editMobileNumber;
    TextInputEditText editEmailId;
    TextInputEditText editDob;
    TextInputEditText editDoj;
    TextInputEditText editUserType;
    TextInputEditText editManagerTypeId;
    TextInputEditText editUserPassword;
    AppCompatButton buttonSaveRegistration;
    private RegistrationUserTypeDialog userTypeDialog;
    private UserTypeModel userTypeModel;
    ManagerListDialog managerListDialog;
    ManagerRightsModel managerRightsModel;
    Calendar calendar1,calendar;
    SimpleDateFormat simpleDateFormat;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        editFirstName=findViewById(R.id.edit_first_name);
        editMiddleName=findViewById(R.id.edit_middle_name);
        editLastName=findViewById(R.id.edit_last_name);
        editMobileNumber=findViewById(R.id.edit_mobile_number);
        editEmailId=findViewById(R.id.edit_email_id);
        editDob=findViewById(R.id.edit_dob);
        editDoj=findViewById(R.id.edit_doj);
        editUserType = findViewById(R.id.edit_user_type);
        editUserPassword=findViewById(R.id.edit_user_password);
        buttonSaveRegistration=findViewById(R.id.button_save_registration);
        editManagerTypeId = findViewById(R.id.edit_manager_right_id);
        buttonSaveRegistration.setOnClickListener(this);
        editUserType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getUserTypeDataToServer();
            }
        });
        editManagerTypeId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getManagerRightsDataFromServer();
            }
        });
        calendar = Calendar.getInstance();
        simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd");
        editDob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getTodayFromDateWise(editDob);
            }
        });
        editDoj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getTodayFromDateWise(editDoj);
            }
        });
    }

    @Override
    public void onClick(View view) {
        ValidationToSave();
    }
    public void ValidationToSave(){
        if (!TextUtils.isEmpty(editFirstName.getText().toString())
                && !TextUtils.isEmpty(editMiddleName.getText().toString())
                && !TextUtils.isEmpty(editLastName.getText().toString())
                && !TextUtils.isEmpty(editMobileNumber.getText().toString())
                && !TextUtils.isEmpty(editDob.getText().toString())
                && !TextUtils.isEmpty(editDoj.getText().toString())
                && !TextUtils.isEmpty(editUserType.getText().toString())
                && !TextUtils.isEmpty(editUserPassword.getText().toString())){
            getDataFromUI();
        }else {
            Toast.makeText(RegistrationActivity.this,"Please enter all fields !",Toast.LENGTH_LONG).show();
        }
    }

    void getDataFromUI(){
        LoginDataModel loginDataModel= AppPreference.getLoginDataPreferences(getApplicationContext());
        RegistrationDataModel registrationDataModel=new RegistrationDataModel();
        registrationDataModel.setFname(editFirstName.getText().toString());
        registrationDataModel.setMname(editMiddleName.getText().toString());
        registrationDataModel.setLname(editLastName.getText().toString());
        registrationDataModel.setMbNo(editMobileNumber.getText().toString());
        registrationDataModel.setEmailid(editEmailId.getText().toString());
        registrationDataModel.setDOj(editDoj.getText().toString());
        registrationDataModel.setDOB(editDob.getText().toString());
        registrationDataModel.setUserTypeId(userTypeModel.getUsertypeId());
        registrationDataModel.setCreated_dt(DateConstant.simpleDateFormat.format(new Date()));
        if(userTypeModel.getUsertypeId().equals("4")){
            registrationDataModel.setManagerId(managerRightsModel.getUserId());
        }else{
            registrationDataModel.setManagerId("1");
        }
        registrationDataModel.setPassword(editUserPassword.getText().toString());
        registrationDataModel.setCreateBy(loginDataModel.getUser_id());
        RetrofitRegistrationAPI registrationAPI=RetrofitService.getRetrofit().create(RetrofitRegistrationAPI.class);
        Call<RegistrationDataModel> regCall =registrationAPI.userRegistrationPost(registrationDataModel);
        regCall.enqueue(new Callback<RegistrationDataModel>() {
            @Override
            public void onResponse(Call<RegistrationDataModel> call, Response<RegistrationDataModel> response) {
                if(response.isSuccessful()){
                    Toast.makeText(getApplicationContext(),response.body().getMsg(),Toast.LENGTH_LONG).show();
                    clearFormData();
                }
            }

            @Override
            public void onFailure(Call<RegistrationDataModel> call, Throwable t) {
                Toast.makeText(getApplicationContext(),t.getMessage(),Toast.LENGTH_LONG).show();
            }
        });
    }
    void clearFormData(){
        editFirstName.setText(null);
        editMiddleName.setText(null);
        editLastName.setText(null);
        editMobileNumber.setText(null);
        editEmailId.setText(null);
        editDob.setText(null);
        editDoj.setText(null);
        editUserType.setText(null);
        editUserPassword.setText(null);
        editManagerTypeId.setText(null);
    }
    //--
    private void getTodayFromDateWise(EditText editText){
        Calendar calendar = Calendar.getInstance(Locale.getDefault());
        DatePickerDialog datePickerDialog = new DatePickerDialog(RegistrationActivity.this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        //todo
                        Calendar newDate = Calendar.getInstance();
                        newDate.set(year, month, dayOfMonth);
                        editText.setText(simpleDateFormat.format(newDate.getTime()));
                    }
                },calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();
    }

    public void getUserTypeDataToServer(){
        RetrofitUserTypeAPI userTypeAPI =RetrofitService.getRetrofit().create(RetrofitUserTypeAPI.class);
        Call<ArrayList<UserTypeModel>> apiCallUserType = userTypeAPI.userTypeData();
        apiCallUserType.enqueue(new Callback<ArrayList<UserTypeModel>>() {
            @Override
            public void onResponse(Call<ArrayList<UserTypeModel>> call, Response<ArrayList<UserTypeModel>> response) {
                userTypeDialog = new RegistrationUserTypeDialog(RegistrationActivity.this,
                        response.body(),
                        RegistrationActivity.this);
                userTypeDialog.show();

            }

            @Override
            public void onFailure(Call<ArrayList<UserTypeModel>> call, Throwable t) {
                Log.e("Error",t.getMessage());
            }
        });
    }

    @Override
    public void onUserTypeClicked(UserTypeModel userTypeModel) {
        this. userTypeModel = userTypeModel;
        if(this.userTypeModel.getUsertypeId().equals("4")){
            editManagerTypeId.setVisibility(View.VISIBLE);
        }else{
            editManagerTypeId.setVisibility(View.GONE);
        }
        editUserType.setText(userTypeModel.getUsertypeName());
        userTypeDialog.dismiss();
//        getManagerRightsDataFromServer();
    }
    public void getManagerRightsDataFromServer(){
        RetrofitManagerRightAPI managerRightAPI =RetrofitService.getRetrofit().create(RetrofitManagerRightAPI.class);
        Call<ArrayList<ManagerRightsModel>> callApiManagerRights= managerRightAPI.getManagerRightsData();
        callApiManagerRights.enqueue(new Callback<ArrayList<ManagerRightsModel>>() {
            @Override
            public void onResponse(Call<ArrayList<ManagerRightsModel>> call, Response<ArrayList<ManagerRightsModel>> response) {
                if (response.isSuccessful()){
                    managerListDialog = new ManagerListDialog(RegistrationActivity.this,
                            response.body(),RegistrationActivity.this);
                    managerListDialog.show();
                }
            }

            @Override
            public void onFailure(Call<ArrayList<ManagerRightsModel>> call, Throwable t) {
                Log.e("Error",t.getMessage());
            }
        });
    }

    @Override
    public void onMangerClicked(ManagerRightsModel managerRightsModel) {
        this.managerRightsModel=managerRightsModel;
        editManagerTypeId.setText(managerRightsModel.getFname());
        managerListDialog.dismiss();
    }

}