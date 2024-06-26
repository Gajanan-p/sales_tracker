package com.vindroidtech.saletracker.reports;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

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
import com.vindroidtech.saletracker.dashboard.DashboardActivity;
import com.vindroidtech.saletracker.registration.RetrofitRegistrationAPI;
import com.vindroidtech.saletracker.registration.userdata.UserDataModel;
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
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class KMReportActivity extends AppCompatActivity implements OnPagination,View.OnClickListener,SelectUserListener {

    private AppCompatEditText editTextSelectUser;
    private AppCompatTextView editTextFromDate;
    private AppCompatTextView editTextToDate;
    private Calendar calendar;
    private ArrayList<UserDataModel> userDataModels;
    private SelectUserDialog selectUserDialog;
    private UserDataModel userDataModel;
    private AppCompatButton buttonSearch;
    private RecyclerView recyclerViewKMList;
    private List<GetKMdataModel> getKMdataModel;
    private GetKMDataAdapter kmAdapter;
    private AppCompatButton buttonExcel;
    private AppCompatButton buttonPdf;
    //    start Pagination
    private AppCompatButton buttonFirst;
    private AppCompatButton buttonPre;
    private AppCompatButton buttonNext;
    private AppCompatButton buttonLast;
    private AppCompatTextView textViewPageNo;
    File file;
    int currentPageNo=1;
    int countPerPage=5;
    int totalPages=0;
    //    end Pagination
    LoginDataModel loginData = AppPreference.getLoginDataPreferences(KMReportActivity.this);
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kmreport);
        editTextSelectUser = findViewById(R.id.report_user_wise_select_user);
        editTextSelectUser.setOnClickListener(this);
        editTextFromDate = findViewById(R.id.edit_from_date);
        editTextFromDate.setOnClickListener(this);
        editTextToDate = findViewById(R.id.edit_to_date);
        editTextToDate.setOnClickListener(this);
        calendar = Calendar.getInstance();
        buttonSearch = findViewById(R.id.button_search);
        buttonSearch.setOnClickListener(this);

        buttonExcel = findViewById(R.id.button_km_report_excel);
        buttonPdf = findViewById(R.id.button_km_report_pdf);
        buttonExcel.setOnClickListener(this);
        buttonPdf.setOnClickListener(this);

        String currentDate = DateConstant.simpleDateFormat.format(calendar.getTime());
        editTextToDate.setText(currentDate);
        Calendar calendar1 = Calendar.getInstance();
        calendar1.add(Calendar.DATE, -1);
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd ", Locale.getDefault());
        String yesterdayAsString = dateFormat.format(calendar1.getTime());
        editTextFromDate.setText(yesterdayAsString);
        recyclerViewKMList = findViewById(R.id.report_km_wise_recycler_view);
        recyclerViewKMList.setLayoutManager( new LinearLayoutManager(KMReportActivity.this));
        recyclerViewKMList.setHasFixedSize(true);
