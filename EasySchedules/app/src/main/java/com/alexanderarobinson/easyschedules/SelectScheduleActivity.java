package com.alexanderarobinson.easyschedules;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class SelectScheduleActivity extends AppCompatActivity {


    private Button currentSchedule;
    private Button upcomingSchedule;
    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.select_schedule_activty);

        Intent intent = getIntent();

        final int company_id = intent.getIntExtra("company_id", -1);
        final String string_company_id = company_id+"";

        currentSchedule = (Button) findViewById(R.id.CurrentWeekB);
        upcomingSchedule = (Button) findViewById(R.id.NextWeekB);

        bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottomNavigationView);

        currentSchedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Intent intent = new Intent(SelectScheduleActivity.this, CreateScheduleActivity.class);
                intent.putExtra("company_id", company_id);
                SelectScheduleActivity.this.startActivity(intent);
            }

        });
        upcomingSchedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }

        });


        final Intent intent1 = new Intent(SelectScheduleActivity.this, ViewPendingShiftsActivity.class);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull final MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.createSchedule:
                        return true;
                    case R.id.home:
                        return true;
                    case R.id.pendingShifts:
                        Response.Listener<String> responseListener = new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                try {
                                    //Convert to JSON object
                                    JSONObject jsonResponse = new JSONObject(response);
                                    JSONArray jsonArray = jsonResponse.getJSONArray("response");
                                    {

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


                                        }
                                        SelectScheduleActivity.this.startActivity(intent1);

                                    }


                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        };
                        //Use the users id to view schedule for view schedule request and add to queue
                        ViewPendingShiftsRequest viewPendingShiftsRequest = new ViewPendingShiftsRequest(string_company_id, responseListener);
                        RequestQueue queue = Volley.newRequestQueue(SelectScheduleActivity.this);
                        queue.add(viewPendingShiftsRequest);
                        return true;
                    default:
                        return false;
                }


            }
        });

    }
}
