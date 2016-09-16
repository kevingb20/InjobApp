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
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.zxing.Result;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.Calendar;

import me.dm7.barcodescanner.zxing.ZXingScannerView;


public class HomeEmpleados extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, ZXingScannerView.ResultHandler {
        private ZXingScannerView mScannerView;
        public int Id,Sueldo;
        String Nombre,Empresa,Email, code,horaEntrada,horaSalida;
        TextView txtDia,txtFecha,txtEntrada2,txtSalida2;
        Button btnCamara;
    public float multa;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Leyendo SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences(Login.MyPREFERENCES, Context.MODE_PRIVATE);
        Id = sharedPreferences.getInt("Id",0);
        Nombre =sharedPreferences.getString("Nombre","");
        Empresa =   sharedPreferences.getString("Empresa","");
        Email   =   sharedPreferences.getString("Email","");
        Sueldo   =   sharedPreferences.getInt("Sueldo",0);
        horaEntrada   =   sharedPreferences.getString("HoraEntrada","");
        horaSalida   =   sharedPreferences.getString("HoraSalida","");

        // Saludando
        //Toast.makeText(getApplicationContext(),("Bienvenido "+Nombre),Toast.LENGTH_SHORT).show();
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

    @Override
    protected void onStart() {
        super.onStart();
        System.out.println("onStart");
        txtDia = (TextView) findViewById(R.id.txt_Dia);
        txtFecha = (TextView) findViewById(R.id.txt_Fecha);

        //Para tener datos del sistema
        Calendar c = Calendar.getInstance();
        //Poniendo el dia de la semana
        txtDia.setText(ConsultarDia(c.get(Calendar.DAY_OF_WEEK)));
        txtFecha.setText(c.get(Calendar.YEAR)+"-"+c.get(Calendar.MONTH)+"-"+c.get(Calendar.DAY_OF_MONTH));
        btnCamara = (Button)findViewById(R.id.button);
        btnCamara.setEnabled(true);
/*
        txtEntrada2 = (TextView) findViewById(R.id.txt_Entrada2);
        txtSalida2 = (TextView) findViewById(R.id.txt_Salida2);
        final SharedPreferences sharedpreferences = getSharedPreferences(Login.MyPREFERENCES, Context.MODE_PRIVATE);

        txtEntrada2.setText(sharedpreferences.getString("MarcadoEntrada","-- : -- : --"));
        txtSalida2.setText(sharedpreferences.getString("MarcadoSalida","-- : -- : --"));
       // txtEntrada2.setText("33:33:33");
       // txtSalida2.setText("33:33:33");
        System.out.println(sharedpreferences.getString("MarcadoEntrada","-- : -- : --")+"////"+sharedpreferences.getString("MarcadoSalida","-- : -- : --") );
*/
    }




