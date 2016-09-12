package com.injob.injob.injobapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TabHost;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class Login extends AppCompatActivity {
    //TabHost tabHost;
    Button btnIngresar;
    EditText txtEmp,txtUsu,txtPas,
            txtEmp2,txtUsu2,txtPas2,txtCod;


    public static final String MyPREFERENCES = "MisPreferencias" ;
    SharedPreferences sharedPreferences;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        sharedPreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);


        //Empleado
        txtEmp=(EditText)findViewById(R.id.txt_Empresa);
        txtUsu=(EditText)findViewById(R.id.txt_Email);
        txtPas=(EditText)findViewById(R.id.txt_Pass);
        //Administrador
        txtEmp2=(EditText)findViewById(R.id.txt_Empresa2);
        txtUsu2=(EditText)findViewById(R.id.txt_Email2);
        txtPas2=(EditText)findViewById(R.id.txt_Pass2);
        txtCod=(EditText)findViewById(R.id.txt_Codigo);

        TabHost host = (TabHost)findViewById(R.id.tabHost);
        host.setup();

        //Tab 1 Empleado
        TabHost.TabSpec spec = host.newTabSpec("Tab One");
        spec.setContent(R.id.tab1);
        spec.setIndicator("Empleado");
        host.addTab(spec);

        //Tab 2 Administrador
        spec = host.newTabSpec("Tab Two");
        spec.setContent(R.id.tab2);
        spec.setIndicator("Administrador");
        host.addTab(spec);
    }


    public void login(View view){

       LlenarSharedPreferences(1);//Es de tipo Empleado
        Thread tr= new Thread() {
            @Override
            public void run() {
                final LoginConexion lCon = new LoginConexion();
                final String resultado = lCon.enviarDatosGet(txtEmp.getText().toString(),txtUsu.getText().toString(), txtPas.getText().toString(),"",1);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        int r = lCon.obtDatosJson(resultado);
                        if (r > 0) {
                            //Entrar a aplicacion
                            Intent i = new Intent(getApplicationContext(), HomeEmpleados.class);
                            i.putExtra("Nombre",lCon.NombreUsuario );
                            startActivity(i);

                        } else {
                            Toast.makeText(getApplicationContext(), "Datos incorrectos", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        };
            tr.start();
        }

    public void loginAdmin(View view){

        LlenarSharedPreferences(2); //Es de tipo Admin

        Thread tr= new Thread() {
            @Override
            public void run() {
                final LoginConexion lCon = new LoginConexion();
                final String resultado = lCon.enviarDatosGet(txtEmp2.getText().toString(), txtUsu2.getText().toString(), txtPas2.getText().toString(),txtCod.getText().toString(),2);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        int r = lCon.obtDatosJson(resultado);
                        if (r > 0) {
                            //Entrar a aplicacion
                            Intent i = new Intent(getApplicationContext(), Admin.class);
                            i.putExtra("Nombre",lCon.NombreUsuario );
                            startActivity(i);
                        } else {
                            Toast.makeText(getApplicationContext(), "Datos incorrectos", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        };
        tr.start();
    }

    void LlenarSharedPreferences(int tipo){
        //Guardar datos en SharedPreferences
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("Logeado",true);
        editor.putInt("Tipo",tipo);
        if(tipo==1){
            editor.putString("Empresa",txtEmp.getText().toString());
            editor.putString("Email",txtUsu.getText().toString());
            editor.putString("Password",txtPas.getText().toString());
            editor.putString("Codigo","");
        }
        if(tipo==2){
            editor.putString("Empresa",txtEmp2.getText().toString());
            editor.putString("Email",txtUsu2.getText().toString());
            editor.putString("Password",txtPas2.getText().toString());
            editor.putString("Codigo",txtCod.getText().toString());
        }
        editor.commit();


    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
       // if (id == R.id.action_settings) {
            //return true;
       // }

        return super.onOptionsItemSelected(item);
    }
    public void EraseText (View v){
        //Esto es para que cuando se comienza a escribir en el texto, se borre su contenido: email, etc


    }

}
