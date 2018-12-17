package com.alexanderarobinson.easyschedules;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;
//Register Http request using volley
public class RegisterRequest extends StringRequest {
    //PHP file used for HTTP requests hosted on GoDaddy server using PHPMyAdmin
    private static final String REGISTER_REQUEST_URL="http://alexanderarobinson.com/Register.php";
    private Map<String,String> params;

    public RegisterRequest(String firstname, String lastname, String username, String password, String email, int phonenumber, Response.Listener<String> listener){
        super(Method.POST, REGISTER_REQUEST_URL, listener, null);
        params = new HashMap<>();
        params.put("firstname",firstname);
        params.put("lastname",lastname);
        params.put("username",username);
        params.put("password",password);
        params.put("email",email);
        params.put("phonenumber",phonenumber + "");
    }
    @Override
    public Map<String,String> getParams(){
        return params;
    }
}
