package kr.hubeen.sharetaxi;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by hubeen on 2018. 2. 10..
 */

public class loginRequest extends StringRequest {
    final static private String URL = "http://hubeen.kr/ShareTaxi/login.php";

    private Map<String,String> param;

    public loginRequest(String uid, String upw, Response.Listener<String> listener)
    {
        super(Method.POST, URL, listener, null);
        param = new HashMap<>();
        param.put("uid", uid);
        param.put("upw", upw);
    }
    @Override
    public Map<String, String> getParams(){
        return param;
    }
}
