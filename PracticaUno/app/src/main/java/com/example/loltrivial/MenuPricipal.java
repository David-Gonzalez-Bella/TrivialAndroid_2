package com.example.loltrivial;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MenuPricipal extends AppCompatActivity{

    //Variables globales
    public TextView nickUsuario;
    public ConstraintLayout fondo;
    public ImageButton icono;
    private ArrayList<Integer> galeria;
    private int indiceGaleria;
    private boolean fondoOscuro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_pricipal);

        //Encontrar las referencias a los controles
        fondo = findViewById(R.id.fondoLayout);
        nickUsuario = findViewById(R.id.CampoUsuario);
        icono = findViewById(R.id.Icono);

        //Inicializaciones
        galeria = new ArrayList<Integer>();
        galeria.add(R.drawable.iconkata);
        galeria.add(R.drawable.iconori);
        galeria.add(R.drawable.iconquinn);
        galeria.add(R.drawable.iconxayah);

        //Llamadas iniciales
        SharedPreferences ajustes = getSharedPreferences(Ajustes.PREFS_NAME, 0);

        indiceGaleria = ajustes.getInt("imagenPerfilIndice", 0);
        icono.setImageResource(galeria.get(indiceGaleria % galeria.size()));
        nickUsuario.setText(LogIn.nombreUsuario.getText()); //Establecer el nick de usuario
        nickUsuario.setPaintFlags(0);

        fondoOscuro = ajustes.getBoolean("tema", true); //Establecer tema claro u oscuro
        if(fondoOscuro){ fondo.setBackgroundResource(R.drawable.fondomenuprincipal); } //Este metodo sera llamado al pulsar el switch que cambia de tema claro a oscuro
        else{ fondo.setBackgroundResource(R.drawable.fondomenuprincipalclaro); }
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

    public void onBackPressed() {
        SalirAlerta(null);
    }

    public void CambarIcono(View v){
        SharedPreferences ajustes = getSharedPreferences(Ajustes.PREFS_NAME, 0);
        SharedPreferences.Editor editor = ajustes.edit();
        editor.putInt("imagenPerfilIndice", ajustes.getInt("imagenPerfilIndice", 0) + 1);
        editor.commit(); //Subir los cambios
        indiceGaleria = ajustes.getInt("imagenPerfilIndice", 0);
        icono.setImageResource(galeria.get(indiceGaleria % galeria.size()));

    }

    public void EntrarElegirDificultad(View v){
        Intent menuElegirDificultad = new Intent(this, ElegirDificultad.class); //Arrancar nueva actividad
        menuElegirDificultad.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(menuElegirDificultad);
        finish();
    }

    public void EntrarCategoria(View v){
        Intent menuCategoria = new Intent(this, Categoria.class); //Arrancar nueva actividad
        menuCategoria.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(menuCategoria);
        finish();
    }

    public void EntrarAjustes(View v){
        Intent menuAjustes = new Intent(this, Ajustes.class); //Arrancar nueva actividad
        menuAjustes.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(menuAjustes);
        finish();
    }

    public void EntrarRanking(View v){
        Intent menuRanking = new Intent(this, Ranking.class); //Arrancar nueva actividad
        menuRanking.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(menuRanking);
        finish();
    }

    private void IrPantallaLogin(){
        Intent pantallaLog = new Intent(this, LogIn.class);
        pantallaLog.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(pantallaLog);
        LogIn.mediaPlayer.stop();
        finish();
    }
    public void SalirAlerta(View v){
        //Crear el objeto alerta
        AlertDialog.Builder alerta = new AlertDialog.Builder(this); //Creamos una alerta
        alerta.setTitle("¿Quieres salir?")
                .setMessage("Volverás a la pantalla de log in")
                .setCancelable(false)
                .setPositiveButton("Si",  new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        IrPantallaLogin();
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