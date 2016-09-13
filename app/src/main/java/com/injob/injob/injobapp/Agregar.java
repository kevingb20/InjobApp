package com.injob.injob.injobapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class Agregar extends AppCompatActivity {

    TextView nombre, apellido, email, cedula, password, sueldo, horaSalida, horaEntrada;
    String strnombre ="", strapellido="",stremail="",strcedula="",strpassword="",strsueldo="",strhoraentrada="",strhorasalida="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar);

        nombre= (TextView)findViewById(R.id.addnombre);
        apellido= (TextView)findViewById(R.id.addapellido);
        email= (TextView)findViewById(R.id.addemail);
        cedula= (TextView)findViewById(R.id.addcedula);
        password= (TextView)findViewById(R.id.addpass);
        sueldo= (TextView)findViewById(R.id.addsueldo);
        horaSalida= (TextView)findViewById(R.id.addsalida);
        horaEntrada= (TextView)findViewById(R.id.addentrada);

    }


}
