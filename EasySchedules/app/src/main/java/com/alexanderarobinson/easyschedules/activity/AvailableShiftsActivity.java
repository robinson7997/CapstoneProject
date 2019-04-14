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

import com.alexanderarobinson.easyschedules.R;

import java.util.Arrays;
import java.util.List;

public class AvailableShiftsActivity extends AppCompatActivity {

    private String[] shifts;
    private Integer[] shiftIds;
    private Integer[] shiftsPosted;
    private Integer[] shiftUserIds;
    private Integer[] UserIds;
    private String company_id;
    private List<String> list;
    private List<Integer> listId;
    private List<Integer> listShiftPosted;
    private List<Integer> listShiftUserId;
    private List<Integer> listUserIds;
    private AvailableShiftsRecyclerAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView recyclerView;
    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.available_shifts_activity);

        //Set the layout manager for recycler view to linear layout
        recyclerView = findViewById(R.id.recyclerView2);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottomNavigationView);
        View view = bottomNavigationView.findViewById(R.id.AvailableShifts);
        view.performClick();

        Intent intent = getIntent();
        final String[] months = intent.getStringArrayExtra("months");
        final int[] days = intent.getIntArrayExtra("days");
        final String[] start_times = intent.getStringArrayExtra("start_times");
        final String[] end_times = intent.getStringArrayExtra("end_times");
        final String firstname = intent.getStringExtra("firstname");
        final String lastname = intent.getStringExtra("lastname");
        final int[] shift_ids = intent.getIntArrayExtra("shift_ids");
        final int[] shifts_posted = intent.getIntArrayExtra("shifts_posted");
        final int[] shift_user_ids = intent.getIntArrayExtra("shift_user_ids");
        final int user_id = intent.getIntExtra("user_ids",-1);
        company_id = intent.getStringExtra("company_id");

        shifts = new String[months.length];
        shiftIds = new Integer[shift_ids.length];
        shiftsPosted = new Integer[shifts_posted.length];
        shiftUserIds = new Integer[shift_user_ids.length];
        UserIds = new Integer[shift_user_ids.length];


        String shift;

        for (int i = 0; i < months.length; i++) {
            shift = months[i] + "   " + days[i] + "   " + start_times[i] + " - " + end_times[i] + "   ";
            shifts[i] = shift;
            shiftIds[i] = shift_ids[i];
            shiftsPosted[i] = shifts_posted[i];
            shiftUserIds[i] = shift_user_ids[i];
            UserIds[i] = user_id;

        }

        list = Arrays.asList(shifts);
        listId = Arrays.asList(shiftIds);
        listShiftPosted = Arrays.asList(shiftsPosted);
        listShiftUserId = Arrays.asList(shiftUserIds);
        listUserIds = Arrays.asList(UserIds);
        adapter = new AvailableShiftsRecyclerAdapter(list,listId,listShiftPosted,listShiftUserId, listUserIds,company_id, getApplicationContext());
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull final MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.schedule:
                        finish();
                        return true;
                    case R.id.home:
                        Intent gotoScreenVar = new Intent(AvailableShiftsActivity.this, HomeActivity.class);

                        gotoScreenVar.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);

                        AvailableShiftsActivity.this.startActivity(gotoScreenVar);
                        return true;
                    case R.id.AvailableShifts:
                        return true;
                    default:
                        return false;
                }
            }
        });

    }
}
