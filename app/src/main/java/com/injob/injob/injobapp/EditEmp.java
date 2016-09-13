package com.injob.injob.injobapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

public class EditEmp extends AppCompatActivity {
    Integer id;
    TextView editnombre1,editapellido1,editemail1,
            editcedula1,editpass1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_emp);
        editnombre1 = (TextView)findViewById(R.id.editnombre);
        editapellido1 = (TextView)findViewById(R.id.editapellido);
        editemail1 = (TextView)findViewById(R.id.editemail);
        editcedula1 = (TextView)findViewById(R.id.editcedula);
        editpass1 = (TextView)findViewById(R.id.editpass);
        id= getIntent().getExtras().getInt("id");
        cargar();
    }
    public void actualizarEmpleado(View view){
        Thread tr= new Thread() {
            @Override
            public void run() {
                final ActualizarEmpleadoConexion lCon = new ActualizarEmpleadoConexion();
                final String resultado = lCon.enviarDatosGet(String.valueOf(id), editnombre1.getText().toString(),editapellido1.getText().toString(),editemail1.getText().toString(), editpass1.getText().toString(),editcedula1.getText().toString());
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        lCon.obtDatosJson(resultado, getApplicationContext());
                        Intent edit = new Intent(getApplicationContext(), HomeEmpleados.class);
                        startActivity(edit);

                    }
                });
            }
        };
        tr.start();
    }
    // carga los datos que se enviaron del activity anterior en un hilo de UI
    public void cargar (){
        Thread tr= new Thread() {

            @Override
            public void run() {

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        editnombre1.setText(getIntent().getExtras().getString("nombre"));
                        editapellido1.setText(getIntent().getExtras().getString("apellido"));
                        editcedula1.setText(getIntent().getExtras().getString("cedula"));
                        editemail1.setText(getIntent().getExtras().getString("email"));
                        editpass1.setText(getIntent().getExtras().getString("password"));

                    }
                });
            }
        };
        tr.start();

    }


}
