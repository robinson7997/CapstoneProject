package com.alexanderarobinson.easyschedules.activity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.alexanderarobinson.easyschedules.R;
import com.alexanderarobinson.easyschedules.requests.CreateJobTitleRequest;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class CreateCustomJobTitleActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_custom_job_title_activity);

        Intent intent = getIntent();
        final String company_id = intent.getStringExtra("company_id");

        final EditText etJobTitle = (EditText) findViewById(R.id.jobTitleTF);


        bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottomNavigationView);
        View view = bottomNavigationView.findViewById(R.id.connect);
        view.performClick();


        final Button submitB = (Button) findViewById(R.id.submitButton);

        submitB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String jobTitle = etJobTitle.getText().toString();
                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override

                    public void onResponse(String response) {
                        try {
                            //Convert to JSON object
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");
                            //if response is successful change intent to login activity
                            if(success){
                                Intent intent = new Intent(CreateCustomJobTitleActivity.this, ConnectActivity.class);
                                startActivity(intent);

                            }else{
                                AlertDialog.Builder builder = new AlertDialog.Builder(CreateCustomJobTitleActivity.this);
                                builder.setMessage("Creation failed")
                                        .setNegativeButton("Retry", null)
                                        .create()
                                        .show();
                            }

                        }catch(JSONException e){
                            e.printStackTrace();
                        }

                    }
                };
                CreateJobTitleRequest createJobTitleRequest = new CreateJobTitleRequest(company_id,jobTitle,responseListener);
                RequestQueue queue = Volley.newRequestQueue(CreateCustomJobTitleActivity.this);
                queue.add(createJobTitleRequest);

            }
        });
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull final MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.schedule:
                        return true;
                    case R.id.home:
                        Intent gotoScreenVar = new Intent(CreateCustomJobTitleActivity.this, AdminHomeActivity.class);

                        gotoScreenVar.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);

                        CreateCustomJobTitleActivity.this.startActivity(gotoScreenVar);
                        return true;
                    case R.id.connect:
                        final Intent intent = new Intent(CreateCustomJobTitleActivity.this, ConnectActivity.class);
                        intent.putExtra("company_id", company_id);
                        CreateCustomJobTitleActivity.this.startActivity(intent);
                        return true;
                    default:
                        return false;
                }
            }
        });

    }
}
