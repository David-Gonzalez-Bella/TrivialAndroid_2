package com.example.loltrivial

data class PreguntaImagen (
    val id: Int,
    val pregunta: String,
    val opcion1: Int,
    val opcion2: Int,
    val opcion3: Int,
    val opcion4: Int,
    val correcta: Int
)