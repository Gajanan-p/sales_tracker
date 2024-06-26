package com.vindroidtech.saletracker.map;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.vindroidtech.saletracker.R;
import com.vindroidtech.saletracker.registration.RegistrationUserTypeAdapter;
import com.vindroidtech.saletracker.registration.RegistrationUserTypeListener;
import com.vindroidtech.saletracker.registration.userdata.UserDataDeleteListener;
import com.vindroidtech.saletracker.registration.userdata.UserDataModel;
import com.vindroidtech.saletracker.usertype.UserTypeModel;

import java.util.ArrayList;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ViewHolder> {

    private ArrayList<UserDataModel> userTypeModels;
    private UserDataDeleteListener userTypeListener;

    public UserAdapter(ArrayList<UserDataModel> userTypeModels,
                       UserDataDeleteListener userTypeListener) {
        this.userTypeModels = userTypeModels;
        this.userTypeListener = userTypeListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.registration_user_type_list,
                parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        UserDataModel userTypeModel = userTypeModels.get(position);
        holder.textViewId.setText(userTypeModel.getUserId());
        holder.textViewName.setText(userTypeModel.getName());
        holder.textViewName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userTypeListener.onUserClicked(userTypeModel);
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
