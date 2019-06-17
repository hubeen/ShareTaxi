package kr.hubeen.sharetaxi;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by hubeen on 2018. 2. 17..
 */

public class joinRoomReqeust extends StringRequest {
    final static private String URL = "http://hubeen.kr/ShareTaxi/joinroom.php";

    private Map<String,String> param;

    public joinRoomReqeust(String uid, String mid, Response.Listener<String> listener)
    {
        super(Method.POST, URL, listener, null);
        param = new HashMap<>();
        param.put("uid", uid);
        param.put("mid", mid);
    }
    @Override
    public Map<String, String> getParams(){
        return param;
    }
}
