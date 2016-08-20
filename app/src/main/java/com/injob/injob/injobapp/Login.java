package com.injob.injob.injobapp;

import android.content.Intent;
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

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class Login extends AppCompatActivity {
    //TabHost tabHost;
    Button btnIngresar;
    EditText txtusu,txtpas,txtusu2,txtpas2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        txtusu=(EditText)findViewById(R.id.txt_Email);
        txtpas=(EditText)findViewById(R.id.txt_Pass);
        txtusu2=(EditText)findViewById(R.id.txt_Email2);
        txtpas2=(EditText)findViewById(R.id.txt_Pass2);
        btnIngresar=(Button)findViewById(R.id.btn_submit);


        TabHost host = (TabHost)findViewById(R.id.tabHost);
        host.setup();

        //Tab 1
        TabHost.TabSpec spec = host.newTabSpec("Tab One");
        spec.setContent(R.id.tab1);
        spec.setIndicator("Empleado");
        host.addTab(spec);

        //Tab 2
        spec = host.newTabSpec("Tab Two");
        spec.setContent(R.id.tab2);
        spec.setIndicator("Gerente");
        host.addTab(spec);


    }
    public String enviarDatosGet(String usu, String pas,boolean tipo){

        URL url =null;
        String line="";
        int respuesta=0;
        StringBuilder resul = null;

        try {
            if(tipo)//empleado
                url= new URL("http://localhost/injob/valida.php?usu="+usu+"&pas="+pas);//Cambiar localhost por IP de servidor
            else // admin
                url= new URL("http://localhost/injob/valida2.php?usu="+usu+"&pas="+pas);//Cambiar localhost por ip de servidor

            HttpURLConnection connection = (HttpURLConnection)url.openConnection();
            respuesta= connection.getResponseCode();
            resul= new StringBuilder();

            if(respuesta== HttpURLConnection.HTTP_OK){
                InputStream in = new BufferedInputStream(connection.getInputStream());
                BufferedReader reader = new BufferedReader(new InputStreamReader(in));

                while ((line= reader.readLine())!=null){
                    resul.append(line);
                }

            }
        }catch (Exception e){

        }
        return resul.toString();

    }
    public int obtDatosJson(String response){
        int res=0;
        try {
            JSONArray json= new JSONArray(response);
            if(json.length()>0){
                res=1;
            }
        }catch (Exception e){
        }
        return res;
    }

    public void login(View view){
        Thread tr= new Thread() {
            @Override
            public void run() {
                final String resultado = enviarDatosGet(txtusu.getText().toString(), txtpas.getText().toString(),true);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        int r = obtDatosJson(resultado);
                        if (r > 0) {
                            Intent i = new Intent(getApplicationContext(), HomeEmpleados.class);
                            i.putExtra("cod", txtusu.getText().toString());
                            startActivity(i);
                        } else {
                            Toast.makeText(getApplicationContext(), "usuario o pass mal", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        };
            tr.start();
        }

    public void loginAdmin(View view){

        Thread tr= new Thread() {
            @Override
            public void run() {
                final String resultado = enviarDatosGet(txtusu2.getText().toString(), txtpas2.getText().toString(),false);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        int r = obtDatosJson(resultado);
                        if (r > 0) {
                            Intent i = new Intent(getApplicationContext(), Admin.class);
                            i.putExtra("cod", txtusu2.getText().toString());
                            startActivity(i);
                        } else {
                            Toast.makeText(getApplicationContext(), "Usuario o Contraseña incorrecta", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        };
        tr.start();
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
