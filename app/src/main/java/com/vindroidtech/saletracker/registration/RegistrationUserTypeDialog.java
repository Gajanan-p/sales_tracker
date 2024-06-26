package com.vindroidtech.saletracker.registration;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.vindroidtech.saletracker.R;
import com.vindroidtech.saletracker.usertype.UserTypeModel;

import java.util.ArrayList;

public class RegistrationUserTypeDialog extends Dialog {
    ArrayList<UserTypeModel> userTypeModels;
    RecyclerView recyclerView;
    RecyclerView.Adapter adapter;
    RegistrationUserTypeListener  userTypeListener;

    public RegistrationUserTypeDialog(@NonNull Context context,
                                      ArrayList<UserTypeModel> userTypeModels,
                                      RegistrationUserTypeListener userTypeListener) {
        super(context);
        this.userTypeModels = userTypeModels;
        this.userTypeListener = userTypeListener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.registration_user_type_dialog);
        adapter = new RegistrationUserTypeAdapter(userTypeModels,userTypeListener);
        recyclerView = findViewById(R.id.list_item_user_tye_list);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

    }
}
