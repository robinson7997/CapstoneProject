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
import android.widget.TextView;

import com.alexanderarobinson.easyschedules.R;

import java.util.Arrays;
import java.util.List;

public class ViewPendingShiftsActivity extends AppCompatActivity {

    private ViewPendingShiftsRecyclerAdapter adapter;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private List<String> list_shift;
    private List<String> list_dates;
    private List<String> list_shift_user;
    private List<String> list_picking_up_user;
    private List<Integer> list_shift_ids;
    private List<Integer> list_user_ids;
    private String[] shifts;
    private String[] dates;
    private String[] shift_user;
    private String[] picking_up_user;
    private Integer[] shift_id;
    private Integer[] user_id;
    private String shift;
    private String date;
    private TextView title;
    private BottomNavigationView bottomNavigationView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_pending_shifts_activity);

        //Set the layout manager for recycler view to linear layout
        recyclerView = findViewById(R.id.recyclerView3);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottomNavigationView);
        View view = bottomNavigationView.findViewById(R.id.pendingShifts);
        view.performClick();

        title = (TextView) findViewById(R.id.title);
        title.setText("Pending Shifts");

        Intent intent = getIntent();
        final int[] shift_ids = intent.getIntArrayExtra("shift_ids");
        final String[] months = intent.getStringArrayExtra("months");
        final int[] days = intent.getIntArrayExtra("days");
        final String[] start_times = intent.getStringArrayExtra("start_times");
        final String[] end_times = intent.getStringArrayExtra("end_times");
        final String[] shift_firstnames = intent.getStringArrayExtra("shift_first_names");
        final String[] shift_lastnames = intent.getStringArrayExtra("shift_last_names");
        final String[] user_firstnames = intent.getStringArrayExtra("user_first_names");
        final String[] user_lastnames = intent.getStringArrayExtra("user_last_names");
        final int[] user_ids = intent.getIntArrayExtra("user_ids");
        final int[] shift_user_ids = intent.getIntArrayExtra("shift_user_ids");
        final int[] company_ids = intent.getIntArrayExtra("company_ids");
        final String company_id =  intent.getStringExtra("company_id");


        shifts = new String[months.length];
        shift_user = new String[months.length];
        picking_up_user = new String[months.length];
        shift_id = new Integer[months.length];
        user_id = new Integer[months.length];
        dates = new String[months.length];

        for (int i = 0; i < months.length; i++) {
            date =  months[i] + " " + days[i] + " ";
            shift = start_times[i] + " - " + end_times[i] + "";
            dates[i] = date;
            shifts[i] = shift;
            shift_user[i] =  "Dropped by: "+shift_firstnames[i] + " " + shift_lastnames[i] + "";
            picking_up_user[i] = "Picked up by: "+user_firstnames[i] + " " + user_lastnames[i] + "";
            shift_id[i] = shift_ids[i];
            user_id[i] = user_ids[i];

        }

        list_shift = Arrays.asList(shifts);
        list_shift_user = Arrays.asList(shift_user);
        list_picking_up_user = Arrays.asList(picking_up_user);
        list_shift_ids = Arrays.asList(shift_id);
        list_user_ids = Arrays.asList(user_id);
        list_dates = Arrays.asList(dates);


        adapter = new ViewPendingShiftsRecyclerAdapter(list_shift,list_dates,list_shift_user,list_picking_up_user,list_shift_ids,list_user_ids,getApplicationContext());
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull final MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.createSchedule:
                        finish();
                        return true;
                    case R.id.home:
                        Intent gotoScreenVar = new Intent(ViewPendingShiftsActivity.this, AdminHomeActivity.class);

                        gotoScreenVar.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);

                        ViewPendingShiftsActivity.this.startActivity(gotoScreenVar);
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
