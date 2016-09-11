package com.injob.injob.injobapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class ModificarAdmin extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modificar_admin);
    }
    public void modificadofinaladmin(View view) {
        Intent i = new Intent(this, ModificadoFinalAdmin.class);
        startActivity(i);
    }
}