//        AppPreference.clearLoginDataPreferences(KMReportActivity.this);

        //    start Pagination
        buttonFirst=findViewById(R.id.report_km_wise_buttonFirst);
        buttonPre=findViewById(R.id.report_km_wise_buttonPre);
        buttonNext=findViewById(R.id.report_km_wise_buttonNext);
        buttonLast=findViewById(R.id.report_km_wise_buttonLast);
        textViewPageNo=findViewById(R.id.report_km_wise_textPageNo);

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

        RetrofitReportAPI retrofitReportAPI = RetrofitService.getRetrofit().create(RetrofitReportAPI.class);
        Call<ArrayList<GetKMdataModel>> callReportUserWiseData = retrofitReportAPI.fetchAllGetKMData(data);
        callReportUserWiseData.enqueue(new Callback<ArrayList<GetKMdataModel>>() {
            @Override
            public void onResponse(Call<ArrayList<GetKMdataModel>> call, Response<ArrayList<GetKMdataModel>> response) {
                if (response.isSuccessful()){
                    getKMdataModel = response.body();
                    if (getKMdataModel!=null) {
                        //start Pagination
                        currentPageNo=1;
                        totalPages=getKMdataModel.size()/countPerPage;
                        if(countPerPage<=getKMdataModel.size()){
                            kmAdapter = new GetKMDataAdapter(getKMdataModel.subList(currentPageNo-1,countPerPage));
                            recyclerViewKMList.setAdapter(kmAdapter);
                        }else{
                            kmAdapter = new GetKMDataAdapter(getKMdataModel);
                            recyclerViewKMList.setAdapter(kmAdapter);
                        }
                        textViewPageNo.setText(String.valueOf(currentPageNo));
                        //    end Pagination
                    }
                }
            }

            @Override
            public void onFailure(Call<ArrayList<GetKMdataModel>> call, Throwable t) {
                Log.e("Error",t.getMessage());
            }
        });
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
                Intent intent=new Intent(KMReportActivity.this, MainActivity.class);
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
        if(secondIndex>= getKMdataModel.size()){
            secondIndex=getKMdataModel.size();
        }
        kmAdapter = new GetKMDataAdapter(getKMdataModel.subList(firstIndex,secondIndex));
        recyclerViewKMList.setAdapter(kmAdapter);
        kmAdapter.notifyDataSetChanged();
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
        if(secondIndex>= getKMdataModel.size()){
            secondIndex=getKMdataModel.size();
        }
        kmAdapter = new GetKMDataAdapter(getKMdataModel.subList(firstIndex,secondIndex));
        recyclerViewKMList.setAdapter(kmAdapter);
        kmAdapter.notifyDataSetChanged();
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
        if(secondIndex >= getKMdataModel.size()){
            secondIndex = getKMdataModel.size();
        }
        kmAdapter = new GetKMDataAdapter(getKMdataModel.subList(firstIndex,secondIndex));
        recyclerViewKMList.setAdapter(kmAdapter);
        kmAdapter.notifyDataSetChanged();
        textViewPageNo.setText(String.valueOf(currentPageNo));
    }

    @Override
    public void onLastClicked(View view) {
        currentPageNo=totalPages;
        int firstIndex = (currentPageNo) * countPerPage;
        int secondIndex = firstIndex + countPerPage;
        if(firstIndex >= getKMdataModel.size()){
            firstIndex=getKMdataModel.size()-countPerPage;
            currentPageNo=totalPages;
        }
        if(secondIndex >= getKMdataModel.size()){
            secondIndex = getKMdataModel.size();
        }
        kmAdapter = new GetKMDataAdapter(getKMdataModel.subList(firstIndex,secondIndex));
        recyclerViewKMList.setAdapter(kmAdapter);
        kmAdapter.notifyDataSetChanged();
        textViewPageNo.setText(String.valueOf(currentPageNo));
    }

    private void fetchUserDataFromServer() {
        RetrofitRegistrationAPI registrationAPI = RetrofitService.getRetrofit().create(RetrofitRegistrationAPI.class);
        Call<ArrayList<UserDataModel>> apiCallUserdata = registrationAPI.fetchUserData();
        apiCallUserdata.enqueue(new Callback<ArrayList<UserDataModel>>() {
            @Override
            public void onResponse(Call<ArrayList<UserDataModel>> call, Response<ArrayList<UserDataModel>> response) {
                if (response.isSuccessful()) {
                    userDataModels = response.body();
                    selectUserDialog = new SelectUserDialog(KMReportActivity.this,
                            userDataModels,
                            KMReportActivity.this);
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
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.report_user_wise_select_user:{
                fetchUserDataFromServer();
                break;
            }

            case R.id.button_km_report_excel:{
                createExcelSheet();
                break;
            }

            case R.id.button_km_report_pdf:{
//                createPdf();
                break;
            }

            case R.id.edit_from_date: {
                DateConstant.getDateModel(KMReportActivity.this,editTextFromDate);
                break;
            }
            case R.id.edit_to_date: {
                DateConstant.getDateModel(KMReportActivity.this,editTextToDate);
                break;
            }
            case R.id.button_search:{
                if (!TextUtils.isEmpty(editTextToDate.getText().toString())
                        && !TextUtils.isEmpty(editTextFromDate.getText().toString()))
                {
                    fetchReportUserWiseData();
                }else {
                    Toast.makeText(KMReportActivity.this, "Fill all fields !", Toast.LENGTH_SHORT).show();
                }
                break;
            }
        }
    }

    @Override
    public void onUserNameClicked(UserDataModel userDataModel) {
        this.userDataModel = userDataModel;
        editTextSelectUser.setText(userDataModel.getName());
        selectUserDialog.dismiss();
    }
    //    End Pagination


//    create excel sheet ............

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
        cell.setCellValue("User Name");
        cell.setCellStyle(cellStyle);

        cell=row.createCell(1);
        cell.setCellValue("User Type");
        cell.setCellStyle(cellStyle);

        cell=row.createCell(2);
        cell.setCellValue("KM Date");
        cell.setCellStyle(cellStyle);

        cell=row.createCell(3);
        cell.setCellValue("Total KM");
        cell.setCellStyle(cellStyle);

        sheet.setColumnWidth(0,(10*500));
        sheet.setColumnWidth(1,(10*200));
        sheet.setColumnWidth(2,(10*500));
        sheet.setColumnWidth(3,(10*200));


        for (int i=0; i<getKMdataModel.size();i++){
            // Create a New Row for every new entry in list
            Row rowData = sheet.createRow(i + 1);
            // Create Cells for each row
            cell = rowData.createCell(0);
            cell.setCellValue(getKMdataModel.get(i).getUserName());
            cell = rowData.createCell(1);
            cell.setCellValue(getKMdataModel.get(i).getUsertype());
            cell = rowData.createCell(2);
            cell.setCellValue(getKMdataModel.get(i).getKmdate());
            cell = rowData.createCell(3);
            cell.setCellValue(getKMdataModel.get(i).getTotalkm());


        }
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy-hh-mm-ss");
        String currentDate = dateFormat.format(calendar.getTime());
        String excelPath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).toString();
        File file = new File(excelPath,currentDate+".xls");
        FileOutputStream outputStream =null;

        try {
            outputStream=new FileOutputStream(file);
            wb.write(outputStream);
            Toast.makeText(KMReportActivity.this,"Excel Created Successfully",Toast.LENGTH_LONG).show();
        } catch (java.io.IOException e) {
            e.printStackTrace();

            Toast.makeText(KMReportActivity.this,"Excel Not Created Successfully",Toast.LENGTH_LONG).show();
            try {
                outputStream.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

//    create pdf in all records..

    public void createPdf() {
        String pdfPath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS ).toString();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy-hh-mm-ss");
        String currentDate = dateFormat.format(calendar.getTime());
        file = new File(pdfPath, currentDate+".pdf");//listReportViewModels.get(0).getTrdate()+

        PdfWriter pdfWriter = null;
        try {
            pdfWriter = new PdfWriter(file);
            PdfDocument pdfDocument = new PdfDocument(pdfWriter);

            Document document = new Document(pdfDocument);

            pdfDocument.setDefaultPageSize(PageSize.A4);
            document.setMargins(20, 5, 20, 5);

            Drawable drawable = ContextCompat.getDrawable(KMReportActivity.this,R.drawable.ic_launcher_foreground);

            Bitmap bitmap = ((BitmapDrawable)drawable).getBitmap();
            ByteArrayOutputStream outputStream1 = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG,100,outputStream1);
            byte[] bitmapData = outputStream1.toByteArray();

            ImageData imageData = ImageDataFactory.create(bitmapData);
            Image image = new Image(imageData);
            image.scaleToFit(400, 700);
            image.setFixedPosition(100,350);
            image.setOpacity(0.3f);



            float[] width = {150f,150f,150f,300f};
            Table table = new Table(width);
            table.setHorizontalAlignment(HorizontalAlignment.CENTER);

            table.addCell(new com.itextpdf.layout.element.Cell().add(new Paragraph("User Name")).setBold().setFontColor(ColorConstants.WHITE)
                    .setBackgroundColor(ColorConstants.BLUE));
            table.addCell(new com.itextpdf.layout.element.Cell().add(new Paragraph("User Type")).setBold().setFontColor(ColorConstants.WHITE)
                    .setBackgroundColor(ColorConstants.BLUE));
            table.addCell(new com.itextpdf.layout.element.Cell().add(new Paragraph("KM Date")).setBold().setFontColor(ColorConstants.WHITE)
                    .setBackgroundColor(ColorConstants.BLUE));
            table.addCell(new com.itextpdf.layout.element.Cell().add(new Paragraph("Total KM")).setBold().setFontColor(ColorConstants.WHITE)
                    .setBackgroundColor(ColorConstants.BLUE));

            for (int i=0;i<getKMdataModel.size();i++){
                GetKMdataModel reportViewModel = getKMdataModel.get(i);
                table.addCell(new com.itextpdf.layout.element.Cell().add(new Paragraph(reportViewModel.getUserName())));
                table.addCell(new com.itextpdf.layout.element.Cell().add(new Paragraph(reportViewModel.getUsertype())));
                table.addCell(new com.itextpdf.layout.element.Cell().add(new Paragraph(reportViewModel.getKmdate())));
                table.addCell(new com.itextpdf.layout.element.Cell().add(new Paragraph(reportViewModel.getTotalkm())));
            }
            document.add(image);
            document.add(table);
            document.close();
            Toast.makeText(KMReportActivity.this,"pdf Created",Toast.LENGTH_LONG).show();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

//    share document

    public void shareDocument(){
        // Assuming you have the document path (e.g., fileUri) to share
        String documentPath = "/path/to/your/document.pdf";

// Create an Intent with the ACTION_SEND action
        Intent shareIntent = new Intent(Intent.ACTION_SEND);

// Set the type of content to share (e.g., PDF, text, image)
        shareIntent.setType("application/pdf");

// Set the document's Uri (file path) as the content to share
        Uri uri = Uri.fromFile(new File(documentPath));
        shareIntent.putExtra(Intent.EXTRA_STREAM, uri);

// Add an optional subject for the shared content
        shareIntent.putExtra(Intent.EXTRA_SUBJECT, "Document Sharing");

// Optionally, provide a message or body for the shared content
        shareIntent.putExtra(Intent.EXTRA_TEXT, "Check out this document!");

// Start the chooser dialog to let the user select the sharing app
        startActivity(Intent.createChooser(shareIntent, "Share Document via..."));

    }

}