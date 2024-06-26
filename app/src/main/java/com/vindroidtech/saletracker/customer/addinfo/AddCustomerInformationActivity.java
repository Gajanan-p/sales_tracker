package com.vindroidtech.saletracker.customer.addinfo;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.os.Looper;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.app.ActivityCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.Priority;
import com.google.android.material.textfield.TextInputEditText;
import com.vindroidtech.AppPreference;
import com.vindroidtech.saletracker.MainActivity;
import com.vindroidtech.saletracker.R;
import com.vindroidtech.saletracker.customer.login.LoginDataModel;
import com.vindroidtech.saletracker.location.service.LocationService;
import com.vindroidtech.saletracker.retrofit.RetrofitService;
import com.vindroidtech.saletracker.utils.DateConstant;

import java.util.ArrayList;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddCustomerInformationActivity extends AppCompatActivity implements View.OnClickListener,AddCustomerInformationListener {

    TextInputEditText   editAddCustomerId;
    TextInputEditText editAddCustomerBrand;
    TextInputEditText editAddCustomerRemark;
    TextInputEditText editAddCustomerCounterApprox;
    TextInputEditText editAddCustomerResponse;
    AppCompatButton buttonSaveAddCustomerData;
    private AddCustomerInformationDialog customerInformationDialog;
    private ArrayList<GetCustomerInfoModel> customerInfoModels;
    private GetCustomerInfoModel customerModelInfo;

    private String lat=null;
    private String longi=null;

    private static final long UPDATE_INTERVAL_IN_MILLISECONDS = 1000;
    private FusedLocationProviderClient fusedLocationClient;
    private LocationRequest locationRequest;
    private LocationSettingsRequest locationSettingsRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_customer_information);

        editAddCustomerId=findViewById(R.id.edit_add_customer_id);
        editAddCustomerId.setOnClickListener(this);
        editAddCustomerBrand=findViewById(R.id.edit_add_customer_brand);
        editAddCustomerRemark=findViewById(R.id.edit_add_customer_remark);
        editAddCustomerCounterApprox=findViewById(R.id.edit_add_customer_counter_approx);
        editAddCustomerResponse=findViewById(R.id.edit_add_customer_response);
        buttonSaveAddCustomerData=findViewById(R.id.button_save_add_customer_data);
        buttonSaveAddCustomerData.setOnClickListener(this);

        Log.d("Location", " 1");
        locationRequest = new LocationRequest.Builder(Priority.PRIORITY_HIGH_ACCURACY, UPDATE_INTERVAL_IN_MILLISECONDS)
                .setWaitForAccurateLocation(false)
                .setMinUpdateIntervalMillis(UPDATE_INTERVAL_IN_MILLISECONDS)
                .setMaxUpdateDelayMillis(UPDATE_INTERVAL_IN_MILLISECONDS)
                .build();

        fusedLocationClient =
                LocationServices.getFusedLocationProviderClient(this);
    }
    private void startLocationUpdates() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Log.d("Location", "Location Location Not Available 2");
            return;
        }
        Log.d("Locations", "Service Started");

        fusedLocationClient.requestLocationUpdates(locationRequest,
                locationCallback,
                Looper.getMainLooper());
    }

    private void stopLocationUpdates() {
        fusedLocationClient.removeLocationUpdates(locationCallback);
    }

    @Override
    protected void onResume() {
        super.onResume();
        startLocationUpdates();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.edit_add_customer_id: {
                getCustomerDataFromServer();
                break;
            }
            case R.id.button_save_add_customer_data:
                final LocationManager manager = (LocationManager) getSystemService( Context.LOCATION_SERVICE );

                if ( !manager.isProviderEnabled(LocationManager.GPS_PROVIDER ) ) {
                    buildAlertMessageNoGps();

                }
                    validationToSave();
                break;
        }

    }

    private void getCustomerDataFromServer() {
        RetrofitAddCustomerInformationAPI retrofitAddCustomerInformationAPI= RetrofitService.getRetrofit().create(RetrofitAddCustomerInformationAPI.class);
        Call<ArrayList<GetCustomerInfoModel>> callCustInfo = retrofitAddCustomerInformationAPI.fetchCustomerData();
        callCustInfo.enqueue(new Callback<ArrayList<GetCustomerInfoModel>>() {
            @Override
            public void onResponse(Call<ArrayList<GetCustomerInfoModel>> call, Response<ArrayList<GetCustomerInfoModel>> response) {
                if (response.isSuccessful()) {
                    customerInfoModels = response.body();
                    customerInformationDialog = new AddCustomerInformationDialog(AddCustomerInformationActivity.this,
                        customerInfoModels,
                            AddCustomerInformationActivity.this);
                    customerInformationDialog.show();
                }
            }

            @Override
            public void onFailure(Call<ArrayList<GetCustomerInfoModel>> call, Throwable t) {
                Log.e("Error",t.getMessage());
            }
        });

    }
    //Location Callback
    private LocationCallback locationCallback = new LocationCallback() {
        @Override
        public void onLocationResult(LocationResult locationResult) {
            super.onLocationResult(locationResult);
            Location currentLocation = locationResult.getLastLocation();
            lat=String.valueOf(currentLocation.getLatitude());
            longi=String.valueOf(currentLocation.getLongitude());
            Log.d("Locations", currentLocation.getLatitude() + "," + currentLocation.getLongitude());
            //Share/Publish Location
        }
    };

    void saveDatToServer(){
        LoginDataModel loginDataModel= AppPreference.getLoginDataPreferences(getApplicationContext());
        AddCustomerInformationDataModel addCustomerMasterDataModel=new AddCustomerInformationDataModel();
        addCustomerMasterDataModel.setCustid(customerModelInfo.getUser_Id());
        addCustomerMasterDataModel.setBrand(editAddCustomerBrand.getText().toString());
        addCustomerMasterDataModel.setRemark(editAddCustomerRemark.getText().toString());
        addCustomerMasterDataModel.setCounter_Approx(editAddCustomerCounterApprox.getText().toString());
        addCustomerMasterDataModel.setResponse(editAddCustomerResponse.getText().toString());
        addCustomerMasterDataModel.setLatituede(lat);
        addCustomerMasterDataModel.setLongtitude(longi);
        addCustomerMasterDataModel.setCreateBy(Double.parseDouble(loginDataModel.getUser_id()));
        addCustomerMasterDataModel.setCreated_dt(DateConstant.simpleDateFormat.format(new Date()));

        if (isNetworkAvailable()) {
            RetrofitAddCustomerInformationAPI retrofitAddCustomerInformationAPI = RetrofitService.getRetrofit().create(RetrofitAddCustomerInformationAPI.class);
            Call<AddCustomerInformationDataModel> regCall = retrofitAddCustomerInformationAPI.addCustomerPost(addCustomerMasterDataModel);
            regCall.enqueue(new Callback<AddCustomerInformationDataModel>() {
                @Override
                public void onResponse(Call<AddCustomerInformationDataModel> call, Response<AddCustomerInformationDataModel> response) {
                    if (response.isSuccessful()) {
                        Toast.makeText(getApplicationContext(), response.body().getMsg(), Toast.LENGTH_LONG).show();
                        clearUIComponents();
                    }
                }

                @Override
                public void onFailure(Call<AddCustomerInformationDataModel> call, Throwable t) {
                    Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_LONG).show();
                }
            });
        }else {
            Toast.makeText(AddCustomerInformationActivity.this, "Network not available !", Toast.LENGTH_SHORT).show();
        }
    }

