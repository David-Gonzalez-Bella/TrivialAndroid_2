package com.example.loltrivial;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;

public class RankingGlobal extends AppCompatActivity {

    //Variables globales
    public ConstraintLayout fondo;
    private boolean fondoOscuro;
    private TextView ultimaPartida, partidasTotales;
    private TextView mp5_1, mp5_2, mp5_3, mp5_4, mp5_5;
    private TextView mp7_1, mp7_2, mp7_3, mp7_4, mp7_5;
    private TextView mp10_1, mp10_2, mp10_3, mp10_4, mp10_5;

    private ArrayList<TextView> ranking_5_Texts = new ArrayList<TextView>();
    private ArrayList<TextView> ranking_7_Texts = new ArrayList<TextView>();
    private ArrayList<TextView> ranking_10_Texts = new ArrayList<TextView>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ranking_global);

        //Encontrar las referencias a los controles
        ultimaPartida = findViewById(R.id.ultimaPartida);
        partidasTotales = findViewById(R.id.partidasTotales);

        fondo = findViewById(R.id.fondoLayout);
        mp5_1 = findViewById(R.id.mejorPuntuacion1_5);
        mp5_2 = findViewById(R.id.mejorPuntuacion2_5);
        mp5_3 = findViewById(R.id.mejorPuntuacion3_5);
        mp5_4 = findViewById(R.id.mejorPuntuacion4_5);
        mp5_5 = findViewById(R.id.mejorPuntuacion5_5);

        mp7_1 = findViewById(R.id.mejorPuntuacion1_7);
        mp7_2 = findViewById(R.id.mejorPuntuacion2_7);
        mp7_3 = findViewById(R.id.mejorPuntuacion3_7);
        mp7_4 = findViewById(R.id.mejorPuntuacion4_7);
        mp7_5 = findViewById(R.id.mejorPuntuacion5_7);

        mp10_1 = findViewById(R.id.mejorPuntuacion1_10);
        mp10_2 = findViewById(R.id.mejorPuntuacion2_10);
        mp10_3 = findViewById(R.id.mejorPuntuacion3_10);
        mp10_4 = findViewById(R.id.mejorPuntuacion4_10);
        mp10_5 = findViewById(R.id.mejorPuntuacion5_10);

        //Llamadas iniciales
        SharedPreferences ajustes = getSharedPreferences(Ajustes.PREFS_NAME, 0);



        ultimaPartida.setText("" + ajustes.getString("ultimaPartida", "XX - XX - XXXX"));
        partidasTotales.setText("" + ajustes.getInt("partidasTotales", 0));

        ranking_5_Texts.add(mp5_1);
        ranking_5_Texts.add(mp5_2);
        ranking_5_Texts.add(mp5_3);
        ranking_5_Texts.add(mp5_4);
        ranking_5_Texts.add(mp5_5);

        ranking_7_Texts.add(mp7_1);
        ranking_7_Texts.add(mp7_2);
        ranking_7_Texts.add(mp7_3);
        ranking_7_Texts.add(mp7_4);
        ranking_7_Texts.add(mp7_5);

        ranking_10_Texts.add(mp10_1);
        ranking_10_Texts.add(mp10_2);
        ranking_10_Texts.add(mp10_3);
        ranking_10_Texts.add(mp10_4);
        ranking_10_Texts.add(mp10_5);

        for(int i = 0; i < 5; i++){ //Mostrar los rankings
            ranking_5_Texts.get(i).setText("" + ajustes.getInt("puntuacion_5_" + i, 0));
            ranking_7_Texts.get(i).setText("" + ajustes.getInt("puntuacion_7_" + i, 0));
            ranking_10_Texts.get(i).setText("" + ajustes.getInt("puntuacion_10_" + i, 0));
        }
//        ranking_5_Texts.get(0).setText("" + ajustes.getInt("puntuacion_5_" + 0, 0));
        fondoOscuro = ajustes.getBoolean("tema", true);
        if(fondoOscuro){ fondo.setBackgroundResource(R.drawable.fondomenuprincipal); } //Establecer el tema claro u oscuro segun corresponda
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

    public void onBackPressed() { SalirMenuPrincipalAlerta(null); }

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