    private float  CalcularMulta(boolean tipo){
    float valorMulta=0f;

            System.out.println("EMAIL       : " + Email);
            System.out.println("Hora ENTRADA: " + horaEntrada);
            System.out.println("Hora SALIDA : " + horaSalida);
            System.out.println("Sueldo      : " + Sueldo);

            Calendar c = Calendar.getInstance();
            int horaActual = Integer.parseInt("" + c.get(Calendar.HOUR_OF_DAY));
            int minutoActual = Integer.parseInt("" + c.get(Calendar.MINUTE));
            int segundoActual = Integer.parseInt("" + c.get(Calendar.SECOND));

            int hora1 = 0, minuto1=0, segundo1=0;

        if (tipo==false) {//MULTA AL MARCAR ENTRADA
            String s = horaEntrada;
            String[] splitted = s.split(":");

            // add the end brace for every entry except the last
            for (int i = 0; i < splitted.length - 1; i++) {
                splitted[i] = splitted[i] + "";
            }

            // print out the string array
            for (int i = 0; i < splitted.length; i++) {
                System.out.println("String s" + i + " = " + splitted[i]);
                if (i == 0)
                    hora1 = Integer.parseInt(splitted[i].toString());
                if (i == 1)
                    minuto1 = Integer.parseInt(splitted[i].toString());
                if (i == 2)
                    segundo1 = Integer.parseInt(splitted[i].toString());
            }

            if (horaActual >= hora1) {
                if (horaActual > hora1) {
                    valorMulta = ((Sueldo / 240f) * (horaActual - hora1));
                    System.out.println(valorMulta + "Multa por hora");
                }
                if (minutoActual > minuto1) {
                    valorMulta = valorMulta + ((Sueldo / (240f * 60f)) * (minutoActual - minuto1));

                    System.out.println(valorMulta + "Multa por minuto");
                }
            }
            valorMulta = round(valorMulta, 2);
            return valorMulta;

        }else//Multa para MARCAR SALIDA
        {
            String s = horaSalida;
            String[] splitted = s.split(":");

            // add the end brace for every entry except the last
            for (int i = 0; i < splitted.length - 1; i++) {
                splitted[i] = splitted[i] + "";
            }

            // print out the string array
            for (int i = 0; i < splitted.length; i++) {
                System.out.println("String s" + i + " = " + splitted[i]);
                if (i == 0)
                    hora1 = Integer.parseInt(splitted[i].toString());
                if (i == 1)
                    minuto1 = Integer.parseInt(splitted[i].toString());
                if (i == 2)
                    segundo1 = Integer.parseInt(splitted[i].toString());
            }

            if (horaActual <= hora1) {
                if (horaActual < hora1) {
                    valorMulta = ((Sueldo / 240f) * (hora1-horaActual));
                    System.out.println(valorMulta + "Multa por hora");
                }
                if (minutoActual < minuto1) {
                    valorMulta = valorMulta + ((Sueldo / (240f * 60f)) * (minuto1-minutoActual));

                    System.out.println(valorMulta + "Multa por minuto");
                }
            }
            valorMulta = round(valorMulta, 2);
            return valorMulta;
        }

    }

    public static float round(float d, int decimalPlace) {//SOLO 2 DECIMALES
        BigDecimal bd = new BigDecimal(Float.toString(d));
        bd = bd.setScale(decimalPlace, BigDecimal.ROUND_HALF_UP);
        return bd.floatValue();
    }


/*
    @Override
    protected void onPostResume() {
        super.onPostResume();
        System.out.println("onPostResume");
        txtEntrada2 = (TextView) findViewById(R.id.txt_Entrada2);
        txtSalida2 = (TextView) findViewById(R.id.txt_Salida2);
        final SharedPreferences sharedpreferences = getSharedPreferences(Login.MyPREFERENCES, Context.MODE_PRIVATE);

        txtEntrada2.setText(sharedpreferences.getString("MarcadoEntrada","-- : -- : --"));
        txtSalida2.setText(sharedpreferences.getString("MarcadoSalida","-- : -- : --"));
       // txtEntrada2.setText("33:33:33");
        //txtSalida2.setText("33:33:33");
        System.out.println(sharedpreferences.getString("MarcadoEntrada","-- : -- : --")+"////"+sharedpreferences.getString("MarcadoSalida","-- : -- : --") );

    }
*/
    public void PruebasHora(View view){

   // CalcularMulta();
   // Calendar c = Calendar.getInstance();
   // txtEntrada2.setText("18:68:33");
    //System.out.println(c.get(Calendar.HOUR_OF_DAY)+"-"+c.get(Calendar.MINUTE)+"-"+c.get(Calendar.SECOND));


   // SharedPreferences sharedpreferences = getSharedPreferences(Login.MyPREFERENCES, Context.MODE_PRIVATE);
  //  SharedPreferences.Editor editor = sharedpreferences.edit();

    //editor.putBoolean("MarcadoSalida", false);
   // editor.putBoolean("MarcadoEntrada", false);
    //editor.remove("tipoMarcado");
     //   editor.remove("MarcadoEntrada");
       // editor.remove("MarcadoSalida");
    //editor.clear();
   // editor.commit();
        float valor = CalcularMulta(true);
System.out.println("MULTA ACTUAL: "+valor);
}


