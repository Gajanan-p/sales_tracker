package com.vindroidtech.saletracker.customer.master;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Toast;
import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.Gson;
import com.vindroidtech.AppPreference;
import com.vindroidtech.saletracker.R;
import com.vindroidtech.saletracker.customer.login.LoginDataModel;
import com.vindroidtech.saletracker.map.BodyData;
import com.vindroidtech.saletracker.reports.OnPagination;
import com.vindroidtech.saletracker.reports.ReportUserWiseActivity;
import com.vindroidtech.saletracker.reports.ReportUserWiseAdapter;
import com.vindroidtech.saletracker.retrofit.RetrofitService;
import com.vindroidtech.saletracker.utils.DateConstant;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CustomerMasterActivity extends AppCompatActivity implements View.OnClickListener,
                CustomerDataDeleteListener, OnPagination {

    TextInputEditText editCustomerName;
    TextInputEditText editCustomerAddress;
    TextInputEditText editCustomerMobile;
    AppCompatButton buttonSaveCustomerMaster;
    private RecyclerView viewCustomerDataList;
    private CustomerDataAdapter customerDataAdapter;
    private ArrayList<GetCustomerDataModel> customerDataModels;
    //    start Pagination
    private AppCompatButton buttonFirst;
    private AppCompatButton buttonPre;
    private AppCompatButton buttonNext;
    private AppCompatButton buttonLast;
    private AppCompatTextView textViewPageNo;
    private Calendar calendar;
    int currentPageNo=1;
    int countPerPage=5;
    int totalPages=0;
    //    end Pagination
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_master);
        editCustomerName=findViewById(R.id.edit_customer_name);
        editCustomerAddress=findViewById(R.id.edit_customer_address);
        editCustomerMobile=findViewById(R.id.edit_customer_mobile);
        buttonSaveCustomerMaster=findViewById(R.id.button_save_customer_master);
        buttonSaveCustomerMaster.setOnClickListener(this);

        viewCustomerDataList = findViewById(R.id.view_customer_list);

        calendar = Calendar.getInstance();
        String currentDate = DateConstant.simpleDateFormat.format(calendar.getTime());

        //    start Pagination
        buttonFirst=findViewById(R.id.customer_master_buttonFirst);
        buttonPre=findViewById(R.id.customer_master_buttonPre);
        buttonNext=findViewById(R.id.customer_master_buttonNext);
        buttonLast=findViewById(R.id.customer_master_buttonLast);
        textViewPageNo=findViewById(R.id.customer_master_textPageNo);

        buttonFirst.setOnClickListener(this::onFirstClicked);
        buttonPre.setOnClickListener(this::onPreClicked);
        buttonNext.setOnClickListener(this::onNextClicked);
        buttonLast.setOnClickListener(this::onLastClicked);
        //    end Pagination

    }

    @Override
    protected void onResume() {
        super.onResume();
        getCustomerDataFromServer();
    }

    @Override
    public void onClick(View view) {
        if (!TextUtils.isEmpty(editCustomerName.getText().toString())
                && !TextUtils.isEmpty(editCustomerAddress.getText().toString())
                && !TextUtils.isEmpty(editCustomerMobile.getText().toString())) {
            saveDatToServer();
        }else {
            Toast.makeText(getApplicationContext(),"Please enter all fields",Toast.LENGTH_LONG).show();
        }
    }

    void saveDatToServer(){
        LoginDataModel loginDataModel= AppPreference.getLoginDataPreferences(getApplicationContext());
        CustomerMasterDataModel customerMasterDataModel=new CustomerMasterDataModel();
        customerMasterDataModel.setCustname(editCustomerName.getText().toString());
        customerMasterDataModel.setAddress(editCustomerAddress.getText().toString());
        customerMasterDataModel.setMbNo(editCustomerMobile.getText().toString());
        customerMasterDataModel.setCreateBy(loginDataModel.getUser_id());

        if (isNetworkAvailable()) {
            RetrofitCustomerMasterAPI retrofitCustomerMasterAPI = RetrofitService.getRetrofit().create(RetrofitCustomerMasterAPI.class);
            Call<CustomerMasterDataModel> regCall = retrofitCustomerMasterAPI.customerMasterPost(customerMasterDataModel);
            regCall.enqueue(new Callback<CustomerMasterDataModel>() {
                @Override
                public void onResponse(Call<CustomerMasterDataModel> call, Response<CustomerMasterDataModel> response) {
                    if (response.isSuccessful()) {
                        Toast.makeText(getApplicationContext(), response.body().getMsg(), Toast.LENGTH_LONG).show();
                        clearUIComponents();
                    }
                }

                @Override
                public void onFailure(Call<CustomerMasterDataModel> call, Throwable t) {
                    Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_LONG).show();
                }
            });
        }else {
            Toast.makeText(CustomerMasterActivity.this, "Network not available !", Toast.LENGTH_SHORT).show();
        }
    }

    void clearUIComponents(){
        editCustomerName.setText(null);
        editCustomerAddress.setText(null);
        editCustomerMobile.setText(null);
    }

    public void getCustomerDataFromServer(){
        if (isNetworkAvailable()) {
            RetrofitCustomerMasterAPI retrofitCustomerMasterAPI = RetrofitService.getRetrofit().create(RetrofitCustomerMasterAPI.class);
            Call<ArrayList<GetCustomerDataModel>> customerCall = retrofitCustomerMasterAPI.fetchCustomerData();
            customerCall.enqueue(new Callback<ArrayList<GetCustomerDataModel>>() {
                @Override
                public void onResponse(Call<ArrayList<GetCustomerDataModel>> call, Response<ArrayList<GetCustomerDataModel>> response) {
                    if (response.isSuccessful()) {
                        customerDataModels = response.body();
                        //    start Pagination
                        totalPages = customerDataModels.size() / countPerPage;
                        if (countPerPage <= customerDataModels.size()) {
                            customerDataAdapter = new CustomerDataAdapter(customerDataModels.subList(currentPageNo - 1, countPerPage), CustomerMasterActivity.this, CustomerMasterActivity.this);
                            viewCustomerDataList.setAdapter(customerDataAdapter);
                            viewCustomerDataList.setLayoutManager(new LinearLayoutManager(CustomerMasterActivity.this));

                        } else {
                            customerDataAdapter = new CustomerDataAdapter(customerDataModels, CustomerMasterActivity.this, CustomerMasterActivity.this);
                            viewCustomerDataList.setAdapter(customerDataAdapter);
                            viewCustomerDataList.setLayoutManager(new LinearLayoutManager(CustomerMasterActivity.this));
                        }
                        textViewPageNo.setText(String.valueOf(currentPageNo));
                        //    end Pagination


                    }
                }

                @Override
                public void onFailure(Call<ArrayList<GetCustomerDataModel>> call, Throwable t) {
                    Log.e("Error", t.getMessage());
                }
            });
        }else {
            Toast.makeText(CustomerMasterActivity.this, "Network not available !", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onCustomerDeleteClicked(GetCustomerDataModel customerDataModel) {
        RequestCustBody data  = new RequestCustBody();
        data.setCustid(Integer.parseInt(customerDataModel.getCustId()));
        RetrofitCustomerMasterAPI retrofitCustomerMasterAPI= RetrofitService.getRetrofit().create(RetrofitCustomerMasterAPI.class);
        Call<DeleteCustomerModel> deleteCustomerCall =retrofitCustomerMasterAPI.deleteCustomerData(data);
        deleteCustomerCall.enqueue(new Callback<DeleteCustomerModel>() {
            @Override
            public void onResponse(Call<DeleteCustomerModel> call, Response<DeleteCustomerModel> response) {
                if (response.isSuccessful()){
                    Toast.makeText(CustomerMasterActivity.this,response.body().getMsg(),Toast.LENGTH_LONG).show();
                    customerDataAdapter.notifyDataSetChanged();
                    getCustomerDataFromServer();
                }
            }

            @Override
            public void onFailure(Call<DeleteCustomerModel> call, Throwable t) {
                Log.e("Error",t.getMessage());
            }
        });
    }

    //    start Pagination
    @Override
    public void onFirstClicked(View view) {
        currentPageNo=1;
        int firstIndex=(currentPageNo-1);
        int secondIndex=firstIndex + countPerPage;
        if(secondIndex>= customerDataModels.size()){
            secondIndex=customerDataModels.size();
        }
        customerDataAdapter = new CustomerDataAdapter(customerDataModels.subList(firstIndex,secondIndex),CustomerMasterActivity.this,CustomerMasterActivity.this);
        viewCustomerDataList.setAdapter(customerDataAdapter);
        customerDataAdapter.notifyDataSetChanged();
        textViewPageNo.setText(String.valueOf(currentPageNo));
    }

    @Override
    public void onPreClicked(View view) {
        currentPageNo = currentPageNo-1;

        int firstIndex= (currentPageNo) * countPerPage;

        if(firstIndex <= 0){
            firstIndex=0;
            currentPageNo=1;
        }
        int secondIndex= firstIndex + countPerPage;
        if(secondIndex>= customerDataModels.size()){
            secondIndex=customerDataModels.size();
        }
        customerDataAdapter = new CustomerDataAdapter(customerDataModels.subList(firstIndex,secondIndex),CustomerMasterActivity.this,CustomerMasterActivity.this);
        viewCustomerDataList.setAdapter(customerDataAdapter);
        customerDataAdapter.notifyDataSetChanged();
        textViewPageNo.setText(String.valueOf(currentPageNo));
    }

    @Override
    public void onNextClicked(View view) {
        currentPageNo=currentPageNo + 1;
        int firstIndex = (currentPageNo) * countPerPage;
        if(currentPageNo>=totalPages){
            currentPageNo=totalPages;
            firstIndex = (currentPageNo) * countPerPage;
        }
        int secondIndex = firstIndex + countPerPage;
        if(secondIndex >= customerDataModels.size()){
            secondIndex = customerDataModels.size();
        }
        customerDataAdapter = new CustomerDataAdapter(customerDataModels.subList(firstIndex,secondIndex),CustomerMasterActivity.this,CustomerMasterActivity.this);
        viewCustomerDataList.setAdapter(customerDataAdapter);
        customerDataAdapter.notifyDataSetChanged();
        textViewPageNo.setText(String.valueOf(currentPageNo));
    }

    @Override
    public void onLastClicked(View view) {
        currentPageNo=totalPages;
        int firstIndex = (currentPageNo) * countPerPage;
        int secondIndex = firstIndex + countPerPage;

        if(firstIndex >= customerDataModels.size()){
            firstIndex=customerDataModels.size()-countPerPage;
            currentPageNo=totalPages;
        }
        if(secondIndex >= customerDataModels.size()){
            secondIndex = customerDataModels.size();
        }

        customerDataAdapter = new CustomerDataAdapter(customerDataModels.subList(firstIndex,secondIndex),CustomerMasterActivity.this,CustomerMasterActivity.this);
        viewCustomerDataList.setAdapter(customerDataAdapter);
        customerDataAdapter.notifyDataSetChanged();
        textViewPageNo.setText(String.valueOf(currentPageNo));
    }
    //    End Pagination

//    create excel sheet

    private void createExcelSheet() {
        Workbook wb=new HSSFWorkbook();
        Cell cell=null;
        CellStyle cellStyle=wb.createCellStyle();
        cellStyle.setFillForegroundColor(HSSFColor.LIGHT_BLUE.index);
        cellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        Font font = wb.createFont();
        font.setColor(HSSFColor.WHITE.index);
        cellStyle.setFont(font);
        //Now we are creating sheet
        Sheet sheet=null;
        sheet = wb.createSheet("Name of sheet");
        //Now column and row
        Row row =sheet.createRow(0);

        cell=row.createCell(0);
        cell.setCellValue("Customer ID");
        cell.setCellStyle(cellStyle);

        cell=row.createCell(1);
        cell.setCellValue("Customer Name");
        cell.setCellStyle(cellStyle);

        cell=row.createCell(2);
        cell.setCellValue("Mobile No");
        cell.setCellStyle(cellStyle);


        sheet.setColumnWidth(0,(10*100));
        sheet.setColumnWidth(1,(10*500));
        sheet.setColumnWidth(2,(10*200));


        for (int i=0; i<customerDataModels.size();i++){
            // Create a New Row for every new entry in list
            Row rowData = sheet.createRow(i + 1);
            // Create Cells for each row
            cell = rowData.createCell(0);
            cell.setCellValue(customerDataModels.get(i).getCustId());
            cell = rowData.createCell(1);
            cell.setCellValue(customerDataModels.get(i).getCustname());
            cell = rowData.createCell(2);
            cell.setCellValue(customerDataModels.get(i).getMbno());


        }
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy-hh-mm-ss");
        String currentDate = dateFormat.format(calendar.getTime());
        String excelPath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).toString();
        File file = new File(excelPath,currentDate+".xls");
        FileOutputStream outputStream =null;

        try {
            outputStream=new FileOutputStream(file);
            wb.write(outputStream);
            Toast.makeText(CustomerMasterActivity.this,"Excel Created Successfully",Toast.LENGTH_LONG).show();
        } catch (java.io.IOException e) {
            e.printStackTrace();

            Toast.makeText(CustomerMasterActivity.this,"Excel Not Created Successfully",Toast.LENGTH_LONG).show();
            try {
                outputStream.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

//    check network available or not

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager != null ? connectivityManager.getActiveNetworkInfo() : null;
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

}