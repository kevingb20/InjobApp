package com.injob.injob.injobapp;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;

public class Agregar extends AppCompatActivity {

    TextView nombre, apellido, email, cedula, password, sueldo, horaSalida, horaEntrada;
    private ProgressDialog pDialog;
    private JSONObject json;
    private int success=0;
    private AgregarEmpleadoConexion service;
    private String path = "http://drwaltergarcia.com/InjobApp/agregar.php";
    private ImageButton btnadd;
    String strnombre ="", strapellido="",stremail="",strcedula="",strpassword="",strsueldo="",strhoraentrada="",strhorasalida="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar);

        nombre= (TextView)findViewById(R.id.addnombre);
        apellido= (TextView)findViewById(R.id.addapellido);
        email= (TextView)findViewById(R.id.addemail);
        cedula= (TextView)findViewById(R.id.addcedula);
        password= (TextView)findViewById(R.id.addpass);
        sueldo= (TextView)findViewById(R.id.addsueldo);
        horaSalida= (TextView)findViewById(R.id.addsalida);
        horaEntrada= (TextView)findViewById(R.id.addentrada);
        //Initialize HTTPURLConnection class object
        service=new AgregarEmpleadoConexion();


    }

    public void agregarEmpleado(View view){
        if (!nombre.getText().toString().equals("") && !email.getText().toString().equals("")) {
            strnombre = nombre.getText().toString();
            strapellido = apellido.getText().toString();
            strcedula = cedula.getText().toString();
            strpassword = password.getText().toString();
            strsueldo = sueldo.getText().toString();
            stremail = email.getText().toString();
            strhoraentrada = horaEntrada.getText().toString();
            strhorasalida = horaSalida.getText().toString();

//            //Call WebService
//            new PostDataTOServer().execute();
            CreateTask createTask = new CreateTask(Agregar.this);
            createTask.execute("cadenajson", nombre.getText().toString(), apellido.getText().toString(),email.getText().toString(), cedula.getText().toString(), password.getText().toString(), sueldo.getText().toString(), horaEntrada.getText().toString(), horaSalida.getText().toString());
        } else {
            Toast.makeText(getApplicationContext(), "Please Enter all fields", Toast.LENGTH_LONG).show();
        }
    }

    public class CreateTask extends AsyncTask <String, Void, String> {
        String create_url = "http://drwaltergarcia.com/InjobApp/agregar.php";
        Context ctx;
        ProgressDialog progressDialog;
        Activity activity;
        AlertDialog.Builder builder;
        public CreateTask(Context ctx) {
            this.ctx = ctx;
            activity = (Activity) ctx;
        }

        @Override
        protected void onPreExecute() {
            builder = new AlertDialog.Builder(activity);
            progressDialog = new ProgressDialog(ctx);
            progressDialog.setTitle("Please wait...");
            progressDialog.setMessage("Connecting to server...");
            progressDialog.setIndeterminate(true);
            progressDialog.setCancelable(false);
            progressDialog.show();
        }

        @Override
        protected String doInBackground(String... params) {
            try {
                String nombre,email, apellido , password , cedula, sueldo, horaEntrada, horaSalida, FK_Empresa;
                nombre= params[1];
                apellido = params[2];
                email = params[3];
                password = params[5];
                cedula = params[4];
                sueldo = params[6];
                horaEntrada = params[7];
                horaSalida = params[8];
                FK_Empresa="3";

                JSONObject parameters = new JSONObject();
                parameters.put("id", "");
                parameters.put("nombre", nombre);
                parameters.put("apellido", apellido);
                parameters.put("cedula", cedula);
                parameters.put("email", email);
                parameters.put("password", password);
                parameters.put("sueldo", sueldo);
                parameters.put("horaEntrada", horaEntrada);
                parameters.put("horaSalida", horaSalida);
                parameters.put("FK_Empresa", FK_Empresa);

                Log.d("PARAMETROS", parameters.toString());
                URL url = new URL(create_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                String data =  URLEncoder.encode(parameters.toString(), "UTF-8");
                Log.d("!!!!!!!!!!!!!!!", data);
                bufferedWriter.write(data);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                StringBuilder stringBuilder = new StringBuilder();
                String line = "";
                while ((line = bufferedReader.readLine()) != null) {
                    stringBuilder.append(line + "\n");

                }
                httpURLConnection.disconnect();
                Thread.sleep(5000);
                return stringBuilder.toString().trim();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (ProtocolException e) {
                e.printStackTrace();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(String json) {
            progressDialog.dismiss();

            try {
                JSONObject json_data = new JSONObject(json);
                int code = (json_data.getInt("code"));

                if (code == 1) {
                    Toast.makeText(getBaseContext(), "Inserted Successfully",
                            Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getBaseContext(), "Sorry, Try Again",
                            Toast.LENGTH_LONG).show();
                }
            } catch (Exception e) {
                Log.e("Fail 3", e.toString());
            }
//            try {
//                JSONObject jsonObject = new JSONObject(json);
//                String test = jsonObject.getString("response");
//                if (test.equals("1")) {
//                    Log.d("Creado!!!!", test);
//                    Intent intent = new Intent(activity, ListarEmpleado.class);
//                    activity.startActivity(intent);
//                }else if (test.equals("0"))
//                    Log.d("NO CARGO", test);
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
        }
    }

//    private class PostDataTOServer extends AsyncTask<Void, Void, Void> {
//
//        String response = "";
//        //Create hashmap Object to send parameters to web service
//        HashMap<String, String> postDataParams;
//        @Override
//        protected void onPreExecute() {
//            super.onPreExecute();
//
//            pDialog = new ProgressDialog(Agregar.this);
//            pDialog.setMessage("Please wait...");
//            pDialog.setCancelable(false);
//            pDialog.show();
//        }
//        @Override
//        protected Void doInBackground(Void... arg0) {
//            postDataParams=new HashMap<String, String>();
//            postDataParams.put("nombre", strnombre);
//            postDataParams.put("apellido", strapellido);
//            postDataParams.put("email", stremail);
//            postDataParams.put("cedula", strcedula);
//            postDataParams.put("password", strpassword);
//            postDataParams.put("sueldo", (String) strsueldo);
//            postDataParams.put("horaEntrada",(String) strhoraentrada);
//            postDataParams.put("horaSalida",(String) strhorasalida);
//            //Call ServerData() method to call webservice and store result in response
//            response= service.ServerData(path,postDataParams);
//            try {
//                int jsonStart = response.indexOf("{");
//                int jsonEnd = response.lastIndexOf("}");
//
//                if (jsonStart >= 0 && jsonEnd >= 0 && jsonEnd > jsonStart) {
//                    response = response.substring(jsonStart, jsonEnd + 1);
//                    response.replaceFirst("<font>.*?</font>", "");
//                    response.replaceFirst("<b>.*?</b>", "");
//                } else {
//
//                    json = new JSONObject(response);
//                    System.out.println("JSONNNN=" + json);
//                }
//
//                //Get Values from JSONobject
//                System.out.println("success=" + json.get("success"));
//                success = json.getInt("success");
//
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
//            return null;
//        }
//        @Override
//        protected void onPostExecute(Void result) {
//            super.onPostExecute(result);
//            if (pDialog.isShowing())
//                pDialog.dismiss();
//            if(success==1) {
//                Toast.makeText(getApplicationContext(), "Employee Added successfully..!", Toast.LENGTH_LONG).show();
//            }
//        }
//    }
}

