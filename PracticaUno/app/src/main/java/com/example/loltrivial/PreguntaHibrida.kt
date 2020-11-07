package com.example.loltrivial

data class PreguntaHibrida (
    val id: Int,
    val pregunta: String,
    val imagen: Int,
    val opcion1: String,
    val opcion2: String,
    val opcion3: String,
    val opcion4: String,
    val correcta: Int
)