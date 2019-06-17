package kr.hubeen.sharetaxi;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by hubeen on 2018. 2. 10..
 */

public class idckRequest extends StringRequest{

    final static private String URL = "http://hubeen.kr/ShareTaxi/idck.php";

    private Map<String,String> param;

    public idckRequest(String uid, Response.Listener<String> listener)
    {
        super(Method.POST, URL, listener, null);
        param = new HashMap<>();
        param.put("uid", uid);
    }

    @Override
    public Map<String, String> getParams(){
        return param;
    }

}
