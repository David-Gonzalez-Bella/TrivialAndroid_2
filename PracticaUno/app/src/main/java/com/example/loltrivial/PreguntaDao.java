package com.example.loltrivial;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface PreguntaDao<T> {

    @Query("SELECT * from Tabla_Preguntas")
    LiveData<List<T>> getPreguntas();

    @Query("DELETE FROM Tabla_Preguntas")
    void deleteAll();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(T preguntaEntidad);

}
