package com.alexanderarobinson.easyschedules.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.alexanderarobinson.easyschedules.R;

public class AssignCustomJobTitlesActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assign_custom_job_titles);

        //get the spinner from the xml.
        Spinner dropdown = findViewById(R.id.spinner1);
        Spinner dropdown1 = findViewById(R.id.spinner);

        Intent intent = getIntent();
        //create a list of job titles for the spinner.
        final String[] job_titles = intent.getStringArrayExtra("job_titles");
        final String[] names = intent.getStringArrayExtra("names");

        //create an adapter to describe how the items are displayed, adapters are used in several places in android.
        //There are multiple variations of this, but this is the basic variant.
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, job_titles);
        //set the spinners adapter to the previously created one.
        dropdown.setAdapter(adapter);

        ArrayAdapter<String> adapter1 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, names);
        //set the spinners adapter to the previously created one.
        dropdown1.setAdapter(adapter1);

    }
}
