package com.alexanderarobinson.easyschedules;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

//View available shifts Http request using volley
public class AvailableShiftsRequest extends StringRequest {
    //PHP file used for HTTP requests hosted on GoDaddy server using PHPMyAdmin
    private static final String VIEW_AVAILABLE_SHIFTS_REQUEST_URL="http://alexanderarobinson.com/ViewAvailableShifts.php";
    private Map<String,String> params;

    public AvailableShiftsRequest(String company_id,Response.Listener<String> listener){
        super(Method.POST, VIEW_AVAILABLE_SHIFTS_REQUEST_URL, listener, null);
        params = new HashMap<>();
        params.put("company_id", company_id);
    }
    @Override
    public Map<String,String> getParams(){
        return params;
    }


}

