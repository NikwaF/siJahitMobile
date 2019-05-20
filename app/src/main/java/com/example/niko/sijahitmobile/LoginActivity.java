package com.example.niko.sijahitmobile;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {
    private Button btnlogin,btnregister;
    private EditText txtemail, txtpassword;
    private String url_login = "http://10.0.3.2/sijahit/api/login_customer";
    private TextView erroremail;
    SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        txtemail = (EditText)findViewById(R.id.txtemail);
        txtpassword = (EditText)findViewById(R.id.txtpassword);
        erroremail = (TextView)findViewById(R.id.errorEmail) ;
        sessionManager = new SessionManager(this);
        btnlogin = (Button)findViewById(R.id.btn_login);
        btnregister = findViewById(R.id.btn_registrasi);

        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(txtemail.getText().toString().trim().isEmpty() || txtpassword.getText().toString().trim().isEmpty()){
                    erroremail.setText("Email dan Password Harus Diisi!");
                    erroremail.setVisibility(View.VISIBLE);
                } else {
                    erroremail.setVisibility(View.GONE);
                    Login();
                }
            }
        });

        btnregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }

    private void Login(){
        final String email = txtemail.getText().toString().trim();
        final String password = txtpassword.getText().toString().trim();
        //membuat string request

        StringRequest request = new StringRequest(Request.Method.POST, url_login,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            System.out.println(jsonObject);
                            String response_login = jsonObject.getString("kode");


                            if(response_login.equals("1")){
                                String nama = jsonObject.getJSONObject("data").getString("nama");
                                String id = jsonObject.getJSONObject("data").getString("id");
                                String email = jsonObject.getJSONObject("data").getString("email");
                                String no_hp = jsonObject.getJSONObject("data").getString("no_hp");

                                sessionManager.buatSession(id,nama,email,no_hp);

                                gas_customer();
                            }
                            if(response_login.equals("2")){
                                erroremail.setText("Password Anda Salah!");
                                erroremail.setVisibility(View.VISIBLE);
                               // Toast.makeText(LoginActivity.this,"Password Anda Salah!",Toast.LENGTH_SHORT).show();
                            }
                            if(response_login.equals("3")){
                                erroremail.setText("Email Anda belum Terdaftar, Silahkan Daftar dulu!!");
                                erroremail.setVisibility(View.VISIBLE);
                              //  Toast.makeText(LoginActivity.this,"Anda Belum Mendaftar, Silahkan Daftar dulu!",Toast.LENGTH_SHORT).show();
                            }
                            if(response_login.equals("4")){
                                gas_pengukur();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        })
        {
            @Override
            protected Map<String, String> getParams(){
                Map<String,String> params = new HashMap<String, String>();
                params.put("email",email);
                params.put("password",password);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(request);
    }



    private void gas_customer(){
        Intent intent = new Intent(LoginActivity.this, Dasboard_menu.class);
        startActivity(intent);
        finish();
    }

    private void gas_pengukur(){
        Intent intent = new Intent(LoginActivity.this, Dashboard_menu_pengukur.class);
        startActivity(intent);
        finish();
    }
}
