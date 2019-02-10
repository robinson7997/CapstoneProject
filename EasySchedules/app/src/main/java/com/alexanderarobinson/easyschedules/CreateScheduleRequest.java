package com.alexanderarobinson.easyschedules;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;
//Register Http request using volley
public class CreateScheduleRequest extends StringRequest {
    //PHP file used for HTTP requests hosted on GoDaddy server using PHPMyAdmin
    private static final String CREATE_SCHEDULE_REQUEST_URL="http://alexanderarobinson.com/CreateSchedule.php";
    private Map<String,String> params;

    public CreateScheduleRequest(String user_id,String month, String day, String start_time, String end_time, Response.Listener<String> listener){
        super(Method.POST, CREATE_SCHEDULE_REQUEST_URL, listener, null);
        params = new HashMap<>();
        params.put("user_id",user_id);
        params.put("month",month);
        params.put("day",day);
        params.put("start_time",start_time);
        params.put("end_time",end_time);
    }
    @Override
    public Map<String,String> getParams(){
        return params;
    }
}
