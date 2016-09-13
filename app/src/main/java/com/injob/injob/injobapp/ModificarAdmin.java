package com.injob.injob.injobapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

public class ModificarAdmin extends AppCompatActivity {

    private ListView lvModificar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modificar_admin);
        lvModificar = (ListView) findViewById(R.id.listModi);
        ListarConexion lis = new ListarConexion();
        lis.recoger(getApplicationContext(),lvModificar);
    }
    public void modificadofinaladmin(View view) {
        Intent i = new Intent(this, ModificadoFinalAdmin.class);
        startActivity(i);
    }
}
