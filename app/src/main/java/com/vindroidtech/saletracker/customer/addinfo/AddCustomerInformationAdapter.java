package com.vindroidtech.saletracker.customer.addinfo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.vindroidtech.saletracker.R;

import java.util.ArrayList;

public class AddCustomerInformationAdapter  extends RecyclerView.Adapter<AddCustomerInformationAdapter.MyViewHolder> implements Filterable {

    private ArrayList<GetCustomerInfoModel> userList;
    private ArrayList<GetCustomerInfoModel> filteredUserList;
    private Context context;
    private AddCustomerInformationListener customItemClickListener;
    int position;
    public AddCustomerInformationAdapter(Context context, ArrayList<GetCustomerInfoModel> userArrayList,
                                         AddCustomerInformationListener customItemClickListener
    ) {
        this.context = context;
        this.userList = userArrayList;
        this.filteredUserList = userArrayList;
        this.customItemClickListener = customItemClickListener;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.add_customer_information_adapter, viewGroup, false);
        final MyViewHolder myViewHolder = new MyViewHolder(view);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // for click item listener
                customItemClickListener.onCustomerIdClicked(filteredUserList.get(myViewHolder.getAdapterPosition()),myViewHolder.getAdapterPosition());
            }
        });
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        GetCustomerInfoModel model = filteredUserList.get(holder.getAdapterPosition());
        // viewHolder.textViewId.setText(model.getBillId().toString());
        holder.userName.setText(model.getFname());
        holder.textViewId.setText(model.getUser_Id());
        holder.userName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                customItemClickListener.onCustomerIdClicked(model,position);
            }
        });
    }


    @Override
    public int getItemCount() {
        return filteredUserList.size();
    }


    @Override
    public Filter getFilter() {

        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {

                String searchString = charSequence.toString();

                if (searchString.isEmpty()) {

                    filteredUserList = userList;

                } else {

                    ArrayList<GetCustomerInfoModel> tempFilteredList = new ArrayList<>();

                    for (GetCustomerInfoModel model : userList) {
                        if (model.getFname().length() > 0) {
                            // search for user title
                            if(model.getFname().length()<searchString.length()){

                            }
                            else {if(model.getFname().substring(0, searchString.length()).toLowerCase().equals(searchString)) {

                                tempFilteredList.add(model);
                            }}
                        }
                    }
                    filteredUserList = tempFilteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = filteredUserList;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                filteredUserList = (ArrayList<GetCustomerInfoModel>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }



    public class MyViewHolder extends RecyclerView.ViewHolder{
        private TextView userName;
        private TextView textViewId;
              public MyViewHolder(View view) {
            super(view);
            userName = (TextView)view.findViewById(R.id.row_dialog_customer_name);
            textViewId = (TextView) view.findViewById(R.id.row_dialog_customer_id);
        }
    }
}
