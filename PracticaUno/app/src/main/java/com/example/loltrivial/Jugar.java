package com.example.loltrivial;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Collections;
import java.util.List;

public class Jugar extends AppCompatActivity {

    //Variables globales
    //BBDD ROOM
    private List<PreguntaEntidad> listaPreguntas;
    public PreguntaEntidad preguntaActual;
    private PreguntaViewModel preguntaViewModel;
    private LiveData<List<PreguntaEntidad>> listaDificultad;

    //Controles de la actividad
    public ConstraintLayout fondo;
    public TextView puntuacionAcertadas;
    public TextView puntuacionFalladas;
    public TextView contadorPreguntasTexto;

    //Contadores y flags
    private int contadorAcertadas;
    private int contadorFalladas;
    private int contadorPreguntas;
    private int totalPreguntas;
    int puntuacionFinal;
    public int elegida;
    public boolean cuentaAtrasActiva;
    public int tiempoTotal;
    private int tiempoPartida;
    private int segundosPregunta;
    private int puntuacionAcumulada;
    private String dificultad;
    public boolean fondoOscuro;

    //Extras
    public MediaPlayer elegirRespuesta_snd;
    public ProgressBar barraTiempo;
    public CountDownTimer cuentaAtras;

    //Instancia del fragmento de cada tipo
    FragmentoPregunta preguntaFragmento;
    FragmentoPreguntaImagen preguntaImagenFragmento;
    FragmentoPreguntaMixta preguntaMixtaFragmento;
    FragmentoPreguntaVideo preguntaVideoFragmento;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jugar);

        //Coger la informacion proveniente de la actividad de elegir dificultad
        Intent intent = getIntent();
        dificultad = intent.getStringExtra("dificultad");

        ElegirPreguntasDificultad(); //Escogemos la lista de preguntas acorde a la dificultad elegida

        //BBDD ROOM
        //preguntaViewModel = ViewModelProviders.of(this).get(PreguntaViewModel.class);
        listaDificultad.observe(this, new Observer<List<PreguntaEntidad>>() { //Observador de la lista de "preguntas entidad"
            @Override
            public void onChanged(@Nullable List<PreguntaEntidad> preguntasEntidad) {
                //Acciones al crear las preguntas
                LlenarListaPreguntas(preguntasEntidad); //fetch
            }
        });

        //Encontrar la referencia al control correspondiente
        contadorPreguntasTexto = findViewById(R.id.contadorPreguntas);
        barraTiempo = findViewById(R.id.tiempo);
        fondo = findViewById(R.id.fondoLayout);
        puntuacionAcertadas = findViewById(R.id.puntuacionAcertadas);
        puntuacionFalladas = findViewById(R.id.puntuacionFalladas);

        //Inicializar
        SharedPreferences ajustes = getSharedPreferences(Ajustes.PREFS_NAME, 0);

        contadorAcertadas = 0;
        contadorFalladas = 0;
        contadorPreguntas = 1;
        tiempoPartida = 0;
        segundosPregunta = 0;
        puntuacionAcumulada = 0;
        puntuacionAcertadas.setText("Acertadas: " + contadorAcertadas);
        puntuacionFalladas.setText("Falladas: " + contadorFalladas);
        elegirRespuesta_snd = MediaPlayer.create(this, R.raw.elegir_respuesta);
        contadorPreguntasTexto.setText(0 + "/" + ajustes.getInt("nPreguntas", 5));

        fondoOscuro = ajustes.getBoolean("tema", true);

        if (fondoOscuro) //Establecer el tema claro u oscuro segun corresponda
        {
            fondo.setBackgroundResource(R.drawable.pantallajuego);
            puntuacionAcertadas.setTextColor(0xFFFADB6A);
            puntuacionFalladas.setTextColor(0xFFFADB6A);
            contadorPreguntasTexto.setTextColor(0xFFFFFFFF);
        } else {
            fondo.setBackgroundResource(R.drawable.pantallajuegoclaro);
            puntuacionAcertadas.setTextColor(0xFFA66132);
            puntuacionFalladas.setTextColor(0xFFA66132);
            contadorPreguntasTexto.setTextColor(0xFF687372);
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

    private void LlenarListaPreguntas(List<PreguntaEntidad> preguntas) {
        SharedPreferences ajustes = getSharedPreferences(Ajustes.PREFS_NAME, 0);
        listaPreguntas = preguntas; //Llenamos la lista del activity con la lista de preguntas de PreguntaViewModel
        totalPreguntas = ajustes.getInt("nPreguntas", 5); //Igualamos el total de preguntas a la lingitud de la lista
        Collections.shuffle(listaPreguntas); //Barajar las preguntas
        new CountDownTimer(10, 10) { //Dar un pequeño margen para que la tabla de la BBDD cargue las preguntas antes de extraerlas en la actividad de jugar
            public void onTick(long millisUntilFinished) { }
            public void onFinish() {
                //Crear la primera pregunta
                preguntaActual = listaPreguntas.get(0);
                contadorPreguntasTexto.setText(0 + "/" + totalPreguntas);
                switch (preguntaActual.getTipo()) { //Crear el fragmento correspondiente, en funcion del tipo de la pregunta actual
                    case "Video":
                        CrearFragmentoVideo();
                        break;
                    case "Texto":
                        CrearFragmentoTexto();
                        break;
                    case "Imagen":
                        CrearFragmentoImagen();
                        break;
                    case "Hibrida":
                        CrearFragmentoHibrido();
                        break;
                }
                if (!cuentaAtrasActiva) {
                    CrearBarraTiempo();
                }
            }
        }.start();
    }

    private void CrearPregunta() {
        preguntaActual = listaPreguntas.get(contadorPreguntas);
        contadorPreguntasTexto.setText(contadorPreguntas + "/" + totalPreguntas);
        contadorPreguntas++;
        switch (preguntaActual.getTipo()) { //Crear el fragmento correspondiente, en funcion del tipo de la pregunta actual
            case "Video":
                CrearFragmentoVideo();
                break;
            case "Texto":
                CrearFragmentoTexto();
                break;
            case "Imagen":
                CrearFragmentoImagen();
                break;
            case "Hibrida":
                CrearFragmentoHibrido();
                break;
        }
        if (cuentaAtrasActiva) {
            cuentaAtras.cancel();
        }
        CrearBarraTiempo();
    }

    public void GestionarPartida(View view) {
        if (view != null && elegida == 0) {
            Toast.makeText(this, "¡Selecciona una opción!", Toast.LENGTH_SHORT).show();
        } else {
            ComprobarCorrecta();
            puntuacionAcumulada += (15 - segundosPregunta); //Actualizar la puntuacion
            tiempoPartida += segundosPregunta; //Actualizar el tiempo total
            segundosPregunta = 0;
            if (contadorPreguntas < totalPreguntas) {
                CrearPregunta();

            } else {
                if (cuentaAtrasActiva) {
                    cuentaAtras.cancel();
                }
                puntuacionFinal = puntuacionAcumulada * contadorAcertadas;
                ActualizarRanking();
                Intent menuResultados = new Intent(this, Resultados.class); //Vamos a la pantalla de resultados
                menuResultados.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                menuResultados.putExtra("puntuacion", puntuacionFinal);
                menuResultados.putExtra("tiempoTotal", tiempoPartida);
                startActivity(menuResultados);
                finish();
            }
        }
    }

    private void ElegirPreguntasDificultad() {
        preguntaViewModel = ViewModelProviders.of(this).get(PreguntaViewModel.class);
        switch (dificultad) {
            case "Facil":
                listaDificultad = preguntaViewModel.getPreguntasFaciles();
                break;
            case "Normal":
                listaDificultad = preguntaViewModel.getPreguntasNormales();
                break;
            case "Dificil":
                listaDificultad = preguntaViewModel.getPreguntasDificiles();
                break;
            case "URF":
                listaDificultad = preguntaViewModel.getPreguntas();
                break;
        }
    }

    private void ActualizarRanking() {
        int rankingPos = GetPosicionRanking();
        if (rankingPos != -1) {
            SharedPreferences ajustes = getSharedPreferences(Ajustes.PREFS_NAME, 0);
            SharedPreferences.Editor editor = ajustes.edit();
            editor.putInt("puntuacion_" + ajustes.getInt("nPreguntas", 5) + "_" + rankingPos, puntuacionFinal);
            editor.commit(); //Subir los cambios
        }
    }

    private int GetPosicionRanking() {
        SharedPreferences ajustes = getSharedPreferences(Ajustes.PREFS_NAME, 0);
        int auxCount = -1;
        for (int i = 0; i < 5; i++) {
            int puntuacionDelRanking = ajustes.getInt("puntuacion_" + ajustes.getInt("nPreguntas", 5) + "_" + i, 0);
            if (puntuacionFinal > puntuacionDelRanking) {
                if (puntuacionDelRanking == 0) {
                    auxCount = i;
                    break;
                } else {
                    LiberarPosicion(i);
                    auxCount = i;
                    break;
                }
            }
        }
        return auxCount;
    }

    private void LiberarPosicion(int inicio) {
        SharedPreferences ajustes = getSharedPreferences(Ajustes.PREFS_NAME, 0);
        SharedPreferences.Editor editor = ajustes.edit();
        for (int i = 4; i > inicio; i--) {
            editor.putInt("puntuacion_" + ajustes.getInt("nPreguntas", 5) + "_" + i, ajustes.getInt("puntuacion_" + ajustes.getInt("nPreguntas", 5) + "_" + (i - 1), 0));
        }
        editor.commit(); //Subir los cambios
    }

    @Override
    public void onBackPressed() {
        SalirMenuPrincipalAlerta(null);
    }

    private void CrearFragmentoTexto() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        preguntaFragmento = new FragmentoPregunta();
        fragmentTransaction.replace(R.id.marcoPregunta, preguntaFragmento).addToBackStack("preguntaTexto");
        fragmentTransaction.commit();
    }

    private void CrearFragmentoImagen() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        preguntaImagenFragmento = new FragmentoPreguntaImagen();
        fragmentTransaction.replace(R.id.marcoPregunta, preguntaImagenFragmento).addToBackStack("preguntaImagen");
        fragmentTransaction.commit();
    }

    private void CrearFragmentoHibrido() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        preguntaMixtaFragmento = new FragmentoPreguntaMixta();
        fragmentTransaction.replace(R.id.marcoPregunta, preguntaMixtaFragmento).addToBackStack("preguntaMixta");
        fragmentTransaction.commit();
    }

    private void CrearFragmentoVideo() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        preguntaVideoFragmento = new FragmentoPreguntaVideo();
        fragmentTransaction.replace(R.id.marcoPregunta, preguntaVideoFragmento).addToBackStack("preguntaVideo");
        fragmentTransaction.commit();
    }

    private void IrMenuPrincipal() {
        if (cuentaAtrasActiva) {
            cuentaAtras.cancel();
        }
        Intent menuPrincipal = new Intent(this, MenuPricipal.class);
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
                    }
                });

        //Crear la caja de alerta
        AlertDialog cajaAlerta = alerta.create();
        cajaAlerta.show();
    }

    public void ComprobarCorrecta() {
        if (preguntaActual.getCorrecta() == elegida) {
            contadorAcertadas++;
            puntuacionAcertadas.setText("Acertadas: " + contadorAcertadas);

        } else {
            contadorFalladas++;
            puntuacionFalladas.setText("Falladas: " + contadorFalladas);
        }
        elegida = 0; //Tenemos que resetear la pregunta elegida de cara a la siguiente pregunta
    }

    public void CrearBarraTiempo() {
        tiempoTotal = 100;
        barraTiempo.setProgress(tiempoTotal);
        cuentaAtras = new CountDownTimer(15000, 1000) {

            @Override
            public void onTick(long millisUntilFinished) {
                if (millisUntilFinished <= 14000) {
                    tiempoTotal -= 6.66667;
                    segundosPregunta += 1;
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

    public void AlertaFinDeTiempo() {
        //Crear el objeto alerta
        AlertDialog.Builder alerta = new AlertDialog.Builder(this); //Creamos una alerta
        alerta.setTitle("¡Se acabó el tiempo!");
        alerta.setCancelable(false)
                .setPositiveButton("Ir a la siguiente pregunta", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        GestionarPartida(null);
                    }
                });

        //Crear la caja de alerta
        AlertDialog cajaAlerta = alerta.create();
        cajaAlerta.show();
    }
}