package com.hoanh.androidapp;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TabHost;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;

public class Assignment4 extends AppCompatActivity implements View.OnClickListener {

    Button setDateButton, setTimeButton;
    EditText dateView, timeView;
    private int year, month, day, hour, minute;
    TabHost tabHost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assignment4);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle(this.getIntent().getStringExtra("practiceName") + "");

        setDateButton = findViewById(R.id.setDateButton);
        setTimeButton = findViewById(R.id.setTimeButton);
        dateView = findViewById(R.id.dateView);
        timeView = findViewById(R.id.timeView);

        setDateButton.setOnClickListener(this);
        setTimeButton.setOnClickListener(this);

        // set date time default
        final Calendar calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);
        hour = calendar.get(Calendar.HOUR_OF_DAY);
        minute = calendar.get(Calendar.MINUTE);

        // tabs
        tabHost = findViewById(R.id.tabHost);
        tabHost.setup();
        TabHost.TabSpec tabSpec = tabHost.newTabSpec("tab1");
        tabSpec.setContent(R.id.tab1);
        tabSpec.setIndicator("Clock");
        tabHost.addTab(tabSpec);

        tabSpec = tabHost.newTabSpec("tab2");
        tabSpec.setContent(R.id.tab2);
        tabSpec.setIndicator("Login");
        tabHost.addTab(tabSpec);

        tabHost.setCurrentTab(0);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        finish();
        return true;
    }

    @Override
    public void onClick(View view) {
        if (view == setDateButton) {
            DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                    new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker datePicker, int newYear, int monthOfYear, int dayOfMonth) {
                            dateView.setText(dayOfMonth + "/" + (monthOfYear+1) + "/" + newYear);
                            year = newYear;
                            month = monthOfYear;
                            day = dayOfMonth;
                        }
                    }, year, month, day);
            datePickerDialog.show();
        }
        if (view == setTimeButton) {
            TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                    new TimePickerDialog.OnTimeSetListener() {
                        @Override
                        public void onTimeSet(TimePicker timePicker, int newHour, int newMinute) {
                            timeView.setText(newHour + ":" + newMinute);
                            hour = newHour;
                            minute = newMinute;
                        }
                    }, hour, minute, true);
            timePickerDialog.show();
        }
    }
}