package com.injob.injob.injobapp;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class Eliminar extends AppCompatActivity {

    private String TAG = Eliminar.class.getSimpleName();
    private ProgressDialog pDialogDelete;
    private ListView lvDelete;
    // URL to get contacts JSON
    private static String urlDel = "http://drwaltergarcia.com/InjobApp/getEmpleados.php";
    ArrayList<HashMap<String, String>> contactListDelete;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eliminar);
        lvDelete = (ListView) findViewById(R.id.list);
        new DeleteContacts().execute(); //llama al hilo que realizara la coneccion y extraccion de la data
    }

    /**
     * Async task class to get json by making HTTP call
     */
    private class DeleteContacts extends AsyncTask<Void, Void, Void> {


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Showing progress dialog
            pDialogDelete = new ProgressDialog(Eliminar.this);
            pDialogDelete.setMessage("Please wait...");
            pDialogDelete.setCancelable(false);
            pDialogDelete.show();

        }

        @Override
        protected Void doInBackground(Void... arg0) {
            ArrayList<HashMap<String, String>> contactListDelete = new ArrayList<HashMap<String, String>>();
            HttpHandler shi = new HttpHandler();
            String jsonStr = shi.makeServiceCall(urlDel);


            if (jsonStr != null) {
                try {
                    JSONArray contacts = new JSONArray((jsonStr)); //como sabemos que lo primero que llega es un array usamos esto caso contrario un JsonObject
                    // looping through All Contacts
                    for (int i = 0; i < contacts.length(); i++) {

                        JSONObject c = contacts.getJSONObject(i); //dentro del array vienen objetos se los toma de esta manera

                        // se crean variables que vayan guardando los datos para su posterior uso

                        String id = c.getString("id");
                        String nombre = c.getString("nombre");
                        String apellido = c.getString("apellido");
                        String email = c.getString("email");
                        String password = c.getString("password");
                        String cedula = c.getString("cedula");
                        String sueldo = c.getString("sueldo");
                        String horaEntrada = c.getString("horaEntrada");
                        String horaSalida = c.getString("horaSalida");
                        String Multa = c.getString("Multa");

                        // tmp hash map for single contact
                        HashMap<String, String> contact = new HashMap<String, String>();

                        // adding each child node to HashMap key => value
                        contact.put("id", id);
                        contact.put("nombre", nombre);
                        contact.put("apellido", apellido);
                        contact.put("Multa", Multa);

                        // adding contact to contact list
                        contactListDelete.add(contact);
                        Log.e(TAG, contactListDelete.toString());
                    }
                } catch (final JSONException e) {
                    Log.e(TAG, "Json parsing error: " + e.getMessage());
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(),
                                    "Json parsing error: " + e.getMessage(),
                                    Toast.LENGTH_LONG)
                                    .show();
                        }
                    });
                }
            } else {
                Log.e(TAG, "Couldn't get json from server.");
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(),
                                "Couldn't get json from server. Check LogCat for possible errors!",
                                Toast.LENGTH_LONG)
                                .show();
                    }
                });
            }
            return null;
        }


        @Override
        protected void onPostExecute(Void result) {

            super.onPostExecute(result);
            // Dismiss the progress dialog
            if (pDialogDelete.isShowing())
                pDialogDelete.dismiss();
            /**
             * Updating parsed JSON data into ListView
             * */
            //se crea un adaptador del listview que es como se mostrara los datos recibidos en este caso es list_item. se le envia los datos y en donde se los ubicara
            ListAdapter adapter = new SimpleAdapter(
                    Eliminar.this, contactListDelete,
                    R.layout.list_item, new String[]{"id","nombre", "apellido", "Multa"},
                    new int[]{R.id.id, R.id.name, R.id.apellido, R.id.multa});
            lvDelete.setAdapter(adapter);
        }


    }
}
