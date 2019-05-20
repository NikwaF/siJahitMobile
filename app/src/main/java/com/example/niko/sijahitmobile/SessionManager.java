package com.example.niko.sijahitmobile;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import java.util.HashMap;

public class SessionManager {

    SharedPreferences session;
    public SharedPreferences.Editor sessioneditor;
    public Context context;

    public static final String SESS_NAME = "LOGIN";
    public static final String LOGIN = "SUDAH_LOGIN";
    public static final String SESSION_ID = "ID_CUSTOMER";
    public static final String SESSION_NAMA = "NAMA";
    public static final String SESSION_EMAIL = "EMAIL";
    public static final String SESSION_NOHP = "NO_HP";
    int PRIVATE_MODE = 0;


    public SessionManager(Context context){
        this.context = context;
        session = context.getSharedPreferences(SESS_NAME,PRIVATE_MODE);
        sessioneditor = session.edit();
    }

    public void buatSession(String id_cutomer, String nama,String email, String no_hp){
        sessioneditor.putBoolean(LOGIN, true);
        sessioneditor.putString(SESSION_ID,id_cutomer);
        sessioneditor.putString(SESSION_NAMA, nama);
        sessioneditor.putString(SESSION_EMAIL, email);
        sessioneditor.putString(SESSION_NOHP, no_hp);
        sessioneditor.apply();
    }

    public boolean sudahLogin(){
        return session.getBoolean(LOGIN, false);
    }


    public HashMap<String,String> user_info(){
        HashMap<String,String> user = new HashMap<>();
        user.put(SESSION_ID, session.getString(SESSION_ID,null));
        user.put(SESSION_NAMA, session.getString(SESSION_NAMA,null));
        user.put(SESSION_EMAIL, session.getString(SESSION_EMAIL,null));
        user.put(SESSION_NOHP, session.getString(SESSION_NOHP,null));

        return  user;
    }

    public void logout(){
        sessioneditor.clear();
        sessioneditor.commit();
        Intent intent = new Intent(context, LoginActivity.class);
        context.startActivity(intent);
        ((Dasboard_menu) context).finish();
    }


}
