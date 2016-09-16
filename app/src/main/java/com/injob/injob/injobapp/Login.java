package com.injob.injob.injobapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TabHost;
import android.widget.Toast;

public class Login extends AppCompatActivity {

    EditText txtEmp,txtUsu,txtPas,
            txtEmp2,txtUsu2,txtPas2,txtCod;

    private Spinner spinnerEmpresas, spinempresaAdmin;
    String empresa, empresaadmin;
    public static final String MyPREFERENCES = "MisPreferencias" ;
    SharedPreferences sharedPreferences;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        sharedPreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);

        spinnerEmpresas = (Spinner)findViewById(R.id.spinEmpresas);
        spinempresaAdmin =(Spinner)findViewById(R.id.spinEmpresasAdmin);

        ListarEmpresasConexion lis = new ListarEmpresasConexion();
        lis.recoger(getApplicationContext(), spinnerEmpresas);
        ListarEmpresasConexion lisAdmin = new ListarEmpresasConexion();
        lisAdmin.recoger(getApplicationContext(), spinempresaAdmin);

        spinnerEmpresas.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                empresa= parentView.getItemAtPosition(position).toString();
//                Toast.makeText(
//                        getApplicationContext(),
//                        parentView.getItemAtPosition(position).toString() + " Seleccionado" ,
//                        Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });
        spinempresaAdmin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                empresaadmin= parentView.getItemAtPosition(position).toString();
//                Toast.makeText(
//                        getApplicationContext(),
//                        parentView.getItemAtPosition(position).toString() + " Seleccionado" ,
//                        Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });

        //Empleado
//        txtEmp=(EditText)findViewById(R.id.txt_Empresa);
        txtUsu=(EditText)findViewById(R.id.txt_Email);
        txtPas=(EditText)findViewById(R.id.txt_Pass);
        //Administrador
//        txtEmp2=(EditText)findViewById(R.id.txt_Empresa2);
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


        Thread tr= new Thread() {
            @Override
            public void run() {
                final LoginConexion lCon = new LoginConexion();
                final String resultado = lCon.enviarDatosGet(empresa.toString(),txtUsu.getText().toString(), txtPas.getText().toString(),"",1);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        int r = lCon.obtDatosJson(resultado);
                        if (r > 0) {
                            LlenarSharedPreferences(1,lCon.IdUsuario,lCon.NombreUsuario,lCon.Sueldo, lCon.HoraEntrada,lCon.HoraSalida);//Es de tipo Empleado

                            //Entrar a aplicacion
                            Intent i = new Intent(getApplicationContext(), HomeEmpleados.class);
                            i.putExtra("Nombre",lCon.NombreUsuario );
                            i.putExtra("Empresa",empresa);
                            i.putExtra("Email",txtUsu.getText().toString());
                            i.putExtra("Password",txtPas.getText().toString());
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



        Thread tr= new Thread() {
            @Override
            public void run() {
                final LoginConexion lCon = new LoginConexion();
                final String resultado = lCon.enviarDatosGet(empresaadmin, txtUsu2.getText().toString(), txtPas2.getText().toString(),txtCod.getText().toString(),2);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        int r = lCon.obtDatosJson(resultado);
                        if (r > 0) {
                            LlenarSharedPreferences(2,lCon.IdUsuario,lCon.NombreUsuario,0,"",""); //Es de tipo Admin
                            //Entrar a aplicacion
                            Intent i = new Intent(getApplicationContext(), Admin.class);
                            i.putExtra("Nombre",lCon.NombreUsuario );
                            i.putExtra("Empresa",empresaadmin);
                            i.putExtra("Email",txtUsu2.getText().toString());
                            i.putExtra("Password",txtPas2.getText().toString());
                            i.putExtra("Codigo",txtCod.getText().toString());
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

    void LlenarSharedPreferences(int tipo,int id, String Nombre,int suel,String hEntrada,String hSalida){
        //Guardar datos en SharedPreferences

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("Logeado",true);
        editor.putInt("Tipo",tipo);
        editor.putInt("Id",id);

        editor.putString("Nombre",Nombre);
        if(tipo==1){
            editor.putString("Empresa",empresa);
            editor.putString("Email",txtUsu.getText().toString());
            editor.putString("Password",txtPas.getText().toString());
            editor.putInt("Sueldo",suel);
            editor.putString("HoraEntrada",hEntrada);
            editor.putString("HoraSalida",hSalida);
            editor.putString("Codigo","");
        }
        if(tipo==2){
            editor.putString("Empresa",empresaadmin);
            editor.putString("Email",txtUsu2.getText().toString());
            editor.putString("Password",txtPas2.getText().toString());
            editor.putInt("Sueldo",suel);
            editor.putString("HoraEntrada",hEntrada);
            editor.putString("HoraSalida",hSalida);
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
