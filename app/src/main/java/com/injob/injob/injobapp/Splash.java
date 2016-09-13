package com.injob.injob.injobapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

public class Splash extends AppCompatActivity {

    private static int SPLASH_TIME_OUT = 3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);


        new Handler().postDelayed(new Runnable() { //Despues que transcurra el tiempo > 3 Segundos
            @Override
            public void run() {
                ComprobarLogin();
                finish();
            }
        }, SPLASH_TIME_OUT);
    }

    void ComprobarLogin(){

        //Instanciando Shared Preferences que esta en la Clase Login.java
        SharedPreferences sharedPreferences = getSharedPreferences(Login.MyPREFERENCES, Context.MODE_PRIVATE);

        // Si la clave 'Logeado' es TRUE: Se toman los datos del SharedP para comprobar de nuevo con la base de datos
        // a fin de verificar que el usuario siga siendo miembro activo de la empresa
        if(sharedPreferences.getBoolean("Logeado",false)){
            final String Empresa  = sharedPreferences.getString("Empresa","");
            final String Email    = sharedPreferences.getString("Email","");
            final String Password = sharedPreferences.getString("Password","");
            final String Codigo   = sharedPreferences.getString("Codigo","");
            final int    Tipo     = sharedPreferences.getInt("Tipo",0);

            Thread tr= new Thread() {
                @Override
                public void run() {
                    final LoginConexion lCon = new LoginConexion();
                    final String resultado = lCon.enviarDatosGet(Empresa,Email, Password,Codigo,Tipo);

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            int r = lCon.obtDatosJson(resultado);
                            if (r > 0) {
                                if(Tipo==1) { //Empleado
                                    //Entrar a aplicacion
                                    Intent i = new Intent(getApplicationContext(), HomeEmpleados.class);
                                    startActivity(i);
                                }
                                if(Tipo==2) { //Admin
                                    //Entrar a aplicacion
                                    Intent i = new Intent(getApplicationContext(), Admin.class);
                                    startActivity(i);
                                }
                            } else { //Si los datos de inicio de sesion son incorrectos..
                                LanzarLogin();
                            }
                        }
                    });
                }
            };
            tr.start();

        }else //Sino ha sido logeado antes..
            LanzarLogin();

    }
    private void LanzarLogin(){
        Intent i = new Intent(Splash.this, Login.class);
        startActivity(i);
    }


}

