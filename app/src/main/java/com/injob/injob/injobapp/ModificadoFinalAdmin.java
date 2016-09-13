package com.injob.injob.injobapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

public class ModificadoFinalAdmin extends AppCompatActivity {

    Integer id;
    TextView editnombre2,editapellido2,editemail2,
            editcedula2,editpass2,editsueldo2,editentrada2,editsalida2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modificado_final_admin);

        editnombre2 = (TextView)findViewById(R.id.modnombre);
        editapellido2 = (TextView)findViewById(R.id.modapellido);
        editemail2 = (TextView)findViewById(R.id.addemail);
        editcedula2 = (TextView)findViewById(R.id.addcedula);
        editpass2 = (TextView)findViewById(R.id.modpass);
        editsueldo2 = (TextView)findViewById(R.id.addsueldo);
        editentrada2 = (TextView)findViewById(R.id.modentrada);
        editsalida2 = (TextView)findViewById(R.id.modsalida);

        id= Integer.valueOf(getIntent().getExtras().getString("id"));
        cargarMod();
    }

    public void actualizarEmpleadoDesdeAdmin(View view){
        Thread tr= new Thread() {
            @Override
            public void run() {
                final ModificarAdminConexion lCon = new ModificarAdminConexion();
                final String resultado = lCon.actualizarAdmin(String.valueOf(id), editnombre2.getText().toString(),editapellido2.getText().toString(),editemail2.getText().toString(),editcedula2.getText().toString() ,editpass2.getText().toString(),String.valueOf(editsueldo2.getText().toString()), String.valueOf(editentrada2.getText().toString()),String.valueOf(editsalida2.getText().toString()));
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        lCon.obtDatosJsonActualizarAdmin(resultado, getApplicationContext());
                        Intent edit = new Intent(getApplicationContext(), ListarEmpleado.class);
                        startActivity(edit);

                    }
                });
            }
        };
        tr.start();
    }

    public void cargarMod (){
        Thread tr= new Thread() {

            @Override
            public void run() {

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        editnombre2.setText(getIntent().getExtras().getString("nombre"));
                        editapellido2.setText(getIntent().getExtras().getString("apellido"));
                        editcedula2.setText(getIntent().getExtras().getString("cedula"));
                        editemail2.setText(getIntent().getExtras().getString("email"));
                        editpass2.setText(getIntent().getExtras().getString("password"));
                        editsueldo2.setText(getIntent().getExtras().getString("sueldo"));
                        editentrada2.setText(getIntent().getExtras().getString("horaEntrada"));
                        editsalida2.setText(getIntent().getExtras().getString("horaSalida"));
                    }
                });
            }
        };
        tr.start();

    }

}
