package com.vindroidtech;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.google.gson.Gson;
import com.vindroidtech.saletracker.customer.login.LoginDataModel;


public class AppPreference {
    private static String TAG="appDataPreferences";
    public static String appLoginPreferences="appLoginPreferences";
    public static String appLoginPreferencesKey="appLoginPreferencesKey";
    public static SharedPreferences sharedLoginPreferences;

    public static SharedPreferences getLoginSharedPreferences(Context context){
        if(sharedLoginPreferences==null){
            sharedLoginPreferences=context.getSharedPreferences(AppPreference.appLoginPreferences, Context.MODE_PRIVATE);
        }
        return sharedLoginPreferences;
    }

    public static void setLoginDataPreferences(Context context, LoginDataModel userModel){
        Gson gson = new Gson();
        String data=gson.toJson(userModel);
        Log.i(TAG,"Set user model data");
        Log.i(TAG,data);
        SharedPreferences.Editor editor=getLoginSharedPreferences(context).edit();
        editor.putString(appLoginPreferencesKey,data);
        editor.commit();
    }

    public static LoginDataModel getLoginDataPreferences(Context context){
        String data=getLoginSharedPreferences(context).getString(appLoginPreferencesKey,"");
        Gson gson = new Gson();
        LoginDataModel userModel= gson.fromJson(data,LoginDataModel.class);
        Log.i(TAG,"get user model data");
        Log.i(TAG,data);
        return userModel;
    }

}
