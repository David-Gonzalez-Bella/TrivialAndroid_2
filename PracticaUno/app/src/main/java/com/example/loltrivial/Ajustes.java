package com.example.loltrivial;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.AudioManager;
import android.os.Bundle;
import android.view.View;
import android.widget.SeekBar;
import android.widget.Switch;

public class Ajustes extends AppCompatActivity {

    //Variables globales
    private AudioManager audioManager;
    private SeekBar barraVolumen;
    private Switch cambiarModo;
    public ConstraintLayout fondo;
    public static boolean fondoOscuro = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ajustes);

        //Encontrar las referencias a los controles
        fondo = findViewById(R.id.fondoLayout);
        barraVolumen = findViewById(R.id.barraVolumen);
        cambiarModo = findViewById(R.id.cambiarModo);


        //Llamadas iniciales
        if(Ajustes.fondoOscuro)  //Establecer el tema claro u oscuro segun corresponda
        {
            cambiarModo.setText("Oscuro");
            fondo.setBackgroundResource(R.drawable.fondomenuprincipal);
        }else{
            cambiarModo.setText("Claro");
            fondo.setBackgroundResource(R.drawable.fondomenuprincipalclaro);
        }
        setVolumeControlStream(AudioManager.STREAM_MUSIC);
        EstablecerBarraVolumen();
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

    public void EstablecerBarraVolumen(){

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

    public void CambiarTema(View v){ //Este metodo sera llamado al pulsar el switch que cambia de tema claro a oscuro
        fondoOscuro = !fondoOscuro;
        if(fondoOscuro){
            cambiarModo.setText("Oscuro");
            fondo.setBackgroundResource(R.drawable.fondomenuprincipal);
        }else{
            cambiarModo.setText("Claro");
            fondo.setBackgroundResource(R.drawable.fondomenuprincipalclaro);
        }
    }

    private void IrMenuPrincipal(){
        Intent menuPrincipal = new Intent(this, MenuPricipal.class);
        menuPrincipal.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(menuPrincipal);
        finish();
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
                        //PantallaCompleta();
                    }
                });

        //Crear la caja de alerta
        AlertDialog cajaAlerta  = alerta.create();
        cajaAlerta.show();
    }
}