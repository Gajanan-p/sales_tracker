package com.vindroidtech.saletracker.reports;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.vindroidtech.saletracker.R;
import com.vindroidtech.saletracker.registration.RegistrationUserTypeAdapter;
import com.vindroidtech.saletracker.registration.RegistrationUserTypeListener;
import com.vindroidtech.saletracker.registration.userdata.UserDataModel;
import com.vindroidtech.saletracker.usertype.UserTypeModel;

import java.util.ArrayList;

public class SelectUserDialog extends Dialog {
    ArrayList<UserDataModel> userTypeModels;
    RecyclerView recyclerView;
    RecyclerView.Adapter adapter;
    SelectUserListener userTypeListener;

    public SelectUserDialog(@NonNull Context context,
                                      ArrayList<UserDataModel> userTypeModels,
                                      SelectUserListener userTypeListener) {
        super(context);
        this.userTypeModels = userTypeModels;
        this.userTypeListener = userTypeListener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.select_user_dialog_layout);
        adapter = new SelectUserAdapter(userTypeModels,userTypeListener);
        recyclerView = findViewById(R.id.list_select_user_list);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

    }
}