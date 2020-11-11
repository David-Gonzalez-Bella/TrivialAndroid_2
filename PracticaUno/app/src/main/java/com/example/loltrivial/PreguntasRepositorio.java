package com.example.loltrivial;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

public class PreguntasRepositorio{
    private PreguntaDao mPreguntaDao;
    private LiveData<List<PreguntaEntidad>> mPreguntas;

    PreguntasRepositorio (Application application) {
        PreguntasRoomBBDD db = PreguntasRoomBBDD.getInstanciaCompartida(application);
        mPreguntaDao = db.preguntaDao();
        mPreguntas = mPreguntaDao.getPreguntas();
    }

    LiveData<List<PreguntaEntidad>> getPreguntas() { return mPreguntas; }

//    public void insert (PreguntaEntidad preguntaEntidad) {
//        new insertAsyncTask(mPreguntaDao).execute(preguntaEntidad);
//    }
}
