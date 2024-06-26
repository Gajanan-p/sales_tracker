package com.vindroidtech.saletracker.utils;

import android.app.DatePickerDialog;
import android.content.Context;
import android.widget.DatePicker;

import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.app.ActivityCompat;

import com.vindroidtech.saletracker.reports.ReportUserWiseActivity;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class DateConstant {
    public static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd ");

    public static void getDateModel(Context activityCompat, AppCompatTextView editTextToDate) {
        Calendar calendar = Calendar.getInstance(Locale.getDefault());
        DatePickerDialog datePickerDialog = new DatePickerDialog(activityCompat,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        //todo
                        Calendar newDate = Calendar.getInstance();
                        newDate.set(year, month, dayOfMonth);
                        editTextToDate.setText(simpleDateFormat.format(newDate.getTime()));
                    }
                },calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();
    }
}
