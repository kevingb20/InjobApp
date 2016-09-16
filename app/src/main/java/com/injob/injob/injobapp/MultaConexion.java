package com.injob.injob.injobapp;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Just on 16/09/2016.
 */
public class MultaConexion {


    public void recoger(Context context, ListView list, String multa){
        new ListarConexionasytask(context, list,multa).execute();

    }

    public class ListarConexionasytask extends AsyncTask<Void, Void, Void> {

        ArrayList<HashMap<String, String>> contactList = new ArrayList<HashMap<String, String>>();

        private  Context context;
        private  ListView list;
        private  String multa;

        public ListarConexionasytask(Context context, ListView list, String multa) {
            this.context = context;
            this.list= list;
            this.multa=multa;
        }

        @Override
        protected Void doInBackground(Void... arg0) {

            String url = "http://drwaltergarcia.com/InjobApp/mostrarMulta.php?"+
                    "&multa="  +multa;
            HttpHandler sh = new HttpHandler();

            String jsonStr = sh.makeServiceCall(url);

            if (jsonStr != null) {
                try {
                    JSONArray contacts = new JSONArray((jsonStr));
                    for (int i = 0; i < contacts.length(); i++) {

                        JSONObject c = contacts.getJSONObject(i);

                        String id = c.getString("id");
                        String nombre = c.getString("nombre");
                        String apellido = c.getString("apellido");
                        String Multa = c.getString("Multa");
                        HashMap<String, String> contact = new HashMap<>();
                        contact.put("id", id);
                        contact.put("nombre", nombre);
                        contact.put("apellido", apellido);
                        contact.put("Multa", Multa);
                        contactList.add(contact);
                    }
                } catch (final JSONException e) {
                    Log.e("ERRROR", "Json parsing error: " + e.getMessage());
                }
            } else {
                Log.e("ERROR2", "Couldn't get json from server.");
            }
            return null;
        }


        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            ListAdapter adapter = new SimpleAdapter(
                    context, contactList,
                    R.layout.list_item, new String[]{"id","nombre", "apellido", "Multa"},
                    new int[]{R.id.id, R.id.name, R.id.apellido, R.id.multa});
            list.setAdapter(adapter);
        }

    }
}
