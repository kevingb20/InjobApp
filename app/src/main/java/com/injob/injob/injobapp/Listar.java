package com.injob.injob.injobapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

public class Listar extends AppCompatActivity {

    ListView lv;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listar);


        lv = (ListView) findViewById(R.id.list);
        ListarConexion lis = new ListarConexion();
        lis.recoger(getApplicationContext(),lv);

    }

}
