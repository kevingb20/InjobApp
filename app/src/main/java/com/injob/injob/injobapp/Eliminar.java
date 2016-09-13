package com.injob.injob.injobapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

public class Eliminar extends AppCompatActivity {

    private ListView lvDelete;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eliminar);
        lvDelete = (ListView) findViewById(R.id.listDelete);

        ListarConexion lis = new ListarConexion();
        lis.recoger(getApplicationContext(),lvDelete);
    }

}
