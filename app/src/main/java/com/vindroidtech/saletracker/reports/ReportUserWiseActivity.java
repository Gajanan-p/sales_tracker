package com.vindroidtech.saletracker.reports;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.gson.Gson;
import com.itextpdf.io.image.ImageData;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.io.source.ByteArrayOutputStream;
import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.property.HorizontalAlignment;
import com.vindroidtech.AppPreference;
import com.vindroidtech.saletracker.MainActivity;
import com.vindroidtech.saletracker.R;
import com.vindroidtech.saletracker.customer.login.LoginDataModel;
import com.vindroidtech.saletracker.registration.RetrofitRegistrationAPI;
import com.vindroidtech.saletracker.registration.userdata.UserDataModel;
import com.vindroidtech.saletracker.retrofit.RetrofitService;
import com.vindroidtech.saletracker.utils.DateConstant;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReportUserWiseActivity extends AppCompatActivity implements View.OnClickListener ,
                 SelectUserListener,OnPagination{

    private AppCompatEditText editTextSelectUser;
    private AppCompatButton buttonExcel;
    private AppCompatButton buttonPDF;
    private RecyclerView recyclerViewReport;
    private ArrayList<ReportUserWiseModel> reportUserWiseModels;
    private ArrayList<UserDataModel> userDataModels;
    private SelectUserDialog selectUserDialog;
    private ReportUserWiseAdapter userWiseAdapter;
    private AppCompatTextView editTextFromDate;
    private AppCompatTextView editTextToDate;
    private Calendar calendar;
    private SimpleDateFormat simpleDateFormat;
    private AppCompatButton buttonSearch;

    private UserDataModel userDataModel;

//    start Pagination
    private AppCompatButton buttonFirst;
    private AppCompatButton buttonPre;
    private AppCompatButton buttonNext;
    private AppCompatButton buttonLast;
    private AppCompatTextView textViewPageNo;
    LoginDataModel loginData = AppPreference.getLoginDataPreferences(ReportUserWiseActivity.this);

    File file;
    int currentPageNo=1;
    int countPerPage=5;
    int totalPages=0;
    //    end Pagination

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_user_wise);
        editTextSelectUser = findViewById(R.id.report_user_wise_select_user);
        editTextSelectUser.setOnClickListener(this);
        buttonExcel = findViewById(R.id.button_report_user_wise_excel);
        buttonExcel.setOnClickListener(this);
        buttonPDF = findViewById(R.id.button_report_user_wise_pdf);
        buttonPDF.setOnClickListener(this);

        recyclerViewReport = findViewById(R.id.report_user_wise_recycler_view);
        recyclerViewReport.setLayoutManager( new LinearLayoutManager(ReportUserWiseActivity.this));
        recyclerViewReport.setHasFixedSize(true);

        editTextFromDate = findViewById(R.id.edit_from_date);
        editTextFromDate.setOnClickListener(this);
        editTextToDate = findViewById(R.id.edit_to_date);
        editTextToDate.setOnClickListener(this);
        calendar = Calendar.getInstance();
        simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd ");
        String currentDate = simpleDateFormat.format(calendar.getTime());
        editTextToDate.setText(currentDate);
        Calendar calendar1 = Calendar.getInstance();
        calendar1.add(Calendar.DATE, -1);
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd ", Locale.getDefault());
        String yesterdayAsString = dateFormat.format(calendar1.getTime());
        editTextFromDate.setText(yesterdayAsString);
        buttonSearch = findViewById(R.id.button_search);
        buttonSearch.setOnClickListener(this);
