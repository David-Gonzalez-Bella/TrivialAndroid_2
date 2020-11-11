package com.example.loltrivial;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface PreguntaDao {

    @Query("SELECT * from Tabla_Preguntas")
    LiveData<List<PreguntaEntidad>> getPreguntas();

    @Query("DELETE FROM Tabla_Preguntas")
    void deleteAll();

    @Insert
    void insert(PreguntaEntidad preguntaEntidad);

}
