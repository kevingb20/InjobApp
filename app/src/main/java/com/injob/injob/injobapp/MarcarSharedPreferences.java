package com.injob.injob.injobapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by Kevin on 15/9/16.
 */
public class MarcarSharedPreferences extends AppCompatActivity {

    public String getSharedPreferences(){

        final SharedPreferences sharedPreferences = getSharedPreferences(Login.MyPREFERENCES, Context.MODE_PRIVATE);

        return sharedPreferences.getString("HoraEntrada","");
    }

}
