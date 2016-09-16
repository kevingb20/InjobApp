package com.injob.injob.injobapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

public class Multamayor extends AppCompatActivity {

    private ListView lvmulta;
    String multa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multamayor);
        lvmulta = (ListView) findViewById(R.id.listmulta);
        multa= (getIntent().getExtras().getString("multa"));
        MultaConexion mul = new MultaConexion();
        mul.recoger(getApplicationContext(),lvmulta, multa);

    }
}
