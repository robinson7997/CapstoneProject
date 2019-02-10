package com.alexanderarobinson.easyschedules;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;

public class CreateScheduleActivity extends AppCompatActivity {

    //Time Picker
    private TextView tv;
    private Calendar currentTime;
    private int hour, minute;
    private String format;

    //Date Picker
    private Button mSelectDate;
    private DatePickerDialog.OnDateSetListener mDateSetListener;
    private String monthFormat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_schedule_activity);

        //Grab all the shift data from text fields and text views to be created
        final EditText etUserId = (EditText) findViewById(R.id.EmployeeIdTF);
        final TextView etMonth = (TextView) findViewById(R.id.MonthLB);
        final TextView etDay = (TextView) findViewById(R.id.DayLB);
        final TextView etStartTime = (TextView) findViewById(R.id.StartTimeTF);
        final TextView etEndTime = (TextView) findViewById(R.id.EndTimeTF);

        //Button
        final Button bSubmit = (Button) findViewById(R.id.SubmitButton);

        currentTime = Calendar.getInstance();

        hour = currentTime.get(Calendar.HOUR_OF_DAY);
        minute = currentTime.get(Calendar.MINUTE);

        selectedTimeFormat(hour);

        etStartTime.setText(hour + " : " + minute + " "+ format);
        etEndTime.setText(hour + " : " + minute + " "+ format);

        mSelectDate = (Button) findViewById(R.id.SelectDateButton);

        mSelectDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();

                int year  = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(CreateScheduleActivity.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        mDateSetListener,
                        year,month,day);
                        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                        dialog.show();
            }
        });

        etStartTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                TimePickerDialog timePickerDialog = new TimePickerDialog(CreateScheduleActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        selectedTimeFormat(hourOfDay);
                        //Formats hour of day from military time zone
                        if(hourOfDay == 0){
                            hourOfDay = hourOfDay + 12;
                        } else if(hourOfDay == 12){
                        } else if (hourOfDay > 12){
                            hourOfDay = hourOfDay - 12;
                        } else {
                        }
                        etStartTime.setText(hourOfDay + " : " + minute + " "+ format);
                    }
                }, hour, minute, true);
                timePickerDialog.show();
            }
        });
        etEndTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                TimePickerDialog timePickerDialog = new TimePickerDialog(CreateScheduleActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        selectedTimeFormat(hourOfDay);
                        //Formats hour of day from military time zone
                        if(hourOfDay == 0){
                            hourOfDay = hourOfDay + 12;
                        } else if(hourOfDay == 12){
                        } else if (hourOfDay > 12){
                            hourOfDay = hourOfDay - 12;
                        } else {
                        }
                        etEndTime.setText(hourOfDay + " : " + minute + " "+ format);
                    }
                }, hour, minute, true);
                timePickerDialog.show();
            }
        });


        bSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String user_id = etUserId.getText().toString();
                final String month = etMonth.getText().toString();
                final String day = etDay.getText().toString();
                final String start_time = etStartTime.getText().toString();
                final String end_time = etEndTime.getText().toString();



                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override

                    public void onResponse(String response) {
                        try {
                            //Convert to JSON object
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");
                            //if response is successful change intent to login activity
                            if(success){
                                Intent intent = new Intent(CreateScheduleActivity.this,SelectScheduleActivity.class);
                                startActivity(intent);

                            }else{
                                AlertDialog.Builder builder = new AlertDialog.Builder(CreateScheduleActivity.this);
                                builder.setMessage("Register failed")
                                        .setNegativeButton("Retry", null)
                                        .create()
                                        .show();
                            }

                        }catch(JSONException e){
                            e.printStackTrace();
                        }

                    }
                };
                CreateScheduleRequest  createScheduleRequest = new CreateScheduleRequest(user_id,month,day,start_time,end_time,responseListener);
                RequestQueue queue = Volley.newRequestQueue(CreateScheduleActivity.this);
                queue.add(createScheduleRequest);

            }
        });

        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                Log.d("CreateScheduleActivity", "onDateSet: date: " + year + "/" + month + "/" + dayOfMonth);
                selectedMonthFormat(month);
                etMonth.setText(monthFormat);
                etDay.setText(""+dayOfMonth);

            }
        };

    }
    public void selectedTimeFormat(int hour){
        if(hour == 0){
            format = "AM";
        } else if(hour == 12){
            format = "PM";
        } else if (hour > 12){
            format = "PM";
        } else {
            format = "AM";
        }
    }
    //Convert the month int to correct String format
    public void selectedMonthFormat(int month){
        if(month == 0){
            monthFormat = "Janurary";
        } else if(month == 1){
            monthFormat = "Feburary";
        } else if(month == 2){
            monthFormat = "March";
        }else if(month == 3){
            monthFormat = "April";
        } else if(month == 4){
            monthFormat = "May";
        }else if(month == 5){
            monthFormat = "June";
        } else if(month == 6){
            monthFormat = "July";
        } else if(month == 7){
            monthFormat = "August";
        } else if(month == 8){
            monthFormat = "September";
        } else if(month == 9){
            monthFormat = "October";
        } else if(month == 10){
            monthFormat = "November";
        } else if(month == 11){
            monthFormat = "December";
        }
    }
}
