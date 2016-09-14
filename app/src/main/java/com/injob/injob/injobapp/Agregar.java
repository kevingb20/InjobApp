package com.injob.injob.injobapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class Agregar extends AppCompatActivity {

    TextView nombre, apellido, email, cedula, password, sueldo, horaSalida, horaEntrada;
    String Empresa;
    String strnombre ="", strapellido="",stremail="",strcedula="",strpassword="",strsueldo="",strhoraentrada="",strhorasalida="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar);

        SharedPreferences sharedPreferences = getSharedPreferences(Login.MyPREFERENCES, Context.MODE_PRIVATE);
        Empresa=sharedPreferences.getString("Empresa","");
        System.out.println(Empresa);
        nombre= (TextView)findViewById(R.id.addnombre);
        apellido= (TextView)findViewById(R.id.addapellido);
        email= (TextView)findViewById(R.id.addemail);
        cedula= (TextView)findViewById(R.id.addcedula);
        password= (TextView)findViewById(R.id.addpass);
        sueldo= (TextView)findViewById(R.id.addsueldo);
        horaSalida= (TextView)findViewById(R.id.addsalida);
        horaEntrada= (TextView)findViewById(R.id.addentrada);

    }

public void GuardarNuevo (View view){

System.out.println("NUEVO");




    Thread tr= new Thread() {
        @Override
        public void run() {
            final AgregarConexion ACon = new AgregarConexion();
            final String resultado = ACon.enviarDatosGet(nombre.getText().toString(), apellido.getText().toString(), email.getText().toString(),password.getText().toString(),
                    cedula.getText().toString(),Integer.parseInt(sueldo.getText().toString()),horaSalida.getText().toString(),horaSalida.getText().toString(),Empresa);
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    int r = ACon.obtDatosJson(resultado,getApplicationContext());
                    if (r > 0) {
                        //LlenarSharedPreferences(1,lCon.IdUsuario,lCon.NombreUsuario);//Es de tipo Empleado
                        //Entrar a aplicacion
                        System.out.println("SE GUARDO");

                    } else {
                        Toast.makeText(getApplicationContext(), "No se pudo guardar", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    };
    tr.start();


}
}
