package com.injob.injob.injobapp;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Kevin on 12/9/16.
 */
public class LoginConexion {

    public String NombreUsuario = ""; //Sirve para llamarlo desde otros Activities
    public int IdUsuario=0;
    public int Sueldo;
    public String HoraEntrada="", HoraSalida="";
    public String enviarDatosGet(String emp,String usu, String pas,String cod, int tipo){

        URL url =null;
        String line="";
        int respuesta=0;
        StringBuilder resul = null;

        try {
            url= new URL("http://drwaltergarcia.com/InjobApp/login.php?" +
                    "&emp="  +emp+
                    "&usu="  +usu+
                    "&pas=" +pas+
                    "&cod="  +cod+
                    "&tipo="+tipo //Tipo para saber si es Empleado o Admin
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

    public int obtDatosJson(String response){
        int res=0;
        try {
            JSONArray json= new JSONArray(response);
            //Parsear el JSON
            for (int i = 0; i < json.length(); i++) {
                JSONObject jsonobject = json.getJSONObject(i);
                String nombre = jsonobject.getString("nombre");
               int id = jsonobject.getInt("id");

                NombreUsuario = nombre;
                IdUsuario = id;
                try{
                    Sueldo=jsonobject.getInt("sueldo");
                    HoraEntrada=jsonobject.getString("horaEntrada");
                    HoraSalida=jsonobject.getString("horaSalida");
                }catch (Exception e){}

                System.out.println(nombre);
                System.out.println("ID: "+id);
                System.out.println(HoraEntrada);
                System.out.println(HoraSalida);
                System.out.println(Sueldo+"");

            }
            //////////////////
            if(json.length()>0){
                res=1;
            }
        }catch (Exception e){
        }
        return res;
    }

}
