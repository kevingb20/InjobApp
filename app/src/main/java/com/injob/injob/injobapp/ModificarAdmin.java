package com.injob.injob.injobapp;

import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

public class ModificarAdmin extends AppCompatActivity {

    private ListView lvModificar;
    TextView mod;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modificar_admin);
        lvModificar = (ListView) findViewById(R.id.listModi);
        mod = (TextView)findViewById(R.id.modificarTxt);
        ListarConexion lis = new ListarConexion();
        lis.recoger(getApplicationContext(),lvModificar);
        lvModificar.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                // selected item
                String nameOfStation = ((TextView)view.findViewById(R.id.id)).getText().toString();
                mod.setText(nameOfStation);
            }
        });
        int SDK_INT = android.os.Build.VERSION.SDK_INT;
        if (SDK_INT > 8)
        {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                    .permitAll().build();
            StrictMode.setThreadPolicy(policy);
            //your codes here

        }
    }



    public void modificadofinaladmin(View view) {

        Thread tr= new Thread() {

            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        ModificarAdminConexion modi= new ModificarAdminConexion();
                        modi.enviarDatosGetModAdmin(mod.getText().toString(), getApplicationContext());

                    }
                });


            }
        };
        tr.start();


    }
}
