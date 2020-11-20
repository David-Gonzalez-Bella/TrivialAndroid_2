package com.example.loltrivial;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

public class PreguntasRepositorio{
    private PreguntaDao mPreguntaDao;
    private LiveData<List<PreguntaEntidad>> mPreguntas;
    private LiveData<List<PreguntaEntidad>> mPreguntasF;
    private LiveData<List<PreguntaEntidad>> mPreguntasN;
    private LiveData<List<PreguntaEntidad>> mPreguntasD;

    PreguntasRepositorio (Application application) {
        PreguntasRoomBBDD db = PreguntasRoomBBDD.getInstanciaCompartida(application);
        mPreguntaDao = db.preguntaDao();
        mPreguntas = mPreguntaDao.getPreguntas();
        mPreguntasF = mPreguntaDao.getPreguntasFaciles();
        mPreguntasN = mPreguntaDao.getPreguntasNormales();
        mPreguntasD = mPreguntaDao.getPreguntasDificiles();
    }

    LiveData<List<PreguntaEntidad>> getPreguntas() { return mPreguntas; }
    LiveData<List<PreguntaEntidad>> getPreguntasFaciles() { return mPreguntasF; }
    LiveData<List<PreguntaEntidad>> getPreguntasNormales() { return mPreguntasN; }
    LiveData<List<PreguntaEntidad>> getPreguntasDificiles() { return mPreguntasD; }

}
