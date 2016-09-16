package com.injob.injob.injobapp;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Just on 15/09/2016.
 */
public class ListarEmpresasConexion {

    ArrayList<Empresas> empresasList = new ArrayList<Empresas>();

    public void recoger(Context context, Spinner list){
        new Getfrutas(context, list).execute();

    }

    private void populateSpinner(Context context,Spinner list ) {
        List<String> lables = new ArrayList<String>();
        for (int i = 0; i < empresasList.size(); i++) {
            lables.add(empresasList.get(i).getRazonSocial());
        }
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(context,
                android.R.layout.simple_spinner_item, lables);
        spinnerAdapter
                .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        list.setAdapter(spinnerAdapter);
    }

    private class Getfrutas extends AsyncTask<Void, Void, Void> {

        private Context context;
        private Spinner list;
        ProgressDialog pDialog;
//        private String URL_LISTA_Empresas = "http://drwaltergarcia.com/InjobApp/getEmpresas.php";
        //ArrayList<Empresas> empresasList = new ArrayList<Empresas>();

        public Getfrutas(Context context, Spinner list) {
            this.context = context;
            this.list= list;
        }
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }
        @Override
        protected Void doInBackground(Void... arg0) {

            String url = "http://drwaltergarcia.com/InjobApp/getEmpresas.php";
            HttpHandler sh = new HttpHandler();
            String json = sh.makeServiceCall(url);

//            ServiceHandler jsonParser = new ServiceHandler();
//            String json = jsonParser.makeServiceCall(URL_LISTA_Empresas, ServiceHandler.GET);

            Log.e("Response: ", "> " + json);

            if (json != null) {
                try {
                    JSONArray jsonArray= new JSONArray(json);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject c = jsonArray.getJSONObject(i);
                        Empresas cat = new Empresas(c.getString("razonSocial"));
                        empresasList.add(cat);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else {
                Log.e("JSON Data", "¿No ha recibido ningún dato desde el servidor!");
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
//            if (pDialog.isShowing())
//                pDialog.dismiss();
            populateSpinner(context, list);
        }
    }



}
