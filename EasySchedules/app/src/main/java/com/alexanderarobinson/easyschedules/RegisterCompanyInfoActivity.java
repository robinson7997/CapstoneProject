package com.alexanderarobinson.easyschedules;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class RegisterCompanyInfoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_company_info_activity);

        Intent intent = getIntent();

        //Grab all the user data from text fields to be registered
        final EditText etCompanyName = (EditText) findViewById(R.id.CompanyNameTF);
        final EditText etCity = (EditText) findViewById(R.id.CityTF);
        final EditText etState = (EditText) findViewById(R.id.StateTF);
        final EditText etZipcode = (EditText) findViewById(R.id.ZipcodeTF);
        final EditText etType = (EditText) findViewById(R.id.TypeTF);


        final String firstname = intent.getStringExtra("firstname");
        final String lastname = intent.getStringExtra("lastname");
        final String username = intent.getStringExtra("username");;
        final String password = intent.getStringExtra("password");
        final String email = intent.getStringExtra("email");
        final int phonenumber = intent.getIntExtra("phonenumber",-1);



        //Button
        final Button bContinue = (Button) findViewById(R.id.ContinueButton);


        //Continue button clicked
        bContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String company_name = etCompanyName.getText().toString();
                final String city = etCity.getText().toString();
                final String state = etState.getText().toString();
                final String zipcode = etZipcode.getText().toString();
                final String type = etType.getText().toString();

                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override

                    public void onResponse(String response) {
                        try {
                            //Convert to JSON object
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");
                            //if response is successful change intent to login activity
                            if(success){
                                int company_id = jsonResponse.getInt("company_id");
                                Intent intent1 = new Intent(RegisterCompanyInfoActivity.this,GenerateCompanyCodeActivity.class);
                                intent1.putExtra("company_name", company_name);
                                intent1.putExtra("company_id", company_id);
                                startActivity(intent1);



                                System.out.println(company_id);

                            }else{
                                AlertDialog.Builder builder = new AlertDialog.Builder(RegisterCompanyInfoActivity.this);
                                builder.setMessage("Register failed")
                                        .setNegativeButton("Retry", null)
                                        .create()
                                        .show();
                            }

                        }catch(JSONException e){
                            e.printStackTrace();
                        }
                    }
                };
                //Add user data from text fields for register request and add to queue
                RegisterBusinessRequest registerRequest = new RegisterBusinessRequest(company_name, city, state, zipcode,type, username, responseListener);
                RequestQueue queue = Volley.newRequestQueue(RegisterCompanyInfoActivity.this);
                queue.add(registerRequest);

            }
        });


    }
}
