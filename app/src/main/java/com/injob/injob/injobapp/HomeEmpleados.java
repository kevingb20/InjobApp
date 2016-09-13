package com.injob.injob.injobapp;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.zxing.Result;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

import me.dm7.barcodescanner.zxing.ZXingScannerView;


public class HomeEmpleados extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, ZXingScannerView.ResultHandler {
        private ZXingScannerView mScannerView;
        public int Id;
        String Nombre,Empresa,Email;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Leyendo SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences(Login.MyPREFERENCES, Context.MODE_PRIVATE);
        Id = sharedPreferences.getInt("Id",0);
        Nombre =sharedPreferences.getString("Nombre","");
        Empresa =   sharedPreferences.getString("Empresa","");
        Email   =   sharedPreferences.getString("Email","");

        // Saludando
        Toast.makeText(getApplicationContext(),("Bienvenido "+Nombre+Id),Toast.LENGTH_SHORT).show();
        // Fin Saludo


        setContentView(R.layout.activity_home_empleados);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    public void QrScanner(View view) {
        mScannerView=new ZXingScannerView(this);
        setContentView(mScannerView);
        mScannerView.setResultHandler(this);
        mScannerView.startCamera();
    }



    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home_empleados, menu);
        return true;
    }



    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
//            Intent edit = new Intent(this, EditEmp.class);
//            edit.putExtra("id", Id);
//            startActivity(edit);
            Thread tr= new Thread() {

                @Override
                public void run() {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            ModifyTask modifyTask = new ModifyTask(HomeEmpleados.this);
                            modifyTask.execute();
                        }
                    });


                }
            };
            tr.start();




        } else if (id == R.id.nav_gallery) {
            AlertDialog.Builder builder= new  AlertDialog.Builder(this);
            builder.setTitle("Multa");
            builder.setMessage("Su multa es de: $50.00");
            builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    aceptar();
                }
            });
            AlertDialog alert1= builder.create();
            alert1.show();

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) { //Salir
            Logout();
        } else if (id == R.id.nav_send) {


        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void handleResult(Result result) {
        AlertDialog.Builder builder= new  AlertDialog.Builder(this);
        builder.setTitle("scan Result");
        builder.setMessage(result.getText());
        builder.setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                aceptar();
            }
        });
        AlertDialog alert1= builder.create();
        alert1.show();

    }
    public void aceptar(){
        Intent i = new Intent(this, HomeEmpleados.class);
        startActivity(i);
    }
    private void Logout(){
        SharedPreferences sharedpreferences = getSharedPreferences(Login.MyPREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.remove("Id");
        editor.remove("Empresa");
        editor.remove("Email");
        editor.remove("Password");
        editor.remove("Codigo");
        editor.remove("Tipo");
        editor.remove("Logeado");
        editor.clear();
        editor.commit();

        Intent i = new Intent(this, Splash.class);
        startActivity(i);
    }

    ////////// Usando el id mandamos a buscar los datos del empleado y los pasamos por intent para mostrarlos en la siguiente pantalla

    public class ModifyTask extends AsyncTask<String,String,String > {

        Context ctxxy;
        ProgressDialog progressDialog;
        Activity activityyz;
        android.app.AlertDialog.Builder builder;

        public ModifyTask(Context ctx) {
            this.ctxxy = ctx;
            activityyz = (Activity) ctx;
        }

        @Override
        protected void onPreExecute() {
            builder = new android.app.AlertDialog.Builder(activityyz);
            progressDialog = new ProgressDialog(ctxxy);
            progressDialog.setTitle("Please wait...");
            progressDialog.setMessage("employee...");
            progressDialog.setIndeterminate(true);
            progressDialog.setCancelable(false);
            progressDialog.show();
        }

        @Override
        protected String doInBackground(String... params) {
            try {
                URL url = new URL("http://drwaltergarcia.com/InjobApp/ConsultaActualizar.php?" +
                        "&id="  +Id);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.connect();
                InputStream stream= httpURLConnection.getInputStream();
                BufferedReader bufferedReader= new BufferedReader(new InputStreamReader(stream));
                StringBuffer buffer = new StringBuffer();
                String line=" ";
                while ((line =bufferedReader.readLine()) != null){
                    buffer.append(line);
                }
                String finalJson = buffer.toString();
                JSONArray contacts = new JSONArray((finalJson));
                for (int i = 0; i < contacts.length(); i++) {

                    JSONObject finalObject = contacts.getJSONObject(i);
                    String nombre = finalObject.getString("nombre");
                    String apellido = finalObject.getString("apellido");
                    String email = finalObject.getString("email");
                    String password = finalObject.getString("password");
                    String cedula = finalObject.getString("cedula");
                    Intent intent = new Intent(HomeEmpleados.this, EditEmp.class);
                    intent.putExtra("id",Id);
                    intent.putExtra("nombre",nombre);
                    intent.putExtra("apellido",apellido);
                    intent.putExtra("email",email);
                    intent.putExtra("password",password);
                    intent.putExtra("cedula",cedula);
                    startActivity(intent);
               }

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (ProtocolException e) {
                e.printStackTrace();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }
        @Override
        protected void onPostExecute(String result) {
            builder = new android.app.AlertDialog.Builder(activityyz);
            progressDialog.dismiss();
        }


    }
}

