package org.dev4u.hv.guia2_moviles;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    public EditText txtURL,nombre;
    private TextView lblEstado;
    private Button btnDescargar;
    private RadioGroup grupo;
    private RadioButton radioButton;
    private RadioButton rdb1;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //inicializar
        txtURL       = (EditText) findViewById(R.id.txtURL);
        lblEstado    = (TextView) findViewById(R.id.lblEstado);
        btnDescargar = (Button)   findViewById(R.id.btnDescargar);
        nombre = (EditText) findViewById(R.id.Nombre);
        grupo = (RadioGroup) findViewById(R.id.grup);
        progressBar = (ProgressBar) findViewById(R.id.progressPorcentaje);
        nombre.setVisibility(View.INVISIBLE);


        //evento onClick
        btnDescargar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Descargar(
                        MainActivity.this,
                        lblEstado,
                        btnDescargar,
                        progressBar
                ).execute(txtURL.getText().toString());
            }
        });

        verifyStoragePermissions(this);

    }

    //esto es para activar perimiso de escritura y lectura en versiones de android 6 en adelante
    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

    //Funcion para evaluar los radioButton seleccionados
    public void checkbutton(View v){
        int radioid = grupo.getCheckedRadioButtonId();
        radioButton = (RadioButton) findViewById(radioid);
        rdb1 = (RadioButton) findViewById(R.id.rdb1);


        if(radioButton.getId()==rdb1.getId()){
            nombre.setVisibility(View.VISIBLE);
            Toast.makeText(this, "Escribe el nombre + extension jpg", Toast.LENGTH_SHORT).show();
        }else{
            nombre.setVisibility(View.INVISIBLE);
        }



    }

    //persmission method.
    public static void verifyStoragePermissions(Activity activity) {
        // Check if we have read or write permission
        int writePermission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        int readPermission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.READ_EXTERNAL_STORAGE);

        if (writePermission != PackageManager.PERMISSION_GRANTED || readPermission != PackageManager.PERMISSION_GRANTED) {
            // We don't have permission so prompt the user
            ActivityCompat.requestPermissions(
                    activity,
                    PERMISSIONS_STORAGE,
                    REQUEST_EXTERNAL_STORAGE
            );
        }
    }
}
