package com.example.loltrivial;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface PreguntaDao {

    @Query("SELECT * from Tabla_preguntas")
    LiveData<List<PreguntaEntidad>> getPreguntas();

    @Query("DELETE FROM Tabla_preguntas")
    void deleteAll();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(PreguntaEntidad preguntaEntidad);

}
