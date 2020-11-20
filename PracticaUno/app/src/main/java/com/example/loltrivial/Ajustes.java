package com.example.loltrivial;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.SeekBar;
import android.widget.Switch;

public class Ajustes extends AppCompatActivity {

    //Variables globales
    public static final String PREFS_NAME = "PreferencesFile";
    private AudioManager audioManager;
    private int nPreguntas;
    private boolean fondoOscuro = true;

    //Controles de la actividad
    private SeekBar barraVolumen;
    private Switch cambiarModo;
    public ConstraintLayout fondo;
    public RadioButton r1;
    public RadioButton r2;
    public RadioButton r3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ajustes);

        //Encontrar las referencias a los controles
        fondo = findViewById(R.id.fondoLayout);
        barraVolumen = findViewById(R.id.barraVolumen);
        cambiarModo = findViewById(R.id.cambiarModo);
        r1 = findViewById(R.id.respuesta1);
        r2 = findViewById(R.id.respuesta2);
        r3 = findViewById(R.id.respuesta3);

        SharedPreferences ajustes = getSharedPreferences(PREFS_NAME, 0);

        //Settear el numero de preguntas en las preferencias compartidas
        fondoOscuro = ajustes.getBoolean("tema", true);
        nPreguntas = ajustes.getInt("nPreguntas", 10);

        //Llamadas iniciales
       switch (nPreguntas){ //Marcar la casilla correspondiente al numero de preguntas guardadas en las preferencias compartidas
           case 5:
               r1.setChecked(true);
               break;
           case 7:
               r2.setChecked(true);
               break;
           case 10:
               r3.setChecked(true);
               break;
       }

        if (fondoOscuro)  //Establecer el tema claro u oscuro segun corresponda al entrar a la pantalla de ajustes
        {
            cambiarModo.setText("Oscuro");
            fondo.setBackgroundResource(R.drawable.fondomenuprincipal);
        } else {
            cambiarModo.setText("Claro");
            fondo.setBackgroundResource(R.drawable.fondomenuprincipalclaro);
        }
        setVolumeControlStream(AudioManager.STREAM_MUSIC);
        EstablecerBarraVolumen();

        //Establecer la funcionalidad de los botones
        r1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                r2.setChecked(false);
                r3.setChecked(false);

                //Settear el numero de preguntas en las preferencias compartidas
                SharedPreferences ajustes = getSharedPreferences(PREFS_NAME, 0);
                SharedPreferences.Editor editor = ajustes.edit();
                editor.putInt("nPreguntas", 5);
                editor.commit(); //Subir los cambios
            }
        });
        r2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                r1.setChecked(false);
                r3.setChecked(false);

                //Settear el numero de preguntas en las preferencias compartidas
                SharedPreferences ajustes = getSharedPreferences(PREFS_NAME, 0);
                SharedPreferences.Editor editor = ajustes.edit();
                editor.putInt("nPreguntas", 7);
                editor.commit(); //Subir los cambios
            }
        });
        r3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                r2.setChecked(false);
                r1.setChecked(false);

                //Settear el numero de preguntas en las preferencias compartidas
                SharedPreferences ajustes = getSharedPreferences(PREFS_NAME, 0);
                SharedPreferences.Editor editor = ajustes.edit();
                editor.putInt("nPreguntas", 10);
                editor.commit(); //Subir los cambios
            }
        });
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

    public void EstablecerBarraVolumen() {
        audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        barraVolumen.setMax(audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC));
        barraVolumen.setProgress(audioManager.getStreamVolume(AudioManager.STREAM_MUSIC));
        barraVolumen.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, progress, 0);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    public void CambiarTema(View v) { //Este metodo sera llamado al pulsar el switch que cambia de tema claro a oscuro
        SharedPreferences ajustes = getSharedPreferences(PREFS_NAME, 0);
        SharedPreferences.Editor editor = ajustes.edit();
        editor.putBoolean("tema", !ajustes.getBoolean("tema", true));
        editor.commit(); //Subir los cambios

        fondoOscuro = ajustes.getBoolean("tema", true);

        if (fondoOscuro) {
            cambiarModo.setText("Oscuro");
            fondo.setBackgroundResource(R.drawable.fondomenuprincipal);
        } else {
            cambiarModo.setText("Claro");
            fondo.setBackgroundResource(R.drawable.fondomenuprincipalclaro);
        }
    }

    private void IrMenuPrincipal() {
        Intent menuPrincipal = new Intent(this, MenuPricipal.class);
        menuPrincipal.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(menuPrincipal);
        finish();
    }

    public void SalirMenuPrincipalAlerta(View v) {
        //Crear el objeto alerta
        AlertDialog.Builder alerta = new AlertDialog.Builder(this); //Creamos una alerta
        alerta.setTitle("¿Quieres salir?")
                .setMessage("Volverás al menú principal")
                .setCancelable(false)
                .setPositiveButton("Sí", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        IrMenuPrincipal();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                        //PantallaCompleta();
                    }
                });

        //Crear la caja de alerta
        AlertDialog cajaAlerta = alerta.create();
        cajaAlerta.show();
    }
}