package com.alexanderarobinson.easyschedules.activity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import com.alexanderarobinson.easyschedules.R;
import com.alexanderarobinson.easyschedules.requests.CreateScheduleRequest;
import com.alexanderarobinson.easyschedules.requests.ViewPendingShiftsRequest;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;

public class CreateScheduleActivity extends AppCompatActivity {

    //Time Picker
    private TextView tv;
    private Calendar currentTime;
    private int hour, minute;
    private String format;
    private BottomNavigationView bottomNavigationView;

    //Date Picker
    private Button mSelectDate;
    private DatePickerDialog.OnDateSetListener mDateSetListener;
    private String monthFormat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_schedule_activity);

       bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottomNavigationView);

        Intent intent = getIntent();
        final String company_id = intent.getIntExtra("company_id", -1)+"";

        final Intent intent1 = new Intent(CreateScheduleActivity.this, ViewPendingShiftsActivity.class);

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

        if(hour == 0){
            hour = hour + 12;
        } else if(hour == 12){
        } else if (hour > 12){
            hour = hour - 12;
        } else {
        }

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
                                Intent intent = new Intent(CreateScheduleActivity.this,CreateScheduleActivity.class);
                                startActivity(intent);

                            }else{
                                AlertDialog.Builder builder = new AlertDialog.Builder(CreateScheduleActivity.this);
                                builder.setMessage("Creation failed")
                                        .setNegativeButton("Retry", null)
                                        .create()
                                        .show();
                            }

                        }catch(JSONException e){
                            e.printStackTrace();
                        }

                    }
                };
                CreateScheduleRequest createScheduleRequest = new CreateScheduleRequest(user_id,company_id,month,day,start_time,end_time,responseListener);
                RequestQueue queue = Volley.newRequestQueue(CreateScheduleActivity.this);
                queue.add(createScheduleRequest);

            }
        });

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull final MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.createSchedule:

                        return true;
                    case R.id.home:
                        finish();
                        return true;
                    case R.id.pendingShifts:
                        Response.Listener<String> responseListener = new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                try {

                                    //Convert to JSON object
                                    JSONObject jsonResponse = new JSONObject(response);
                                    if(jsonResponse.isNull("response")){
                                        AlertDialog.Builder builder = new AlertDialog.Builder(CreateScheduleActivity.this);
                                        builder.setMessage("No Pending Shifts")
                                                .setNegativeButton("Go Back", null)
                                                .create()
                                                .show();
                                    } else {
                                        JSONArray jsonArray = jsonResponse.getJSONArray("response");

                                        int pending_ids[] = new int[jsonArray.length()];
                                        int shift_ids[] = new int[jsonArray.length()];
                                        int user_ids[] =  new int[jsonArray.length()];
                                        int shift_user_ids[] = new int[jsonArray.length()];
                                        int company_ids[] = new int[jsonArray.length()];
                                        String user_first_names[] = new String[jsonArray.length()];
                                        String user_last_names[] = new String[jsonArray.length()];
                                        String shift_first_names[] = new String[jsonArray.length()];
                                        String shift_last_names[] = new String[jsonArray.length()];
                                        String months[] = new String[jsonArray.length()];
                                        int days[] = new int[jsonArray.length()];
                                        String start_times[] = new String[jsonArray.length()];
                                        String end_times[] = new String[jsonArray.length()];

                                        for (int i = 0; i < jsonArray.length(); i++) {
                                            JSONObject shift = jsonArray.getJSONObject(i);
                                            //Get the current shift json data
                                            int pending_id = shift.getInt("pending_id");
                                            int shift_id = shift.getInt("shift_id");
                                            int user_id = shift.getInt("user_id");
                                            int shift_user_id = shift.getInt("shift_user_id");
                                            int company_id = shift.getInt("company_id");
                                            String user_firstname = shift.getString("user_firstname");
                                            String user_lastname = shift.getString("user_lastname");
                                            String shift_firstname = shift.getString("shift_firstname");
                                            String shift_lastname = shift.getString("shift_lastname");
                                            String month = shift.getString("month");
                                            int day = shift.getInt("day");
                                            String start_time = shift.getString("start_time");
                                            String end_time = shift.getString("end_time");

                                            pending_ids[i] = pending_id;
                                            shift_ids[i] = shift_id;
                                            user_ids[i] = user_id;
                                            shift_user_ids[i] = shift_user_id;
                                            company_ids[i] = company_id;
                                            user_first_names[i] = user_firstname;
                                            user_last_names[i] = user_lastname;
                                            shift_first_names[i] = shift_firstname;
                                            shift_last_names[i] = shift_lastname;
                                            months[i] = month;
                                            days[i] = day;
                                            start_times[i] = start_time;
                                            end_times[i] = end_time;

                                            intent1.putExtra("pending_ids", pending_ids);
                                            intent1.putExtra("shift_ids", shift_ids);
                                            intent1.putExtra("user_ids", user_ids);
                                            intent1.putExtra("shift_user_ids", shift_user_ids);
                                            intent1.putExtra("company_ids", company_ids);
                                            intent1.putExtra("user_first_names", user_first_names);
                                            intent1.putExtra("user_last_names", user_last_names);
                                            intent1.putExtra("shift_first_names", shift_first_names);
                                            intent1.putExtra("shift_last_names", shift_last_names);
                                            intent1.putExtra("months", months);
                                            intent1.putExtra("days", days);
                                            intent1.putExtra("start_times", start_times);
                                            intent1.putExtra("end_times", end_times);

                                            intent1.putExtra("company_id", company_id);


                                        }
                                        CreateScheduleActivity.this.startActivity(intent1);

                                    }


                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }

                        };
                        //Use the users id to view schedule for view schedule request and add to queue
                        ViewPendingShiftsRequest viewPendingShiftsRequest = new ViewPendingShiftsRequest(company_id, responseListener);
                        RequestQueue queue = Volley.newRequestQueue(CreateScheduleActivity.this);
                        queue.add(viewPendingShiftsRequest);
                        return true;
                    default:
                        return false;
                }
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
