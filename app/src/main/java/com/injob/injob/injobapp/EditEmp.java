package com.injob.injob.injobapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

public class EditEmp extends AppCompatActivity {

    EditText editnombre,editapellido,editemail,
            editcedula,editpass;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_emp);


    }
    public void actualizarEmpleado(View view){
        Thread tr= new Thread() {
            @Override
            public void run() {
                final ActualizarEmpleadoConexion lCon = new ActualizarEmpleadoConexion();
                final String resultado = lCon.enviarDatosGet(editnombre.getText().toString(), editnombre.getText().toString(),editapellido.getText().toString(), editemail.getText().toString(),editcedula.getText().toString(), editpass.getText().toString());
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        lCon.obtDatosJson(resultado, getApplicationContext());

                    }
                });
            }
        };
        tr.start();
    }
}
