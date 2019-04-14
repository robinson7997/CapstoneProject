package com.alexanderarobinson.easyschedules.activity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import com.alexanderarobinson.easyschedules.R;
import com.alexanderarobinson.easyschedules.requests.CreateMessageRequest;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

public class ViewMessageWallAdminActivity extends AppCompatActivity {

    private ViewMessageWallRecyclerAdapter adapter;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private List<String> userinfo;
    private List<String> datetime;
    private List<String> message;
    private BottomNavigationView bottomNavigationView;
    private Button createButton;
    private Calendar currentTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_message_wall_admin_activity);

        createButton = (Button) findViewById(R.id.AddMessageButton);

        bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottomNavigationView1);
        View view = bottomNavigationView.findViewById(R.id.connect);
        view.performClick();


        Intent intent = getIntent();
        final int[] message_ids = intent.getIntArrayExtra("message_ids");
        final int[] user_ids = intent.getIntArrayExtra("user_ids");
        final int[] company_ids = intent.getIntArrayExtra("company_ids");
        final String[] messages = intent.getStringArrayExtra("messages");
        final String[] datetimes = intent.getStringArrayExtra("datetimes");
        final String[] firstnames = intent.getStringArrayExtra("firstnames");
        final String[] lastnames = intent.getStringArrayExtra("lastnames");
        final String[] names = intent.getStringArrayExtra("names");
        final int user_id = intent.getIntExtra("user_id", -0);
        final int company_id = intent.getIntExtra("company_id", -0);
        final String id = intent.getStringExtra("id");


        //Set the layout manager for recycler view to linear layout
        recyclerView = findViewById(R.id.recyclerView4);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        userinfo = Arrays.asList(names);
        datetime = Arrays.asList(datetimes);
        message = Arrays.asList(messages);
        adapter = new ViewMessageWallRecyclerAdapter(userinfo, datetime, message, getApplicationContext());
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);

        //Retrieve message from Text field
        final EditText etMessage = (EditText) findViewById(R.id.MessageTF);

        createButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                String newMessage = etMessage.getText().toString();
                etMessage.setText("");

                String userid = user_id + "";
                String companyid = company_id + "";

                currentTime = Calendar.getInstance();
                //Get Date and time
                int month = currentTime.get(Calendar.MONTH) + 1;
                int day = currentTime.get(Calendar.DAY_OF_MONTH);
                int year = currentTime.get(Calendar.YEAR);
                int hour = currentTime.get(Calendar.HOUR);
                int minute = currentTime.get(Calendar.MINUTE);
                int second = currentTime.get(Calendar.SECOND);
                //Formats the date and time to the correct format
                if (hour == 0) {
                    hour = hour + 12;
                } else if (hour == 12) {
                } else if (hour > 12) {
                    hour = hour - 12;
                } else {
                }
                String m;
                if (month < 10) {
                    m = "0" + month;
                } else {
                    m = month + "";
                }
                String s;
                if (second < 10) {
                    s = "0" + second;
                } else {
                    s = second + "";
                }
                String datetime = year + "-" + m + "-" + day + " " + hour + ":" + minute + ":" + s;

                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //Try adding new message to database
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");
                            //If successfully added message to message wall
                            if (success) {
                                System.out.println("successful");
                            } else {
                                //Else message creation has failed alter the user
                                AlertDialog.Builder builder = new AlertDialog.Builder(ViewMessageWallAdminActivity.this);
                                builder.setMessage("Creating Message Failed")
                                        .setNegativeButton("Retry", null)
                                        .create()
                                        .show();
                            }
                            //Catch JSON Exception and print stack trace
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                };
                //Add user data from text fields for create message request and add to queue
                CreateMessageRequest createMessageRequest = new CreateMessageRequest(id, companyid, newMessage, datetime, responseListener);
                RequestQueue queue = Volley.newRequestQueue(ViewMessageWallAdminActivity.this);
                queue.add(createMessageRequest);

            }
        });



        //Intent gotoScreenVar = new Intent(ViewPendingShiftsActivity.this, AdminHomeActivity.class);
        //
        //                        gotoScreenVar.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        //
        //                        ViewPendingShiftsActivity.this.startActivity(gotoScreenVar);


        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull final MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.jobtitles:
                        final Intent intent1 = new Intent(ViewMessageWallAdminActivity.this, CreateCustomJobTitleActivity.class);
                        intent1.putExtra("company_id", id);
                        ViewMessageWallAdminActivity.this.startActivity(intent1);
                        return true;
                    case R.id.home:
                        Intent gotoScreenVar = new Intent(ViewMessageWallAdminActivity.this, AdminHomeActivity.class);

                        gotoScreenVar.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);

                        ViewMessageWallAdminActivity.this.startActivity(gotoScreenVar);
                        return true;
                    case R.id.pendingShifts:
                        return true;
                    default:
                        return false;
                }
            }
        });
    }
}

