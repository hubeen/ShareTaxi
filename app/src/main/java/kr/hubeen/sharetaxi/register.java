package kr.hubeen.sharetaxi;

import android.content.Intent;
import android.provider.DocumentsContract;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.PhoneNumberFormattingTextWatcher;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.util.Pair;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.security.KeyStore;

public class register extends AppCompatActivity {
    private boolean idckB = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        final EditText idText = (EditText) findViewById(R.id.regiID);
        final EditText pwText = (EditText) findViewById(R.id.regiPW);
        final EditText pwTextck = (EditText) findViewById(R.id.regiPWck);
        Button idck = (Button) findViewById(R.id.regiIDck);
        Button btnregi = (Button) findViewById(R.id.btnRegi);

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



        btnregi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String uid = idText.getText().toString();
                String upw = pwText.getText().toString();
                String upwck = pwTextck.getText().toString();

                int lenpw = upw.length();

                if(upw != null && upw.trim().isEmpty())
                {
                    AlertDialog.Builder builder = new AlertDialog.Builder(register.this );
                    builder.setMessage("패스워드는 공백 또는 비울 수 없습니다")
                            .setNegativeButton("확인", null   )
                            .create()
                            .show();
                }
                else if(5 > lenpw || lenpw>19 )
                {

                    AlertDialog.Builder builder = new AlertDialog.Builder(register.this);
                    builder.setMessage("비밀번호를 5글자 이상 20글자 미만으로 해주세요")
                            .setNegativeButton("확인", null)
                            .create()
                            .show();

                }
                else if(!(upw.equals(upwck)))
                {
                    AlertDialog.Builder builder = new AlertDialog.Builder(register.this);
                    builder.setMessage("입력하신 비밀번호가 서로 다릅니다")
                            .setNegativeButton("확인", null)
                            .create()
                            .show();
                }
                else{
                    Response.Listener<String> responses = new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONObject jsonre = new JSONObject(response);
                                boolean suc = jsonre.getBoolean("success");

                                if (suc){
                                    /*
                                    AlertDialog.Builder builder = new AlertDialog.Builder(register.this);
                                    builder.setMessage("회원가입을 성공하였습니다")
                                        .setPositiveButton("확인", null)
                                        .create()
                                        .show();
                                     */
                                    Toast.makeText(getApplicationContext(), "회원가입을 성공하였습니다", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(register.this, login.class);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                    startActivity(intent);
                                }else{
                                    AlertDialog.Builder builder = new AlertDialog.Builder(register.this );
                                    builder.setMessage("알 수 없는 에러로 인해 가입을 실패하였습니다.")
                                            .setNegativeButton("다시 시도", null   )
                                            .create()
                                            .show();
                                    }

                                }catch (JSONException e){
                                    e.printStackTrace();
                                }


                        }
                    };
                    if (idckB == true) {
                        registerRequest regiRe = new registerRequest(uid, upw, responses);
                        RequestQueue rq = Volley.newRequestQueue(register.this);
                        rq.add(regiRe);
                    }
                    else{
                        AlertDialog.Builder builder = new AlertDialog.Builder(register.this );
                        builder.setMessage("아이디를 사용할 수 있는지 확인 체크부터 해주세요.")
                                .setNegativeButton("확인", null   )
                                .create()
                                .show();
                    }
                }
            }
        });



        idck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String uid = idText.getText().toString();
                int lenid = uid.length();

                if(uid != null && uid.trim().isEmpty())
                {
                    AlertDialog.Builder builder = new AlertDialog.Builder(register.this );
                    builder.setMessage("아이디는 공백 또는 비울 수 없습니다")
                            .setNegativeButton("확인", null   )
                            .create()
                            .show();
                }

                else if(5 > lenid || lenid>19 )
                {

                        AlertDialog.Builder builder = new AlertDialog.Builder(register.this);
                        builder.setMessage("아이디를 5글자 이상 20글자 미만으로 해주세요")
                                .setNegativeButton("확인", null)
                                .create()
                                .show();

                }
                else {
                    Response.Listener<String> responses = new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONObject jsonre = new JSONObject(response);
                                boolean suc = jsonre.getBoolean("success");

                                if (suc) {
                                    AlertDialog.Builder builder = new AlertDialog.Builder(register.this);
                                    builder.setMessage("이미 누군가 현재 이 아이디를 사용중입니다...")
                                            .setNegativeButton("확인", null)
                                            .create()
                                            .show();
                                    idckB = false;

                                } else {
                                    AlertDialog.Builder builder = new AlertDialog.Builder(register.this);
                                    builder.setMessage("이 아이디는 사용이 가능합니다")
                                            .setPositiveButton("확인", null)
                                            .create()
                                            .show();
                                    idckB = true;
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    };

                    idckRequest regiRe = new idckRequest(uid, responses);
                    RequestQueue rq = Volley.newRequestQueue(register.this);
                    rq.add(regiRe);
                }
            }
        });

    }





}