package com.injob.injob.injobapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

public class Eliminar extends AppCompatActivity {

    private ListView lvDelete;
    TextView del ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eliminar);
        lvDelete = (ListView) findViewById(R.id.listDelete);
        del =(TextView)findViewById(R.id.deletetxt);
        ListarConexion lis = new ListarConexion();
        lis.recoger(getApplicationContext(),lvDelete);

        lvDelete.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                String nameOfStation = ((TextView)view.findViewById(R.id.id)).getText().toString();
                del.setText(nameOfStation);
            }
        });
    }


    public void eliminarEmpleado(View view){
        Thread tr= new Thread() {
            @Override
            public void run() {

                final EliminarConexion lCon = new EliminarConexion();
                final String resultado = lCon.enviarDatosGet(del.getText().toString());
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        lCon.obtDatosJson(resultado, getApplicationContext());
                        Intent edit = new Intent(getApplicationContext(), ListarEmpleado.class);
                        startActivity(edit);

                    }
                });
            }
        };
        tr.start();
    }


}
