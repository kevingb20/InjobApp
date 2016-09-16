package com.injob.injob.injobapp;

import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;


public class Admin extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
        String Nombre,Empresa,Email,Codigo;
        int Id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Leyendo SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences(Login.MyPREFERENCES, Context.MODE_PRIVATE);
        Id = sharedPreferences.getInt("Id",0);
        Nombre =sharedPreferences.getString ("Nombre","");
        Empresa =   sharedPreferences.getString("Empresa","");
        Email   =   sharedPreferences.getString("Email","");
        Codigo =    sharedPreferences.getString("Codigo","");


        // Saludando
       // Toast.makeText(getApplicationContext(),("Bienvenido "+Nombre),Toast.LENGTH_SHORT).show();
        // Fin Saludo

        setContentView(R.layout.activity_admin);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.content_frame, new Frag_Inicio() )
                .commit();


        //igual
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);




    }

    @Override
    protected void onStart() {
        super.onStart();
        final TextView miCodigo = (TextView) findViewById(R.id.txt_CodigoInicio);
        miCodigo.setText(Codigo);

        final TextView miEmpresa = (TextView) findViewById(R.id.txt_EmpresaInicio);
        miEmpresa.setText(Empresa);
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
        getMenuInflater().inflate(R.menu.admin, menu);

        final TextView miNombre1 = (TextView) findViewById(R.id.textViewAdmin1);
        miNombre1.setText(Nombre);

        final TextView miNombre2 = (TextView) findViewById(R.id.textViewAdmin2);
        miNombre2.setText(Email);

        final TextView miCodigo = (TextView) findViewById(R.id.txt_CodigoInicio);
        miCodigo.setText(Codigo);

        final TextView miEmpresa = (TextView) findViewById(R.id.txt_EmpresaInicio);
        miEmpresa.setText(Empresa);



        return true;
    }



    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        FragmentManager fragmentManager = getFragmentManager();

        if (id == R.id.nav_camera) {
            Intent i = new Intent(getApplicationContext(),Admin.class);
            startActivity(i);
        } else if (id == R.id.nav_gallery) {

            Intent intent = new Intent(Admin.this, InicioMulta.class);
            startActivity(intent);

        } else if (id == R.id.nav_manage) {
            Intent i = new Intent(Admin.this,ListarEmpleado.class);
            startActivity(i);
        } else if (id == R.id.nav_share) {
            fragmentManager.beginTransaction()
                    .replace(R.id.content_frame, new Frag_Creditos() )
                    .commit();
        } else if (id == R.id.nav_send) { //Salir
            Logout();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
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

}
