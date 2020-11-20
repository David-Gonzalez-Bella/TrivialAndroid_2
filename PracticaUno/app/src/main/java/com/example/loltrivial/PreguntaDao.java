package com.example.loltrivial;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface PreguntaDao{

    @Query("SELECT * from Tabla_preguntas")
    LiveData<List<PreguntaEntidad>> getPreguntas();

    @Query("SELECT * from Tabla_preguntas WHERE dificultad = 'Facil' ")
    LiveData<List<PreguntaEntidad>> getPreguntasFaciles();

    @Query("SELECT * from Tabla_preguntas WHERE dificultad = 'Normal' ")
    LiveData<List<PreguntaEntidad>> getPreguntasNormales();

    @Query("SELECT * from Tabla_preguntas WHERE dificultad = 'Dificil' ")
    LiveData<List<PreguntaEntidad>> getPreguntasDificiles();

    @Query("DELETE from Tabla_preguntas")
    void deleteAll();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(PreguntaEntidad preguntaEntidad);

}