    public void QrScanner(View view) {
        mScannerView=new ZXingScannerView(this);
        setContentView(mScannerView);
        mScannerView.setResultHandler(this);
        mScannerView.startCamera();

    }
    public String ConsultarDia(int dia){
        if(dia==1)
            return "Domingo";
        if(dia==2)
            return "Lunes";
        if(dia==3)
            return "Martes";
        if(dia==4)
            return "Miércoles";
        if(dia==5)
            return "Jueves";
        if(dia==6)
            return "Viernes";
        if(dia==7)
            return "Sábado";
        else
            return "Lunes";
    }


//    @Override
//    public void onBackPressed() {
//        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
//        if (drawer.isDrawerOpen(GravityCompat.START)) {
//            drawer.closeDrawer(GravityCompat.START);
//        } else {
//            super.onBackPressed();
//        }
//    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home_empleados, menu);

        final TextView miNombre1 = (TextView) findViewById(R.id.textnombreempleado);
        miNombre1.setText(Nombre);
        final TextView miNombre2 = (TextView) findViewById(R.id.textemailempleado);
        miNombre2.setText(Email);

        return true;
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
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
        final SharedPreferences sharedpreferences = getSharedPreferences(Login.MyPREFERENCES, Context.MODE_PRIVATE);
        AlertDialog.Builder builder= new  AlertDialog.Builder(this);
        builder.setTitle("scan Result");
        builder.setMessage(result.getText());
        code = result.getText();
        Log.d("ESTE ES EL CODE:::", code);
        Thread tr= new Thread() {
            @Override
            public void run() {

                System.out.println(""+sharedpreferences.getBoolean("tipoMarcado",false));
            float multaActual = CalcularMulta(sharedpreferences.getBoolean("tipoMarcado",false));
                final VerificacionQrConexion lCon = new VerificacionQrConexion(Id,sharedpreferences.getBoolean("tipoMarcado",false),multaActual);//false es tipo MarcarEntrada
                final String resultado = lCon.enviarDatosGet(code);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        lCon.obtDatosJson(resultado, getApplicationContext());
System.out.println(""+sharedpreferences.getBoolean("tipoMarcado",true));



                        SharedPreferences.Editor editor = sharedpreferences.edit();
                        if(sharedpreferences.getBoolean("tipoMarcado",true)){
                            editor.putBoolean("tipoMarcado",false);
                            editor.putString("MarcadoSalida",lCon.MarcadoSalida);
                        }
                        if(sharedpreferences.getBoolean("tipoMarcado",false)==false){
                            editor.putBoolean("tipoMarcado",true);

                            editor.putString("MarcadoEntrada",lCon.MarcadoEntrada);

                        }

                        editor.commit();
                        System.out.println(""+sharedpreferences.getBoolean("tipoMarcado",true));

                        mScannerView=new ZXingScannerView(getApplication());
                        setContentView(mScannerView);
                        mScannerView.stopCamera();

                      // restart();
                       // finishAffinity();
                      Intent edit = new Intent(getApplicationContext(), HomeEmpleados.class);
                        startActivity(edit);

                    }
                });
            }
        };
        tr.start();

    }
    public void aceptar(){
        Intent i = new Intent(this, HomeEmpleados.class);
        startActivity(i);
    }
    public void restart() {
        finishAffinity();
        Intent intent = new Intent(getApplicationContext(), Splash.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
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
        editor.remove("TipoMarcado");
        editor.remove("HoraEntrada");
        editor.remove("HoraSalida");
        editor.remove("Logeado");
        editor.remove("Sueldo");
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

