package com.vindroidtech.saletracker.usertype;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.vindroidtech.saletracker.R;

import java.util.ArrayList;
import java.util.List;

public class DeleteUserTypeAdapter extends RecyclerView.Adapter<DeleteUserTypeAdapter.ViewHolder> {
    List<UserTypeModel> userTypeModels;
    DeleteUserTypeListener userTypeListener;

    public DeleteUserTypeAdapter(List<UserTypeModel> userTypeModels, DeleteUserTypeListener userTypeListener) {
        this.userTypeModels = userTypeModels;
        this.userTypeListener = userTypeListener;
    }

    @NonNull
    @Override
    public DeleteUserTypeAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.row_user_type_list,
                parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DeleteUserTypeAdapter.ViewHolder holder, int position) {
        UserTypeModel typeModel = userTypeModels.get(position);
        holder.textViewUserId.setText(typeModel.getUsertypeId());
        holder.textViewUserName.setText(typeModel.getUsertypeName());
        holder.buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userTypeListener.onUserTypeDelete(typeModel);
            }
        });
    }

    @Override
    public int getItemCount() {
        return userTypeModels.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private AppCompatTextView textViewUserId;
        private AppCompatTextView textViewUserName;
        private AppCompatButton buttonDelete;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewUserId = itemView.findViewById(R.id.text_user_type_id);
            textViewUserName =itemView.findViewById(R.id.text_user_type_name);
            buttonDelete =itemView.findViewById(R.id.button_row_user_type_delete);
        }
    }
}
