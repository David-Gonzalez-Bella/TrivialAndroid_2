package com.example.loltrivial;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class Resultados extends AppCompatActivity {

    public int acertadas;
    public ConstraintLayout fondo;
    public MediaPlayer sonidoVictoria_snd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resultados);

        //Encontrar las referencias a los controles

        TextView textoResultado = findViewById(R.id.textoResultado);
        fondo = findViewById(R.id.fondoLayout);

        //Llamadas iniciales
        ObtenerParametos(); //Obtener los parametros provenientes de la actividad anterior
        textoResultado.setText("" + acertadas);
        sonidoVictoria_snd = MediaPlayer.create(this, R.raw.pantalla_resultados);
        sonidoVictoria_snd.start();
        if (Ajustes.fondoOscuro) { //Establecer el tema claro u oscuro segun corresponda
            fondo.setBackgroundResource(R.drawable.fondomenuprincipal);
        } else {
            fondo.setBackgroundResource(R.drawable.fondomenuprincipalclaro);
        }
    }

    public void ObtenerParametos(){
        Bundle parametros = getIntent().getExtras();
        if(parametros != null){
            acertadas = parametros.getInt("acertadas");
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        LogIn.mediaPlayer.pause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        LogIn.mediaPlayer.start();
    }

    @Override
    public void onBackPressed() {
        SalirMenuPrincipalAlerta(null);
    }

    public void EntrarMenuP(View v) {
        IrMenuPrincipal();
    }

    public void IrMenuPrincipal() {
        Intent menuPrincipal = new Intent(this, MenuPricipal.class); //Arrancar nueva actividad
        menuPrincipal.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(menuPrincipal);
        finish();
    }

    public void SalirMenuPrincipalAlerta(View v) {
        //Crear el objeto alerta
        AlertDialog.Builder alerta = new AlertDialog.Builder(this); //Creamos una alerta
        alerta.setTitle("¿Quieres salir?")
                .setMessage("Perderás el progreso actual")
                .setCancelable(false)
                .setPositiveButton("Si", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        IrMenuPrincipal();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

        //Crear la caja de alerta
        AlertDialog cajaAlerta = alerta.create();
        cajaAlerta.show();
    }
}