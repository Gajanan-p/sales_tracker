package com.vindroidtech.saletracker.reports;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.vindroidtech.saletracker.R;

import java.util.ArrayList;
import java.util.List;

public class GetKMDataAdapter extends RecyclerView.Adapter<GetKMDataAdapter.ViewHolder> {
    private List<GetKMdataModel> kMdataModels;

    public GetKMDataAdapter(List<GetKMdataModel> kMdataModels) {
        this.kMdataModels = kMdataModels;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_km_report_layout,
                parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        GetKMdataModel getKMdataModel = kMdataModels.get(position);
       // holder.textViewUID.setText(getKMdataModel.getUserid());
        holder.textViewUName.setText(getKMdataModel.getUserName());
        holder.textViewUType.setText(getKMdataModel.getUsertype());
        holder.textViewKMDate.setText(getKMdataModel.getKmdate());
        holder.textViewTotalKm.setText(getKMdataModel.getTotalkm());


    }

    @Override
    public int getItemCount() {
        return kMdataModels.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private AppCompatTextView textViewUID;
        private AppCompatTextView textViewUName;
        private AppCompatTextView textViewUType;
        private AppCompatTextView textViewKMDate;
        private AppCompatTextView textViewTotalKm;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ///textViewUID = itemView.findViewById(R.id.list_report_km_wise_userid);
            textViewUName = itemView.findViewById(R.id.list_report_km_wise_user_name);
            textViewUType = itemView.findViewById(R.id.list_report_km_wise_usertype);
            textViewKMDate = itemView.findViewById(R.id.list_report_km_wise_kmdate);
            textViewTotalKm = itemView.findViewById(R.id.list_report_km_wise_total_km);

        }
    }
}