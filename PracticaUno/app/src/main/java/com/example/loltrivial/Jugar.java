package com.example.loltrivial;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class Jugar extends AppCompatActivity {

    //Variables globales

    //Listas de preguntas
    public ArrayList<Pregunta> preguntas;
    public ArrayList<PreguntaImagen> preguntasImagen;
    public ArrayList<PreguntaHibrida> preguntasMixtas;

    //Controles de la actividad
    public ConstraintLayout fondo;
    public TextView puntuacion;
    public TextView contadorPreguntas;

    //Contadores y flags
    public int preguntaId;
    public int preguntaImagenId;
    public int preguntaMixtaId;
    public int elegida;
    public int acertadas;
    public int preguntaActual;
    public int totalPreguntas;
    public boolean cuentaAtrasActiva;
    private boolean correcta;
    public int tiempoTotal;

    //Extras
    public MediaPlayer elegirRespuesta_snd;
    public ProgressBar barraTiempo;
    public CountDownTimer cuentaAtras;

    //Instancia del fragmento de cada tipo
    FragmentoPregunta preguntaFragmento;
    FragmentoPreguntaImagen preguntaImagenFragmento;
    FragmentoPreguntaMixta preguntaMixtaFragmento;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jugar);

        //Encontrar la referencia al control correspondiente
        contadorPreguntas = findViewById(R.id.contadorPreguntas);
        barraTiempo = findViewById(R.id.tiempo);
        fondo = findViewById(R.id.fondoLayout);
        puntuacion = findViewById(R.id.puntuacion);

        //Inicializar
        preguntas = ListaPreguntas.INSTANCE.getPreguntas();
        preguntasImagen = ListaPreguntasImagen.INSTANCE.getPreguntasImagen();
        preguntasMixtas = ListaPreguntasHibridas.INSTANCE.getPreguntasHibridas();
        preguntaId= 0;
        preguntaImagenId = -1;
        preguntaMixtaId = -1;
        elegida = 0;
        acertadas = 0;
        puntuacion.setText("Puntuacion: " + acertadas);
        preguntaActual = 1;
        correcta = false;
        elegirRespuesta_snd = MediaPlayer.create(this, R.raw.elegir_respuesta);
        totalPreguntas = preguntas.size() + preguntasImagen.size() + preguntasMixtas.size();
        contadorPreguntas.setText(preguntaActual + "/" + totalPreguntas);
        if(Ajustes.fondoOscuro) //Establecer el tema claro u oscuro segun corresponda
        {
            fondo.setBackgroundResource(R.drawable.pantallajuego);
            contadorPreguntas.setTextColor(0xFFFFFFFF);
        } else{
            fondo.setBackgroundResource(R.drawable.pantallajuegoclaro);
            contadorPreguntas.setTextColor(0xFF687372);
        }

        //Llamadas a metodos iniciales
        CrearBarraTiempo();
        CrearFragmentoTexto();
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

    public void AvanzarPregunta(View v){
        //Determinamos el ultimo fragmento que se añadio a la pila (el fragmento actual)
        int indice = this.getSupportFragmentManager().getBackStackEntryCount() - 1;
        FragmentManager.BackStackEntry fragmentoActual = this.getSupportFragmentManager().getBackStackEntryAt(indice);

        //En funcion del tipo de fragmento, crearemos uno u otro a continuacion
        switch (fragmentoActual.getName()){
            case "preguntaTexto":
                if(v!= null && elegida == 0){
                    Toast.makeText(this,"¡Selecciona una opción!",Toast.LENGTH_SHORT).show();
                }else {
                    ComprobarCorrecta();
                    if(preguntaImagenId < 4){
                        preguntaImagenId++;
                        if(cuentaAtrasActiva){ cuentaAtras.cancel(); }
                        if(barraTiempo.getProgress() != 0){
                            AlertaValidacion(fragmentoActual.getName());
                        }else {
                            CrearFragmentoImagen();
                            CrearBarraTiempo();
                            contadorPreguntas.setText(++preguntaActual + "/" + totalPreguntas);
                        }
                    }
                }
                break;
            case "preguntaImagen":
                if(v!= null && elegida == 0){
                    Toast.makeText(this,"¡Selecciona una imagen!",Toast.LENGTH_SHORT).show();
                }else {
                    ComprobarCorrectaImagen();
                    if(preguntaMixtaId < 4){
                        preguntaMixtaId++;
                        if(cuentaAtrasActiva){ cuentaAtras.cancel(); }
                        if(barraTiempo.getProgress() != 0){
                            AlertaValidacion(fragmentoActual.getName());
                        }else {
                            CrearFragmentoHibrido();
                            CrearBarraTiempo();
                            contadorPreguntas.setText(++preguntaActual + "/" + totalPreguntas);
                        }
                    }
                }
                break;
            case "preguntaMixta":
                if(v!= null && elegida == 0){
                    Toast.makeText(this,"¡Selecciona una opción!",Toast.LENGTH_SHORT).show();
                }else {
                    ComprobarCorrectaHibrida();
                    if(preguntaId < 4){
                        preguntaId++;
                        if(cuentaAtrasActiva){ cuentaAtras.cancel(); }
                        if(barraTiempo.getProgress() != 0){
                            AlertaValidacion(fragmentoActual.getName());
                        }else {
                            CrearFragmentoTexto();
                            CrearBarraTiempo();
                            contadorPreguntas.setText(++preguntaActual + "/" + totalPreguntas);
                        }
                    }else{
                        if(cuentaAtrasActiva){ cuentaAtras.cancel(); }
                        Intent menuResultados = new Intent(this, Resultados.class); //Vamos a la pantalla de resultados
                        Bundle parametros = new Bundle(); //Pasamos la puntuacion final a la actividad de Resultados
                        parametros.putInt("acertadas", acertadas);
                        menuResultados.putExtras(parametros);
                        menuResultados.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(menuResultados);
                        finish();
                    }
                }
                break;
        }
        elegida = 0; //La pregunta elegido al cambiar de pregunta sera, evidentemente, 0
        correcta = false;
    }

    private void CrearFragmentoTexto(){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        preguntaFragmento = new FragmentoPregunta();
        fragmentTransaction.replace(R.id.marcoPregunta, preguntaFragmento).addToBackStack("preguntaTexto");
        fragmentTransaction.commit();
    }

    private void CrearFragmentoImagen(){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        preguntaImagenFragmento = new FragmentoPreguntaImagen();
        fragmentTransaction.replace(R.id.marcoPregunta, preguntaImagenFragmento).addToBackStack("preguntaImagen");
        fragmentTransaction.commit();
    }

    private void CrearFragmentoHibrido(){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        preguntaMixtaFragmento = new FragmentoPreguntaMixta();
        fragmentTransaction.replace(R.id.marcoPregunta, preguntaMixtaFragmento).addToBackStack("preguntaMixta");
        fragmentTransaction.commit();
    }

    private void IrMenuPrincipal(){
        if(cuentaAtrasActiva){ cuentaAtras.cancel(); }
        Intent menuPrincipal = new Intent(this, MenuPricipal.class);
        menuPrincipal.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(menuPrincipal);
        finish();
    }

    public void SalirMenuPrincipalAlerta(View v){
        //Crear el objeto alerta
        AlertDialog.Builder alerta = new AlertDialog.Builder(this); //Creamos una alerta
        alerta.setTitle("¿Quieres salir?")
                .setMessage("Perderás el progreso actual")
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

    public void ComprobarCorrecta(){
        if(preguntas.get(preguntaId).getCorrecta() == elegida){
            acertadas++;
            puntuacion.setText("Puntuacion: " + acertadas);
            correcta = true;
        }
    }

    public void ComprobarCorrectaImagen(){
        if(preguntasImagen.get(preguntaImagenId).getCorrecta() == elegida){
            acertadas++;
            puntuacion.setText("Puntuacion: " + acertadas);
            correcta = true;
        }
    }

    public void ComprobarCorrectaHibrida(){
        if(preguntasMixtas.get(preguntaMixtaId).getCorrecta() == elegida){
            acertadas++;
            puntuacion.setText("Puntuacion: " + acertadas);
            correcta = true;
        }
    }

    public void CrearBarraTiempo() {
        tiempoTotal = 100;
        barraTiempo.setProgress(tiempoTotal);
        cuentaAtras = new CountDownTimer(15000, 1000) {

            @Override
            public void onTick(long millisUntilFinished) {
                if(millisUntilFinished <= 14000) {
                    tiempoTotal -= 6.66667;
                    barraTiempo.setProgress(tiempoTotal);
                }
            }

            @Override
            public void onFinish() {
                barraTiempo.setProgress(0);
                cuentaAtrasActiva = false;
                AlertaFinDeTiempo();
            }
        };
        cuentaAtras.start();
        cuentaAtrasActiva = true;
    }

    public void AlertaValidacion(final String nombre){
        //Crear el objeto alert
        AlertDialog.Builder alerta = new AlertDialog.Builder(this); //Creamos una alerta
        if(correcta){
            alerta.setTitle("¡Respuesta correcta!");
        }else{
            alerta.setTitle("Respuesta incorrecta...");
        }
        alerta.setCancelable(false)
                .setPositiveButton("Ir a la siguiente pregunta",  new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch(nombre){
                            case"preguntaTexto":
                                CrearFragmentoImagen();
                                break;
                            case"preguntaImagen":
                                CrearFragmentoHibrido();
                                break;
                            case"preguntaMixta":
                                CrearFragmentoTexto();
                                break;
                        }
                        CrearBarraTiempo();
                        contadorPreguntas.setText(++preguntaActual + "/" + totalPreguntas);
                        elegida = 0; //La pregunta elegido al cambiar de pregunta sera, evidentemente, 0
                    }
                });

        //Crear la caja de alerta
        AlertDialog cajaAlerta  = alerta.create();
        cajaAlerta.show();
    }

    public void AlertaFinDeTiempo(){
        //Crear el objeto alerta
        AlertDialog.Builder alerta = new AlertDialog.Builder(this); //Creamos una alerta
        alerta.setTitle("¡Se acabó el tiempo!");

        if(correcta){
            alerta.setMessage("¡Respuesta correcta!");
        }else{
            alerta.setMessage("Respuesta incorrecta...");
        }
                alerta.setCancelable(false)
                .setPositiveButton("Ir a la siguiente pregunta",  new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        AvanzarPregunta(null);
                    }
                });

        //Crear la caja de alerta
        AlertDialog cajaAlerta  = alerta.create();
        cajaAlerta.show();
    }
}