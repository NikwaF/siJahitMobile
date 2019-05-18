package com.example.niko.sijahitmobile;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
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
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {
    Button btnRegister;
    EditText namaUser, noHp, Email, Password, Confirm, Desa, Kecamatan, Kelurahan, kodePos, detailAlamat;
    private String namauser, nohp, email, password, confirm, desa, kecamatan, kelurahan, kodepos, detailalamat;
    private String url_register = "http://192.168.43.80/sijahit/api/Register_customer";
    //private static final String KEY_STATUS = "status";
    private static final String KEY_NAMA_USER = "nama_customer";
    private static final String KEY_NO_HP = "no_hp";
    private static final String KEY_EMAIL = "email";
    private static final String KEY_PASSWORD = "password";
    private static final String KEY_DESA = "desa";
    private static final String KEY_KECAMATAN = "kecamatan";
    //private static final String KEY_CONFIRM = "password";
    private static final String KEY_KELURAHAN = "kelurahan";
    private static final String KEY_KODEPOS = "kode_pos";
    private static final String KEY_DETAILALAMAT = "detail_alamat";
    //private static final String KEY_MESSAGE = "pesan";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);


        namaUser = findViewById(R.id.txtnamaUser);
        noHp = findViewById(R.id.txtnoHp);
        Email = findViewById(R.id.txtEmail);
        Password = findViewById(R.id.txtPassword);
        Confirm = findViewById(R.id.txtconfirmPassword);
        Desa = findViewById(R.id.txtdesa);
        Kecamatan = findViewById(R.id.txtkecamatan);
        Kelurahan = findViewById(R.id.txtkelurahan);
        kodePos = findViewById(R.id.txtkodePos);
        detailAlamat = findViewById(R.id.txtdetailAlamat);
        btnRegister = findViewById(R.id.buttonRegister);

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                namauser = namaUser.getText().toString().trim();
                nohp = noHp.getText().toString().trim();
                email = Email.getText().toString().trim();
                password = Password.getText().toString().trim();
                confirm = Confirm.getText().toString().trim();
                desa = Desa.getText().toString().trim();
                kecamatan = Kecamatan.getText().toString().trim();
                kelurahan = Kelurahan.getText().toString().trim();
                kodepos = kodePos.getText().toString().trim();
                detailalamat = detailAlamat.getText().toString().trim();
                if (validateInputs()) {
                    registerUser();
                }

            }
        });

    }

    private boolean validateInputs() {
        //first we will do the validations
        if (TextUtils.isEmpty(namauser)) {
            namaUser.setError("Masukkan Nama Anda !");
            namaUser.requestFocus();
            return false;
        }

        if (TextUtils.isEmpty(nohp)) {
            noHp.setError("Masukkan No Hp Anda !");
            noHp.requestFocus();
            return false;
        }

        if (TextUtils.isEmpty(email)) {
            Email.setError("Masukkan Email Anda !");
            Email.requestFocus();
            return false;
        }

        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            Email.setError("Penulisan Email Harus Benar !");
            Email.requestFocus();
            return false;
        }

        if (TextUtils.isEmpty(password)) {
            Password.setError("Masukkan Password Anda !");
            Password.requestFocus();
            return false;
        }

        if (TextUtils.isEmpty(confirm)) {
            Confirm.setError("Masukkan Konfirmasi Password !");
            Confirm.requestFocus();
            return false;
        }
        if (!password.equals(confirm)){
            Confirm.setError("Password Harus Sesuai !");
            Confirm.requestFocus();
            return false;
        }
        if (TextUtils.isEmpty(desa)) {
            Desa.setError("Masukkan Desa Anda");
            Desa.requestFocus();
            return false;
        }
        if (TextUtils.isEmpty(kecamatan)) {
            Kecamatan.setError("Masukkan Kecamatan Anda !");
            Kecamatan.requestFocus();
            return false;
        }
        if (TextUtils.isEmpty(kelurahan)){
            Kelurahan.setError("Masukkan Kelurahan Anda !");
            Kelurahan.requestFocus();
            return false;
        }
        if (TextUtils.isEmpty(kodepos)) {
            kodePos.setError("Masukkan Kode Pos Anda !");
            kodePos.requestFocus();
            return false;
        }
        if (TextUtils.isEmpty(detailalamat)) {
            detailAlamat.setError("Masukkan Kecamatan Anda !");
            detailAlamat.requestFocus();
            return false;

        }
        return false;
    }
    private void registerUser() {

        JSONObject request = new JSONObject();
        try {
            //Populate the request parameters
            request.put(KEY_NAMA_USER, namauser);
            request.put(KEY_NO_HP, nohp);
            request.put(KEY_EMAIL, email);
            request.put(KEY_PASSWORD, password);
            request.put(KEY_DESA, desa);
            request.put(KEY_KECAMATAN, kecamatan);
            request.put(KEY_KELURAHAN, kelurahan);
            request.put(KEY_KODEPOS, kodepos);
            request.put(KEY_DETAILALAMAT, detailalamat);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        JsonObjectRequest jsArrayRequest = new JsonObjectRequest
                (Request.Method.POST, url_register, request, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            //Check if user got registered successfully
                            JSONObject jsonObject = new JSONObject((Map) response);
                            System.out.println(jsonObject);
                            String response_register = jsonObject.getString("status");

                            if(response_register.equals("false")){
                                Toast.makeText(RegisterActivity.this, "Email di pakai", Toast.LENGTH_SHORT).show();
                            } else {
                                if (response_register.equals("false")) {
//                                    erroremail.setText("Email Anda belum Terdaftar, Silahkan Daftar dulu!!");
//                                    erroremail.setVisibility(View.VISIBLE);
                                    Toast.makeText(RegisterActivity.this, "Registrasi gagal!", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(RegisterActivity.this, "Register Berhasil", Toast.LENGTH_SHORT).show();
                                }
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
                params.put("password", password);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(jsArrayRequest);
    }
}
