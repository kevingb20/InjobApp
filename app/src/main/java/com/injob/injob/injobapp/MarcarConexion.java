package com.injob.injob.injobapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.TimeZone;

/**
 * Created by Kevin on 15/9/16.
 */
public class MarcarConexion extends AppCompatActivity{
    String resultado="";
    public int idEmpleado;
    public boolean yaMarco=false;
    public  boolean tipoMarcado;//false ENTRADA, true Salida
    public String MarcadoEntrada="",MarcadoSalida="";
    public float multaM;
    public TextView txt1,txt2;
    MarcarConexion(int id,boolean tMarcado, float mul){

        System.out.println("Antes3");
        idEmpleado=id;
        tipoMarcado = tMarcado;//
        multaM = mul;
       // txt1=(TextView)findViewById(R.id.txt_Entrada2);
      //  txt2=(TextView)findViewById(R.id.txt_Salida2);
        System.out.println("CREANDO MARCAR CONEXION y la multa es: "+multaM);
    }
    public void CalcularMulta(final Context context){

        System.out.println("CALCULANDO MULTA...");
       // final MarcarSharedPreferences msp = new MarcarSharedPreferences();


        //ID
        //final SharedPreferences sharedPreferences = getSharedPreferences(Login.MyPREFERENCES, context.MODE_PRIVATE);
        System.out.println("Luego del Shared");
        //Dia
        Calendar c = Calendar.getInstance();
        final String diaActual = c.get(Calendar.YEAR)+"-"+c.get(Calendar.MONTH)+"-"+c.get(Calendar.DAY_OF_MONTH);
        System.out.println(diaActual);
        //HoraEntrada
        final String horaActual = c.get(Calendar.HOUR_OF_DAY)+":"+c.get(Calendar.MINUTE)+":"+c.get(Calendar.SECOND);
        System.out.println(horaActual);

        Thread tr= new Thread() {
            @Override
            public void run() {
                   if(tipoMarcado==false){//MARCAR ENTRADA
                       System.out.println("Marcar Entrada");
                       resultado = enviarDatosGet(idEmpleado,diaActual,horaActual,"",multaM,1);
                       MarcadoEntrada=horaActual;
                    //   txt1.setText(horaActual);
                   }
                else{//Marcar SALIDA
                       System.out.println("Marcar Salida");
                       resultado = enviarDatosGet(idEmpleado,diaActual,horaActual,horaActual,multaM,2);
                       MarcadoSalida=horaActual;
                      // txt2.setText(horaActual);
                   }//
                      //



                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        int r = obtDatosJson(resultado,context);
                        if (r > 0) {
                            System.out.println("SE GUARDO");
/*
                            if(sharedPreferences.getBoolean("MarcadoEntrada",false)) {
                                Toast.makeText(context,"MARCADO SALIDA", Toast.LENGTH_SHORT).show();
                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                editor.putString("HoraSalida", horaActual);
                                editor.putBoolean("MarcadoSalida", true);
                                editor.commit();
                            }
                            else{
                                Toast.makeText(context,"MARCADO ENTRADA", Toast.LENGTH_SHORT).show();
                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                editor.putString("HoraEntrada", horaActual);
                                editor.putBoolean("MarcadoEntrada", true);
                                editor.commit();
                            }
*/
                        } else {
                            Toast.makeText(context,"Intente Nuevamente", Toast.LENGTH_SHORT).show();
                            System.out.println("Intente nuevamente por favor");
                        }
                    }
                });
            }
        };
        tr.start();



    }
    public String enviarDatosGet(int empleado, String dia, String horaEntrada, String horaSalida, Float multa, int tipo){

        URL url =null;
        String line="";
        int respuesta=0;
        StringBuilder resul = null;

        try {
            url= new URL("http://drwaltergarcia.com/InjobApp/marcar.php?" +
                    "&empleado="+empleado+
                    "&dia="+dia+
                    "&horaEntrada=" +horaEntrada+
                    "&horaSalida="+horaSalida+
                    "&multa="+multa+
                    "&tipo="+tipo

            );


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

    public int obtDatosJson(String response,Context context){

        int code = 0;

        try {
            JSONObject json_data = new JSONObject(response);
            code=(json_data.getInt("code"));

            if(code==1)
            {
               Toast.makeText(context, "Gracias",
                       Toast.LENGTH_SHORT).show();

            }
            else
            {
                Toast.makeText(context, "ERROR AL GUARDAR",
                       Toast.LENGTH_LONG).show();
            }
        }catch (Exception e){
        }
        return code;
    }




}
