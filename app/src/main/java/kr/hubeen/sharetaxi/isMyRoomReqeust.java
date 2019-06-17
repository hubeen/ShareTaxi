package kr.hubeen.sharetaxi;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by hubeen on 2018. 2. 11..
 */

public class isMyRoomReqeust extends StringRequest {
    final static private String URL = "http://hubeen.kr/ShareTaxi/Ismyroom.php";

    private Map<String,String> param;

    public isMyRoomReqeust(String uid, Response.Listener<String> listener)
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

