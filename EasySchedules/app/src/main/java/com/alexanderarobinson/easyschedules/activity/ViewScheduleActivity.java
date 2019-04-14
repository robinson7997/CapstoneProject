package com.alexanderarobinson.easyschedules.activity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.alexanderarobinson.easyschedules.R;
import com.alexanderarobinson.easyschedules.requests.AvailableShiftsRequest;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.List;

public class ViewScheduleActivity extends AppCompatActivity {

    private TextView userInfo;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private List<String> list;
    private List<Integer> listId;
    private List<Integer> listShiftPosted;
    private ViewScheduleRecyclerAdapter adapter;
    private String[] shifts;
    private Integer[] shiftIds;
    private Integer[] shiftsPosted;
    private Integer[] userIds;
    private Button dropShift;
    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_schedule_activity);
        //Set the layout manager for recycler view to linear layout
        recyclerView = findViewById(R.id.recyclerView);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);


        bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottomNavigationView);

        View view = bottomNavigationView.findViewById(R.id.schedule);
        view.performClick();

        userInfo = (TextView) findViewById(R.id.userInfo);

        Intent intent = getIntent();
        final String[] months = intent.getStringArrayExtra("months");
        final int[] days = intent.getIntArrayExtra("days");
        final String[] start_times = intent.getStringArrayExtra("start_times");
        final String[] end_times = intent.getStringArrayExtra("end_times");
        final String firstname = intent.getStringExtra("firstname");
        final String lastname = intent.getStringExtra("lastname");
        final int[] shift_ids = intent.getIntArrayExtra("shift_ids");
        final int[] shifts_posted = intent.getIntArrayExtra("shifts_posted");
        final int user_ids = intent.getIntExtra("user_id",-1);

        userInfo.setText("" + firstname + " " + lastname);
        String shift;
        final String company_id = intent.getIntExtra("company_id", -1)+"";

        shifts = new String[months.length];
        shiftIds = new Integer[shift_ids.length];
        shiftsPosted = new Integer[shifts_posted.length];

        for (int i = 0; i < months.length; i++) {
            shift = months[i] + "   " + days[i] + "   " + start_times[i] + " - " + end_times[i] + "";
            shifts[i] = shift;
            shiftIds[i] = shift_ids[i];
            shiftsPosted[i] = shifts_posted[i];
        }

        list = Arrays.asList(shifts);
        listId = Arrays.asList(shiftIds);
        listShiftPosted = Arrays.asList(shiftsPosted);
        adapter = new ViewScheduleRecyclerAdapter(list,listId,listShiftPosted,getApplicationContext());
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(ViewScheduleActivity.this));
        recyclerView.setAdapter(adapter);


        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull final MenuItem menuItem) {

                switch (menuItem.getItemId()) {
                    case R.id.home:
                        Intent gotoScreenVar = new Intent(ViewScheduleActivity.this, HomeActivity.class);

                        gotoScreenVar.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);

                        ViewScheduleActivity.this.startActivity(gotoScreenVar);
                        return true;
                    case R.id.schedule:
                    case R.id.AvailableShifts:
                        final Intent intent1 = new Intent(ViewScheduleActivity.this, AvailableShiftsActivity.class);
                        Response.Listener<String> responseListener = new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
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
                                            int shift_user_ids[] = new int[jsonArray.length()];

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
                                                int user_id = shift.getInt("user_id");


                                                shift_user_ids[i] = user_id;
                                                months[i] = month;
                                                days[i] = day;
                                                start_times[i] = start_time;
                                                end_times[i] = end_time;
                                                shift_ids[i] = shift_id;
                                                shifts_posted[i] = shift_posted;
                                                shift_user_ids[i] = user_id;


                                                intent1.putExtra("months", months);
                                                intent1.putExtra("days", days);
                                                intent1.putExtra("start_times", start_times);
                                                intent1.putExtra("end_times", end_times);
                                                intent1.putExtra("shift_ids", shift_ids);
                                                intent1.putExtra("shifts_posted", shifts_posted);
                                                intent1.putExtra("shift_user_ids", shift_user_ids);
                                                intent1.putExtra("user_ids", user_ids);
                                                intent1.putExtra("company_id", company_id);

                                                intent1.putExtra("firstname", firstname);
                                                intent1.putExtra("lastname", lastname);
                                            }



                                        ViewScheduleActivity.this.startActivity(intent1);
                                    }


                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        };
                        //Add request to queue
                        AvailableShiftsRequest availableShiftsRequest = new AvailableShiftsRequest(company_id,responseListener);
                        RequestQueue queue = Volley.newRequestQueue(ViewScheduleActivity.this);
                        queue.add(availableShiftsRequest);
                        return true;
                    default:
                        return false;
                }
            }
         });

    }

}
