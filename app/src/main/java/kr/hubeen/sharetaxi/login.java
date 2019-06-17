package kr.hubeen.sharetaxi;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class login extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        final EditText idText = (EditText) findViewById(R.id.txtID);
        final EditText pwText = (EditText) findViewById(R.id.txtpw);

        Button logbtn = (Button) findViewById(R.id.btnlogin);
        Button regibtn = (Button) findViewById(R.id.btnregister);

        regibtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it = new Intent(login.this, register.class);
                startActivity(it);
            }
        });


        idText.addTextChangedListener(new TextWatcher() {
            int KeyDel;
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                idText.setOnKeyListener(new View.OnKeyListener() {
                    @Override
                    public boolean onKey(View view, int i, KeyEvent keyEvent) {
                        if (i == KeyEvent.KEYCODE_DEL)
                            KeyDel = 1;
                        return false;
                    }
                });

                if (KeyDel == 0) {
                    int len = idText.getText().length();
                    if(len == 3 || len == 8) {
                        idText.setText(idText.getText() + "-");
                        idText.setSelection(idText.getText().length());
                    }
                } else {
                    KeyDel = 0;
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });


        logbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String uid = idText.getText().toString();
                String upw = pwText.getText().toString();
                if (upw != null && upw.trim().isEmpty() || uid != null && uid.trim().isEmpty() ) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(login.this);
                    builder.setMessage("아이디나 패스워드는 공백 또는 비울 수 없습니다")
                            .setNegativeButton("다시 시도", null)
                            .create()
                            .show();
                }
                else{
                    Response.Listener<String> repons = new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try{
                                JSONObject jsonres = new JSONObject(response);
                                boolean suc = jsonres.getBoolean("success");

                                if(suc){

                                    String id = jsonres.getString("uid");
                                    /*
                                    AlertDialog.Builder builder = new AlertDialog.Builder(login.this);
                                    builder.setMessage("로그인에 성공하였습니다.")
                                            .setPositiveButton("확인", null)
                                            .create()
                                            .show();
                                            */
                                    Intent intent = new Intent(login.this, main.class);
                                    intent.putExtra("UserID", id);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

                                    Toast.makeText(getApplicationContext(), "로그인을 성공하였습니다", Toast.LENGTH_SHORT).show();
                                    startActivity(intent);
                                }else
                                {

                                    AlertDialog.Builder builder = new AlertDialog.Builder(login.this);
                                    builder.setMessage("로그인 실패!")
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
                    loginRequest logq = new loginRequest(uid, upw, repons);
                    RequestQueue req = Volley.newRequestQueue(login.this);
                    req.add(logq);

                }
            }
        });
    }
}
