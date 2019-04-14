package com.alexanderarobinson.easyschedules.activity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.alexanderarobinson.easyschedules.R;
import com.alexanderarobinson.easyschedules.requests.ViewMessageWallRequest;
import com.alexanderarobinson.easyschedules.requests.ViewScheduleRequest;
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
        final int company_id = intent.getIntExtra("company_id",-1);
        //Id sent to view schedule request
        final String id = user_id + "";
        final String companyid = company_id + "";

        welcomeMessage.setText("Hello, " + firstname + "  " + lastname);
        companyMessage.setText("Welcome to " + company_name);

        userInfo.setText("");

        final Intent intent1 = new Intent(HomeActivity.this, ViewScheduleActivity.class);
        final Intent intent2 = new Intent(HomeActivity.this, ViewMessageWallActivity.class);

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
                                        int shift_ids[] =  new int[jsonArray.length()];
                                        int shifts_posted[] = new int[jsonArray.length()];
                                        int company_ids[] = new int[jsonArray.length()];

                                        for (int i = 0; i < jsonArray.length(); i++) {
                                            JSONObject shift = jsonArray.getJSONObject(i);
                                            //Get the current shift json data
                                            int shift_id = shift.getInt("shift_id");
                                            int schedule_id = shift.getInt("schedule_id");
                                            String month = shift.getString("month");
                                            int day = shift.getInt("day");
                                            String start_time = shift.getString("start_time");
                                            String end_time = shift.getString("end_time");
                                            int shift_posted = shift.getInt("shift_posted");
                                            int company_id = shift.getInt("company_id");


                                            months[i] = month;
                                            days[i] = day;
                                            start_times[i] = start_time;
                                            end_times[i] = end_time;
                                            shift_ids[i] = shift_id;
                                            shifts_posted[i] = shift_posted;
                                            company_ids[i] = company_id;


                                            intent1.putExtra("months", months);
                                            intent1.putExtra("days", days);
                                            intent1.putExtra("start_times", start_times);
                                            intent1.putExtra("end_times", end_times);
                                            intent1.putExtra("shift_ids", shift_ids);
                                            intent1.putExtra("shifts_posted", shifts_posted);
                                            intent1.putExtra("company_ids", company_ids);
                                            intent1.putExtra("company_id", company_id);
                                            intent1.putExtra("user_id", user_id);
                                            intent1.putExtra("id", id);

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
                        //Use the users id to view schedule for view schedule request and add to queue
                        ViewScheduleRequest viewScheduleRequest = new ViewScheduleRequest(id, responseListener);
                        RequestQueue queue = Volley.newRequestQueue(HomeActivity.this);
                        queue.add(viewScheduleRequest);
                        return true;
                    case R.id.home:
                        userInfo.setText("Home with message center");
                        return true;
                    case R.id.connect:
                        Response.Listener<String> responseListener2 = new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                try {
                                    //Convert to JSON object
                                    JSONObject jsonResponse = new JSONObject(response);
                                    JSONArray jsonArray = jsonResponse.getJSONArray("response");
                                    {

                                        int[] message_ids = new int[jsonArray.length()];
                                        int[] user_ids = new int[jsonArray.length()];
                                        int[] company_ids = new int[jsonArray.length()];
                                        String[] messages = new String[jsonArray.length()];
                                        String[] datetimes = new String[jsonArray.length()];
                                        String[] firstnames = new String[jsonArray.length()];
                                        String[] lastnames = new String[jsonArray.length()];
                                        String[] names = new String[jsonArray.length()];

                                        for (int i = 0; i < jsonArray.length(); i++) {
                                            JSONObject shift = jsonArray.getJSONObject(i);
                                            //Get the current shift json data
                                            int message_id = shift.getInt("message_id");
                                            int user_id = shift.getInt("user_id");
                                            int company_id = shift.getInt("company_id");
                                            String message = shift.getString("message");
                                            String datetime = shift.getString("datetime");
                                            String firstname = shift.getString("firstname");
                                            String lastname = shift.getString("lastname");

                                            String name = firstname+" "+lastname;



                                            message_ids[i] = message_id;
                                            user_ids[i] = user_id;
                                            company_ids[i] = company_id;
                                            messages[i] = message;
                                            datetimes[i] = datetime;
                                            firstnames[i] = firstname;
                                            lastnames[i] = lastname;
                                            names[i] = name;


                                            intent2.putExtra("message_id", message_id);
                                            intent2.putExtra("user_ids", user_ids);
                                            intent2.putExtra("company_ids", company_ids);
                                            intent2.putExtra("messages", messages);
                                            intent2.putExtra("datetimes", datetimes);

                                            intent2.putExtra("company_id", company_id);
                                            intent2.putExtra("user_id", user_id);
                                            intent2.putExtra("id", id);

                                            intent2.putExtra("names", names);
                                            intent2.putExtra("firstnames", firstnames);
                                            intent2.putExtra("lastnames", lastnames);

                                            intent2.putExtra("firstname", firstname);
                                            intent2.putExtra("lastname", lastname);


                                        }

                                        HomeActivity.this.startActivity(intent2);
                                    }


                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        };
                        //Use the company id to view message wall
                        ViewMessageWallRequest viewMessageWallRequest = new ViewMessageWallRequest(companyid, responseListener2);
                        RequestQueue queue2 = Volley.newRequestQueue(HomeActivity.this);
                        queue2.add(viewMessageWallRequest);
                        return true;
                    default:
                        return false;
                }
            }
        });


    }

}

