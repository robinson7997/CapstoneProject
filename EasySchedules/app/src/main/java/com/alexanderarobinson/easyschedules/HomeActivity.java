package com.alexanderarobinson.easyschedules;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_layout);

        TextView welcomeMessage = (TextView) findViewById(R.id.WelcomeMessage);

        //Get intent passed from login activity
        Intent intent = getIntent();
        int user_id = intent.getIntExtra("user_id",-1);
        String firstname = intent.getStringExtra("firstname");
        String lastname = intent.getStringExtra("firstname");
        String username = intent.getStringExtra("username");

        String message = "Hello, "+firstname+" "+lastname+" Welcome!   ID is: "+user_id;

        welcomeMessage.setText(message);

    }
}
