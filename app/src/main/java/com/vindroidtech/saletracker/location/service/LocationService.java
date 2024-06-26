package com.vindroidtech.saletracker.location.service;

import android.Manifest;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.IBinder;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.Priority;
import com.vindroidtech.AppPreference;
import com.vindroidtech.saletracker.MainActivity;
import com.vindroidtech.saletracker.R;
import com.vindroidtech.saletracker.customer.login.LoginDataModel;
;
import com.vindroidtech.saletracker.dashboard.UserDashboardActivity;
import com.vindroidtech.saletracker.retrofit.RetrofitService;
import com.vindroidtech.saletracker.utils.DateConstant;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LocationService extends Service {
    int startMode;       // indicates how to behave if the service is killed
    IBinder binder;      // interface for clients that bind
    boolean allowRebind; // indicates whether onRebind should be used
    private static final long UPDATE_INTERVAL_IN_MILLISECONDS = 1000;
    private FusedLocationProviderClient fusedLocationClient;
    private LocationRequest locationRequest;
    private LocationSettingsRequest locationSettingsRequest;
    @Override
    public void onCreate() {
        // The service is being created
        Log.d("Location", " 1");
        locationRequest = new LocationRequest.Builder(Priority.PRIORITY_HIGH_ACCURACY, UPDATE_INTERVAL_IN_MILLISECONDS)
                .setWaitForAccurateLocation(false)
                .setMinUpdateIntervalMillis(UPDATE_INTERVAL_IN_MILLISECONDS)
                .setMaxUpdateDelayMillis(UPDATE_INTERVAL_IN_MILLISECONDS)
                .build();

        fusedLocationClient =
                LocationServices.getFusedLocationProviderClient(this);
    }
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // The service is starting, due to a call to startService()
        Log.d("Location", " 2");
        startLocationUpdates();
        final String CHANNELID = "Foreground Service ID";

        NotificationChannel channel = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            channel = new NotificationChannel(
                    CHANNELID,
                    CHANNELID,
                    NotificationManager.IMPORTANCE_LOW
            );
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            getSystemService(NotificationManager.class).createNotificationChannel(channel);
        }
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            Notification.Builder notification = new Notification.Builder(this, CHANNELID)
                    .setContentText("Service is running")
                    .setContentTitle("Service enabled")
                    .setSmallIcon(R.drawable.ic_launcher_background);
            startForeground(1001,notification.build());
        }

        return startMode;
    }
    @Override
    public IBinder onBind(Intent intent) {
        // A client is binding to the service with bindService()
        Log.d("Location", " 3");
        return binder;
    }
    @Override
    public boolean onUnbind(Intent intent) {
        // All clients have unbound with unbindService()
        Log.d("Location", " 4");
        return allowRebind;
    }
    @Override
    public void onRebind(Intent intent) {
        // A client is binding to the service with bindService(),
        // after onUnbind() has already been called
        Log.d("Location", " 5");
    }
    @Override
    public void onDestroy() {
        // The service is no longer used and is being destroyed
        Log.d("Location", " 6");
        stopLocationUpdates();
    }

    //region data


    //endregion

    //onCreate



    //Location Callback
    private LocationCallback locationCallback = new LocationCallback() {
        @Override
        public void onLocationResult(LocationResult locationResult) {
            super.onLocationResult(locationResult);
            Location currentLocation = locationResult.getLastLocation();
            saveDatToServer(String.valueOf(currentLocation.getLatitude()),String.valueOf(currentLocation.getLongitude()));
            Log.d("Locations", currentLocation.getLatitude() + "," + currentLocation.getLongitude());
            //Share/Publish Location
        }
    };




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

    void saveDatToServer(String lat,String longi){
        LocationTrackingModel locationTrackingModel=new LocationTrackingModel();
        LoginDataModel loginDataModel= AppPreference.getLoginDataPreferences(getApplicationContext());
        locationTrackingModel.setUserid(loginDataModel.getUser_id());
        locationTrackingModel.setCreateBy(loginDataModel.getUser_id());
        locationTrackingModel.setLatituede(lat);
        locationTrackingModel.setLongtitude(longi);
        locationTrackingModel.setCreated_dt(DateConstant.simpleDateFormat.format(new Date()));

        if (isNetworkAvailable()) {
            RetrofitLocaionTrackingAPI retrofitCustomerMasterAPI = RetrofitService.getRetrofit().create(RetrofitLocaionTrackingAPI.class);
            Call<LocationTrackingModel> locationTracking = retrofitCustomerMasterAPI.postLocationTracking(locationTrackingModel);
            locationTracking.enqueue(new Callback<LocationTrackingModel>() {
                @Override
                public void onResponse(Call<LocationTrackingModel> call, Response<LocationTrackingModel> response) {
                    Log.d("Location", "Tracking Location Saved");
                    logout();
                }

                @Override
                public void onFailure(Call<LocationTrackingModel> call, Throwable t) {
                    Log.d("Location", "Tracking Location Not Saved\n" + t.getMessage());
                }
            });
        }else {
            Toast.makeText(LocationService.this, "Network not available !", Toast.LENGTH_SHORT).show();
        }
    }
    void logout(){
        Calendar c = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("hh:mm aa");
        String getCurrentDateTime = sdf.format(c.getTime());
        String getMyTime="11:00 pm";
        Log.d("getCurrentDateTime",getCurrentDateTime);
        // getCurrentDateTime: 05/23/2016 18:49 PM
        if (getCurrentDateTime.compareTo(getMyTime) == 0)
        {
            Intent LocationIntent = new Intent(getApplicationContext(), LocationService.class);
            //Toast.makeText(getApplicationContext(),"Tracking Stopped",Toast.LENGTH_LONG).show();
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                stopService(LocationIntent);
            }
            AppPreference.setLoginDataPreferences(this,null);
            Intent intent=new Intent(this,MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }
        else
        {
            Log.d("Return","getMyTime older than getCurrentDateTime ");
        }
    }
//

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager != null ? connectivityManager.getActiveNetworkInfo() : null;
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}