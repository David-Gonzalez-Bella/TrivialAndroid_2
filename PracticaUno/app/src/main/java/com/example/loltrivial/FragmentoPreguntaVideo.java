package com.example.loltrivial;

import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.MediaController;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.VideoView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentoPreguntaVideo#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentoPreguntaVideo extends Fragment {

    //Variables globales
    public TextView enunciado;
    public VideoView video;
    public RadioButton r1;
    public RadioButton r2;
    public RadioButton r3;
    public RadioButton r4;
    private Jugar actividadJugar;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public FragmentoPreguntaVideo() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentoPreguntaVideo.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentoPreguntaVideo newInstance(String param1, String param2) {
        FragmentoPreguntaVideo fragment = new FragmentoPreguntaVideo();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        PreguntaEntidad preguntaActual = actividadJugar.preguntaActual;

        //Actualizar los textos del fragmento al crearlo
        enunciado.setText(preguntaActual.getPregunta());
        video.setVideoURI(Uri.parse("android.resource://" + getActivity().getPackageName() + "/" + preguntaActual.getMultimedia()));
        video.setMediaController(new MediaController(getContext()));
        video.requestFocus(); //Nos ahorra un tic al play (activa el focus o foco sobre este video)
        video.setOnCompletionListener(new MediaPlayer.OnCompletionListener() { //Reproducir en bucle
            public void onCompletion(MediaPlayer mp) {
                video.start();
            }
        });
        video.start(); //Reproducir desde que entramos a la pregunta

        r1.setText(preguntaActual.getOpcion1());
        r2.setText(preguntaActual.getOpcion2());
        r3.setText(preguntaActual.getOpcion3());
        r4.setText(preguntaActual.getOpcion4());

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_fragmento_pregunta_video, container, false);

        //Buscamos los controles cuyo contenido querremos modificar
        enunciado = view.findViewById(R.id.enunciado4);
        video = view.findViewById(R.id.preguntaVideo);
        r1 = view.findViewById(R.id.respuesta1);
        r2 = view.findViewById(R.id.respuesta2);
        r3 = view.findViewById(R.id.respuesta3);
        r4 = view.findViewById(R.id.respuesta4);

        //Inicializar
        actividadJugar = (Jugar)getActivity();

        //Establecer color de la letra, enfuncion del tema (claro u oscuro)
        SharedPreferences ajustes = getActivity().getSharedPreferences(Ajustes.PREFS_NAME, 0);
        actividadJugar.fondoOscuro = ajustes.getBoolean("tema", true);

        if(actividadJugar.fondoOscuro) {
            enunciado.setTextColor(0xFFFFFFFF);
            r1.setTextColor(0xFFFFFFFF);
            r2.setTextColor(0xFFFFFFFF);
            r3.setTextColor(0xFFFFFFFF);
            r4.setTextColor(0xFFFFFFFF);
        }else{
            enunciado.setTextColor(0xFF000000);
            r1.setTextColor(0xFF000000);
            r2.setTextColor(0xFF000000);
            r3.setTextColor(0xFF000000);
            r4.setTextColor(0xFF000000);
        }

        //Establecer la funcionalidad de los botones
        r1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                r2.setChecked(false);
                r3.setChecked(false);
                r4.setChecked(false);
                ReproducirSonido();
                actividadJugar.elegida = 1;
            }
        });
        r2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                r1.setChecked(false);
                r3.setChecked(false);
                r4.setChecked(false);
                ReproducirSonido();
                actividadJugar.elegida = 2;
            }
        });
        r3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                r2.setChecked(false);
                r1.setChecked(false);
                r4.setChecked(false);
                ReproducirSonido();
                actividadJugar.elegida = 3;
            }
        });
        r4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                r1.setChecked(false);
                r2.setChecked(false);
                r3.setChecked(false);
                ReproducirSonido();
                actividadJugar.elegida = 4;
            }
        });
        return view;
    }

    public void ReproducirSonido(){
        if(actividadJugar.elegirRespuesta_snd.isPlaying()){
            actividadJugar.elegirRespuesta_snd.stop();
            actividadJugar.elegirRespuesta_snd = MediaPlayer.create(getActivity(), R.raw.elegir_respuesta);
        }
        actividadJugar.elegirRespuesta_snd.start();
    }
}