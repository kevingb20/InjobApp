package com.injob.injob.injobapp;

import android.content.Context;
import android.widget.Toast;

import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Just on 12/09/2016.
 */
public class ActualizarEmpleadoConexion {

    public String enviarDatosGet(String id,String nombre, String apellido,String email, String password, String cedula){

        URL url =null;
        String line="";
        int respuesta=0;
        StringBuilder resul = null;

        try {
            url= new URL("http://drwaltergarcia.com/InjobApp/actualizar.php?" +
                    "&id="  +id+
                    "&nombre="  +nombre+
                    "&apellido=" +apellido+
                    "&email="  +email+
                    "&password="+password+
                    "&cedula="+cedula
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
    public int obtDatosJson(String response, Context context){
        int code = 0;

        try {
            JSONObject json_data = new JSONObject(response);
            code=(json_data.getInt("code"));

            if(code==1)
            {
                Toast.makeText(context, "Update Successfully",
                        Toast.LENGTH_SHORT).show();
            }
            else
            {
                Toast.makeText(context, "Sorry, Try Again",
                        Toast.LENGTH_LONG).show();
            }
        }catch (Exception e){
        }
        return code;
    }
}
