package com.alexanderarobinson.easyschedules.requests;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;
public class ViewJobTitlesRequest extends StringRequest {
    //PHP file used for HTTP requests hosted on GoDaddy server using PHPMyAdmin
    private static final String CREATE_MESSAGE_REQUEST_URL="http://alexanderarobinson.com/ViewJobTitles.php";
    private Map<String,String> params;

    public ViewJobTitlesRequest(String company_id, Response.Listener<String> listener){
        super(Method.POST, CREATE_MESSAGE_REQUEST_URL, listener, null);
        params = new HashMap<>();
        params.put("company_id",company_id);

    }
    @Override
    public Map<String,String> getParams(){
        return params;
    }
}