package com.alexanderarobinson.easyschedules;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

//Drop shift Http request using volley
public class DropShiftRequest extends StringRequest{
    //PHP file used for HTTP requests hosted on GoDaddy server using PHPMyAdmin
    private static final String DROP_SHIFT_REQUEST_URL="http://alexanderarobinson.com/DropShift.php";
    private Map<String,String> params;

    public DropShiftRequest(String shift_id, Response.Listener<String> listener){
        super(Method.POST, DROP_SHIFT_REQUEST_URL, listener, null);
        params = new HashMap<>();
        params.put("shift_id",shift_id);
    }
    @Override
    public Map<String,String> getParams(){
        return params;
    }


}