package com.injob.injob.injobapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

public class InicioMulta extends AppCompatActivity {

    TextView txtmulta;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio_multa);
        txtmulta= (TextView)findViewById(R.id.txtmulta);
    }

    public void buscar (View view){
        Intent i = new Intent(this, Multamayor.class);
        String valor= txtmulta.getText().toString();
        i.putExtra("multa", valor);
        startActivity(i);
    }
}
