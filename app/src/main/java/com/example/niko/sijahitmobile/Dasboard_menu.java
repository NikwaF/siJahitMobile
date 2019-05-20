package com.example.niko.sijahitmobile;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.util.HashMap;

public class Dasboard_menu extends AppCompatActivity {

    SessionManager sessionManager;
    private TextView nama,id,nohp,email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dasboard_menu);

        sessionManager = new SessionManager(this);


        nama = findViewById(R.id.nama);
        id = findViewById(R.id.id);
        email = findViewById(R.id.email);
        nohp = findViewById(R.id.nohp);

        HashMap<String,String> user = sessionManager.user_info();
        String nama_user = user.get(sessionManager.SESSION_NAMA);
        String id_user = user.get(sessionManager.SESSION_ID);
        String email_heeh = user.get(sessionManager.SESSION_EMAIL);
        String no_hp = user.get(sessionManager.SESSION_NOHP);

        nama.setText(nama_user);
        id.setText(id_user);
        email.setText(email_heeh);
        nohp.setText(no_hp);
    }
}
