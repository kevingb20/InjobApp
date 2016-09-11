package com.injob.injob.injobapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class ListarEmpleado extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listar_empleado);
    }

    public void agregar(View view) {
        Intent i = new Intent(this, Agregar.class);
        startActivity(i);
    }
    public void modificar(View view) {
        Intent i = new Intent(this, ModificarAdmin.class);
        startActivity(i);
    }
    public void eliminar(View view) {
        Intent i = new Intent(this, Eliminar.class);
        startActivity(i);
    }
    public void listar(View view) {
        Intent i = new Intent(this, Listar.class);
        startActivity(i);
    }

}
