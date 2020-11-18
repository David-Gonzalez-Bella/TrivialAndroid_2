package com.example.loltrivial;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class Ranking extends AppCompatActivity {

    //Controles de la actividad
    public ConstraintLayout fondo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ranking);

        //Encontrar la referencia al control correspondiente
        fondo = findViewById(R.id.fondoLayout);

        if (Ajustes.fondoOscuro) //Establecer el tema claro u oscuro segun corresponda
        {
            fondo.setBackgroundResource(R.drawable.pantallajuego);
        } else {
            fondo.setBackgroundResource(R.drawable.pantallajuegoclaro);
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