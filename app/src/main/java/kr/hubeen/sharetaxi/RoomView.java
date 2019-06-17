package kr.hubeen.sharetaxi;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Node;

import java.net.UnknownServiceException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;


public class RoomView extends AppCompatActivity {


    private JSONObject Node;
    private JSONArray NodeMain;
    private ListView listv;
    private String place;
    private String uid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room_view);

        listv = (ListView) findViewById(R.id.lstView);
        Intent it = getIntent();
        place = it.getStringExtra("place");
        uid = it.getStringExtra("uid");

        final Response.Listener<String> response = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try{
                    HashMap<String, String> info = new HashMap<>();
                    Node = new JSONObject(response);
                    if(Node.getBoolean("success")){
                        NodeMain = Node.getJSONArray("room");

                        for (int i=0; i<NodeMain.length(); i++){
                            JSONObject c = NodeMain.getJSONObject(i);
                            String master = c.getString("master").toString();
                            String title = c.getString("title").toString();
                            String date = c.getString("date").toString();
                            int count = c.getInt("count");

                            info.put(master + " " + date, title + " [" + String.valueOf(count) + "/4]");

                        }
                        initListView(info);
                    }else{
                        listv.setAdapter(null);
                    }

                }catch (JSONException e){
                    e.printStackTrace();
                }
            }
        };

        TimerTask Load = new TimerTask() {
            @Override
            public void run() {
                AddList(response);
            }
        };

        Timer timer = new Timer();
        timer.schedule(Load, 0, 1000);

        final Response.Listener<String> joinre = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject joinrespon = new JSONObject(response);
                    boolean sucs = joinrespon.getBoolean("success");

                    if(sucs){
                        AlertDialog.Builder builder = new AlertDialog.Builder(RoomView.this);
                        builder.setMessage("방 입장에 성공하였습니다.")
                                .setPositiveButton("확인", null)
                                .create()
                                .show();
                    }else{
                        AlertDialog.Builder builder = new AlertDialog.Builder(RoomView.this);
                        builder.setMessage("방을 이미 생성하셨거나 들어가있는 상태거나 방의 인원이 다 차있는 상태이므로 입장이 불가합니다")
                                .setNegativeButton("다시 시도", null)
                                .create()
                                .show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };

        listv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                HashMap<String, String> get =  (HashMap<String, String>) adapterView.getItemAtPosition(i);
                String Title = get.get("First Line").split(" \\[")[0];
                String Master = get.get("Second Line").split(" ")[0];

                joinRoomReqeust RoomjoinReq = new joinRoomReqeust(uid, Master, joinre);
                RequestQueue rqjoin = Volley.newRequestQueue(RoomView.this);
                rqjoin.add(RoomjoinReq);

            }
        });



    }

    public void AddList(Response.Listener<String> responses)
    {
        RoomViewReqeust RoomViewReq = new RoomViewReqeust(place, responses);
        RequestQueue rq = Volley.newRequestQueue(RoomView.this);
        rq.add(RoomViewReq);
    }


    public void initListView(HashMap<String, String> info){

        List<HashMap<String, String>> item = new ArrayList<>();

        SimpleAdapter adapter = new SimpleAdapter(this, item, R.layout.list_item,
                new String[]{"First Line", "Second Line"},
                new int[]{R.id.Title, R.id.UserID});

        Iterator ite = info.entrySet().iterator();
        while(ite.hasNext()){
            HashMap<String, String> reMap = new HashMap<>();
            Map.Entry pair = (Map.Entry)ite.next();
            reMap.put("First Line", pair.getValue().toString());
            reMap.put("Second Line", pair.getKey().toString());
            item.add(reMap);
        }

        listv.setAdapter(adapter);
    }

}

