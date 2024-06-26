package com.vindroidtech.saletracker.map;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.vindroidtech.saletracker.R;
import com.vindroidtech.saletracker.registration.userdata.UserDataDeleteListener;
import com.vindroidtech.saletracker.registration.userdata.UserDataModel;
import java.util.ArrayList;

public class UserDialog extends Dialog {
    ArrayList<UserDataModel> userDataModels;
    RecyclerView recyclerView;
    RecyclerView.Adapter adapter;
    UserDataDeleteListener userTypeListener;

    public UserDialog(@NonNull Context context,
                                      ArrayList<UserDataModel> userDataModels,
                      UserDataDeleteListener userTypeListener)
    {
        super(context);
        this.userDataModels = userDataModels;
        this.userTypeListener = userTypeListener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.registration_user_type_dialog);
        adapter = new UserAdapter(userDataModels, userTypeListener);
        recyclerView = findViewById(R.id.list_item_user_tye_list);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }
}
