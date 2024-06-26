package com.vindroidtech.saletracker.registration;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.vindroidtech.saletracker.R;
import com.vindroidtech.saletracker.managerRights.ManagerRightsModel;
import com.vindroidtech.saletracker.usertype.UserTypeModel;

import java.util.ArrayList;

public class ManagerListAdapter extends RecyclerView.Adapter<ManagerListAdapter.ViewHolder> {

    private ArrayList<ManagerRightsModel> managerRightsModels;
    private ManagerListener managerListener;

    public ManagerListAdapter(ArrayList<ManagerRightsModel> ManagerRightsModel, ManagerListener managerListener) {
        this.managerRightsModels = ManagerRightsModel;
        this.managerListener = managerListener;
    }

    @NonNull
    @Override
    public ManagerListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.registration_user_type_list,
                parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ManagerListAdapter.ViewHolder holder, int position) {
        ManagerRightsModel userTypeModel = managerRightsModels.get(position);
        holder.textViewId.setText(userTypeModel.getUserId());
        holder.textViewName.setText(userTypeModel.getFname());
        holder.textViewName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                managerListener.onMangerClicked(userTypeModel);
            }
        });


    }

    @Override
    public int getItemCount() {
        return managerRightsModels.size();
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
