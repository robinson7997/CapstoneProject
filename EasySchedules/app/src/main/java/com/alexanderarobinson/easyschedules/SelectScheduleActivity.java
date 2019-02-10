package com.alexanderarobinson.easyschedules;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class SelectScheduleActivity extends AppCompatActivity {


    private Button currentSchedule;
    private Button upcomingSchedule;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.select_schedule_activty);

        currentSchedule = (Button) findViewById(R.id.CurrentWeekB);
        upcomingSchedule = (Button) findViewById(R.id.NextWeekB);

        currentSchedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Intent intent = new Intent(SelectScheduleActivity.this, CreateScheduleActivity.class);
                SelectScheduleActivity.this.startActivity(intent);
            }

        });
        upcomingSchedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }

        });


    }
}
