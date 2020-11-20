package com.example.loltrivial;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;

public class Ranking extends AppCompatActivity {

    //Controles de la actividad
    public ConstraintLayout fondo;
    public TextView tipoDeRanking;
    public TextView p1;
    public TextView p2;
    public TextView p3;
    public TextView p4;
    public TextView p5;

    //Variables globales y flags
    private boolean fondoOscuro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ranking);

        //Encontrar la referencia al control correspondiente
        tipoDeRanking = findViewById(R.id.tipoRanking);
        fondo = findViewById(R.id.fondoLayout);
        p1 = findViewById(R.id.mejorPuntuacion1);
        p2 = findViewById(R.id.mejorPuntuacion2);
        p3 = findViewById(R.id.mejorPuntuacion3);
        p4 = findViewById(R.id.mejorPuntuacion4);
        p5 = findViewById(R.id.mejorPuntuacion5);

        //Mostrar los textos correspondientes
        SharedPreferences ajustes = getSharedPreferences(Ajustes.PREFS_NAME, 0);

        tipoDeRanking.setText("- " + ajustes.getInt("nPreguntas", 0) + " preguntas -");
        MostrarPuntuaciones();

        //Establecer el tema claro u oscuro segun corresponda
        fondoOscuro = ajustes.getBoolean("tema", true);
        if (fondoOscuro)
        {
            fondo.setBackgroundResource(R.drawable.fondomenuprincipal);
        } else {
            fondo.setBackgroundResource(R.drawable.fondomenuprincipalclaro);
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

    private void IrMenuPrincipal(){
        Intent menuPrincipal = new Intent(this, MenuPricipal.class);
        menuPrincipal.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(menuPrincipal);
        finish();
    }

    private void MostrarPuntuaciones(){
        SharedPreferences ajustes = getSharedPreferences(Ajustes.PREFS_NAME, 0);
        p1.setText("" + ajustes.getInt("puntuacion_" + ajustes.getInt("nPreguntas", 0) + "_" + 0, 0));
        p2.setText("" + ajustes.getInt("puntuacion_" + ajustes.getInt("nPreguntas", 0) + "_" + 1, 0));
        p3.setText("" + ajustes.getInt("puntuacion_" + ajustes.getInt("nPreguntas", 0) + "_" + 2, 0));
        p4.setText("" + ajustes.getInt("puntuacion_" + ajustes.getInt("nPreguntas", 0) + "_" + 3, 0));
        p5.setText("" + ajustes.getInt("puntuacion_" + ajustes.getInt("nPreguntas", 0) + "_" + 4, 0));
    }

    public void SalirMenuPrincipalAlerta(View v){
        //Crear el objeto alerta
        AlertDialog.Builder alerta = new AlertDialog.Builder(this); //Creamos una alerta
        alerta.setTitle("¿Quieres salir?")
                .setMessage("Volverás al menú principal")
                .setCancelable(false)
                .setPositiveButton("Sí",  new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        IrMenuPrincipal();
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