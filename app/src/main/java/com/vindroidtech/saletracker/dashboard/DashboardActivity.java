package com.vindroidtech.saletracker.dashboard;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.LinearLayoutCompat;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.vindroidtech.AppPreference;
import com.vindroidtech.saletracker.MainActivity;
import com.vindroidtech.saletracker.R;
import com.vindroidtech.saletracker.customer.addinfo.AddCustomerInformationActivity;
import com.vindroidtech.saletracker.customer.login.LoginDataModel;
import com.vindroidtech.saletracker.customer.master.CustomerMasterActivity;
import com.vindroidtech.saletracker.location.service.LocationService;
import com.vindroidtech.saletracker.map.MapActivity;
import com.vindroidtech.saletracker.registration.RegistrationActivity;
import com.vindroidtech.saletracker.registration.userdata.UserDataActivity;
import com.vindroidtech.saletracker.reports.KMReportActivity;
import com.vindroidtech.saletracker.reports.ReportUserWiseActivity;
import com.vindroidtech.saletracker.usertype.UserTypeActivity;

public class DashboardActivity extends AppCompatActivity implements View.OnClickListener {
    AppCompatButton buttonAddUser;
    AppCompatButton buttonAddUserType;
    AppCompatButton buttonAddCustomer;
    AppCompatButton buttonAddCustomerInformation;

    AppCompatButton buttonMapTrack;
    AppCompatButton buttonDeleteUser;
    AppCompatButton buttonReportUserWise;
    AppCompatButton buttonKmReport;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        buttonAddUser=findViewById(R.id.button_add_user);
        buttonAddUser.setOnClickListener(this);

        buttonAddUserType=findViewById(R.id.button_add_user_type);
        buttonAddUserType.setOnClickListener(this);

        buttonAddCustomer=findViewById(R.id.button_add_customer);
        buttonAddCustomer.setOnClickListener(this);

        buttonAddCustomerInformation=findViewById(R.id.button_add_customer_information);
        buttonAddCustomerInformation.setOnClickListener(this);

        buttonMapTrack=findViewById(R.id.button_map_track);
        buttonMapTrack.setOnClickListener(this);
        buttonDeleteUser = findViewById(R.id.button_dashboard_delete_user);
        buttonDeleteUser.setOnClickListener(this);
        buttonReportUserWise = findViewById(R.id.button_dashboard_report_user_wise);
        buttonReportUserWise.setOnClickListener(this);
        buttonKmReport = findViewById(R.id.button_dashboard_km_report);
        buttonKmReport.setOnClickListener(this);

    }

    @Override
    protected void onResume() {
        super.onResume();
        Intent intent = new Intent(getApplicationContext(), LocationService.class);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            Toast.makeText(getApplicationContext(),"Tracking Started",Toast.LENGTH_LONG).show();
            startForegroundService(intent);
        }else{
            Toast.makeText(getApplicationContext(),"Device Not Supported For Tracking",Toast.LENGTH_LONG).show();
        }

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.button_add_user:{
                startActivity(new Intent(this, RegistrationActivity.class));
                break;
            }
            case R.id.button_add_customer:{
                startActivity(new Intent(this, CustomerMasterActivity.class));
                break;
            }
            case R.id.button_add_customer_information:{
                startActivity(new Intent(this, AddCustomerInformationActivity.class));
                break;
            }
            case R.id.button_add_user_type:{
                startActivity(new Intent(this, UserTypeActivity.class));
                break;
            }
            case R.id.button_map_track:{
                startActivity(new Intent(this, MapActivity.class));
                break;
            }
            case R.id.button_dashboard_delete_user:{
                startActivity(new Intent(this, UserDataActivity.class));
                break;
            }
            case R.id.button_dashboard_report_user_wise:{
                startActivity(new Intent(this, ReportUserWiseActivity.class));
                break;
            }
            case R.id.button_dashboard_km_report:{
                startActivity(new Intent(this, KMReportActivity.class));
                break;
            }

        }
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
                showAppExitDialog();
                break;
        }
        return true;
    }
void logout(){
    Intent LocationIntent = new Intent(getApplicationContext(), LocationService.class);
//    Toast.makeText(getApplicationContext(),"Tracking Stopped",Toast.LENGTH_LONG).show();
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        stopService(LocationIntent);
    }
    AppPreference.setLoginDataPreferences(this,null);
    Intent intent=new Intent(DashboardActivity.this,MainActivity.class);
    startActivity(intent);
    finish();
}
    void showAppExitDialog(){
        new AlertDialog.Builder(this).setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("Logout").setMessage("Are you sure you want to logout ?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        logout();
                    }
                }).setNegativeButton("No", null).show();
    }

    @Override
    public void onBackPressed() {
        showAppExitDialog();
    }
}