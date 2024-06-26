package com.vindroidtech.saletracker.registration;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.vindroidtech.saletracker.R;
import com.vindroidtech.saletracker.managerRights.ManagerRightsModel;
import com.vindroidtech.saletracker.usertype.UserTypeModel;

import java.util.ArrayList;

public class ManagerListDialog extends Dialog {
    ArrayList<ManagerRightsModel> userTypeModels;
    RecyclerView recyclerView;
    RecyclerView.Adapter mangerAdapter;
    ManagerListener managerListener;

    public ManagerListDialog(@NonNull Context context,
                             ArrayList<ManagerRightsModel> userTypeModels,
                             ManagerListener managerListener) {
        super(context);
        this.userTypeModels = userTypeModels;
        this.managerListener = managerListener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.registration_user_type_dialog);
        mangerAdapter = new ManagerListAdapter(userTypeModels,managerListener);
        recyclerView = findViewById(R.id.list_item_user_tye_list);
        recyclerView.setAdapter(mangerAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

    }
}
