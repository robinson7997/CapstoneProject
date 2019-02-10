package com.alexanderarobinson.easyschedules;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class HomeActivity extends AppCompatActivity {

    TextView welcomeMessage;
    TextView companyMessage;
    TextView userInfo;
    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_layout);

        welcomeMessage = (TextView) findViewById(R.id.WelcomeMessage);
        companyMessage = (TextView) findViewById(R.id.CompanyMessage);
        userInfo = (TextView) findViewById(R.id.UserInfo);
        bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottomNavigationView);
        View view = bottomNavigationView.findViewById(R.id.home);
        view.performClick();
        //Get intent passed from login activity
        Intent intent = getIntent();
        final int user_id = intent.getIntExtra("user_id", -1);
        final String firstname = intent.getStringExtra("firstname");
        final String lastname = intent.getStringExtra("lastname");
        final String username = intent.getStringExtra("username");
        final String company_name = intent.getStringExtra("company_name");
        final int permission_level = intent.getIntExtra("permission_level", -1);
        //Id sent to view schedule request
        final String id = user_id + "";

        welcomeMessage.setText("Hello, " + firstname + "  " + lastname);
        companyMessage.setText("Welcome to " + company_name);

        userInfo.setText("");

        final Intent intent1 = new Intent(HomeActivity.this, ViewScheduleActivity.class);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull final MenuItem menuItem) {

                switch (menuItem.getItemId()) {
                    case R.id.schedule:
                        Response.Listener<String> responseListener = new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                userInfo.setText("");
                                try {
                                    //Convert to JSON object
                                    JSONObject jsonResponse = new JSONObject(response);
                                    JSONArray jsonArray = jsonResponse.getJSONArray("response");
                                    {

                                        String[] months = new String[jsonArray.length()];
                                        int[] days = new int[jsonArray.length()];
                                        String[] start_times = new String[jsonArray.length()];
                                        String[] end_times = new String[jsonArray.length()];
                                        for (int i = 0; i < jsonArray.length(); i++) {
                                            JSONObject shift = jsonArray.getJSONObject(i);

                                            //Get the current shift json data
                                            int shift_id = shift.getInt("shift_id");
                                            int schedule_id = shift.getInt("schedule_id");
                                            String month = shift.getString("month");
                                            int day = shift.getInt("day");
                                            String start_time = shift.getString("start_time");
                                            String end_time = shift.getString("end_time");
                                            String shift_posted = shift.getString("shift_posted");

                                            months[i] = month;
                                            days[i] = day;
                                            start_times[i] = start_time;
                                            end_times[i] = end_time;

                                            intent1.putExtra("months", months);
                                            intent1.putExtra("days", days);
                                            intent1.putExtra("start_times", start_times);
                                            intent1.putExtra("end_times", end_times);
                                            intent1.putExtra("firstname", firstname);
                                            intent1.putExtra("lastname", lastname);
                                        }

                                        HomeActivity.this.startActivity(intent1);
                                    }


                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        };
                        //Add user data from text fields for login request and add to queue
                        ViewScheduleRequest viewScheduleRequest = new ViewScheduleRequest(id, responseListener);
                        RequestQueue queue = Volley.newRequestQueue(HomeActivity.this);
                        queue.add(viewScheduleRequest);
                        return true;
                    case R.id.home:
                        userInfo.setText("Home with message center");
                        return true;
                    case R.id.connect:
                        userInfo.setText("Connect Center");
                        return true;
                    default:
                        return false;
                }
            }
        });


    }

}

