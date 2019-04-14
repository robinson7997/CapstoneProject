package com.alexanderarobinson.easyschedules.requests;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;
//Create message wall Http request using volley
public class CreateMessageRequest extends StringRequest {
    //PHP file used for HTTP requests hosted on GoDaddy server using PHPMyAdmin
    private static final String CREATE_MESSAGE_REQUEST_URL="http://alexanderarobinson.com/CreateMessage.php";
    private Map<String,String> params;

    public CreateMessageRequest(String user_id, String company_id, String message, String datetime, Response.Listener<String> listener){
        super(Method.POST, CREATE_MESSAGE_REQUEST_URL, listener, null);
        params = new HashMap<>();
        params.put("user_id",user_id);
        params.put("company_id",company_id);
        params.put("message",message);
        params.put("datetime",datetime);
    }
    @Override
    public Map<String,String> getParams(){
        return params;
    }
}
