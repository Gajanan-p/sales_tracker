package com.vindroidtech.saletracker.registration;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.vindroidtech.saletracker.R;
import com.vindroidtech.saletracker.usertype.UserTypeModel;

import java.util.ArrayList;

public class RegistrationUserTypeAdapter extends RecyclerView.Adapter<RegistrationUserTypeAdapter.ViewHolder> {

    private ArrayList<UserTypeModel>userTypeModels;
    private RegistrationUserTypeListener userTypeListener;

    public RegistrationUserTypeAdapter(ArrayList<UserTypeModel> userTypeModels, RegistrationUserTypeListener userTypeListener) {
        this.userTypeModels = userTypeModels;
        this.userTypeListener = userTypeListener;
    }

    @NonNull
    @Override
    public RegistrationUserTypeAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.registration_user_type_list,
                parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RegistrationUserTypeAdapter.ViewHolder holder, int position) {
        UserTypeModel userTypeModel = userTypeModels.get(position);
        holder.textViewId.setText(userTypeModel.getUsertypeId());
        holder.textViewName.setText(userTypeModel.getUsertypeName());
        holder.textViewName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userTypeListener.onUserTypeClicked(userTypeModel);
            }
        });


    }

    @Override
    public int getItemCount() {
        return userTypeModels.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private AppCompatTextView textViewName;
        private AppCompatTextView textViewId;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewName = itemView.findViewById(R.id.row_dialog_user_type_name);
            textViewId  = itemView.findViewById(R.id.row_dialog_user_type_id);
        }
    }
}
