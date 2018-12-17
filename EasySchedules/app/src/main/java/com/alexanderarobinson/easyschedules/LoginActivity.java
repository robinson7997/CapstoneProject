package com.alexanderarobinson.easyschedules;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);

        //Grabs users data to be logged in
        final EditText etUsername= (EditText) findViewById(R.id.UsernameEmailTF);
        final EditText etPassword = (EditText) findViewById(R.id.PasswordTF);
        //Button
        final Button bLogin = (Button) findViewById(R.id.LoginButton);
        //Links
        final TextView registerUserLink = (TextView) findViewById(R.id.RegisterUserLink);
        final TextView registerUserBusinessOwnerLink = (TextView) findViewById(R.id.RegisterBusinessAccountLink);

        //Action listeners for links
        registerUserLink.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(LoginActivity.this,RegisterActivity.class);
                startActivity(intent);
            }
        });
        etUsername.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (etUsername.getText().length() < 1) {
                    etUsername.setError("Username cannot be empty");
                } else {

                    //Login button clicked
                    bLogin.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            final String username = etUsername.getText().toString();
                            final String password = etPassword.getText().toString();

                            Response.Listener<String> responseListener = new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {

                                    try {
                                        //Convert to JSON object
                                        JSONObject jsonResponse = new JSONObject(response);
                                        boolean success = jsonResponse.getBoolean("success");
                                        //If response is successful get JSON response object
                                        if (success) {

                                            int user_id = jsonResponse.getInt("user_id");
                                            int company_id = jsonResponse.getInt("company_id");
                                            int job_id = jsonResponse.getInt("job_id");
                                            String firstname = jsonResponse.getString("firstname");
                                            String lastname = jsonResponse.getString("lastname");
                                            String username = jsonResponse.getString("username");
                                            String password = jsonResponse.getString("password");
                                            String email = jsonResponse.getString("email");
                                            int phonenumber = jsonResponse.getInt("phonenumber");
                                            //Change intent to the home activity and put user data in intent
                                            Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                                            intent.putExtra("user_id", user_id);
                                            intent.putExtra("company_id", company_id);
                                            intent.putExtra("job_id", job_id);
                                            intent.putExtra("firstname", firstname);
                                            intent.putExtra("lastname", lastname);
                                            intent.putExtra("username", username);
                                            intent.putExtra("password", password);
                                            intent.putExtra("email", email);
                                            intent.putExtra("phonenumber", phonenumber);

                                            LoginActivity.this.startActivity(intent);

                                        } else {
                                            AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                                            builder.setMessage("Login failed")
                                                    .setNegativeButton("Retry", null)
                                                    .create()
                                                    .show();
                                        }

                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                            };
                            //Add user data from text fields for login request and add to queue
                            LoginRequest loginRequest = new LoginRequest(username, password, responseListener);
                            RequestQueue queue = Volley.newRequestQueue(LoginActivity.this);
                            queue.add(loginRequest);
                        }
                    });
                }
            }
        });
        etPassword.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (etPassword.getText().length() < 1) {
                    etPassword.setError("Password cannot be empty");
                } else {

                    //Login button clicked
                    bLogin.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            final String username = etUsername.getText().toString();
                            final String password = etPassword.getText().toString();

                            Response.Listener<String> responseListener = new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {

                                    try {
                                        //Convert to JSON object
                                        JSONObject jsonResponse = new JSONObject(response);
                                        boolean success = jsonResponse.getBoolean("success");
                                        //If response is successful get JSON response object
                                        if (success) {

                                            int user_id = jsonResponse.getInt("user_id");
                                            int company_id = jsonResponse.getInt("company_id");
                                            int job_id = jsonResponse.getInt("job_id");
                                            String firstname = jsonResponse.getString("firstname");
                                            String lastname = jsonResponse.getString("lastname");
                                            String username = jsonResponse.getString("username");
                                            String password = jsonResponse.getString("password");
                                            String email = jsonResponse.getString("email");
                                            int phonenumber = jsonResponse.getInt("phonenumber");
                                            //Change intent to the home activity and put user data in intent
                                            Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                                            intent.putExtra("user_id", user_id);
                                            intent.putExtra("company_id", company_id);
                                            intent.putExtra("job_id", job_id);
                                            intent.putExtra("firstname", firstname);
                                            intent.putExtra("lastname", lastname);
                                            intent.putExtra("username", username);
                                            intent.putExtra("password", password);
                                            intent.putExtra("email", email);
                                            intent.putExtra("phonenumber", phonenumber);

                                            LoginActivity.this.startActivity(intent);

                                        } else {
                                            AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                                            builder.setMessage("Login failed")
                                                    .setNegativeButton("Retry", null)
                                                    .create()
                                                    .show();
                                        }

                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                            };
                            //Add user data from text fields for login request and add to queue
                            LoginRequest loginRequest = new LoginRequest(username, password, responseListener);
                            RequestQueue queue = Volley.newRequestQueue(LoginActivity.this);
                            queue.add(loginRequest);
                        }
                    });
                }
            }
        });
    }
}
