package kr.hubeen.sharetaxi;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
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

public class main extends AppCompatActivity {
    private String place;
    private EditText LoginID;
    private String id;
    private Button btnMake;
    private Button btnView;
    private Button btnMyview;

    @Override
    protected void onStart(){
        super.onStart();

        Response.Listener<String> response = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try{
                    JSONObject jsonRe = new JSONObject(response);
                    boolean suc = jsonRe.getBoolean("success");

                    if (suc)
                    {
                        btnMake.setVisibility(View.INVISIBLE);
                        btnMyview.setVisibility(View.VISIBLE);
                    } else {
                        btnMyview.setVisibility(View.INVISIBLE);
                        btnMake.setVisibility(View.VISIBLE);
                    }
                }catch (JSONException e)
                {
                    e.printStackTrace();
                }
            }
        };
        isMyRoomReqeust ismyroom = new isMyRoomReqeust(id, response);
        RequestQueue rq = Volley.newRequestQueue(main.this);
        rq.add(ismyroom);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        LoginID = (EditText) findViewById(R.id.loginID);
        TextView notictitle = (TextView) findViewById(R.id.txtNoticTitle);
        EditText noticts = (EditText) findViewById(R.id.txtNotics);
        Spinner places = (Spinner) findViewById(R.id.sprList);
        btnMake = (Button) findViewById(R.id.btnRoomMake);
        btnView = (Button) findViewById(R.id.btnRoomView);
        btnMyview = (Button) findViewById(R.id.btnMyRoom);
        place = places.getSelectedItem().toString();

        LoginID.setEnabled(false);
        notictitle.setEnabled(false);
        noticts.setEnabled(false);

        Intent it = getIntent();
        id = it.getStringExtra("UserID");
        final Response.Listener<String> response = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try{
                    JSONObject jsonRe = new JSONObject(response);
                    boolean suc = jsonRe.getBoolean("success");

                    if (suc)
                    {
                        btnMake.setVisibility(View.INVISIBLE);
                        btnMyview.setVisibility(View.VISIBLE);
                    } else {
                        btnMyview.setVisibility(View.INVISIBLE);
                        btnMake.setVisibility(View.VISIBLE);
                    }
                }catch (JSONException e)
                {
                    e.printStackTrace();
                }
            }
        };
        places.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                place = adapterView.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        LoginID.setText(id);


        TimerTask Load = new TimerTask() {
            @Override
            public void run() {
                isMyRoom(response);
            }
        };

        Timer timer = new Timer();
        timer.schedule(Load, 0, 1000);

        btnMake.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Response.Listener<String> response = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try{
                            JSONObject jsonRe = new JSONObject(response);
                            boolean suc = jsonRe.getBoolean("success");

                            if(suc)
                            {
                                AlertDialog.Builder builder = new AlertDialog.Builder(main.this);
                                builder.setMessage("방을 성공적으로 생성하였습니다")
                                        .setPositiveButton("확인", null)
                                        .create()
                                        .show();
                                btnMake.setVisibility(View.INVISIBLE);
                                btnMyview.setVisibility(View.VISIBLE);
                            }
                            else {
                                AlertDialog.Builder builder = new AlertDialog.Builder(main.this);
                                builder.setMessage("방을 이미 생성하셨거나 들어가있는 상태입니다")
                                        .setNegativeButton("다시 시도", null)
                                        .create()
                                        .show();
                            }
                        }catch (JSONException e)
                        {
                            e.printStackTrace();
                        }
                    }
                };
                makeroomRequest makeroomRe = new makeroomRequest(id, (place + "에서 같이가요!").toString(), place, response);
                RequestQueue rq = Volley.newRequestQueue(main.this);
                rq.add(makeroomRe);
            }
        });

        btnMyview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it = new Intent(main.this, myRoomview.class);
                it.putExtra("uid", id);
                startActivity(it);
            }
        });


        btnView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it = new Intent(main.this, RoomView.class);
                it.putExtra("place",place);
                it.putExtra("uid", id);
                startActivity(it);
                Toast.makeText(getApplicationContext(), "같이 택시탈 방들을 보러갑니다", Toast.LENGTH_SHORT).show();
            }
        });

    }

    public void isMyRoom(Response.Listener<String> responses){
        isMyRoomReqeust ismyroom = new isMyRoomReqeust(id, responses);
        RequestQueue rq = Volley.newRequestQueue(main.this);
        rq.add(ismyroom);
    }
}
