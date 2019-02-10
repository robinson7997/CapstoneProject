package com.alexanderarobinson.easyschedules;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class RegisterActivity extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_activity);

        //Grab all the user data from text fields to be registered
        final EditText etCompanyId = (EditText) findViewById(R.id.CompanyCodeTF);
        final EditText etFirstName = (EditText) findViewById(R.id.FirstNameTF);
        final EditText etLastName = (EditText) findViewById(R.id.LastNameTF);
        final EditText etUsername= (EditText) findViewById(R.id.UsernameTF);
        final EditText etPassword = (EditText) findViewById(R.id.PasswordTF);
        final EditText etEmail = (EditText) findViewById(R.id.EmailTF);
        final EditText etPhoneNumber = (EditText) findViewById(R.id.PhoneNumberTF);
        //Button
        final Button bContinue = (Button) findViewById(R.id.ContinueButton);

        //Error label
        final TextView errorLB = (TextView)findViewById(R.id.ErrorLB);

        //Validate first name
        etFirstName.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(etFirstName.getText().length()<1){
                    etFirstName.setError("First name cannot be empty");
                } else {
                    //Continue button clicked
                    bContinue.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            final String companyid = etCompanyId.getText().toString();
                            final String firstname = etFirstName.getText().toString();
                            final String lastname = etLastName.getText().toString();
                            final String username = etUsername.getText().toString();
                            final String password = etPassword.getText().toString();
                            final String email = etEmail.getText().toString();
                            final int phonenumber = Integer.parseInt(etPhoneNumber.getText().toString());

                            Response.Listener<String> responseListener = new Response.Listener<String>() {
                                @Override

                                public void onResponse(String response) {
                                    try {
                                        //Convert to JSON object
                                        JSONObject jsonResponse = new JSONObject(response);
                                        boolean success = jsonResponse.getBoolean("success");
                                        //if response is successful change intent to login activity
                                        if(success){
                                            Intent intent = new Intent(RegisterActivity.this,LoginActivity.class);
                                            startActivity(intent);

                                        }else{
                                            AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
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
                            RegisterRequest registerRequest = new RegisterRequest(companyid,firstname, lastname, username, password, email, phonenumber, responseListener);
                            RequestQueue queue = Volley.newRequestQueue(RegisterActivity.this);
                            queue.add(registerRequest);

                        }
                    });

                }
            }

        });
        //Validate last name
        etLastName.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(etLastName.getText().length()<1) {
                    etLastName.setError("Last name cannot be empty");
                } else {
                    //Continue button clicked
                    bContinue.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            final String companyid = etCompanyId.getText().toString();
                            final String firstname = etFirstName.getText().toString();
                            final String lastname = etLastName.getText().toString();
                            final String username = etUsername.getText().toString();
                            final String password = etPassword.getText().toString();
                            final String email = etEmail.getText().toString();
                            final int phonenumber = Integer.parseInt(etPhoneNumber.getText().toString());

                            Response.Listener<String> responseListener = new Response.Listener<String>() {
                                @Override

                                public void onResponse(String response) {
                                    try {
                                        //Convert to JSON object
                                        JSONObject jsonResponse = new JSONObject(response);
                                        boolean success = jsonResponse.getBoolean("success");
                                        //if response is successful change intent to login activity
                                        if(success){
                                            Intent intent = new Intent(RegisterActivity.this,LoginActivity.class);
                                            startActivity(intent);

                                        }else{
                                            AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
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
                            RegisterRequest registerRequest = new RegisterRequest(companyid,firstname, lastname, username, password, email, phonenumber, responseListener);
                            RequestQueue queue = Volley.newRequestQueue(RegisterActivity.this);
                            queue.add(registerRequest);

                        }
                    });

                }
            }

        });
        //Validate username
        etUsername.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (etUsername.getText().length() < 1) {
                    etUsername.setError("Username cannot be empty");
                }
                if (etUsername.getText().length() != 0 && etUsername.getText().length() < 4){
                    etUsername.setError("Username cannot be less than 4 characters");
                }
                else {
                    //Continue button clicked
                    bContinue.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            final String companyid = etCompanyId.getText().toString();
                            final String firstname = etFirstName.getText().toString();
                            final String lastname = etLastName.getText().toString();
                            final String username = etUsername.getText().toString();
                            final String password = etPassword.getText().toString();
                            final String email = etEmail.getText().toString();
                            final int phonenumber = Integer.parseInt(etPhoneNumber.getText().toString());

                            Response.Listener<String> responseListener = new Response.Listener<String>() {
                                @Override

                                public void onResponse(String response) {
                                    try {
                                        //Convert to JSON object
                                        JSONObject jsonResponse = new JSONObject(response);
                                        boolean success = jsonResponse.getBoolean("success");
                                        //if response is successful change intent to login activity
                                        if(success){
                                            Intent intent = new Intent(RegisterActivity.this,LoginActivity.class);
                                            startActivity(intent);

                                        }else{
                                            AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
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
                            RegisterRequest registerRequest = new RegisterRequest(companyid,firstname, lastname, username, password, email, phonenumber, responseListener);
                            RequestQueue queue = Volley.newRequestQueue(RegisterActivity.this);
                            queue.add(registerRequest);

                        }
                    });

                }
            }

        });

        //Validate password
        etPassword.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (etPassword.getText().length() < 1) {
                    etPassword.setError("Password cannot be empty");
                }
                if (etPassword.getText().length() != 0 && etPassword.getText().length() < 4){
                    etPassword.setError("Password cannot be less than 4 characters");
                }
                else {
                    //Continue button clicked
                    bContinue.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            final String companyid = etCompanyId.getText().toString();
                            final String firstname = etFirstName.getText().toString();
                            final String lastname = etLastName.getText().toString();
                            final String username = etUsername.getText().toString();
                            final String password = etPassword.getText().toString();
                            final String email = etEmail.getText().toString();
                            final int phonenumber = Integer.parseInt(etPhoneNumber.getText().toString());

                            Response.Listener<String> responseListener = new Response.Listener<String>() {
                                @Override

                                public void onResponse(String response) {
                                    try {
                                        //Convert to JSON object
                                        JSONObject jsonResponse = new JSONObject(response);
                                        boolean success = jsonResponse.getBoolean("success");
                                        //if response is successful change intent to login activity
                                        if(success){
                                            Intent intent = new Intent(RegisterActivity.this,LoginActivity.class);
                                            startActivity(intent);

                                        }else{
                                            AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
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
                            RegisterRequest registerRequest = new RegisterRequest(companyid,firstname, lastname, username, password, email, phonenumber, responseListener);
                            RequestQueue queue = Volley.newRequestQueue(RegisterActivity.this);
                            queue.add(registerRequest);

                        }
                    });

                }
            }

        });
        //Validate email address
        etEmail.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (etEmail.getText().length() < 1) {
                    etEmail.setError("Email address cannot be empty");
                }
                if (etEmail.getText().length() != 0 && etEmail.getText().length() < 4){
                    etEmail.setError("Email address cannot be less than 4 characters");
                }
                else {
                    //Continue button clicked
                    bContinue.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            final String companyid = etCompanyId.getText().toString();
                            final String firstname = etFirstName.getText().toString();
                            final String lastname = etLastName.getText().toString();
                            final String username = etUsername.getText().toString();
                            final String password = etPassword.getText().toString();
                            final String email = etEmail.getText().toString();
                            final int phonenumber = Integer.parseInt(etPhoneNumber.getText().toString());

                            Response.Listener<String> responseListener = new Response.Listener<String>() {
                                @Override

                                public void onResponse(String response) {
                                    try {
                                        //Convert to JSON object
                                        JSONObject jsonResponse = new JSONObject(response);
                                        boolean success = jsonResponse.getBoolean("success");
                                        //if response is successful change intent to login activity
                                        if(success){
                                            Intent intent = new Intent(RegisterActivity.this,LoginActivity.class);
                                            startActivity(intent);

                                        }else{
                                            AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
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
                            RegisterRequest registerRequest = new RegisterRequest(companyid,firstname, lastname, username, password, email, phonenumber, responseListener);
                            RequestQueue queue = Volley.newRequestQueue(RegisterActivity.this);
                            queue.add(registerRequest);

                        }
                    });

                }
            }

        });
        //Validate phone number
        etPhoneNumber.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {


                    //Continue button clicked
                    bContinue.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            final String companyid = etCompanyId.getText().toString();
                            final String firstname = etFirstName.getText().toString();
                            final String lastname = etLastName.getText().toString();
                            final String username = etUsername.getText().toString();
                            final String password = etPassword.getText().toString();
                            final String email = etEmail.getText().toString();
                            final int phonenumber = Integer.parseInt(etPhoneNumber.getText().toString());

                            Response.Listener<String> responseListener = new Response.Listener<String>() {
                                @Override

                                public void onResponse(String response) {
                                    try {
                                        //Convert to JSON object
                                        JSONObject jsonResponse = new JSONObject(response);
                                        boolean success = jsonResponse.getBoolean("success");
                                        //if response is successful change intent to login activity
                                        if(success){
                                            Intent intent = new Intent(RegisterActivity.this,LoginActivity.class);
                                            startActivity(intent);

                                        }else{
                                            AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
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
                            RegisterRequest registerRequest = new RegisterRequest(companyid,firstname, lastname, username, password, email, phonenumber, responseListener);
                            RequestQueue queue = Volley.newRequestQueue(RegisterActivity.this);
                            queue.add(registerRequest);

                        }
                    });

                }


        });

    }
}
