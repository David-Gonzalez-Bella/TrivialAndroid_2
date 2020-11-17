package com.example.loltrivial;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class PreguntaViewModel extends AndroidViewModel {
    private PreguntasRepositorio mRepositorio;
    private LiveData<List<PreguntaEntidad>> mPreguntas;

    public PreguntaViewModel (Application application) {
        super(application);
        mRepositorio = new PreguntasRepositorio(application);
        mPreguntas = mRepositorio.getPreguntas();
    }

    public void deleteAll(){
        mRepositorio.deleteAll();
    }

    LiveData<List<PreguntaEntidad>> getPreguntas() { return mPreguntas; }

    //public void insert(PreguntaEntidad preguntaEntidad) { mRepositorio.insert(preguntaEntidad); }
}
