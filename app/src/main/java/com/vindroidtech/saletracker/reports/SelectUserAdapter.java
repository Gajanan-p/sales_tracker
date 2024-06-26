package com.vindroidtech.saletracker.reports;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.vindroidtech.saletracker.R;
import com.vindroidtech.saletracker.registration.userdata.UserDataModel;

import java.util.ArrayList;


public class SelectUserAdapter extends RecyclerView.Adapter<SelectUserAdapter.ViewHolder> {

    ArrayList<UserDataModel> userDataModels;
    SelectUserListener selectUserListener;

    public SelectUserAdapter(ArrayList<UserDataModel> userDataModels,
                             SelectUserListener selectUserListener)
    {
        this.userDataModels = userDataModels;
        this.selectUserListener = selectUserListener;
    }

    @NonNull
    @Override
    public SelectUserAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.select_user_layput,
                parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SelectUserAdapter.ViewHolder holder, int position) {
        UserDataModel userDataModel= userDataModels.get(position);

        holder.textViewId.setText(userDataModel.getUserId());
        holder.textViewName.setText(userDataModel.getName());
        holder.textViewName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectUserListener.onUserNameClicked(userDataModel);
            }
        });

    }

    @Override
    public int getItemCount() {
        return userDataModels.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private AppCompatTextView textViewId;
        private AppCompatTextView textViewName;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewId = itemView.findViewById(R.id.row_dialog_report_user_id);
            textViewName = itemView.findViewById(R.id.row_dialog_report_user_name);
        }
    }
}