//        AppPreference.clearLoginDataPreferences(ReportUserWiseActivity.this);
        //    start Pagination
        buttonFirst=findViewById(R.id.buttonFirst);
        buttonPre=findViewById(R.id.buttonPre);
        buttonNext=findViewById(R.id.buttonNext);
        buttonLast=findViewById(R.id.buttonLast);
        textViewPageNo=findViewById(R.id.textPageNo);
        buttonFirst.setOnClickListener(this::onFirstClicked);
        buttonPre.setOnClickListener(this::onPreClicked);
        buttonNext.setOnClickListener(this::onNextClicked);
        buttonLast.setOnClickListener(this::onLastClicked);
        //    end Pagination
        if(loginData.getUsertypeid().equals("4")){
            findViewById(R.id.linearLayoutCompat).setVisibility(View.GONE);
        }else{
            findViewById(R.id.linearLayoutCompat).setVisibility(View.VISIBLE);
        }

    }

//TODO-------------start logic----------------------------------------------------------------------

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.report_user_wise_select_user:{
                fetchUserDataFromServer();
                break;
            }
            case R.id.button_report_user_wise_excel:{
                createExcelSheet();
                break;
            }
            case R.id.button_report_user_wise_pdf:{
//                    Toast.makeText(ReportUserWiseActivity.this, "Work in progress !", Toast.LENGTH_SHORT).show();
                try {
                    createPdf(reportUserWiseModels);
                } catch (FileNotFoundException e) {
                    throw new RuntimeException(e);
                }
                break;
            }
            case R.id.edit_from_date: {
                DateConstant.getDateModel(ReportUserWiseActivity.this,editTextFromDate);
                break;
            }
            case R.id.edit_to_date: {
                DateConstant.getDateModel(ReportUserWiseActivity.this,editTextToDate);
                break;
            }
            case R.id.button_search:{
                if (!TextUtils.isEmpty(editTextToDate.getText().toString())
                        && !TextUtils.isEmpty(editTextFromDate.getText().toString()))
                {
                    fetchReportUserWiseData();
                }else {
                    Toast.makeText(ReportUserWiseActivity.this, "Fill all fields !", Toast.LENGTH_SHORT).show();
                }
                break;
            }
        }

    }

    private void fetchUserDataFromServer() {
        RetrofitRegistrationAPI registrationAPI = RetrofitService.getRetrofit().create(RetrofitRegistrationAPI.class);
        Call<ArrayList<UserDataModel>> apiCallUserdata = registrationAPI.fetchUserData();

        apiCallUserdata.enqueue(new Callback<ArrayList<UserDataModel>>() {
            @Override
            public void onResponse(Call<ArrayList<UserDataModel>> call, Response<ArrayList<UserDataModel>> response) {
                if (response.isSuccessful()) {
                    userDataModels = response.body();
                    selectUserDialog = new SelectUserDialog(ReportUserWiseActivity.this,
                            userDataModels,
                            ReportUserWiseActivity.this);
                    selectUserDialog.show();
                }
            }
            @Override
            public void onFailure(Call<ArrayList<UserDataModel>> call, Throwable t) {
                Log.e("Error",t.getMessage());
            }
        });
    }

    @Override
    public void onUserNameClicked(UserDataModel userDataModel) {
        this.userDataModel = userDataModel;
        editTextSelectUser.setText(userDataModel.getName());
        selectUserDialog.dismiss();
        //fetchReportUserWiseData();
    }

    public void fetchReportUserWiseData(){
        RequestReportUserWiseModel data = new RequestReportUserWiseModel();
        if(loginData.getUsertypeid().equals("4")){
            data.setUserid(loginData.getUser_id());
            data.setManagerid(loginData.getManagerId());
        }
        else if(loginData.getUsertypeid().equals("3")){
            if(userDataModel!=null){
                data.setUserid(userDataModel.getUserId());
                data.setManagerid(userDataModel.getManagerId());
            }else{
                data.setUserid(loginData.getUser_id());
                data.setManagerid("0");
            }
        } else {
            if(userDataModel !=null ){
                data.setUserid(userDataModel.getUserId());
                data.setManagerid(userDataModel.getManagerId());
            }else{
                data.setUserid("0");
                data.setManagerid("0");
            }

        }

        data.setFromdate(editTextFromDate.getText().toString());
        data.setTodate(editTextToDate.getText().toString());
        Gson gson = new Gson();
        String datas=gson.toJson(data);

        Log.d("SenData",datas);
        RetrofitReportAPI retrofitReportAPI = RetrofitService.getRetrofit().create(RetrofitReportAPI.class);
        Call<ArrayList<ReportUserWiseModel>> callReportUserWiseData = retrofitReportAPI.fetchReportUserWiseData(data);
        callReportUserWiseData.enqueue(new Callback<ArrayList<ReportUserWiseModel>>() {
            @Override
            public void onResponse(Call<ArrayList<ReportUserWiseModel>> call, Response<ArrayList<ReportUserWiseModel>> response) {
                if (response.isSuccessful()){
                    reportUserWiseModels = response.body();
                    if (reportUserWiseModels!=null) {
                        //    start Pagination
                        currentPageNo=1;
                        totalPages=reportUserWiseModels.size()/countPerPage;
                        if(countPerPage<=reportUserWiseModels.size()){
                            userWiseAdapter = new ReportUserWiseAdapter(reportUserWiseModels.subList(currentPageNo-1,countPerPage));
                            recyclerViewReport.setAdapter(userWiseAdapter);

                        }else{
                            userWiseAdapter = new ReportUserWiseAdapter(reportUserWiseModels);
                            recyclerViewReport.setAdapter(userWiseAdapter);
                        }
                        textViewPageNo.setText(String.valueOf(currentPageNo));
                        //    end Pagination
                    }
                }
            }

            @Override
            public void onFailure(Call<ArrayList<ReportUserWiseModel>> call, Throwable t) {
                Log.e("Error",t.getMessage());
            }
        });
    }

