package com.example.loltrivial;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

public class LogIn extends AppCompatActivity {

    //Variables globales
    public static EditText nombreUsuario;
    private CheckBox checkBox;
    public static MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Encontrar las referencias a los controles
        nombreUsuario = findViewById(R.id.CampoNombre);
        checkBox = findViewById(R.id.nombreValido);

        //Inicializaciones
        mediaPlayer = MediaPlayer.create(this, R.raw.bensound_adventure);

        //Llamadas iniciales
        mediaPlayer.setLooping(true); //La musica de fondo debe estar en loop y reprodicirse desde el primer momento
        mediaPlayer.start();
        checkBox.setChecked(false);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mediaPlayer.pause();
    }
    @Override
    protected void onResume() {
        super.onResume();
        mediaPlayer.start();
    }

    public void onBackPressed() {
        SalirAppAlerta(null);
    }

    public void EntrarMenuPricipal(View v){
        if(nombreUsuario.getText().toString().isEmpty()){
            Toast.makeText(this,"Nombre vacío",Toast.LENGTH_SHORT).show();
            checkBox.setChecked(false);
            return;
        }else if (nombreUsuario.getText().toString().length() > 12 ){
            Toast.makeText(this,"Nombre demasiado largo",Toast.LENGTH_SHORT).show();
            checkBox.setChecked(false);
            return;
        }
        checkBox.setChecked(true);
        Intent menuPrincipal = new Intent(this, MenuPricipal.class); //Arrancar nueva actividad
        menuPrincipal.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(menuPrincipal);
        finish();
    }

    public void WebIcon(View v){
        String url;
        if(v.getId() == R.id.Icono){
            url = "https://www.artstation.com/sainny";
        }else{
            url = "https://kakugames.itch.io/";
        }
        Intent web = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        startActivity(web);
    }

    public void SalirAppAlerta(View v){
        //Crear el objeto alerta
        AlertDialog.Builder alerta = new AlertDialog.Builder(this); //Creamos una alerta
        alerta.setTitle("¿Quieres salir de la aplicación?")
                .setCancelable(false)
                .setPositiveButton("Si",  new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mediaPlayer.stop();
                        finish();
                    }
                })
                .setNegativeButton("No",  new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

        //Crear la caja de alerta
        AlertDialog cajaAlerta  = alerta.create();
        cajaAlerta.show();
    }
}