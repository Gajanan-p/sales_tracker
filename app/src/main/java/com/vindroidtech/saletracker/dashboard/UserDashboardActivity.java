package com.vindroidtech.saletracker.dashboard;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

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
import com.vindroidtech.saletracker.customer.master.CustomerMasterActivity;
import com.vindroidtech.saletracker.location.service.LocationService;
import com.vindroidtech.saletracker.registration.RegistrationActivity;
import com.vindroidtech.saletracker.reports.KMReportActivity;
import com.vindroidtech.saletracker.reports.ReportUserWiseActivity;

public class UserDashboardActivity extends AppCompatActivity implements View.OnClickListener {
    private AppCompatButton buttonCustomerData;
    private AppCompatButton buttonAddCustomer;
    private AppCompatButton buttonTrackReport;
    private AppCompatButton buttonCheckCustomer;
    private AppCompatButton buttonSelf;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_dashboard);
//        buttonAddCustomer=findViewById(R.id.button_user_dashboard_add_customer);
//        buttonAddCustomer.setOnClickListener(this);
        buttonCustomerData=findViewById(R.id.button_user_dashboard_customer_data);
        buttonCustomerData.setOnClickListener(this);
        buttonTrackReport=findViewById(R.id.button_user_dashboard_track_report);
        buttonTrackReport.setOnClickListener(this);
        buttonCheckCustomer = findViewById(R.id.button_user_dashboard_check_customer_visit_history);
        buttonCheckCustomer.setOnClickListener(this);
        buttonAddCustomer=findViewById(R.id.button_add_customer);
        buttonAddCustomer.setOnClickListener(this);

//        buttonSelf = findViewById(R.id.button_user_dashboard_self);
//        buttonSelf.setOnClickListener(this);

//        AppPreference.clearLoginDataPreferences(UserDashboardActivity.this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
//            case R.id.button_user_dashboard_add_customer:{
//                startActivity(new Intent(this, CustomerMasterActivity.class));
//                break;
//            }
            case R.id.button_user_dashboard_customer_data:{
                startActivity(new Intent(this, AddCustomerInformationActivity.class));
                break;
            }
            case R.id.button_user_dashboard_track_report:{
                startActivity(new Intent(this, KMReportActivity.class));
                break;
            }
            case R.id.button_user_dashboard_check_customer_visit_history:{
                startActivity(new Intent(this, ReportUserWiseActivity.class));
                break;
            }
            case R.id.button_add_customer:{
                startActivity(new Intent(this, CustomerMasterActivity.class));
                break;
            }
        }
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
//        Toast.makeText(getApplicationContext(),"Tracking Stopped",Toast.LENGTH_LONG).show();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            stopService(LocationIntent);
        }
        AppPreference.setLoginDataPreferences(this,null);
        Intent intent=new Intent(UserDashboardActivity.this,MainActivity.class);
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