//
    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager != null ? connectivityManager.getActiveNetworkInfo() : null;
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    @Override
    public void onCustomerIdClicked(GetCustomerInfoModel customerMasterDataModel, int position) {
        this.customerModelInfo = customerMasterDataModel;
        editAddCustomerId.setText(customerMasterDataModel.getFname());
        customerInformationDialog.dismiss();
    }

    void clearUIComponents(){
        editAddCustomerId.setText(null);
        editAddCustomerBrand.setText(null);
        editAddCustomerRemark.setText(null);
        editAddCustomerCounterApprox.setText(null);
        editAddCustomerResponse.setText(null);
    }
    public void validationToSave(){
        if (!TextUtils.isEmpty(editAddCustomerId.getText().toString())
                && !TextUtils.isEmpty(editAddCustomerResponse.getText().toString())
                && !TextUtils.isEmpty(editAddCustomerRemark.getText().toString())
                && !TextUtils.isEmpty(editAddCustomerBrand.getText().toString())
                && !TextUtils.isEmpty(editAddCustomerCounterApprox.getText().toString())){
            saveDatToServer();
        }else {
            Toast.makeText(getApplicationContext(),"Please enter all fields !",Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopLocationUpdates();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    private void buildAlertMessageNoGps() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Your GPS seems to be disabled, do you want to enable it?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(@SuppressWarnings("unused") final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
                        startActivity(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
                        dialog.cancel();
                        logout();
                    }
                });
        final AlertDialog alert = builder.create();
        alert.show();
    }
    void logout(){
        Intent LocationIntent = new Intent(getApplicationContext(), LocationService.class);
//    Toast.makeText(getApplicationContext(),"Tracking Stopped",Toast.LENGTH_LONG).show();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            stopService(LocationIntent);
        }
        AppPreference.setLoginDataPreferences(this,null);
        Intent intent=new Intent(AddCustomerInformationActivity.this,MainActivity.class);
        startActivity(intent);
        finish();
    }
}