package com.alexanderarobinson.easyschedules;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

//Pick up available shift shifts Http request using volley
public class PickUpShiftRequest extends StringRequest {
    //PHP file used for HTTP requests hosted on GoDaddy server using PHPMyAdmin
    private static final String PICK_UP_SHIFT_REQUEST_URL="http://alexanderarobinson.com/PickUpShift.php";
    private Map<String,String> params;

    public PickUpShiftRequest(String shift_id, String user_id, String user_id_1, String company_id,Response.Listener<String> listener){
        super(Method.POST,  PICK_UP_SHIFT_REQUEST_URL, listener, null);
        params = new HashMap<>();
        params.put("shift_id", shift_id);
        params.put("user_id", user_id);
        params.put("shift_user_id", user_id_1);
        params.put("company_id", company_id);
    }
    @Override
    public Map<String,String> getParams(){
        return params;
    }


}
