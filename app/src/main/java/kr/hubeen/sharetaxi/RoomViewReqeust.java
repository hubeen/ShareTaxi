package kr.hubeen.sharetaxi;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by hubeen on 2018. 2. 11..
 */

public class RoomViewReqeust extends StringRequest {
    final static private String URL = "http://hubeen.kr/ShareTaxi/viewroom.php";

    private Map<String,String> param;

    public RoomViewReqeust(String place, Response.Listener<String> listener)
    {
        super(Method.POST, URL, listener, null);
        param = new HashMap<>();
        param.put("place", place);
    }
    @Override
    public Map<String, String> getParams(){
        return param;
    }
}

