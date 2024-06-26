package com.vindroidtech.saletracker;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Settings;
import android.view.View;
import android.widget.Toast;
import com.google.android.material.textfield.TextInputEditText;
import com.itextpdf.io.image.ImageData;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.io.source.ByteArrayOutputStream;
import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.property.HorizontalAlignment;
import com.vindroidtech.AppPreference;
import com.vindroidtech.saletracker.customer.login.LoginDataModel;
import com.vindroidtech.saletracker.customer.login.RetrofitLoginAPI;
import com.vindroidtech.saletracker.dashboard.DashboardActivity;
import com.vindroidtech.saletracker.dashboard.ManagerDashboardActivity;
import com.vindroidtech.saletracker.dashboard.UserDashboardActivity;
import com.vindroidtech.saletracker.location.service.LocationService;
import com.vindroidtech.saletracker.retrofit.RetrofitService;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfPage;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.kernel.geom.PageSize;
public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    TextInputEditText editUserName;
    TextInputEditText editUserPassword;
    AppCompatButton buttonLoginOK;
    AppCompatButton buttonForgotPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editUserName=findViewById(R.id.editUserName);
        editUserPassword=findViewById(R.id.editUserPassword);
        buttonLoginOK=findViewById(R.id.buttonLoginOk);
        buttonLoginOK.setOnClickListener(this);
        buttonForgotPassword=findViewById(R.id.buttonForgotPassword);
        buttonForgotPassword.setOnClickListener(this);

    }

    @Override
    protected void onResume() {
        super.onResume();
        final LocationManager manager = (LocationManager) getSystemService( Context.LOCATION_SERVICE );

        if ( !manager.isProviderEnabled(LocationManager.GPS_PROVIDER ) ) {
            buildAlertMessageNoGps();
        }else{
            LoginDataModel loginDataModel=AppPreference.getLoginDataPreferences(MainActivity.this);
            if(loginDataModel != null){
                if(loginDataModel.getUsertypeid() != null){
                    openActivity(loginDataModel);
                }
            }
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.buttonLoginOk:{
                if(editUserName.getText().toString().matches("") || editUserPassword.getText().toString().matches("")){
                    Toast.makeText(MainActivity.this,"Enter User Name And Password",Toast.LENGTH_LONG).show();
                }else{
                    checkRunTimePermission();
                };
                break;}
            case R.id.buttonForgotPassword:{
                // do logic for update password
                break;
            }
        }
    }

    void saveDatToServer(){
        LoginDataModel loginDataModel=new LoginDataModel();
        loginDataModel.setLogin_Name(editUserName.getText().toString());
        loginDataModel.setUser_Pwd(editUserPassword.getText().toString());

        if (isNetworkAvailable()) {
            RetrofitLoginAPI retrofitCustomerMasterAPI = RetrofitService.getRetrofit().create(RetrofitLoginAPI.class);
            Call<ArrayList<LoginDataModel>> regCall = retrofitCustomerMasterAPI.loginPost(loginDataModel);
            regCall.enqueue(new Callback<ArrayList<LoginDataModel>>() {
                @Override
                public void onResponse(Call<ArrayList<LoginDataModel>> call, Response<ArrayList<LoginDataModel>> response) {
                    if (response.isSuccessful()) {
                        LoginDataModel loginDataModel1 = response.body().get(0);

                        AppPreference.setLoginDataPreferences(MainActivity.this, loginDataModel1);
                        if (loginDataModel1.getLogin_Name() == null) {
                            Toast.makeText(getApplicationContext(), loginDataModel1.getMsg(), Toast.LENGTH_LONG).show();
                        } else {
                            AppPreference.setLoginDataPreferences(getApplicationContext(), loginDataModel1);
                            Toast.makeText(getApplicationContext(), loginDataModel1.getMsg(), Toast.LENGTH_LONG).show();
                            openActivity(loginDataModel1);
                        }
                    }
                }

                @Override
                public void onFailure(Call<ArrayList<LoginDataModel>> call, Throwable t) {
                    Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_LONG).show();
                }
            });
        }else {
            Toast.makeText(MainActivity.this, "Network not available !", Toast.LENGTH_SHORT).show();
        }
    }

    void openActivity(LoginDataModel loginDataModel){
//    startActivity(new Intent(MainActivity.this, UserDashboardActivity.class));
        if(loginDataModel.getUsertypeid().matches("1") || loginDataModel.getUsertypeid().matches("2")){
            startActivity(new Intent(MainActivity.this, DashboardActivity.class));
            finish();
        }
        if(loginDataModel.getUsertypeid().matches("3")){
            startActivity(new Intent(MainActivity.this, ManagerDashboardActivity.class));
            finish();
        }
        if(loginDataModel.getUsertypeid().matches("4")){
            startActivity(new Intent(MainActivity.this, UserDashboardActivity.class));
            finish();
        }
    }

    public void checkRunTimePermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED ||
                    ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED||
                    ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_BACKGROUND_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                final LocationManager manager = (LocationManager) getSystemService( Context.LOCATION_SERVICE );

                if ( !manager.isProviderEnabled(LocationManager.GPS_PROVIDER ) ) {
                    buildAlertMessageNoGps();
                }
                saveDatToServer();

            } else {
                requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION},
                        10);
            }
        } else {
            final LocationManager manager = (LocationManager) getSystemService( Context.LOCATION_SERVICE );

            if ( !manager.isProviderEnabled(LocationManager.GPS_PROVIDER ) ) {
                buildAlertMessageNoGps();
            }
            saveDatToServer(); //GPSTracker is class that is used for retrieve user current location
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 10) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                saveDatToServer();
            } else {
                if (!ActivityCompat.shouldShowRequestPermissionRationale((Activity) this, Manifest.permission.ACCESS_FINE_LOCATION)) {
                    // If User Checked 'Don't Show Again' checkbox for runtime permission, then navigate user to Settings
                    AlertDialog.Builder dialog = new AlertDialog.Builder(this);
                    dialog.setTitle("Permission Required");
                    dialog.setCancelable(false);
                    dialog.setMessage("You have to Allow permission to access user location");
                    dialog.setPositiveButton("Settings", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent i = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS, Uri.fromParts("package",
                                    getApplicationContext().getPackageName(), null));
                            //i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivityForResult(i, 1001);
                        }
                    });
                    AlertDialog alertDialog = dialog.create();
                    alertDialog.show();
                }
                //code for deny
            }

        }
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
        Intent intent=new Intent(MainActivity.this,MainActivity.class);
        startActivity(intent);
        finish();
    }
//  network not

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager != null ? connectivityManager.getActiveNetworkInfo() : null;
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
    
}