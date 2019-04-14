package com.alexanderarobinson.easyschedules.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.alexanderarobinson.easyschedules.R;

public class GenerateCompanyCodeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.generate_company_code_activity);

        Intent intent = getIntent();
        final String company_name = intent.getStringExtra("company_name");
        final int company_id = intent.getIntExtra("company_id",-1);
        final String city = intent.getStringExtra("city");
        final String state = intent.getStringExtra("state");
        final String zipcode = intent.getStringExtra("zipcode");
        final String type = intent.getStringExtra("type");

        //Button
        final Button bRegister = (Button) findViewById(R.id.RegisterOwnerButton);

        //Label
        final TextView companyNameLB = (TextView) findViewById(R.id.CompanyNameLB);

        final EditText companyCode = (EditText) findViewById(R.id.etCompanyCode);


        companyNameLB.setText("Creating Company: "+company_name);
        companyCode.setText(company_id+"");



        bRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GenerateCompanyCodeActivity.this, LoginActivity.class);
                startActivity(intent);

            }
        });
    }
}
