package com.vindroidtech.saletracker.customer.addinfo;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.vindroidtech.saletracker.R;

import java.util.ArrayList;

public class AddCustomerInformationDialog extends Dialog implements AddCustomerInformationListener{

    ArrayList<GetCustomerInfoModel> models;
    RecyclerView recyclerView;
    AddCustomerInformationAdapter adapter;
    AddCustomerInformationListener customItemClickListener;
    AppCompatEditText editTextSearchName;
    public AddCustomerInformationDialog(@NonNull Context context, ArrayList<GetCustomerInfoModel> models,
                                        AddCustomerInformationListener customItemClickListener) {
        super(context);
        this.models = models;
        this.customItemClickListener = customItemClickListener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.add_customer_information_dialog);
        recyclerView = (RecyclerView) findViewById(R.id.list_item_customer_list);
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getContext(), 1);
        recyclerView.setLayoutManager(mLayoutManager);
        editTextSearchName = (AppCompatEditText) findViewById(R.id.edit_text_customer);
        openList();
        editTextSearchName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {

            }

            @Override
            public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {

                adapter.getFilter().filter(arg0);
            }

            @Override
            public void afterTextChanged(Editable arg0) {

            }
        });
    }

    @Override
    public void onCustomerIdClicked(GetCustomerInfoModel customerMasterDataModel, int position) {

    }
    public void openList() {
        adapter = new AddCustomerInformationAdapter(
                getContext(),models,customItemClickListener);
        recyclerView.setAdapter(adapter);
    }
}
