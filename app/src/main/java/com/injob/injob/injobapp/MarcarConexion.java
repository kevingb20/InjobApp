package com.injob.injob.injobapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
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
    public void CalcularMulta(final Context context){

        SimpleDateFormat dateFormatGmt = new SimpleDateFormat("yyyy-MMM-dd");
        dateFormatGmt.setTimeZone(TimeZone.getTimeZone("GMT"));
        System.out.println(dateFormatGmt.toString());

        System.out.println("CALCULANDO MULTA...");

        //ID
        final SharedPreferences sharedPreferences = getSharedPreferences(Login.MyPREFERENCES, Context.MODE_PRIVATE);

        //Dia
        Calendar c = Calendar.getInstance();
        final String diaActual = c.get(Calendar.YEAR)+"-"+c.get(Calendar.MONTH)+"-"+c.get(Calendar.DAY_OF_MONTH);
        System.out.println(diaActual);
        //HoraEntrada
        final String horaActual = c.get(Calendar.HOUR_OF_DAY)+"-"+c.get(Calendar.MINUTE)+"-"+c.get(Calendar.SECOND);
        System.out.println(horaActual);

        Thread tr= new Thread() {
            @Override
            public void run() {
                    if(sharedPreferences.getBoolean("MarcadoEntrada",false)){//SI YA MARCO ENTRADA
                        resultado = enviarDatosGet(sharedPreferences.getInt("Id",0),diaActual,horaActual,horaActual,5f);

                     }else{ // SI VA A MARCAR ENTRADA
                         resultado = enviarDatosGet(sharedPreferences.getInt("Id",0),diaActual,horaActual,null,5f);
                     }

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        int r = obtDatosJson(resultado,context);
                        if (r > 0) {
                            System.out.println("SE GUARDO");

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
    public String enviarDatosGet(int empleado, String dia, String horaEntrada, String horaSalida, Float multa){

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
                    "&multa="+multa

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
               Toast.makeText(context, "Tenga un Buen día",
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
