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
 * Created by Just on 14/09/2016.
 */
public class VerificacionQrConexion {
    public String enviarDatosGet(String number){

        URL url =null;
        String line="";
        int respuesta=0;
        StringBuilder resul = null;

        try {
            url= new URL("http://drwaltergarcia.com/InjobApp/buscarqr.php?" +
                    "&number="  +number

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

            if(code==1) //EL QR ES CORRECTO
            {
                Toast.makeText(context, "Guardando Registro...",
                    Toast.LENGTH_SHORT).show();
                //Mandar a guardar a Base
                MarcarConexion marcarConexion = new MarcarConexion();
                marcarConexion.CalcularMulta(context);

            }
            else //EL QR ES INCORRECTO
            {
                Toast.makeText(context, "No es el codigo de hoy :(",
                        Toast.LENGTH_LONG).show();
            }
        }catch (Exception e){
        }
        return code;
    }
}
