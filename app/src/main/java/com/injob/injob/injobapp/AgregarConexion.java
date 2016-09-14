package com.injob.injob.injobapp;

import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

/**
 * Created by Kevin on 13/9/16.
 */
public class AgregarConexion {



    public String enviarDatosGet(String nombre,String apellido, String email,String password, String cedula,int sueldo,String HEntrada,String HSalida,String Empresa){

        URL url =null;
        String line="";
        int respuesta=0;
        StringBuilder resul = null;

        try {

            url= new URL("http://drwaltergarcia.com/InjobApp/agregar.php?"+
                    "&nombre="+nombre+
                    "&apellido="+apellido+
                    "&email="+email+
                    "&password="+password+
                    "&cedula="+cedula+
                    "&sueldo="+sueldo+
                    "&horaEntrada="+HEntrada+
                    "&horaSalida="+HSalida+
                    "&Empresa="+Empresa
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
                Toast.makeText(context, "SE GUARDO",
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
