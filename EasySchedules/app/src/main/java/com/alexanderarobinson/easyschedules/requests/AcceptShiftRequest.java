package com.alexanderarobinson.easyschedules.requests;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

//Accept shift Http request using volley
public class AcceptShiftRequest extends StringRequest{
    //PHP file used for HTTP requests hosted on GoDaddy server using PHPMyAdmin
    private static final String ACCEPT_SHIFT_REQUEST_URL="http://alexanderarobinson.com/AcceptShift.php";
    private Map<String,String> params;

    public AcceptShiftRequest(String user_id, String shift_id, Response.Listener<String> listener){
        super(Method.POST, ACCEPT_SHIFT_REQUEST_URL, listener, null);
        params = new HashMap<>();
        params.put("user_id",user_id);
        params.put("shift_id",shift_id);
    }
    @Override
    public Map<String,String> getParams(){
        return params;
    }


}