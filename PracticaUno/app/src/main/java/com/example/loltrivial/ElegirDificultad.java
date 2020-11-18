package com.example.loltrivial;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

public class ElegirDificultad extends AppCompatActivity implements View.OnClickListener{

    //Controles de la actividad
    public ConstraintLayout fondo;
    public ImageButton botonFacil;
    public ImageButton botonNormal;
    public ImageButton botonDificil;
    public ImageButton botonURF;

    //Variables y flags
    private String dificultad;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_elegir_dificultad);

        //Encontrar la referencia al control correspondiente
        fondo = findViewById(R.id.fondoLayout);
        botonFacil = findViewById(R.id.facil);
        botonNormal = findViewById(R.id.normal);
        botonDificil = findViewById(R.id.dificil);
        botonURF = findViewById(R.id.URF);

        //Establecer los metodos onClick de los botones
        botonFacil.setOnClickListener(this);
        botonNormal.setOnClickListener(this);
        botonDificil.setOnClickListener(this);
        botonURF.setOnClickListener(this);

        if (Ajustes.fondoOscuro) //Establecer el tema claro u oscuro segun corresponda
        {
            fondo.setBackgroundResource(R.drawable.pantallajuego);
        } else {
            fondo.setBackgroundResource(R.drawable.pantallajuegoclaro);
        }
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case (R.id.facil):
                dificultad = "Facil";
                break;

            case (R.id.normal):
                dificultad = "Normal";
                break;

            case (R.id.dificil):
                dificultad = "Dificil";
                break;

            case (R.id.URF):
                dificultad = "URF";
                break;
        }
        Intent menuJugar = new Intent(this, Jugar.class); //Arrancar nueva actividad
        menuJugar.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        menuJugar.putExtra("dificultad", dificultad);
        startActivity(menuJugar);
        finish();
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