package com.example.loltrivial;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class PreguntaViewModel extends AndroidViewModel {
    private PreguntasRepositorio mRepositorio;
    private LiveData<List<PreguntaEntidad>> mPreguntas;
    private LiveData<List<PreguntaEntidad>> mPreguntasF;
    private LiveData<List<PreguntaEntidad>> mPreguntasN;
    private LiveData<List<PreguntaEntidad>> mPreguntasD;

    public PreguntaViewModel (Application application) {
        super(application);
        mRepositorio = new PreguntasRepositorio(application);
        mPreguntas = mRepositorio.getPreguntas();
        mPreguntasF = mRepositorio.getPreguntasFaciles();
        mPreguntasN = mRepositorio.getPreguntasNormales();
        mPreguntasD = mRepositorio.getPreguntasDificiles();
    }

    LiveData<List<PreguntaEntidad>> getPreguntas() { return mPreguntas; }
    LiveData<List<PreguntaEntidad>> getPreguntasFaciles() { return mPreguntasF; }
    LiveData<List<PreguntaEntidad>> getPreguntasNormales() { return mPreguntasN; }
    LiveData<List<PreguntaEntidad>> getPreguntasDificiles() { return mPreguntasD; }
}
