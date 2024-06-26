package com.vindroidtech.saletracker.customer.master;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.vindroidtech.AppPreference;
import com.vindroidtech.saletracker.R;
import com.vindroidtech.saletracker.customer.login.LoginDataModel;
import com.vindroidtech.saletracker.registration.ManagerListAdapter;
import com.vindroidtech.saletracker.reports.KMReportActivity;

import java.util.ArrayList;
import java.util.List;

public class CustomerDataAdapter extends RecyclerView.Adapter<CustomerDataAdapter.ViewHolder> {

    List<GetCustomerDataModel> customerDataModels;
    CustomerDataDeleteListener dataDeleteListener;
    LoginDataModel loginData;

    public CustomerDataAdapter(List<GetCustomerDataModel> customerDataModels,
                               CustomerDataDeleteListener dataDeleteListener, Context context)
    {
        this.customerDataModels = customerDataModels;
        this.dataDeleteListener = dataDeleteListener;
         loginData= AppPreference.getLoginDataPreferences(context);
    }

    @NonNull
    @Override
    public CustomerDataAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.row_customer_list,
                parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomerDataAdapter.ViewHolder holder, int position) {
        GetCustomerDataModel model = customerDataModels.get(position);
        holder.textViewId.setText(model.getCustId());
        holder.textViewName.setText(model.getCustname());
        holder.textViewMobileNo.setText(model.getMbno());
        holder.buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dataDeleteListener.onCustomerDeleteClicked(model);
            }
        });
        if(loginData.getUsertypeid().equals("4")){
            holder.buttonDelete.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return customerDataModels.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private AppCompatTextView textViewId;
        private AppCompatTextView textViewName;
        private AppCompatTextView textViewMobileNo;
        private AppCompatButton buttonDelete;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            textViewId= itemView.findViewById(R.id.row_text_customer_id);
            textViewName = itemView.findViewById(R.id.row_text_customer_name);
            textViewMobileNo =itemView.findViewById(R.id.row_text_customer_mobile_no);
            buttonDelete = itemView.findViewById(R.id.row_button_customer_delete);
        }
    }
}
