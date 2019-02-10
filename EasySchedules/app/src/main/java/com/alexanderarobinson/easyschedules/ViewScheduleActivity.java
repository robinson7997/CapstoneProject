package com.alexanderarobinson.easyschedules;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import java.util.Arrays;
import java.util.List;

public class ViewScheduleActivity extends AppCompatActivity {

    private TextView userInfo;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private List<String> list;
    private ViewScheduleRecyclerAdapter adapter;
    private String[] shifts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_schedule_activity);
        //Set the layout manager for recycler view to linear layout
        recyclerView = findViewById(R.id.recyclerView);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);


        userInfo = (TextView) findViewById(R.id.userInfo);

        Intent intent = getIntent();
        final String[] months = intent.getStringArrayExtra("months");
        final int[] days = intent.getIntArrayExtra("days");
        final String[] start_times = intent.getStringArrayExtra("start_times");
        final String[] end_times = intent.getStringArrayExtra("end_times");
        final String firstname = intent.getStringExtra("firstname");
        final String lastname = intent.getStringExtra("lastname");


        userInfo.setText(""+firstname+" "+lastname);
        String shift;

        shifts = new String[10];

        for(int i = 0; i < months.length; i++){
          shift = months[i]+"   "+days[i]+"   "+start_times[i]+" - "+end_times[i]+"";
          shifts[i] = shift;
        }

        list = Arrays.asList(shifts);
        adapter = new ViewScheduleRecyclerAdapter(list);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);

    }
}
