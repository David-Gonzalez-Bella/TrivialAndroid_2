package com.example.loltrivial;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "Tabla_Preguntas")
public class PreguntaEntidad {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private int id;

    @ColumnInfo(name = "pregunta")
    private String pregunta;

    @ColumnInfo(name = "opcion1")
    private String opcion1;

    @ColumnInfo(name = "opcion2")
    private String opcion2;

    @ColumnInfo(name = "opcion3")
    private String opcion3;

    @ColumnInfo(name = "opcion4")
    private String opcion4;

    @ColumnInfo(name = "correcta")
    private int correcta;

    //Constructor (el id no es necesario porque es autogenerado)
    public PreguntaEntidad(String pregunta, String opcion1, String opcion2, String opcion3, String opcion4, int correcta) {
        this.pregunta = pregunta;
        this.opcion1 = opcion1;
        this.opcion2 = opcion2;
        this.opcion3 = opcion3;
        this.opcion4 = opcion4;
        this.correcta = correcta;
    }

    //Constructor por defecto
    public PreguntaEntidad() {
        this.id = id;
        this.pregunta = pregunta;
        this.opcion1 = opcion1;
        this.opcion2 = opcion2;
        this.opcion3 = opcion3;
        this.opcion4 = opcion4;
        this.correcta = correcta;
    }


    //Getters y setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPregunta() {
        return pregunta;
    }

    public void setPregunta(String pregunta) {
        this.pregunta = pregunta;
    }

    public String getOpcion1() {
        return opcion1;
    }

    public void setOpcion1(String opcion1) {
        this.opcion1 = opcion1;
    }

    public String getOpcion2() {
        return opcion2;
    }

    public void setOpcion2(String opcion2) {
        this.opcion2 = opcion2;
    }

    public String getOpcion3() {
        return opcion3;
    }

    public void setOpcion3(String opcion3) {
        this.opcion3 = opcion3;
    }

    public String getOpcion4() {
        return opcion4;
    }

    public void setOpcion4(String opcion4) {
        this.opcion4 = opcion4;
    }

    public int getCorrecta() {
        return correcta;
    }

    public void setCorrecta(int correcta) {
        this.correcta = correcta;
    }
}
