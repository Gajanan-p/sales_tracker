package com.vindroidtech.saletracker.registration.userdata;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.vindroidtech.saletracker.R;
import com.vindroidtech.saletracker.customer.master.GetCustomerDataModel;

import java.util.ArrayList;
import java.util.List;

public class UserDataAdapter extends RecyclerView.Adapter<UserDataAdapter.ViewHolder>{
    List<UserDataModel> userDataModels;
    UserDataDeleteListener dataDeleteListener;

    public UserDataAdapter(List<UserDataModel> userDataModels,
                           UserDataDeleteListener dataDeleteListener)
    {
        this.userDataModels = userDataModels;
        this.dataDeleteListener = dataDeleteListener;
    }

    @NonNull
    @Override
    public UserDataAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_user_list,
                parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserDataAdapter.ViewHolder holder, int position) {
        UserDataModel model = userDataModels.get(position);
        holder.textViewId.setText(model.getUserId());
        holder.textViewName.setText(model.getName());
        holder.textViewMobileNo.setText(model.getMbNo());
        holder.buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dataDeleteListener.onUserDeleteClicked(model);
            }
        });
    }

    @Override
    public int getItemCount() {
        return userDataModels.size() ;
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        private AppCompatTextView textViewId;
        private AppCompatTextView textViewName;
        private AppCompatTextView textViewMobileNo;
        private AppCompatButton buttonDelete;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewId= itemView.findViewById(R.id.row_text_user_id);
            textViewName = itemView.findViewById(R.id.row_text_user_name);
            textViewMobileNo =itemView.findViewById(R.id.row_text_user_mobile_no);
            buttonDelete = itemView.findViewById(R.id.row_button_user_delete);
        }
    }
}