//    create excel
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
        cell.setCellValue("Customer Name");
        cell.setCellStyle(cellStyle);

        cell=row.createCell(1);
        cell.setCellValue("Address");
        cell.setCellStyle(cellStyle);

        cell=row.createCell(2);
        cell.setCellValue("Mobile No");
        cell.setCellStyle(cellStyle);

        cell=row.createCell(3);
        cell.setCellValue("Brand");
        cell.setCellStyle(cellStyle);

        cell=row.createCell(4);
        cell.setCellValue("Remark");
        cell.setCellStyle(cellStyle);

        cell=row.createCell(5);
        cell.setCellValue("Counter Approx");
        cell.setCellStyle(cellStyle);

        cell=row.createCell(6);
        cell.setCellValue("Response");
        cell.setCellStyle(cellStyle);

        cell=row.createCell(7);
        cell.setCellValue("First Name");
        cell.setCellStyle(cellStyle);

        sheet.setColumnWidth(0,(10*200));
        sheet.setColumnWidth(1,(10*500));
        sheet.setColumnWidth(2,(10*100));
        sheet.setColumnWidth(3,(10*100));
        sheet.setColumnWidth(4,(10*500));
        sheet.setColumnWidth(5,(10*100));
        sheet.setColumnWidth(6,(10*100));
        sheet.setColumnWidth(7,(10*200));

        for (int i=0; i<reportUserWiseModels.size();i++){
            // Create a New Row for every new entry in list
            Row rowData = sheet.createRow(i + 1);
            // Create Cells for each row
            cell = rowData.createCell(0);
            cell.setCellValue(reportUserWiseModels.get(i).getCustname());
            cell = rowData.createCell(1);
            cell.setCellValue(reportUserWiseModels.get(i).getAddress());
            cell = rowData.createCell(2);
            cell.setCellValue(reportUserWiseModels.get(i).getMbNo());
            cell = rowData.createCell(3);
            cell.setCellValue(reportUserWiseModels.get(i).getBrand());
            cell = rowData.createCell(4);
            cell.setCellValue(reportUserWiseModels.get(i).getRemark());
            cell = rowData.createCell(5);
            cell.setCellValue(reportUserWiseModels.get(i).getCounterApprox());
            cell = rowData.createCell(6);
            cell.setCellValue(reportUserWiseModels.get(i).getResponse());
            cell = rowData.createCell(7);
            cell.setCellValue(reportUserWiseModels.get(i).getFname());

        }
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy-hh-mm-ss");
        String currentDate = dateFormat.format(calendar.getTime());
        String excelPath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).toString();
        File file = new File(excelPath,currentDate+".xls");
        FileOutputStream outputStream =null;

        try {
            outputStream=new FileOutputStream(file);
            wb.write(outputStream);
            Toast.makeText(ReportUserWiseActivity.this,"Excel Created Successfully",Toast.LENGTH_LONG).show();
        } catch (java.io.IOException e) {
            e.printStackTrace();

            Toast.makeText(ReportUserWiseActivity.this,"Excel Not Created Successfully",Toast.LENGTH_LONG).show();
            try {
                outputStream.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

//    create pdf
    private void createPdf(ArrayList<ReportUserWiseModel> reportUserWiseModels) throws FileNotFoundException {
        // progressDialog = createProgressDialog(getContext());
        String pdfPath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).toString();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy-hh-mm-ss");
        String currentDate = dateFormat.format(calendar.getTime());
        file = new File(pdfPath, currentDate+".pdf");//listReportViewModels.get(0).getTrdate()+

        PdfWriter pdfWriter = new PdfWriter(file);

        PdfDocument pdfDocument = new PdfDocument(pdfWriter);

        Document document = new Document(pdfDocument);

        pdfDocument.setDefaultPageSize(PageSize.A4);
        document.setMargins(20, 5, 20, 5);

//        Drawable drawable = ContextCompat.getDrawable(ReportUserWiseActivity.this,R.drawable.ic_launcher_foreground);
//
//        Bitmap bitmap = ((BitmapDrawable)drawable).getBitmap();
//        ByteArrayOutputStream outputStream1 = new ByteArrayOutputStream();
//        bitmap.compress(Bitmap.CompressFormat.PNG,100,outputStream1);
//        byte[] bitmapData = outputStream1.toByteArray();
//
//        ImageData imageData = ImageDataFactory.create(bitmapData);
//        Image image = new Image(imageData);
//        image.scaleToFit(400, 700);
//        image.setFixedPosition(100,350);
//        image.setOpacity(0.3f);



        float[] width = {150f,150f,150f,100f,100f,100f,150f,100f};
        Table table = new Table(width);
        table.setHorizontalAlignment(HorizontalAlignment.CENTER);

        table.addCell(new com.itextpdf.layout.element.Cell().add(new Paragraph("Customer Name"))
                .setBold().setFontColor(ColorConstants.WHITE).setBackgroundColor(ColorConstants.BLUE));
        table.addCell(new com.itextpdf.layout.element.Cell().add(new Paragraph("Address"))
                .setBold().setFontColor(ColorConstants.WHITE).setBackgroundColor(ColorConstants.BLUE));
        table.addCell(new com.itextpdf.layout.element.Cell().add(new Paragraph("Mobile No"))
                .setBold().setFontColor(ColorConstants.WHITE).setBackgroundColor(ColorConstants.BLUE));
        table.addCell(new com.itextpdf.layout.element.Cell().add(new Paragraph("Brand"))
                .setBold().setFontColor(ColorConstants.WHITE).setBackgroundColor(ColorConstants.BLUE));
        table.addCell(new com.itextpdf.layout.element.Cell().add(new Paragraph("Remark"))
                .setBold().setFontColor(ColorConstants.WHITE).setBackgroundColor(ColorConstants.BLUE));
        table.addCell(new com.itextpdf.layout.element.Cell().add(new Paragraph("Counter Approx"))
                .setBold().setFontColor(ColorConstants.WHITE).setBackgroundColor(ColorConstants.BLUE));
        table.addCell(new com.itextpdf.layout.element.Cell().add(new Paragraph("Response"))
                .setBold().setFontColor(ColorConstants.WHITE).setBackgroundColor(ColorConstants.BLUE));
        table.addCell(new com.itextpdf.layout.element.Cell().add(new Paragraph("First Name"))
                .setBold().setFontColor(ColorConstants.WHITE).setBackgroundColor(ColorConstants.BLUE));

        for (int i=0;i<reportUserWiseModels.size();i++){
            ReportUserWiseModel reportViewModel = reportUserWiseModels.get(i);
            table.addCell(new com.itextpdf.layout.element.Cell().add(new Paragraph(reportViewModel.getCustname())));
            table.addCell(new com.itextpdf.layout.element.Cell().add(new Paragraph(reportViewModel.getAddress())));
            table.addCell(new com.itextpdf.layout.element.Cell().add(new Paragraph(reportViewModel.getMbNo())));
            table.addCell(new com.itextpdf.layout.element.Cell().add(new Paragraph(reportViewModel.getBrand())));
            table.addCell(new com.itextpdf.layout.element.Cell().add(new Paragraph(reportViewModel.getRemark())));
            table.addCell(new com.itextpdf.layout.element.Cell().add(new Paragraph(reportViewModel.getCounterApprox())));
            table.addCell(new com.itextpdf.layout.element.Cell().add(new Paragraph(reportViewModel.getResponse())));
            table.addCell(new com.itextpdf.layout.element.Cell().add(new Paragraph(reportViewModel.getFname())));
        }
//        document.add(image);
        document.add(table);
        document.close();
        Toast.makeText(ReportUserWiseActivity.this,"pdf Created",Toast.LENGTH_LONG).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId())
        {
            case R.id.menu_logout:
                Intent intent=new Intent(ReportUserWiseActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
                break;
        }
        return true;
    }

    //    start Pagination
    @Override
    public void onFirstClicked(View view) {
        currentPageNo=1;
        int firstIndex=(currentPageNo-1);
        int secondIndex=firstIndex + countPerPage;
        if(secondIndex>= reportUserWiseModels.size()){
            secondIndex=reportUserWiseModels.size();
        }
        userWiseAdapter = new ReportUserWiseAdapter(reportUserWiseModels.subList(firstIndex,secondIndex));
        recyclerViewReport.setAdapter(userWiseAdapter);
        userWiseAdapter.notifyDataSetChanged();
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
        if(secondIndex>= reportUserWiseModels.size()){
            secondIndex=reportUserWiseModels.size();
        }
        userWiseAdapter = new ReportUserWiseAdapter(reportUserWiseModels.subList(firstIndex,secondIndex));
        recyclerViewReport.setAdapter(userWiseAdapter);
        userWiseAdapter.notifyDataSetChanged();
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
        if(secondIndex >= reportUserWiseModels.size()){
            secondIndex = reportUserWiseModels.size();
        }
        userWiseAdapter = new ReportUserWiseAdapter(reportUserWiseModels.subList(firstIndex,secondIndex));
        recyclerViewReport.setAdapter(userWiseAdapter);
        userWiseAdapter.notifyDataSetChanged();
        textViewPageNo.setText(String.valueOf(currentPageNo));
    }

    @Override
    public void onLastClicked(View view) {
        currentPageNo=totalPages;
        int firstIndex = (currentPageNo) * countPerPage;
        int secondIndex = firstIndex + countPerPage;

        if(firstIndex >= reportUserWiseModels.size()){
            firstIndex=reportUserWiseModels.size()-countPerPage;
            currentPageNo=totalPages;
        }
        if(secondIndex >= reportUserWiseModels.size()){
            secondIndex = reportUserWiseModels.size();
        }

        userWiseAdapter = new ReportUserWiseAdapter(reportUserWiseModels.subList(firstIndex,secondIndex));
        recyclerViewReport.setAdapter(userWiseAdapter);
        userWiseAdapter.notifyDataSetChanged();
        textViewPageNo.setText(String.valueOf(currentPageNo));
    }
    //    End Pagination
}