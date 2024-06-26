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

public class ReportUserWiseAdapter extends RecyclerView.Adapter<ReportUserWiseAdapter.ViewHolder> {
    private List<ReportUserWiseModel> reportUserWiseModels;

    public ReportUserWiseAdapter(List<ReportUserWiseModel> reportUserWiseModels) {
        this.reportUserWiseModels = reportUserWiseModels;
    }

    @NonNull
    @Override
    public ReportUserWiseAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.report_user_wise_list_layout,
                parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ReportUserWiseAdapter.ViewHolder holder, int position) {
        ReportUserWiseModel userWiseModel = reportUserWiseModels.get(position);
        holder.textViewCname.setText(userWiseModel.getCustname());
        holder.textViewAddress.setText(userWiseModel.getAddress());
        holder.textViewMobileNo.setText(userWiseModel.getMbNo());
        holder.textViewBrand.setText(userWiseModel.getBrand());
        holder.textViewRemark.setText(userWiseModel.getRemark());
        holder.textViewCounterApprox.setText(userWiseModel.getCounterApprox());
        holder.textViewResponse.setText(userWiseModel.getResponse());
        holder.textViewFname.setText(userWiseModel.getFname());

    }

    @Override
    public int getItemCount() {
       return reportUserWiseModels.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private AppCompatTextView textViewCname;
        private AppCompatTextView textViewAddress;
        private AppCompatTextView textViewMobileNo;
        private AppCompatTextView textViewBrand;
        private AppCompatTextView textViewRemark;
        private AppCompatTextView textViewCounterApprox;
        private AppCompatTextView textViewResponse;
        private AppCompatTextView textViewFname;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewCname = itemView.findViewById(R.id.list_report_user_wise_customer);
            textViewAddress = itemView.findViewById(R.id.list_report_user_wise_address);
            textViewMobileNo = itemView.findViewById(R.id.list_report_user_wise_MbNo);
            textViewBrand = itemView.findViewById(R.id.list_report_user_wise_brand);
            textViewRemark = itemView.findViewById(R.id.list_report_user_wise_remark);
            textViewCounterApprox = itemView.findViewById(R.id.list_report_user_wise_counter_approx);
            textViewResponse = itemView.findViewById(R.id.list_report_user_wise_response);
            textViewFname = itemView.findViewById(R.id.list_report_user_wise_fname);

        }
    }
}
