package com.alexanderarobinson.easyschedules.requests;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;
//Register Http request using volley
public class RegisterBusinessRequest extends StringRequest {
    //PHP file used for HTTP requests hosted on GoDaddy server using PHPMyAdmin
    private static final String REGISTER_REQUEST_URL="http://alexanderarobinson.com/RegisterBusiness.php";
    private Map<String,String> params;

    public  RegisterBusinessRequest(String company_name, String city, String state, String zipcode, String type, String username, Response.Listener<String> listener){
        super(Method.POST, REGISTER_REQUEST_URL, listener, null);
        params = new HashMap<>();;
        params.put("company_name",company_name);
        params.put("city",city);
        params.put("state",state);
        params.put("zipcode",zipcode);
        params.put("type",type);
        params.put("username",username);
    }
    @Override
    public Map<String,String> getParams(){
        return params;
    }
}
