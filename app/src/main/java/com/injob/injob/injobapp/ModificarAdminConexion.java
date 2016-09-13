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
 * Created by Just on 13/09/2016.
 */
public class ModificarAdminConexion {

    public String enviarDatosGetModAdmin(String id, Context context) {

        try {
            URL url = new URL("http://drwaltergarcia.com/InjobApp/ConsultaActualizar.php?" +
                    "&id=" + id);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.connect();
            InputStream stream = httpURLConnection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(stream));
            StringBuffer buffer = new StringBuffer();
            String line = " ";
            while ((line = bufferedReader.readLine()) != null) {
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
                String sueldo = (String) finalObject.get("sueldo");
                String horaEntrada = (String) finalObject.get("horaEntrada");
                String horaSalida = (String)finalObject.get("horaSalida");

                Intent intent = new Intent(context, ModificadoFinalAdmin.class);
                intent.putExtra("id", id);
                intent.putExtra("nombre", nombre);
                intent.putExtra("apellido", apellido);
                intent.putExtra("email", email);
                intent.putExtra("password", password);
                intent.putExtra("cedula", cedula);
                intent.putExtra("sueldo", sueldo);
                intent.putExtra("horaEntrada", horaEntrada);
                intent.putExtra("horaSalida", horaSalida);

                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
//                parentActivity.finish();
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

    public  String actualizarAdmin (String id,String nombre, String apellido,String email, String password, String cedula, String sueldo, String horaEntrada, String horaSalida){
        URL url =null;
        String line="";
        int respuesta=0;
        StringBuilder resul = null;

        try {
            url= new URL("http://drwaltergarcia.com/InjobApp/actualizarAdmin.php?" +
                    "&id="  +id+
                    "&nombre="  +nombre+
                    "&apellido=" +apellido+
                    "&email="  +email+
                    "&password="+password+
                    "&cedula="+cedula+
                    "&sueldo="+sueldo+
                    "&horaEntrada="+horaEntrada+
                    "&horaSalida="+horaSalida
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
    public int obtDatosJsonActualizarAdmin(String response, Context context){
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
