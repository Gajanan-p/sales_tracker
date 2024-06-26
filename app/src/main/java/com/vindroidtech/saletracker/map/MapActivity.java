package com.vindroidtech.saletracker.map;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.material.textfield.TextInputEditText;
import com.vindroidtech.AppPreference;
import com.vindroidtech.saletracker.MainActivity;
import com.vindroidtech.saletracker.R;
import com.vindroidtech.saletracker.customer.login.LoginDataModel;
import com.vindroidtech.saletracker.customer.master.CustomerMasterDataModel;
import com.vindroidtech.saletracker.customer.master.RetrofitCustomerMasterAPI;
import com.vindroidtech.saletracker.dashboard.DashboardActivity;
import com.vindroidtech.saletracker.registration.RetrofitRegistrationAPI;
import com.vindroidtech.saletracker.registration.userdata.UserDataActivity;
import com.vindroidtech.saletracker.registration.userdata.UserDataAdapter;
import com.vindroidtech.saletracker.registration.userdata.UserDataDeleteListener;
import com.vindroidtech.saletracker.registration.userdata.UserDataModel;
import com.vindroidtech.saletracker.retrofit.RetrofitService;
import com.vindroidtech.saletracker.usertype.UserTypeDataModel;

import java.util.ArrayList;
import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MapActivity extends AppCompatActivity implements OnMapReadyCallback,
        UserDataDeleteListener {
    private GoogleMap mMap;
    ArrayList<UserTrackingDataModel> userTrackingDataModels;
    LoginDataModel loginDataModel;
    TextInputEditText editTextSelectUser;
    TextInputEditText editTextSelectDate;
    AppCompatButton buttonOk;
    private UserDialog userDialog;
    private ArrayList<UserDataModel> userDataModels;
    private UserDataModel userDataModel;
    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        loginDataModel= AppPreference.getLoginDataPreferences(MapActivity.this);
        editTextSelectUser = findViewById(R.id.edit_map_add_user_type);
        editTextSelectDate = findViewById(R.id.edit_map_add_date);
        editTextSelectUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadUserDataFromServer();
            }
        });
        buttonOk = findViewById(R.id.button_map_ok);
        buttonOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getTrackingData();
            }
        });
        editTextSelectDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // Get current date
                final Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);

                // Create a date picker dialog
                DatePickerDialog datePickerDialog = new DatePickerDialog(MapActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                // Handle the selected date
                                String selectedDate = dayOfMonth + "-" + (monthOfYear + 1) + "-" + year;
                                editTextSelectDate.setText(selectedDate);
                            }
                        }, year, month, day);

                // Show the date picker dialog
                datePickerDialog.show();
            }
        });

        SupportMapFragment mapFragment = SupportMapFragment.newInstance();
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.map_view, mapFragment)
                .commit();
        mapFragment.getMapAsync(this);

//        AppPreference.clearLoginDataPreferences(MapActivity.this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
    }

    void addStartAndEndMarkerToMap(ArrayList<UserTrackingDataModel> userTrackingDataModels){
        // Add a marker in Sydney and move the camera
        UserTrackingDataModel startLocation;

        if(mMap!=null){
            PolylineOptions polylineOptions = new PolylineOptions();
            for(int i=0;i<userTrackingDataModels.size();i++){
                startLocation = userTrackingDataModels.get(i);

                if(startLocation.latituede!=null && startLocation.longtitude!=null){

                    if(i==0){
                        addMarker(
                                Double.parseDouble(startLocation.latituede),
                                Double.parseDouble(startLocation.longtitude),
                                "End Location");
                    }
                    if(i==userTrackingDataModels.size()-1){
                        addMarker(
                                Double.parseDouble(startLocation.latituede),
                                Double.parseDouble(startLocation.longtitude),
                                "End Location");
                    }
//                polylineOptions.add(location);
                    polylineOptions.add(new LatLng( Double.parseDouble(startLocation.latituede),
                            Double.parseDouble(startLocation.longtitude)));
                }
            }
            mMap.addPolyline(polylineOptions);
            mMap.setMinZoomPreference(10.0f);
            mMap.setMaxZoomPreference(14.0f);




//            startLocation = userTrackingDataModels.get(userTrackingDataModels.size()-1);
//            if(startLocation.latituede!=null && startLocation.longtitude!=null){
//                addMarker(
//                        Double.parseDouble(startLocation.latituede),
//                        Double.parseDouble(startLocation.longtitude),
//                        "End location");
//                polylineOptions.add(
//                        new LatLng(
//                                Double.parseDouble(startLocation.latituede),
//                                Double.parseDouble(startLocation.longtitude)
//                        )
//                );
//                mMap.addPolyline(polylineOptions);
//            }
        }
    }

    void addMarker(double lat,double longt,String title){
        LatLng sydney = new LatLng(lat, longt);
        mMap.addMarker(new MarkerOptions()
                .position(sydney)
                .title(title));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));

    }
    public  void DrawLine(LatLng location) {

    }

    public void getTrackingData(){
        RetrofitMapAPI retrofitMapAPI = RetrofitService.getRetrofit().create(RetrofitMapAPI.class);
        BodyData bodyData=new BodyData();
        bodyData.setUserid(userDataModel.getUserId());
        bodyData.setFromdate(editTextSelectDate.getText().toString());
        Call<ArrayList<UserTrackingDataModel>> call =retrofitMapAPI.getTrackingUserData(bodyData);
        call.enqueue(new Callback<ArrayList<UserTrackingDataModel>>() {
            @Override
            public void onResponse(Call<ArrayList<UserTrackingDataModel>> call, Response<ArrayList<UserTrackingDataModel>> response) {
                if(response.isSuccessful()){
                    userTrackingDataModels=response.body();
                    addStartAndEndMarkerToMap(userTrackingDataModels);
                }else{
                    Toast.makeText(getApplicationContext(),"Tracking data not available",Toast.LENGTH_LONG).show();
                }

            }

            @Override
            public void onFailure(Call<ArrayList<UserTrackingDataModel>> call, Throwable t) {
                Toast.makeText(getApplicationContext(),t.getMessage(),Toast.LENGTH_LONG).show();
            }
        });
    }

    public void loadUserDataFromServer(){
        RetrofitRegistrationAPI registrationAPI = RetrofitService.getRetrofit().create(RetrofitRegistrationAPI.class);
        Call<ArrayList<UserDataModel>> apiCallUserdata = registrationAPI.fetchUserData();
        apiCallUserdata.enqueue(new Callback<ArrayList<UserDataModel>>() {
            @Override
            public void onResponse(Call<ArrayList<UserDataModel>> call, Response<ArrayList<UserDataModel>> response) {
                if (response.isSuccessful()){
                    userDataModels = response.body();
                    userDialog = new UserDialog(MapActivity.this,
                            userDataModels,
                            MapActivity.this);
                    userDialog.show();
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

    }

    @Override
    public void onUserClicked(UserDataModel userDataModel) {
        this.userDataModel = userDataModel;
        editTextSelectUser.setText(userDataModel.getName());
        userDialog.dismiss();

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
                Intent intent=new Intent(MapActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
                break;
        }
        return true;
    }
}