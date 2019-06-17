package kr.hubeen.sharetaxi;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class myRoomview extends AppCompatActivity {
    private Button btnLeft;
    private String id;
    private String Title;
    private String Master;
    private ListView listv;
    private JSONObject MainNode;
    private String[] lv_arr = {};
    private ArrayList<String> info;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_roomview);

        btnLeft = (Button) findViewById(R.id.RoomLeft);
        listv = (ListView) findViewById(R.id.MyRoomView);
        final TextView MyroomTitle = (TextView) findViewById(R.id.MyRoomTitle);
        final TextView MyroomMaster = (TextView) findViewById(R.id.RoomMaster);

        Intent it = getIntent();
        id = it.getStringExtra("uid");

        final Response.Listener<String> response = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try{
                    JSONObject Node = new JSONObject(response);
                    if (Node.getBoolean("success")){
                        Toast.makeText(myRoomview.this, "방을 나가셨습니다.", Toast.LENGTH_SHORT).show();
                        Intent it = new Intent(myRoomview.this, main.class);
                        it.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        it.putExtra("UserID", id);
                        startActivity(it);
                    }else{
                        Toast.makeText(myRoomview.this, "알 수 없는 이유로 인해 방을 나가는 것에 실패하였습니다.", Toast.LENGTH_SHORT).show();
                        Intent it = new Intent(myRoomview.this, main.class);
                        it.putExtra("UserID", id);
                        startActivity(it);
                    }
                }catch (JSONException e){
                    e.printStackTrace();
                }

            }
        };

        Response.Listener<String> responses = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try{
                    JSONObject Node = new JSONObject(response);
                    if (Node.getBoolean("success")){
                        Master = Node.getString("master");
                        Title = Node.getString("title");
                        MyroomTitle.setText(Title);
                        MyroomMaster.setText(Master);
                    }else{
                        Toast.makeText(myRoomview.this, "알 수 없는 이유로 인해 방을 불러오는 것에 실패하였습니다.", Toast.LENGTH_SHORT).show();
                        Intent it = new Intent(myRoomview.this, main.class);
                        it.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        it.putExtra("UserID", id);
                        startActivity(it);
                    }
                }catch (JSONException e){
                    e.printStackTrace();
                }
            }
        };

        MyRoomInfo MyroomReq = new MyRoomInfo(id, responses);
        RequestQueue rqs = Volley.newRequestQueue(myRoomview.this);
        rqs.add(MyroomReq);

        final Response.Listener<String> listResponse = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try{
                    info =  new ArrayList<String>();
                    MainNode = new JSONObject(response);
                    JSONArray Main = MainNode.getJSONArray("room");
                    //Toast.makeText(getApplicationContext(), String.valueOf(Main.length()), Toast.LENGTH_SHORT).show();

                    for (int i=0; i<Main.length(); i++) {
                        JSONObject c = Main.getJSONObject(i);
                        String user = c.getString("user").toString();
                        info.add(user);
                    }

                    Listinit();

                }catch (JSONException e){
                    e.printStackTrace();
                }
            }
        };

        TimerTask Load = new TimerTask() {
            @Override
            public void run() {
                AddList(listResponse);
            }
        };

        Timer timer = new Timer();
        timer.schedule(Load, 0, 1000);

        btnLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LeftRoomReqeust LeftRoomReq = new LeftRoomReqeust(id, response);
                RequestQueue rq = Volley.newRequestQueue(myRoomview.this);
                rq.add(LeftRoomReq);
            }
        });

    }

    public void AddList(Response.Listener<String> responses) {
        MyRoomInfo MyRoomListReq = new MyRoomInfo(id, responses);
        RequestQueue rql = Volley.newRequestQueue(myRoomview.this);
        rql.add(MyRoomListReq);
    }

    void Listinit(){
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(myRoomview.this, android.R.layout.simple_list_item_1, info);
        listv.setAdapter(arrayAdapter);
    }
}
