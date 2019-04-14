package com.alexanderarobinson.easyschedules.requests;


import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;
//Create custom job title Http request using volley
public class CreateJobTitleRequest extends StringRequest {
    //PHP file used for HTTP requests hosted on GoDaddy server using PHPMyAdmin
    private static final String CREATE_MESSAGE_REQUEST_URL="http://alexanderarobinson.com/CreateJobTitle.php";
    private Map<String,String> params;

    public CreateJobTitleRequest(String company_id, String job_title, Response.Listener<String> listener){
        super(Method.POST, CREATE_MESSAGE_REQUEST_URL, listener, null);
        params = new HashMap<>();
        params.put("company_id",company_id);
        params.put("job_title",job_title);

    }
    @Override
    public Map<String,String> getParams(){
        return params;
    }
}