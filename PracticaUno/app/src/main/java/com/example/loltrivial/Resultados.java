package com.example.loltrivial;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class Resultados extends AppCompatActivity {

    //Controles de la actividad
    public ConstraintLayout fondo;
    public TextView textoResultado;
    public TextView textoTiempoTotal;

    //Variables globales y flags
    public int puntuacion;
    public int tiempoTotal;
    public MediaPlayer sonidoVictoria_snd;
    private boolean fondoOscuro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resultados);

        //Coger la informacion proveniente de la actividad de elegir dificultad
        Intent intent = getIntent();
        puntuacion = intent.getIntExtra("puntuacion", 0);
        tiempoTotal = intent.getIntExtra("tiempoTotal", 0);

        //Encontrar las referencias a los controles
        textoResultado = findViewById(R.id.textoResultado);
        textoTiempoTotal = findViewById(R.id.tiempoTotal);
        fondo = findViewById(R.id.fondoLayout);

        //Llamadas iniciales
        textoResultado.setText("" + puntuacion);
        textoTiempoTotal.setText("" + tiempoTotal + "''");
        sonidoVictoria_snd = MediaPlayer.create(this, R.raw.pantalla_resultados);
        sonidoVictoria_snd.start();

        //Determinar la fecha actual
        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy", Locale.getDefault());
        String formattedDate = df.format(c);

        SharedPreferences ajustes = getSharedPreferences(Ajustes.PREFS_NAME, 0);
        SharedPreferences.Editor editor = ajustes.edit();
        editor.putInt("partidasTotales", ajustes.getInt("partidasTotales", 0) + 1); //Actualizar el numero de partidas totales
        editor.putString("ultimaPartida", formattedDate); //Actualizar la fecha de la ultima partida
        editor.commit(); //Subir los cambios
        fondoOscuro = ajustes.getBoolean("tema", true);
        if (fondoOscuro) { //Establecer el tema claro u oscuro segun corresponda
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

    public void EntrarRanking(View v){
        Intent menuRanking = new Intent(this, Ranking.class); //Arrancar nueva actividad
        menuRanking.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(menuRanking);
        finish();
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