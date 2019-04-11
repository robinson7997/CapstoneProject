package com.alexanderarobinson.easyschedules;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

public class AdminHomeActivity extends AppCompatActivity {

    private TextView welcomeMessage;
    private TextView companyMessage;
    private BottomNavigationView bottomNavigationView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_home_activity);

        Intent intent = getIntent();
        final int user_id = intent.getIntExtra("user_id", -1);
        final String firstname = intent.getStringExtra("firstname");
        final String lastname = intent.getStringExtra("lastname");
        final String username = intent.getStringExtra("username");
        final String company_name = intent.getStringExtra("company_name");
        final int permission_level = intent.getIntExtra("permission_level", -1);
        final int company_id = intent.getIntExtra("company_id", -1);
        final String id = user_id+"";

        //Change bottom nav bar button to clicked as home
        bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottomNavigationView);
        View view = bottomNavigationView.findViewById(R.id.createSchedule);
        view.performClick();

        welcomeMessage = (TextView) findViewById(R.id.WelcomeMessage);
        companyMessage = (TextView) findViewById(R.id.CompanyMessage);

        welcomeMessage.setText("Hello Admin, " + firstname + "  " + lastname);
        companyMessage.setText("Welcome to " + company_name);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull final MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.createSchedule:
                        final Intent intent = new Intent(AdminHomeActivity.this, SelectScheduleActivity.class);
                        intent.putExtra("company_id", company_id);
                        AdminHomeActivity.this.startActivity(intent);
                        return true;
                    case R.id.home:
                        return true;
                    case R.id.connect:
                        final Intent intent1 = new Intent(AdminHomeActivity.this, Connect.class);
                        intent1.putExtra("company_id", company_id);
                        intent1.putExtra("id", id);
                        AdminHomeActivity.this.startActivity(intent1);
                        return true;
                    default:
                        return false;
                }


            }
        });
    }
}
