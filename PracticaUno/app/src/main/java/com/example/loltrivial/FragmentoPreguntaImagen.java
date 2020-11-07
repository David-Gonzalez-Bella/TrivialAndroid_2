package com.example.loltrivial;

import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentoPreguntaImagen#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentoPreguntaImagen extends Fragment {

    //Variables globales
    public TextView enunciado;
    public ImageButton r1;
    public ImageButton r2;
    public ImageButton r3;
    public ImageButton r4;
    private Jugar actividadJugar;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public FragmentoPreguntaImagen() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentoPreguntaImagen.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentoPreguntaImagen newInstance(String param1, String param2) {
        FragmentoPreguntaImagen fragment = new FragmentoPreguntaImagen();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //Actualizar los textos del fragmento al crearlo
        enunciado.setText(((Jugar)getActivity()).preguntasImagen.get(((Jugar)getActivity()).preguntaImagenId).getPregunta());
        r1.setImageResource(((Jugar)getActivity()).preguntasImagen.get(((Jugar)getActivity()).preguntaImagenId).getOpcion1());
        r2.setImageResource(((Jugar)getActivity()).preguntasImagen.get(((Jugar)getActivity()).preguntaImagenId).getOpcion2());
        r3.setImageResource(((Jugar)getActivity()).preguntasImagen.get(((Jugar)getActivity()).preguntaImagenId).getOpcion3());
        r4.setImageResource(((Jugar)getActivity()).preguntasImagen.get(((Jugar)getActivity()).preguntaImagenId).getOpcion4());
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
        View view = inflater.inflate(R.layout.fragment_fragmento_pregunta_imagen, container, false);

        //Buscamos los controles cuyo contenido querremos modificar
        enunciado = view.findViewById(R.id.enunciado3);
        r1 = view.findViewById(R.id.respuesta1);
        r2 = view.findViewById(R.id.respuesta2);
        r3 = view.findViewById(R.id.respuesta3);
        r4 = view.findViewById(R.id.respuesta4);

        //Inicializar
        actividadJugar = (Jugar)getActivity();

        //Establecer color de la letra, enfuncion del tema (claro u oscuro)
        if(Ajustes.fondoOscuro) {
            enunciado.setTextColor(0xFFFFFFFF);
        }else{
            enunciado.setTextColor(0xFF000000);
        }

        //Establecer la funcionalidad de los botones
        r1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                r1.setColorFilter(Color.argb(35, 0, 142, 255)); // Selected tint
                r2.setColorFilter(Color.argb(0, 0, 142, 255));
                r3.setColorFilter(Color.argb(0, 0, 142, 255));
                r4.setColorFilter(Color.argb(0, 0, 142, 255));
                ReproducirSonido();
                actividadJugar.elegida = 1;
            }
        });
        r2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                r1.setColorFilter(Color.argb(0, 0, 142, 255));
                r2.setColorFilter(Color.argb(35, 0, 142, 255));
                r3.setColorFilter(Color.argb(0, 0, 142, 255));
                r4.setColorFilter(Color.argb(0, 0, 142, 255));
                ReproducirSonido();
                actividadJugar.elegida = 2;
            }

        });
        r3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                r1.setColorFilter(Color.argb(0, 0, 142, 255));
                r2.setColorFilter(Color.argb(0, 0, 142, 255));
                r3.setColorFilter(Color.argb(35, 0, 142, 255));
                r4.setColorFilter(Color.argb(0, 0, 142, 255));
                ReproducirSonido();
                actividadJugar.elegida = 3;
            }

        });
        r4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                r1.setColorFilter(Color.argb(0, 0, 142, 255));
                r2.setColorFilter(Color.argb(0, 0, 142, 255));
                r3.setColorFilter(Color.argb(0, 0, 142, 255));
                r4.setColorFilter(Color.argb(35, 0, 142, 255));
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