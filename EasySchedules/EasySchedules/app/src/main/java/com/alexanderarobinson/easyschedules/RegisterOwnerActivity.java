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

public class RegisterOwnerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_owner_activity);

        //Grab all the user data from text fields to be registered
        final EditText etFirstName = (EditText) findViewById(R.id.FirstNameTF);
        final EditText etLastName = (EditText) findViewById(R.id.LastNameTF);
        final EditText etUsername = (EditText) findViewById(R.id.UsernameTF);
        final EditText etPassword = (EditText) findViewById(R.id.PasswordTF);
        final EditText etEmail = (EditText) findViewById(R.id.EmailTF);
        final EditText etPhoneNumber = (EditText) findViewById(R.id.PhoneNumberTF);

        //Button
        final Button bContinue = (Button) findViewById(R.id.ContinueButton);

        //Continue button clicked
        bContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
                            if (success) {
                                Intent intent = new Intent(RegisterOwnerActivity.this, RegisterCompanyInfoActivity.class);
                                intent.putExtra("username", username);
                                startActivity(intent);

                            } else {
                                AlertDialog.Builder builder = new AlertDialog.Builder(RegisterOwnerActivity.this);
                                builder.setMessage("Register failed")
                                        .setNegativeButton("Retry", null)
                                        .create()
                                        .show();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };
                //Add user data from text fields for register request and add to queue
                RegisterOwnerRequest registerRequest = new RegisterOwnerRequest(firstname, lastname, username, password, email, phonenumber, responseListener);
                RequestQueue queue = Volley.newRequestQueue(RegisterOwnerActivity.this);
                queue.add(registerRequest);

            }
        });
    }
}