package com.alexanderarobinson.easyschedules;

import android.content.Context;
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

public class Connect extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.connect_activity);

        //Buttons
        final Button bViewMessageWall = (Button) findViewById(R.id.MCButton);
        final Button bCreateNewJobTitle = (Button) findViewById(R.id.CCJTButton);

        bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottomNavigationView);

        Intent intent = getIntent();
        final String id = intent.getStringExtra("id");
        final int company_id = intent.getIntExtra("company_id", -1);
        final String companyid = company_id + "";

        final Intent intent2 = new Intent(Connect.this, ViewMessageWallAdmin.class);

        bViewMessageWall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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

                                    String name = firstname + " " + lastname;

                                    System.out.println(lastname);


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

                                }

                                Connect.this.startActivity(intent2);
                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };
                //Use the company id to view message wall
                ViewMessageWallRequest viewMessageWallRequest = new ViewMessageWallRequest(companyid, responseListener2);
                RequestQueue queue2 = Volley.newRequestQueue(Connect.this);
                queue2.add(viewMessageWallRequest);
            }
        });

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull final MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.createSchedule:
                        return true;
                    case R.id.home:
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
