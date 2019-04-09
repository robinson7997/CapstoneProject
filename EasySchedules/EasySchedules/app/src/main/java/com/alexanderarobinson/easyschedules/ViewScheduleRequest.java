package com.alexanderarobinson.easyschedules;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

//View schedule Http request using volley
public class ViewScheduleRequest extends StringRequest{
    //PHP file used for HTTP requests hosted on GoDaddy server using PHPMyAdmin
    private static final String VIEW_SCHEDULE_REQUEST_URL="http://alexanderarobinson.com/ViewSchedule.php";
    private Map<String,String> params;

    public ViewScheduleRequest(String user_id, Response.Listener<String> listener){
        super(Method.POST, VIEW_SCHEDULE_REQUEST_URL, listener, null);
        params = new HashMap<>();
        params.put("user_id",user_id);
    }
    @Override
    public Map<String,String> getParams(){
        return params;
    }


}
